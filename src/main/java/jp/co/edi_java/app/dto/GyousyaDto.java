package jp.co.edi_java.app.dto;

import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

import lombok.Getter;
import lombok.Setter;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Getter
@Setter
public class GyousyaDto {

    public String gyousyaCode;

    public String gyousyaName;

    public String gyousyaKana;

    public int torihikiStatus;



}