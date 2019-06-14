package jp.co.edi_java.app.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchForm extends BaseForm {

	//共通項目
	public int page = 1;

    public int count = 20;

    public String eigyousyoGroupCode;

    public String staffName;

    public String eigyousyoCode;

    public String koujiCode;

    public String koujiName;

    public String gyousyaName;

    public String saimokuKousyuCode;

    public String orderNumber;

	//見積情報検索
    public String estimateStatus;

    public String estimateRequestNumber;

    public String estimateNumber;

    public String estimateRequestDateFrom;

    public String estimateRequestDateTo;

    public String estimateDateFrom;

    public String estimateDateTo;

	//発注情報検索
    public String userFlg;

    public String koujiStatus;

    public String orderStatus;	//listから単一へ変更

    public String process;

    public String cancelKbn;

    public String koujiStartDateFrom;

    public String koujiStartDateTo;

    public String koujiCompleteDateFrom;

    public String koujiCompleteDateTo;

    public String koujiDeliveryDateFrom;

    public String koujiDeliveryDateTo;

    public String orderDateFrom;

    public String orderDateTo;

    public String confirmationDateFrom;

    public String confirmationDateTo;

    public String completionDateFrom;

    public String completionDateTo;

    public String orderCancelRequestDateFrom;

    public String orderCancelRequestDateTo;

    public String orderCancelAgreeDateFrom;

    public String orderCancelAgreeDateTo;

	//納品・出来高情報検索
    public String receptionStatus;

    public String deliveryDateFrom;

    public String deliveryDateTo;

    public String inspectionReceiptDateFrom;

    public String inspectionReceiptDateTo;

	//検収明細情報検索
    public String inspectionReceiptStatus;

    public String inspectionReceiptYear;

	public String inspectionReceiptMonth;


}
