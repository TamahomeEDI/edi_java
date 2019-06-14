package jp.co.edi_java.app.dto;

import java.util.Date;
import java.util.List;

import jp.co.edi_java.app.entity.TWorkReportItemEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkReportDto {

    public String workReportNumber;

    public String orderNumber;

    public String koujiCode;

    public String gyousyaCode;

    public String workReportDate;

    public int workReportCount;

    public int workRate;

    public String staffReceiptFlg;

    public Date staffReceiptDate;

    public String clerkReceiptFlg;

    public Date clerkReceiptDate;

    public String managerReceiptFlg;

    public Date managerReceiptDate;

    public String userBikou;

    public String partnerBikou;

    public String remandFlg;

    public List<TWorkReportItemEntity> itemList;

}