package jp.co.edi_java.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSummaryDto {

    public String orderStatus;

    public int orderCount;

    public String gyousyaCode;

    public String eigyousyoCode;

}