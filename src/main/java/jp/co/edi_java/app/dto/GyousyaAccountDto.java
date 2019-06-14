package jp.co.edi_java.app.dto;

import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

import lombok.Getter;
import lombok.Setter;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Getter
@Setter
public class GyousyaAccountDto {

    public String gyousyaCode;

    public String gyousyaName;

    public String saimokuNameList;

    public String registFlg;

    public String authFlg;

}