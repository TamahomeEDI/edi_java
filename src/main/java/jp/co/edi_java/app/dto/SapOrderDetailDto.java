package jp.co.edi_java.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SapOrderDetailDto {

    public String name;

    public String price;

    public String quantity;

    public String unit;

    public int amount;

    public int amountTax;

    public int amountTaxInclud;

    public String deliveryQuantity;

    public int deliveryAmount;

    public String remainQuantity;

    public int remainAmount;

}