package jp.co.edi_java.app.dto;

import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

import lombok.Getter;
import lombok.Setter;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Getter
@Setter
public class SearchKoujiInfoDto {

    public String koujiCode;

    public String koujiName;

    public String koujiStatusName;

    public String tyakkouDate;

    public String syunkouDate;

    public String hikiwatasiDate;

    public String eigyousyoName;

    public String syainName;

}