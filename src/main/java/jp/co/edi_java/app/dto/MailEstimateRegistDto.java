package jp.co.edi_java.app.dto;

import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

import lombok.Getter;
import lombok.Setter;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Getter
@Setter
public class MailEstimateRegistDto {

    public String estimateNumber;

    public String partnerBikou;

    public String eigyousyoName;

    public String syainName;

    public String koujiName;

    public String eigyousyoCode;

    public String gyousyaName;


}