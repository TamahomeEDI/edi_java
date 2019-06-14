package jp.co.edi_java.app.dto;

import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

import lombok.Getter;
import lombok.Setter;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Getter
@Setter
public class SearchDeliveryDto {

    public String deliveryNumber;

    public String deliveryDate;

    public int deliveryCount;

    public String orderNumber;

    public String staffReceiptFlg;

    public String clerkReceiptFlg;

    public String managerReceiptFlg;

    public String koujiCode;

    public String koujiName;

    public String koujiStatusName;

    public String gyousyaName;

    public String saimokuKousyuName;

    public String eigyousyoName;

    public String syainName;

    public String remandFlg;

    public String userBikou;

    public String partnerBikou;

    public int torihikiStatus;


}