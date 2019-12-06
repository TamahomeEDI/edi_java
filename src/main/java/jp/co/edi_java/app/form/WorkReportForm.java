package jp.co.edi_java.app.form;

import java.util.List;

import jp.co.edi_java.app.dto.ApprovalDto;
import jp.co.edi_java.app.entity.TWorkReportItemEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkReportForm extends BaseForm {

    public String workReportNumber;

    public String orderNumber;

    public String koujiCode;

    public String saimokuKousyuCode;

    public String gyousyaCode;

    public String workReportDate;

    public int workReportCount;

    public int workRate;

    public String staffReceiptFlg;

    public String staffReceiptDate;

    public String clerkReceiptFlg;

    public String clerkReceiptDate;

    public String managerReceiptFlg;

    public String managerReceiptDate;

    public String userBikou;

    public String partnerBikou;

    public String remandFlg;

    public String remandDate;

    public List<TWorkReportItemEntity> itemList;

    public String userKbn;

    public String judgmentKbn;

    public List<ApprovalDto> workReportApprovalList;

    public String fileId;

    public String encryptWorkReportNumber;

    public String approverCode;

    public String approverComments;

    public String approveDate;

    public String approveDateTime;

    public String eigyousyoCode;

    public String orderAmount;

    public String orderAmountTax;

}
