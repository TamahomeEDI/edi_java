package jp.co.edi_java.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dao.FileEstimateRelDao;
import jp.co.edi_java.app.dao.TEstimateDao;
import jp.co.edi_java.app.dao.TEstimateRequestDao;
import jp.co.edi_java.app.dao.syain.MSyainDao;
import jp.co.edi_java.app.dto.EstimateDto;
import jp.co.edi_java.app.dto.MailEstimateRegistDto;
import jp.co.edi_java.app.entity.FileEstimateRelEntity;
import jp.co.edi_java.app.entity.TEstimateEntity;
import jp.co.edi_java.app.entity.TEstimateRequestEntity;
import jp.co.edi_java.app.entity.syain.MSyainEntity;
import jp.co.edi_java.app.form.EstimateForm;
import jp.co.edi_java.app.util.file.FileApi;
import jp.co.keepalive.springbootfw.util.dxo.BeanUtils;

@Service
@Scope("request")
public class EstimateService {

	public String UNREAD_FLG_OFF = "0";

	@Autowired
    public MailService mailService;

	@Autowired
    public TEstimateRequestDao tEstimateRequestDao;

	@Autowired
    public TEstimateDao tEstimateDao;

	@Autowired
    public FileEstimateRelDao fileEstimateRelDao;

	@Autowired
    public MSyainDao mSyainDao;

	private static String STG_FLG;
	private static String STG_FLG_ON = "1";

	private EstimateService(@Value("${stg.flg}") String flg) {
		STG_FLG = flg;
	}

	public TEstimateEntity select(String estimateNumber) {
		return tEstimateDao.select(estimateNumber);
	}

	public List<EstimateDto> selectListByEstimateRequestNumber(String estimateRequestNumber) {
		return tEstimateDao.selectListByEstimateRequestNumber(estimateRequestNumber);
	}

	public List<Map<String, Object>> getFileIdList(String estimateNumber) {
		List<FileEstimateRelEntity> fileRelList = fileEstimateRelDao.selectList(estimateNumber);
		List<Map<String, Object>> ret = new ArrayList<>();
		for (FileEstimateRelEntity fileEstimateRequestRelEntity : fileRelList) {
			Map<String, Object> fileInfo = FileApi.getFileInfo(fileEstimateRequestRelEntity.getFileId());
			ret.add(fileInfo);
		}
		return ret;
	}

	public String regist(EstimateForm form) {
		//見積登録
		String estimateNumber = registEstimate(form);
		List<String> fileIdList = form.fileIdList;
		if(fileIdList != null) {
			//見積添付ファイル登録
			registEstimateFile(estimateNumber, fileIdList, form.userId);
		}

		//メール送信
		TEstimateRequestEntity estimateRequest = tEstimateRequestDao.select(form.getEstimateRequestNumber());
		MSyainEntity syain = mSyainDao.select(estimateRequest.getRegistSyainCode());
		MailEstimateRegistDto dto = tEstimateDao.selectMailDto(estimateNumber);
		String cc = null;
		if(!STG_FLG.equals(STG_FLG_ON)) {
			cc = "jimu-" + dto.getEigyousyoCode() + "@tamahome.jp";
		}else {
			cc = MailService.STG_CC_MAIL;
		}
		mailService.sendMailEstimateRegist(syain.getSyainMail(), cc, dto);
		return estimateNumber;
	}

	//見積登録
	private String registEstimate(EstimateForm form) {
		String estimateNumber = "E_" + System.currentTimeMillis();
		TEstimateEntity entity = new TEstimateEntity();
		BeanUtils.copyBeanToBean(form, entity);
		entity.setEstimateNumber(estimateNumber);
		entity.setInsertUser(form.getUserId());
		entity.setUpdateUser(form.getUserId());
		tEstimateDao.insert(entity);
		return estimateNumber;
	}

	//見積添付ファイル登録
	private void registEstimateFile(String estimateNumber, List<String> fileIdList, String userId) {
		for (String fileId : fileIdList) {
			FileEstimateRelEntity entity = new FileEstimateRelEntity();
			entity.setEstimateNumber(estimateNumber);
			entity.setFileId(fileId);
			entity.setInsertUser(userId);
			entity.setUpdateUser(userId);
			fileEstimateRelDao.insert(entity);
		}
	}

	public int update(EstimateForm form) {
		String estimateNumber = form.estimateNumber;
		TEstimateEntity entity = this.select(estimateNumber);

		List<String> fileIdList = form.fileIdList;
		if(fileIdList != null) {
			//見積添付ファイル登録
			registEstimateFile(estimateNumber, fileIdList, form.userId);
		}

		entity.setUserBikou(form.getUserBikou());
		entity.setPartnerBikou(form.getPartnerBikou());
		entity.setUpdateUser(form.getUserId());
		return tEstimateDao.update(entity);
	}

	public int delete(EstimateForm form) {
		TEstimateEntity entity = this.select(form.estimateNumber);
		return tEstimateDao.delete(entity);
	}

	public int read(EstimateForm form) {
		TEstimateEntity entity = this.select(form.estimateNumber);
		entity.setUnreadFlg(UNREAD_FLG_OFF);
		entity.setUpdateUser(form.getUserId());
		return tEstimateDao.update(entity);
	}

}
