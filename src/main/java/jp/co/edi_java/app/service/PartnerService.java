package jp.co.edi_java.app.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dao.gyousya.MGyousyaDao;
import jp.co.edi_java.app.dao.gyousya.TGyousyaAccountDao;
import jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDao;
import jp.co.edi_java.app.dao.gyousya.TGyousyaProcessLogDao;
import jp.co.edi_java.app.dao.syain.MSyainDao;
import jp.co.edi_java.app.dto.GyousyaAccountDto;
import jp.co.edi_java.app.dto.GyousyaDto;
import jp.co.edi_java.app.entity.gyousya.MGyousyaEntity;
import jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity;
import jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity;
import jp.co.edi_java.app.entity.syain.MSyainEntity;
import jp.co.edi_java.app.form.PartnerForm;
import jp.co.keepalive.springbootfw.util.dxo.BeanUtils;
import jp.co.keepalive.springbootfw.util.lang.StringUtils;

@Service
@Scope("request")
public class PartnerService {

	private static int TOKEN_LENGTH = 16;//16*2=32バイト

	public String AUTH_FLG_OFF = "0";
	public String AUTH_FLG_ON = "1";

	@Autowired
    public MailService mailService;

	@Autowired
    public MGyousyaDao mGyousyaDao;

	@Autowired
    public MSyainDao mSyainDao;

	@Autowired
    public TGyousyaAccountDao tGyousyaAccountDao;

	@Autowired
    public TGyousyaMailaddressDao tGyousyaMailaddressDao;

	@Autowired
    public TGyousyaProcessLogDao mGyousyaProcessLogDao;

	public MGyousyaEntity select(String gyousyaCode) {
		return mGyousyaDao.select(gyousyaCode);
	}

	public TGyousyaAccountEntity selectAccount(String gyousyaCode) {
		return tGyousyaAccountDao.select(gyousyaCode);
	}

	public TGyousyaAccountEntity selectAccountByToken(String token) {
		return tGyousyaAccountDao.selectByToken(token);
	}

	public List<TGyousyaMailaddressEntity> selectMailaddressAll(String gyousyaCode) {
		return tGyousyaMailaddressDao.selectAll(gyousyaCode);
	}

	public List<String> selectMailaddressList(String gyousyaCode) {
		return tGyousyaMailaddressDao.selectList(gyousyaCode);
	}

	public TGyousyaMailaddressEntity selectByMailaddress(String mailaddress) {
		return tGyousyaMailaddressDao.select(mailaddress);
	}

	public void regist(PartnerForm form) {
		String userId = form.getUserId();

		//アカウント登録処理
		TGyousyaAccountEntity entity = new TGyousyaAccountEntity();
		String token = createToken();
		BeanUtils.copyBeanToBean(form, entity);
		entity.setToken(token);
		entity.setInsertUser(userId);
		entity.setUpdateUser(userId);
		tGyousyaAccountDao.insert(entity);

		//メールアドレス登録
		TGyousyaMailaddressEntity mailEntity = new TGyousyaMailaddressEntity();
		BeanUtils.copyBeanToBean(form, mailEntity);
		mailEntity.setSerialNumber(1);
		mailEntity.setInsertUser(userId);
		mailEntity.setUpdateUser(userId);
		tGyousyaMailaddressDao.insert(mailEntity);

		//メール送信
		String gyousyaCode = form.getGyousyaCode();
		List<String> addressList = selectMailaddressList(gyousyaCode);
		MGyousyaEntity gyousya = select(gyousyaCode);
		TGyousyaAccountEntity gyousyaAccount = selectAccount(gyousyaCode);
		MSyainEntity syain = mSyainDao.select(form.getSyainCode());
		mailService.sendMailPartnerRegist(addressList, syain.getSyainMail(), gyousya.getGyousyaName(), gyousyaAccount.getToken());

	}

	//トークン発行
	private String createToken() {
		byte token[] = new byte[TOKEN_LENGTH];
		StringBuffer buf = new StringBuffer();
		SecureRandom random = null;

		try {
			random = SecureRandom.getInstance("SHA1PRNG");
			random.nextBytes(token);

			for (int i = 0; i < token.length; i++) {
				buf.append(String.format("%02x", token[i]));
			}

			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}

		return buf.toString();
	}

	public void update(PartnerForm form) {
		//業者メールアドレス削除
		deletePartnerEmail(form.getGyousyaCode(), form.getUserId());
		//業者メールアドレス登録
		updatePartnerEmail(form.getGyousyaCode(), form.getMailaddressList(), form.getUserId());
	}

	//使わない
//	private void updatePartner(PartnerForm form) {
//		MGyousyaEntity entity = this.select(form.getGyousyaCode());
//		BeanUtils.copyBeanToBean(form, entity);
//		gyousyaDao.update(entity);
//	}

	private void updatePartnerEmail(String gyousyaCode, List<TGyousyaMailaddressEntity> list, String userId) {
		int count = 1;
		for (TGyousyaMailaddressEntity email : list) {
			if(!StringUtils.isNullString(email.getMailaddress()) && !chkMail(email.getMailaddress(), gyousyaCode)) {
				TGyousyaMailaddressEntity entity = new TGyousyaMailaddressEntity();
				entity.setGyousyaCode(gyousyaCode);
				entity.setMailaddress(email.getMailaddress());
				entity.setSerialNumber(count);
				entity.setSendFlg(email.getSendFlg());
				entity.setInsertUser(userId);
				entity.setUpdateUser(userId);
				tGyousyaMailaddressDao.insert(entity);
				count++;
			}
		}
	}

	private void deletePartnerEmail(String gyousyaCode, String userId) {
		List<TGyousyaMailaddressEntity> list = this.selectMailaddressAll(gyousyaCode);
		for (TGyousyaMailaddressEntity entity : list) {
			entity.setDeleteUser(userId);
			tGyousyaMailaddressDao.delete(entity);
		}
	}

	public int setPassword(PartnerForm form) {
		TGyousyaAccountEntity entity = tGyousyaAccountDao.select(form.getGyousyaCode());
		//パスワードの設定の際にトークン削除と認証フラグを立てる
		entity.setPassword(form.getPassword());
		entity.setToken("");
		entity.setAuthFlg(AUTH_FLG_ON);
		entity.setUpdateUser(form.getUserId());
		return tGyousyaAccountDao.update(entity);
	}

	public TGyousyaAccountEntity passwordForget(PartnerForm form) {
		String gyousyaCode = form.getGyousyaCode();
		String mailaddress = form.getMailaddress();
		//メールアドレスから業者を選択
		TGyousyaMailaddressEntity mailEntity = tGyousyaMailaddressDao.selectPasswordForget(gyousyaCode, mailaddress);
		if(mailEntity == null) {
			return null;
		}

		//トークンの再発行
		TGyousyaAccountEntity entity = tGyousyaAccountDao.select(gyousyaCode);
		String token = createToken();
		entity.setToken(token);
		entity.setUpdateUser(form.getUserId());
		tGyousyaAccountDao.update(entity);

		//メール送信
		MGyousyaEntity gyousya = select(gyousyaCode);
		TGyousyaAccountEntity gyousyaAccount = selectAccount(gyousyaCode);
		mailService.sendMailForgetPassword(mailaddress, gyousya.getGyousyaName(), gyousyaAccount.getToken());
		return gyousyaAccount;
	}

	public List<GyousyaDto> getList(String eigyousyoCode, String torihikiStatus) {
		return mGyousyaDao.selectListByEigyousyoCode(eigyousyoCode, torihikiStatus);
	}

	public List<MGyousyaEntity> getAll() {
		return mGyousyaDao.selectAll();
	}

	public List<MGyousyaEntity> searchEstimate(PartnerForm form) {
		return mGyousyaDao.selectListBySearch(form.getGyousyaCode(), form.getEigyousyoCode());
	}

	public void remind(String gyousyaCode, String syainCode) {
		//トークンの再発行と認証フラグの更新
		TGyousyaAccountEntity entity = selectAccount(gyousyaCode);
		String token = createToken();
		entity.setToken(token);
		entity.setAuthFlg(AUTH_FLG_OFF);
		tGyousyaAccountDao.update(entity);

		//メール送信
		List<String> addressList = selectMailaddressList(gyousyaCode);
		MGyousyaEntity gyousya = select(gyousyaCode);
		TGyousyaAccountEntity gyousyaAccount = selectAccount(gyousyaCode);
		MSyainEntity syain = mSyainDao.select(syainCode);
		mailService.sendMailPartnerRegistRemind(addressList, syain.getSyainMail(), gyousya.getGyousyaName(), gyousyaAccount.getToken());
	}

	public boolean chkMail(String mailaddress, String gyousyaCode) {
		if(StringUtils.isNullString(mailaddress)) {
			return false;
		}
		int count = tGyousyaMailaddressDao.countByMailaddress(mailaddress, gyousyaCode);
		if(count > 0) {
			return true;
		}else {
			return false;
		}
	}

	public int countAccount(PartnerForm form) {
		return mGyousyaDao.countByAccount(form.getGyousyaName(), form.getSaimokuKousyuCode(), form.getRegistKbn());
	}

	public List<GyousyaAccountDto> searchAccount(PartnerForm form) {
		int offset = (form.getPage() - 1) * form.getCount();
		return mGyousyaDao.selectListByAccount(form.getGyousyaName(), form.getSaimokuKousyuCode(), form.getRegistKbn(), form.getCount(), offset);
	}
}
