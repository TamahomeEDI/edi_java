package jp.co.edi_java.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SapSearchOrderDto {

    public String orderNumber;

    public String acceptStatus;

    public String gyousyaCode;

    public String gyousyaName;

    public String koujiCode;

    public String sapOrderDate;

    public String orderDate;

    public String saimokuKousyuCode;

    public String saimokuKousyuName;

    public String orderAmount;

    public String orderAmountTax;

    public String acceptAmount;

    public String acceptAmountTax;

    public SearchOrderInfoDto orderInfo;

    public SearchKoujiInfoDto koujiInfo;

    public String searchOrderStatus;

    public String orderStatus;

    public String settlementCompFlg;

    public String orderSettlementFlg;

    public String documentId;


}