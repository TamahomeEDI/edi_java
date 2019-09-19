package jp.co.edi_java.app.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobcanDto {

public String orderType;

public String reportNumber;

public String eigyousyoCode;

public String eigyousyoName;

public String syainCode;

public String syainName;

public String koujiCode;

public String koujiName;

public String gyousyaCode;

public String gyousyaName;

public String orderNumber;

public String saimokuKousyuCode;

public String saimokuKousyuName;

public String orderDate;

public String deliveryDate;

public int deliveryCount;

public int workRate;

public String userCode;

public String fileId;

public String toshoCode;

public String fileCode;

public String fileNo;

public String encryptNumber;

public List<JobcanDeliveryItemDto> deliveryItemList;

public List<JobcanWorkReportItemDto> workReportItemList;

}