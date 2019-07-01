package jp.co.edi_java.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChecklistOrderDetailDto {

	//品名
    public String name;
    //単価（税抜）
    public String price;
    //数量
    public String quantity;
    //単位
    public String unit;
    //発注金額（税抜）
    public String amount;
    //消費税
    public String amountTax;

    public String amountTaxInclud;
    //
    public String deliveryQuantity;
    //
    public String deliveryAmount;
    //
    public String remainQuantity;
    //
    public String remainAmount;
}