package jp.co.edi_java.app.dto;

import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

import lombok.Getter;
import lombok.Setter;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Getter
@Setter
public class SearchWorkReportDto {

    public String workReportNumber;

    public String workReportDate;

    public int workReportCount;

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

    public int workRate;

    public String remandFlg;

    public String userBikou;

    public String partnerBikou;

    public int torihikiStatus;

}