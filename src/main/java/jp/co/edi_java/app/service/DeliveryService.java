package jp.co.edi_java.app.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dao.MEigyousyoDao;
import jp.co.edi_java.app.dao.MKoujiDao;
import jp.co.edi_java.app.dao.TDeliveryDao;
import jp.co.edi_java.app.dao.TDeliveryItemDao;
import jp.co.edi_java.app.dao.gyousya.MGyousyaDao;
import jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDao;
import jp.co.edi_java.app.dao.syain.MSyainDao;
import jp.co.edi_java.app.dto.ApprovalDto;
import jp.co.edi_java.app.dto.DeliveryDto;
import jp.co.edi_java.app.entity.MEigyousyoEntity;
import jp.co.edi_java.app.entity.MKoujiEntity;
import jp.co.edi_java.app.entity.TDeliveryEntity;
import jp.co.edi_java.app.entity.TDeliveryItemEntity;
import jp.co.edi_java.app.entity.gyousya.MGyousyaEntity;
import jp.co.edi_java.app.entity.syain.MSyainEntity;
import jp.co.edi_java.app.form.DeliveryForm;
import jp.co.edi_java.app.util.crypto.CipherUtils;
import jp.co.edi_java.app.util.file.FileApi;
import jp.co.keepalive.springbootfw.util.consts.CommonConsts;
import jp.co.keepalive.springbootfw.util.dxo.BeanUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Scope("request")
public class DeliveryService {

	@Autowired
    public MailService mailService;

	@Autowired
    public TDeliveryDao tDeliveryDao;

	@Autowired
    public TDeliveryItemDao tDeliveryItemDao;

	@Autowired
    public MKoujiDao mKoujiDao;

	@Autowired
    public MEigyousyoDao mEigyousyoDao;

	@Autowired
    public MSyainDao mSyainDao;

	@Autowired
    public MGyousyaDao mGyousyaDao;

	@Autowired
    public TGyousyaMailaddressDao tGyousyaMailaddressDao;

	private static String STG_FLG;
	private static String STG_FLG_ON = "1";
	private static String TEMPORARY_SYAIN_CODE = "990000";

	private DeliveryService(@Value("${stg.flg}") String flg) {
		STG_FLG = flg;
	}

	public TDeliveryEntity get(String deliveryNumber) {
		return tDeliveryDao.select(deliveryNumber);
	}

	public TDeliveryEntity getByOrderNumber(String orderNumber, int deliveryCount) {
		return tDeliveryDao.selectByOrderNumber(orderNumber, deliveryCount);
	}

	public List<TDeliveryItemEntity> getItemList(String deliveryNumber) {
		return tDeliveryItemDao.selectAll(deliveryNumber);
	}

	public List<DeliveryDto> getList(String orderNumber, String remandFlg) {
		List<DeliveryDto> dtoList = new ArrayList<>();
		List<TDeliveryEntity> list = tDeliveryDao.selectAll(orderNumber, remandFlg);
		for (TDeliveryEntity delivery : list) {
			DeliveryDto dto = new DeliveryDto();
			List<TDeliveryItemEntity> itemList = getItemList(delivery.getDeliveryNumber());
			BeanUtils.copyBeanToBean(delivery, dto);
			dto.setItemList(itemList);
			dtoList.add(dto);
		}
		return dtoList;
	}

	//納品書リマインド対象のリスト取得
	public List<TDeliveryEntity> selectRemindList() {
		return tDeliveryDao.selectUnconfirmList();
	}

	public String regist(DeliveryForm form) {
		//納品情報登録
		String deliveryNumber = registDelivery(form);
		//納品明細登録
		registDeliveryItem(deliveryNumber, form.itemList, form.userId);
		return deliveryNumber;
	}

	//納品情報登録
	private String registDelivery(DeliveryForm form) {
		String deliveryNumber = "D_" + System.currentTimeMillis();
		TDeliveryEntity entity = new TDeliveryEntity();
		BeanUtils.copyBeanToBean(form, entity);
		entity.setDeliveryNumber(deliveryNumber);
		entity.setInsertUser(form.getUserId());
		entity.setUpdateUser(form.getUserId());
		tDeliveryDao.insert(entity);
		return deliveryNumber;
	}

	//納品明細登録
	private void registDeliveryItem(String deliveryNumber, List<TDeliveryItemEntity> itemList, String userId) {
		int count = 1;
		for (TDeliveryItemEntity item : itemList) {
			TDeliveryItemEntity entity = new TDeliveryItemEntity();
			BeanUtils.copyBeanToBean(item, entity);
			entity.setDeliveryNumber(deliveryNumber);
			entity.setItemNumber(count);
			entity.setInsertUser(userId);
			entity.setUpdateUser(userId);
			tDeliveryItemDao.insert(entity);
			count++;
		}
	}

	public int update(DeliveryForm form) {
		TDeliveryEntity entity = this.get(form.deliveryNumber);
//		BeanUtils.copyBeanToBean(form, entity);
		entity.setUserBikou(form.getUserBikou());
		entity.setPartnerBikou(form.getPartnerBikou());
		entity.setFileId(form.getFileId());
		entity.setUpdateUser(form.getUserId());
		return tDeliveryDao.update(entity);
	}

	public int delete(DeliveryForm form) {
		TDeliveryEntity entity = this.get(form.deliveryNumber);
		return tDeliveryDao.delete(entity);
	}

	public void approval(DeliveryForm form) {
		List<ApprovalDto> approvalList = form.getDeliveryApprovalList();
		if(approvalList != null) {
			for (ApprovalDto dto : approvalList) {
				TDeliveryEntity entity = this.get(dto.getWorkNumber());
				String userKbn = form.getUserKbn();
				String judgmentKbn = dto.getJudgmentKbn();
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				//承認の場合
				if(judgmentKbn.equals(CommonConsts.JUDGMENT_KBN_APPROVAL)) {
					//工務担当
					if(userKbn.equals(CommonConsts.USER_KBN_STAFF)) {
						entity.setStaffReceiptFlg(CommonConsts.RECEIPT_FLG_ON);
						entity.setStaffReceiptDate(timestamp);
					}
					//工務事務
					else if (userKbn.equals(CommonConsts.USER_KBN_CLERK)) {
						entity.setClerkReceiptFlg(CommonConsts.RECEIPT_FLG_ON);
						entity.setClerkReceiptDate(timestamp);
					}
					//工務課長
					else if (userKbn.equals(CommonConsts.USER_KBN_MANAGER)) {
						entity.setManagerReceiptFlg(CommonConsts.RECEIPT_FLG_ON);
						entity.setManagerReceiptDate(timestamp);
					}
					entity.setUserBikou(dto.getUserBikou());
					entity.setUpdateUser(form.getUserId());
					tDeliveryDao.update(entity);
				}
				//差し戻しの場合
				else if (judgmentKbn.equals(CommonConsts.JUDGMENT_KBN_REMAND)) {

					entity.setRemandFlg(CommonConsts.REMAND_FLG_ON);
					entity.setRemandDate(timestamp);
					entity.setUserBikou(dto.getUserBikou());
					entity.setUpdateUser(form.getUserId());
					tDeliveryDao.update(entity);

					//差し戻し用のメール送信
					TDeliveryEntity delivery = this.get(dto.getWorkNumber());
					String gyousyaCode = delivery.getGyousyaCode();
					List<String> addressList = tGyousyaMailaddressDao.selectList(gyousyaCode);
					MGyousyaEntity gyousya = mGyousyaDao.select(gyousyaCode);
					mailService.sendMailDeliveryRemand(addressList, gyousya, delivery);
				}

				/*
				 * 初期開発ではSAP連携をしない
				//SAP連携（工務課長で承認した場合）
				if(judgmentKbn.equals(CommonConsts.JUDGMENT_KBN_APPROVAL) && userKbn.equals(CommonConsts.USER_KBN_MANAGER)) {

				}
				*/
			}
		}
	}

	//納品書Noのデコード
	public String decodeDeliveryNumber(DeliveryForm form) {
		String ret = "";
		if (Objects.nonNull(form) && Objects.nonNull(form.getEncryptDeliveryNumber())) {
			ret = CipherUtils.getDecryptAES(form.getEncryptDeliveryNumber());
		}
		return ret;
	}

	//納品書の受入確認メール再送
	public void remindList(List<TDeliveryEntity> deliveryList) {
		if (Objects.nonNull(deliveryList)) {
			DeliveryForm form = new DeliveryForm();
			for (TDeliveryEntity delivery : deliveryList) {
				form.setDeliveryNumber(delivery.getDeliveryNumber());
				form.setGyousyaCode(delivery.getGyousyaCode());
				sendMailDelivery(form, true);
			}
		}
	}

	//納品書登録時にメールを送信
	public void sendMailDelivery(DeliveryForm form, Boolean remind) {
		String deliveryNumber = form.getDeliveryNumber();
		String gyousyaCode = form.getGyousyaCode();

		//納品情報取得
		TDeliveryEntity delivery = get(deliveryNumber);
		if (Objects.isNull(delivery)) {
			log.info("delivery nothing: " + deliveryNumber);
			return;
		}
		//納品書明細取得
		List<TDeliveryItemEntity> itemList = getItemList(deliveryNumber);
		//工事情報取得
		MKoujiEntity kouji = mKoujiDao.select(delivery.getKoujiCode());
		//支店情報取得
		MEigyousyoEntity eigyousyo = mEigyousyoDao.select(kouji.getEigyousyoCode());
		//社員情報取得
		MSyainEntity syain = mSyainDao.select(kouji.getTantouSyainCode());
		//業者情報取得
		MGyousyaEntity gyousya = mGyousyaDao.select(gyousyaCode);

		List<String> toList = new ArrayList<String>();
		String eigyousyoCode = eigyousyo.getEigyousyoCode();

		if (Objects.nonNull(syain) && Objects.nonNull(syain.getSyainCode())) {
			//'990000'ユーザは工務長宛にメール
			if (Objects.equals(syain.getSyainCode(),TEMPORARY_SYAIN_CODE) && Objects.nonNull(eigyousyoCode)) {
				List<MSyainEntity> s3SyainList = mSyainDao.selectListBySyokusyu3(eigyousyoCode);
				for (MSyainEntity s3Syain : s3SyainList) {
					if (Objects.nonNull(s3Syain.getSyainMail())) {
						toList.add(s3Syain.getSyainMail());
					}
				}
			} else {
				if (Objects.nonNull(syain.getSyainMail())) {
					toList.add(syain.getSyainMail());
				}
			}
		}
		//to
		String to = "";
		if (!toList.isEmpty()) {
			to = String.join(",", toList);
			//log.info("delivery send mail: " + to);
		}
		//CC
		String cc = null;
		if(!STG_FLG.equals(STG_FLG_ON) && Objects.nonNull(eigyousyoCode)) {
			cc = "jimu-" + eigyousyoCode + "@tamahome.jp";
		}else {
			cc = MailService.STG_CC_MAIL;
		}

		//添付ファイル
		List<Map<String,String>> fileList = new ArrayList<Map<String,String>>();
		if (Objects.nonNull(delivery.getFileId())) {
			String fileName = delivery.getFileId() + ".pdf";
			String filePath = FileApi.getFile(delivery.getKoujiCode(), FileApi.TOSHO_CODE_EDI, FileApi.FILE_CODE_FORM, FileApi.FILE_NO_DELIVERY, delivery.getFileId(), "pdf", fileName);
			//ファイル名をわかりやすい名前に変更して添付
			fileName = "納品書.pdf";

			if (Objects.nonNull(filePath)) {
				Map<String,String> fileMap = new HashMap<String,String>();
				fileMap.put("filePath", filePath);
				fileMap.put("fileName", fileName);
				fileList.add(fileMap);
			}
		}
		//メール送信
		mailService.sendMailDelivery(to, cc, eigyousyo.getEigyousyoName(), syain.getSyainName(), kouji.getKoujiName(), gyousya.getGyousyaName(), delivery.getOrderNumber(), fileList, deliveryNumber, itemList, remind);
	}

}
