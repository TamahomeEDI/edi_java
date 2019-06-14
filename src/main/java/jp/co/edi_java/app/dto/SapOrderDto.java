package jp.co.edi_java.app.dto;

import java.util.List;
import java.util.Map;

import jp.co.edi_java.app.entity.TOrderEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SapOrderDto {

    public String orderNumber;

    public String deliveryStatus;

    public String gyousyaCode;

    public String gyousyaName;

    public String koujiCode;

    public String saimokuKousyuCode;

    public String saimokuKousyuName;

    public String sapOrderDate;

    public String orderDate;

    public int orderAmount;

    public int orderAmountTax;

    public int deliveryAmount;

    public int deliveryAmountTax;

    public String settlementCompFlg;

    public String orderSettlementFlg;

    public List<SapOrderDetailDto> itemList;

    public TOrderEntity orderInfo;

    public List<Map<String, Object>> fileList;

}