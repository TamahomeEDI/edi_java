package jp.co.edi_java.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobcanDeliveryItemDto {


public String deliveryNumber;

public int itemNumber;

public String itemName;

public double orderQuantity;

public double deliveryQuantity;

public double deliveryRemainingQuantity;

public String unit;

public String completeDeliveryFlg;

}