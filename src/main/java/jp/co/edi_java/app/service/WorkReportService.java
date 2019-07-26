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
import jp.co.edi_java.app.dao.TWorkReportDao;
import jp.co.edi_java.app.dao.TWorkReportItemDao;
import jp.co.edi_java.app.dao.gyousya.MGyousyaDao;
import jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDao;
import jp.co.edi_java.app.dao.syain.MSyainDao;
import jp.co.edi_java.app.dto.ApprovalDto;
import jp.co.edi_java.app.dto.WorkReportDto;
import jp.co.edi_java.app.entity.MEigyousyoEntity;
import jp.co.edi_java.app.entity.MKoujiEntity;
import jp.co.edi_java.app.entity.TWorkReportEntity;
import jp.co.edi_java.app.entity.TWorkReportItemEntity;
import jp.co.edi_java.app.entity.gyousya.MGyousyaEntity;
import jp.co.edi_java.app.entity.syain.MSyainEntity;
import jp.co.edi_java.app.form.WorkReportForm;
import jp.co.edi_java.app.util.crypto.CipherUtils;
import jp.co.edi_java.app.util.file.FileApi;
import jp.co.keepalive.springbootfw.util.consts.CommonConsts;
import jp.co.keepalive.springbootfw.util.dxo.BeanUtils;

@Service
@Scope("request")
public class WorkReportService {

	@Autowired
    public MailService mailService;

	@Autowired
    public TWorkReportDao tWorkReportDao;

	@Autowired
    public TWorkReportItemDao tWorkReportItemDao;

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

	private WorkReportService(@Value("${stg.flg}") String flg) {
		STG_FLG = flg;
	}

	public TWorkReportEntity get(String workReportNumber) {
		return tWorkReportDao.select(workReportNumber);
	}

	public TWorkReportEntity getByOrderNumber(String orderNumber, int workReportCount) {
		return tWorkReportDao.selectByOrderNumber(orderNumber, workReportCount);
	}

	public List<TWorkReportItemEntity> getItemList(String workReportNumber) {
		return tWorkReportItemDao.selectAll(workReportNumber);
	}

	public List<WorkReportDto> getList(String orderNumber, String remandFlg) {
		List<WorkReportDto> dtoList = new ArrayList<>();
		List<TWorkReportEntity> list = tWorkReportDao.selectAll(orderNumber, remandFlg);
		for (TWorkReportEntity workReport : list) {
			WorkReportDto dto = new WorkReportDto();
			List<TWorkReportItemEntity> itemList = getItemList(workReport.getWorkReportNumber());
			BeanUtils.copyBeanToBean(workReport, dto);
			dto.setItemList(itemList);
			dtoList.add(dto);
		}
		return dtoList;
	}

	//出来高書リマインド対象のリスト取得
	public List<TWorkReportEntity> selectRemindList() {
		return tWorkReportDao.selectUnconfirmList();
	}

	public String regist(WorkReportForm form) {
		//出来高報告情報登録
		String workReportNumber = registWorkReport(form);
		//出来高報告明細登録
		registWorkReportItem(workReportNumber, form.itemList, form.userId);
		return workReportNumber;
	}

	//出来高報告情報登録
	private String registWorkReport(WorkReportForm form) {
		String workReportNumber = "R_" + System.currentTimeMillis();
		TWorkReportEntity entity = new TWorkReportEntity();
		BeanUtils.copyBeanToBean(form, entity);
		entity.setWorkReportNumber(workReportNumber);
		entity.setInsertUser(form.getUserId());
		entity.setUpdateUser(form.getUserId());
		tWorkReportDao.insert(entity);
		return workReportNumber;
	}

	//出来高報告明細登録
	private void registWorkReportItem(String workReportNumber, List<TWorkReportItemEntity> itemList, String userId) {
		int count = 1;
		for (TWorkReportItemEntity item : itemList) {
			TWorkReportItemEntity entity = new TWorkReportItemEntity();
			BeanUtils.copyBeanToBean(item, entity);
			entity.setWorkReportNumber(workReportNumber);
			entity.setItemNumber(count);
			entity.setInsertUser(userId);
			entity.setUpdateUser(userId);
			tWorkReportItemDao.insert(entity);
			count++;
		}
	}

	public int update(WorkReportForm form) {
		TWorkReportEntity entity = this.get(form.workReportNumber);
//		BeanUtils.copyBeanToBean(form, entity);
		entity.setUserBikou(form.getUserBikou());
		entity.setPartnerBikou(form.getPartnerBikou());
		entity.setFileId(form.getFileId());
		entity.setUpdateUser(form.getUserId());
		return tWorkReportDao.update(entity);
	}

	public int delete(WorkReportForm form) {
		TWorkReportEntity entity = this.get(form.workReportNumber);
		return tWorkReportDao.delete(entity);
	}

	public void approval(WorkReportForm form) {
		List<ApprovalDto> approvalList = form.getWorkReportApprovalList();
		if(approvalList != null) {
			for (ApprovalDto dto : approvalList) {
				TWorkReportEntity entity = this.get(dto.getWorkNumber());
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
					tWorkReportDao.update(entity);
				}
				//差し戻しの場合
				else if (judgmentKbn.equals(CommonConsts.JUDGMENT_KBN_REMAND)) {

					entity.setRemandFlg(CommonConsts.REMAND_FLG_ON);
					entity.setRemandDate(timestamp);
					entity.setUserBikou(dto.getUserBikou());
					entity.setUpdateUser(form.getUserId());
					tWorkReportDao.update(entity);

					//差し戻し用のメール送信
					TWorkReportEntity workReport = this.get(dto.getWorkNumber());
					String gyousyaCode = workReport.getGyousyaCode();
					List<String> addressList = tGyousyaMailaddressDao.selectList(gyousyaCode);
					MGyousyaEntity gyousya = mGyousyaDao.select(gyousyaCode);
					mailService.sendMailWorkReportRemand(addressList, gyousya, workReport);
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

	//出来高報告書Noのデコード
	public String decodeWorkReportNumber(WorkReportForm form) {
		String ret = "";
		if (Objects.nonNull(form) && Objects.nonNull(form.getEncryptWorkReportNumber())) {
			ret = CipherUtils.getDecryptAES(form.getEncryptWorkReportNumber());
		}
		return ret;
	}

	//出来高報告書の受入確認メール再送
	public void remindList(List<TWorkReportEntity> workReportList) {
		if (Objects.nonNull(workReportList)) {
			WorkReportForm form = new WorkReportForm();
			for (TWorkReportEntity workReport : workReportList) {
				form.setWorkReportNumber(workReport.getWorkReportNumber());
				sendMailWorkReport(form, true);
			}
		}
	}

	//出来高報告書登録時にメールを送信
	public void sendMailWorkReport(WorkReportForm form, Boolean remind) {
		String workReportNumber = form.getWorkReportNumber();

		//出来高情報取得
		TWorkReportEntity workReport = get(workReportNumber);
		//出来高明細取得
		List<TWorkReportItemEntity> itemList = getItemList(workReportNumber);
		//工事情報取得
		MKoujiEntity kouji = mKoujiDao.select(workReport.getKoujiCode());
		//支店情報取得
		MEigyousyoEntity eigyousyo = mEigyousyoDao.select(kouji.getEigyousyoCode());
		//社員情報取得
		MSyainEntity syain = mSyainDao.select(kouji.getTantouSyainCode());
		//業者情報取得
		MGyousyaEntity gyousya = mGyousyaDao.select(workReport.getGyousyaCode());

		//CC
		String cc = null;
		if(!STG_FLG.equals(STG_FLG_ON)) {
			cc = "jimu-" + eigyousyo.getEigyousyoCode() + "@tamahome.jp";
		}else {
			cc = MailService.STG_CC_MAIL;
		}
		//添付ファイル
		String fileName = workReport.getFileId() + ".pdf";
		String filePath = FileApi.getFile(workReport.getKoujiCode(), FileApi.TOSHO_CODE_EDI, FileApi.FILE_CODE_FORM, FileApi.FILE_NO_WORK_REPORT, workReport.getFileId(), "pdf", fileName);
		//ファイル名をわかりやすい名前に変更して添付
		fileName = "出来高報告書.pdf";
		List<Map<String,String>> fileList = new ArrayList<Map<String,String>>();
		if (Objects.nonNull(filePath)) {
			Map<String,String> fileMap = new HashMap<String,String>();
			fileMap.put("filePath", filePath);
			fileMap.put("fileName", fileName);
			fileList.add(fileMap);
		}
		//メール送信
		mailService.sendMailWorkReport(syain.getSyainMail(), cc, eigyousyo.getEigyousyoName(), syain.getSyainName(), kouji.getKoujiName(), gyousya.getGyousyaName(), workReport.getOrderNumber(), workReport.getWorkRate(), fileList, workReportNumber, itemList, remind);
	}

}
