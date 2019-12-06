package jp.co.edi_java.app.form;

import java.util.List;

import jp.co.edi_java.app.dto.ApprovalDto;
import jp.co.edi_java.app.entity.TDeliveryItemEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryForm extends BaseForm {

    public String deliveryNumber;

    public String orderNumber;

    public String koujiCode;

    public String saimokuKousyuCode;

    public String deliveryDate;

    public int deliveryCount;

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

    public List<TDeliveryItemEntity> itemList;

    public String userKbn;

    public String judgmentKbn;

    public List<ApprovalDto> deliveryApprovalList;

    public String fileId;

    public String encryptDeliveryNumber;

    public String approverCode;

    public String approverComments;

    public String approveDate;

    public String approveDateTime;

    public String eigyousyoCode;

    public String orderAmount;

    public String orderAmountTax;

}
