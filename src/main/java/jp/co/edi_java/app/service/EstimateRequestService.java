package jp.co.edi_java.app.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dao.FileEstimateRequestRelDao;
import jp.co.edi_java.app.dao.TEstimateRequestDao;
import jp.co.edi_java.app.entity.FileEstimateRequestRelEntity;
import jp.co.edi_java.app.entity.TEstimateRequestEntity;
import jp.co.edi_java.app.form.EstimateRequestForm;
import jp.co.edi_java.app.util.file.FileApi;
import jp.co.keepalive.springbootfw.util.dxo.BeanUtils;

@Service
@Scope("request")
public class EstimateRequestService {

	@Autowired
    public TEstimateRequestDao tEstimateRequestDao;

	@Autowired
    public FileEstimateRequestRelDao fileEstimateRequestRelDao;

	public TEstimateRequestEntity get(String estimateRequestNumber) {
		return tEstimateRequestDao.select(estimateRequestNumber);
	}

	public List<Map<String, Object>> getFileIdList(String estimateRequestNumber) {
		List<FileEstimateRequestRelEntity> fileRelList = fileEstimateRequestRelDao.selectList(estimateRequestNumber);
		List<Map<String, Object>> ret = new ArrayList<>();
		for (FileEstimateRequestRelEntity fileEstimateRequestRelEntity : fileRelList) {
			Map<String, Object> fileInfo = FileApi.getFileInfo(fileEstimateRequestRelEntity.getFileId());
			ret.add(fileInfo);
		}
		return ret;
	}

	public String regist(EstimateRequestForm form) {
		//見積依頼登録
		String estimateRequestNumber = registEstimateRequest(form);
		List<String> fileIdList = form.fileIdList;
		if(fileIdList != null) {
			//見積依頼添付ファイル登録
			registEstimateRequestFile(estimateRequestNumber, fileIdList, form.userId);
		}
		return estimateRequestNumber;
	}

	//見積依頼登録
	private String registEstimateRequest(EstimateRequestForm form) {
		String estimateRequestNumber = createEstimateRequestNumber(form.getEigyousyoCode());
		TEstimateRequestEntity entity = new TEstimateRequestEntity();
		BeanUtils.copyBeanToBean(form, entity);
		entity.setEstimateRequestNumber(estimateRequestNumber);
		entity.setInsertUser(form.getUserId());
		entity.setUpdateUser(form.getUserId());
		tEstimateRequestDao.insert(entity);
		return estimateRequestNumber;
	}

	private String createEstimateRequestNumber(String eigyousyoCode) {
		//先頭「M」
		String ret = "M";
		//支店コードの真ん中3桁取得
		ret = ret + eigyousyoCode.substring(1, 4);
		//カレンダーの初期化
		Calendar cal = Calendar.getInstance();
		//フォーマットの指定
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//日に1日をセット
		cal.set(Calendar.DATE, 1);
		//年の取得
		String year = String.valueOf(cal.get(Calendar.YEAR));
		//月の取得（0埋め）
		String month = String.format("%02d", cal.get(Calendar.MONTH) + 1);
		//年の後ろ2桁と月を設定
		ret = ret + year.substring(2, 4) + month;
		//今月1日を取得
		String from = sdf.format(cal.getTime());
		//月に1を足す
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		//来月1日を取得
		String to = sdf.format(cal.getTime());
		//連番を取得し指定
		ret = ret + (tEstimateRequestDao.count(eigyousyoCode, from, to) + 1);
		return ret;
	}

	//見積依頼添付ファイル登録
	private void registEstimateRequestFile(String estimateRequestNumber, List<String> fileIdList, String userId) {
		for (String fileId : fileIdList) {
			FileEstimateRequestRelEntity entity = new FileEstimateRequestRelEntity();
			entity.setEstimateRequestNumber(estimateRequestNumber);
			entity.setFileId(fileId);
			entity.setInsertUser(userId);
			entity.setUpdateUser(userId);
			fileEstimateRequestRelDao.insert(entity);
		}
	}

	public int update(EstimateRequestForm form) {
		String estimateRequestNumber = form.estimateRequestNumber;
		TEstimateRequestEntity entity = this.get(estimateRequestNumber);

		List<String> fileIdList = form.fileIdList;
		if(fileIdList != null) {
			//見積添付ファイル登録
			registEstimateRequestFile(estimateRequestNumber, fileIdList, form.getUserId());
		}

		entity.setUserBikou(form.getUserBikou());
		entity.setPartnerBikou(form.getPartnerBikou());
		entity.setUpdateUser(form.getUserId());
		return tEstimateRequestDao.update(entity);
	}

	public int delete(EstimateRequestForm form) {
		TEstimateRequestEntity entity = this.get(form.estimateRequestNumber);
		return tEstimateRequestDao.delete(entity);
	}

}
