package jp.co.edi_java.app.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dao.FileOrderRelDao;
import jp.co.edi_java.app.dao.MSaimokuKousyuDao;
import jp.co.edi_java.app.dao.TCloudSignDao;
import jp.co.edi_java.app.dao.TOrderDao;
import jp.co.edi_java.app.dto.SapOrderDetailDto;
import jp.co.edi_java.app.dto.SapOrderDto;
import jp.co.edi_java.app.entity.FileOrderRelEntity;
import jp.co.edi_java.app.entity.MSaimokuKousyuEntity;
import jp.co.edi_java.app.entity.TCloudSignEntity;
import jp.co.edi_java.app.entity.TOrderEntity;
import jp.co.edi_java.app.form.OrderForm;
import jp.co.edi_java.app.util.cloudsign.CloudSignApi;
import jp.co.edi_java.app.util.file.FileApi;
import jp.co.edi_java.app.util.sap.SapApi;
import jp.co.edi_java.app.util.sap.SapApiAnalyzer;
import jp.co.edi_java.app.util.sap.SapApiConsts;
import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;
import jp.co.keepalive.springbootfw.util.consts.ResponseCode;
import jp.co.keepalive.springbootfw.util.dxo.BeanUtils;
import jp.co.keepalive.springbootfw.util.lang.StringUtils;

@Service
@Scope("request")
public class OrderService {

	@Autowired
    public TOrderDao tOrderDao;

	@Autowired
    public FileOrderRelDao fileOrderRelDao;

	@Autowired
    public TCloudSignDao tCloudSignDao;

	@Autowired
	public MSaimokuKousyuDao mSaimokuKousyuDao;

	public static String FILE_FLG_OFF = "0";
	public static String FILE_FLG_ON = "1";
	public static String FILE_FLG_REJECT = "2";

	//未発注の発注日（00000000）
	public static String ORDER_DATE_VALUE_NOT_ORDERING = "00000000";

	//精算完了フラグ（マイナス発注が存在する）
	public static String SETTLEMENT_COMP_FLG_VALUE_X = "X";
	//発注精算フラグ（マイナス発注）
	public static String ORDER_SETTLEMENT_FLG_VALUE_X = "X";

	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	public TOrderEntity selectInfo(String orderNumber) {
		return tOrderDao.select(orderNumber);
	}

	public List<Map<String, Object>> getFileIdList(String orderNumber) {
		List<FileOrderRelEntity> fileRelList = fileOrderRelDao.selectList(orderNumber);
		List<Map<String, Object>> ret = new ArrayList<>();
		for (FileOrderRelEntity fileEstimateRequestRelEntity : fileRelList) {
			Map<String, Object> fileInfo = FileApi.getFileInfo(fileEstimateRequestRelEntity.getFileId());
			ret.add(fileInfo);
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	public SapOrderDto get(String orderNumber) {
		//発注詳細取得
		Map<String, Object> data = SapApi.orderDetail(orderNumber);
		//発注詳細は1件の場合と複数件の場合がある（複数件の場合はマイナス発注有）
		Object sapObj = data.get(SapApiConsts.PARAMS_KEY_T_E_01004);
		Map<String, Object> retDetail = null;
		//オブジェクトがListの場合
		if(sapObj instanceof List){
			List<Map<String, Object>> retDetailList = (List<Map<String, Object>>) sapObj;
			//発注詳細分チェックする
			for (Map<String, Object> detail : retDetailList) {
				//発注精算フラグ取得
				String orderSettlementFlg = detail.get(SapApiConsts.PARAMS_ID_ZHCSSF).toString();
				//マイナス発注じゃなかったら発注詳細とする
				if(!(!StringUtils.isNullString(orderSettlementFlg) && orderSettlementFlg.equals(ORDER_SETTLEMENT_FLG_VALUE_X))) {
					retDetail = detail;
					break;
				}
			}
			//発注詳細が存在しなかったら
			if(retDetail == null) {
				throw new CoreRuntimeException(ResponseCode.ERROR_CODE_1000);
			}
		}
		//List以外の場合
		else {
			retDetail = (Map<String, Object>) sapObj;
		}
//		Map<String, Object> retDetail = SapApiAnalyzer.analyzeResult(data, SapApiConsts.PARAMS_KEY_T_E_01004);
		Map<String, Object> resultInfo = SapApiAnalyzer.analyzeResultInfo(data);
		if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
			throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
		}

		//表示に必要な情報を取得
		TOrderEntity orderInfo = tOrderDao.select(orderNumber);
		List<Map<String, Object>> fileList = getFileIdList(orderNumber);
		String orderDate = retDetail.get(SapApiConsts.PARAMS_ID_ZORDDT).toString();

		SapOrderDto ret = new SapOrderDto();
		ret.setOrderNumber(retDetail.get(SapApiConsts.PARAMS_ID_SEBELN).toString());
		ret.setDeliveryStatus(retDetail.get(SapApiConsts.PARAMS_ID_ZNKFLG).toString());
		ret.setGyousyaCode(retDetail.get(SapApiConsts.PARAMS_ID_ZGYSCD).toString());
		ret.setGyousyaName(retDetail.get(SapApiConsts.PARAMS_ID_ZGYSNM).toString());
		ret.setKoujiCode(retDetail.get(SapApiConsts.PARAMS_ID_ZWRKCD).toString());
		ret.setSapOrderDate(orderDate);
		//SAPで発注済みのものはそれを優先
		if(!StringUtils.isNullString(orderDate) && !orderDate.equals(ORDER_DATE_VALUE_NOT_ORDERING)) {
			ret.setOrderDate(orderDate);
		}
		//EDIで発注済みのもので発注依頼日がnullでない場合はそれを優先
		else if(orderInfo != null && orderInfo.getConfirmationRequestDate() != null) {
			ret.setOrderDate(sdf.format(orderInfo.getConfirmationRequestDate()));
		}
		//上記以外はSAPのデータを設定
		else {
			ret.setOrderDate(orderDate);
		}
		ret.setSaimokuKousyuCode(retDetail.get(SapApiConsts.PARAMS_ID_ZSMKNO).toString());
		ret.setSaimokuKousyuName(retDetail.get(SapApiConsts.PARAMS_ID_ZSMKSY).toString());
		ret.setOrderAmount(Integer.valueOf(retDetail.get(SapApiConsts.PARAMS_ID_ZHTKGK).toString()));
		ret.setOrderAmountTax(Integer.valueOf(retDetail.get(SapApiConsts.PARAMS_ID_ZHTKZG).toString()));
		ret.setDeliveryAmount(Integer.valueOf(retDetail.get(SapApiConsts.PARAMS_ID_ZUKKGK).toString()));
		ret.setDeliveryAmountTax(Integer.valueOf(retDetail.get(SapApiConsts.PARAMS_ID_ZUKKZG).toString()));
		ret.setSettlementCompFlg(retDetail.get(SapApiConsts.PARAMS_ID_ZSKFLG).toString());
		ret.setOrderSettlementFlg(retDetail.get(SapApiConsts.PARAMS_ID_ZHCSSF).toString());
		ArrayList<SapOrderDetailDto> itemList = new ArrayList<SapOrderDetailDto>();

		Object obj = data.get(SapApiConsts.PARAMS_KEY_T_E_01002);
		List<Map<String, Object>> retItemList = new ArrayList<>();
		if(obj instanceof List) {
			retItemList = (List<Map<String, Object>>)obj;
		}else {
			retItemList.add((Map<String, Object>)obj);
		}

		//データの詰め替え＆必要なデータ取得
		for (Map<String, Object> map : retItemList) {
			SapOrderDetailDto dto = new SapOrderDetailDto();
			dto.setName(map.get(SapApiConsts.PARAMS_ID_MAKTX).toString());
			dto.setPrice(map.get(SapApiConsts.PARAMS_ID_ZHTTNK).toString());
			dto.setQuantity(map.get(SapApiConsts.PARAMS_ID_ZMENGE).toString());
			dto.setUnit(map.get(SapApiConsts.PARAMS_ID_ZTANIN).toString());
			dto.setAmount(Integer.valueOf(map.get(SapApiConsts.PARAMS_ID_ZHTKGK).toString()));
			dto.setAmountTax(Integer.valueOf(map.get(SapApiConsts.PARAMS_ID_ZHTKZG).toString()));
			dto.setAmountTaxInclud(Integer.valueOf(map.get(SapApiConsts.PARAMS_ID_ZHTKZTG).toString()));
			dto.setDeliveryQuantity(map.get(SapApiConsts.PARAMS_ID_ZUKEMG).toString());
			dto.setDeliveryAmount(Integer.valueOf(map.get(SapApiConsts.PARAMS_ID_ZUKEKN).toString()));
			dto.setRemainQuantity(map.get(SapApiConsts.PARAMS_ID_ZZANMG).toString());
			dto.setRemainAmount(Integer.valueOf(map.get(SapApiConsts.PARAMS_ID_ZZANKN).toString()));
			itemList.add(dto);
		}
		ret.setItemList(itemList);
		ret.setOrderInfo(orderInfo);
		ret.setFileList(fileList);
		return ret;
	}

	public String update(OrderForm form) {
		String orderNumber = form.getOrderNumber();
		TOrderEntity orderInfo = selectInfo(orderNumber);
		//orderInfoが存在するならupdate
		if(orderInfo != null) {
			BeanUtils.copyBeanToBean(form, orderInfo);
			orderInfo.setUpdateUser(form.getUserId());
			tOrderDao.update(orderInfo);
		}
		//存在しないならinsert
		else {
			TOrderEntity entity = new TOrderEntity();
			BeanUtils.copyBeanToBean(form, entity);
			MSaimokuKousyuEntity saimoku = mSaimokuKousyuDao.select(form.getSaimokuKousyuCode());
			entity.setOrderType(saimoku.getHattyuuSyubetuFlg());
			entity.setInsertUser(form.getUserId());
			entity.setUpdateUser(form.getUserId());
			tOrderDao.insert(entity);
		}
		List<String> fileIdList = form.getFileIdList();
		if(fileIdList != null) {
			//発注添付ファイル登録
			registFile(orderNumber, fileIdList, form.userId);
		}
		return orderNumber;
	}

	//発注添付ファイル登録
	private void registFile(String orderNumber, List<String> fileIdList, String userId) {
		for (String fileId : fileIdList) {
			FileOrderRelEntity entity = new FileOrderRelEntity();
			entity.setOrderNumber(orderNumber);
			entity.setFileId(fileId);
			entity.setInsertUser(userId);
			entity.setUpdateUser(userId);
			fileOrderRelDao.insert(entity);
		}
	}

	public void confirmationInfo(OrderForm form) {
		String orderNumber = form.getOrderNumber();
		TOrderEntity orderInfo = selectInfo(orderNumber);
		orderInfo.setConfirmationFlg(FILE_FLG_OFF);
		orderInfo.setConfirmationRequestDate(new Timestamp(System.currentTimeMillis()));
		orderInfo.setUpdateUser(form.getUserId());
		tOrderDao.update(orderInfo);
	}

	public void cancelInfo(OrderForm form) {
		String orderNumber = form.getOrderNumber();
		TOrderEntity orderInfo = selectInfo(orderNumber);
		orderInfo.setCancelFlg(FILE_FLG_OFF);
		orderInfo.setCancelRequestDate(new Timestamp(System.currentTimeMillis()));
		orderInfo.setUpdateUser(form.getUserId());
		tOrderDao.update(orderInfo);
	}

	public void conectCloudSign(OrderForm form, String formType) {
		String orderNumber = form.getOrderNumber();
		String title = "";
		String fileName = "";
		String eigyousyoName = (Objects.nonNull(form.getEigyousyoName())) ?  form.getEigyousyoName() : "";
		String saimokuKousyuName = (Objects.nonNull(form.getSaimokuKousyuName())) ?  form.getSaimokuKousyuName() : "";
		String koujiName = (Objects.nonNull(form.getKoujiName())) ?  form.getKoujiName() : "";
		// メールタイトル、ファイル名の生成
		if(formType.equals(CloudSignApi.FORM_TYPE_ORDER)) {
			// 2019/6/24 クラウドサインメール件名変更対応(業者コード→支店名)
			// title = CloudSignApi.PREFIX_TITLE_CONFIRMATION + form.getGyousyaCode() + "_" + orderNumber;
			title = CloudSignApi.PREFIX_TITLE_CONFIRMATION + eigyousyoName + "_" + saimokuKousyuName + "_" + koujiName + "_" + orderNumber;
			fileName = CloudSignApi.PREFIX_FILE_CONFIRMATION + form.getGyousyaCode() + "_" + orderNumber;
		}else {
			// 2019/6/24 クラウドサインメール件名変更対応(業者コード→支店名)
			// title = CloudSignApi.PREFIX_TITLE_CANCEL + form.getGyousyaCode() + "_" + orderNumber;
			title = CloudSignApi.PREFIX_TITLE_CANCEL + eigyousyoName + "_" + saimokuKousyuName + "_" + koujiName + "_" + orderNumber;
			fileName = CloudSignApi.PREFIX_FILE_CANCEL + form.getGyousyaCode() + "_" + orderNumber;
		}

		Map<String, Object> documentRet = CloudSignApi.postDocuments(title, "", "", null, false);
		String documentId = documentRet.get("id").toString();

//		if(StringUtils.isNullString(documentId)) {
//			throw new CoreRuntimeException("documentId not found");
//		}

		String mailaddress = form.getMailaddress();
		int plus = mailaddress.indexOf("+");
		if(plus != -1) {
			int at = mailaddress.indexOf("@");
			String mailaddressBefore = mailaddress.substring(0, plus);
			String mailaddressAfter = mailaddress.substring(at);
			mailaddress = mailaddressBefore + mailaddressAfter;
		}

//		if(StringUtils.isNullString(mailaddress)) {
//			throw new CoreRuntimeException("mailaddress not found");
//		}

		CloudSignApi.postParticipants(documentId, mailaddress, form.getGyousyaName());

		CloudSignApi.postFile(documentId, form.getFilePath(), fileName + ".pdf");

		CloudSignApi.postDocumentId(documentId);

		//連携テーブルの登録
		registCloudSign(documentId, orderNumber, formType, form.getUserId());
	}

	public void registCloudSign(String fileId, String orderNumber, String formType, String userId) {
		TCloudSignEntity entity = new TCloudSignEntity();
		entity.setFileId(fileId);
		entity.setOrderNumber(orderNumber);
		entity.setFormType(formType);
		entity.setApplicationDate(new Timestamp(System.currentTimeMillis()));
		entity.setInsertUser(userId);
		entity.setUpdateUser(userId);
		tCloudSignDao.insert(entity);
	}

//	public Map<String, Object> confirmation(OrderForm form) {
//		Map<String, Object> data = SapApi.confirmation(form.getOrderNumber(), form.getEigyousyoCode(), form.getSyainCode());
//		Map<String, Object> result = SapApiAnalyzer.analyzeResult(data, SapApiConsts.PARAMS_KEY_T_IE_03002);
//		Map<String, Object> resultInfo = SapApiAnalyzer.analyzeResultInfo(data);
//		if(!SapApiAnalyzer.chkResultInfo(resultInfo)) {
//			throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
//		}
//		return result;
//	}


}
