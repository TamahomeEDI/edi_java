package jp.co.edi_java.app.dto;

import java.sql.Date;

import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

import lombok.Getter;
import lombok.Setter;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Getter
@Setter
public class SearchEstimateDto {

    public String estimateRequestNumber;

    public Date estimateRequestDate;

    public String estimateNumber;

    public Date estimateDate;

    public String unreadFlg;

    public String koujiCode;

    public String koujiName;

    public String koujiStatusName;

    public String gyousyaName;

    public String eigyousyoName;

    public String syainName;

    public int torihikiStatus;


}