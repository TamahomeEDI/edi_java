package jp.co.edi_java.app.dto;

import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

import lombok.Getter;
import lombok.Setter;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Getter
@Setter
public class GyousyaEigyousyoDto {

    public String eigyousyoCode;

    public String eigyousyoName;

    public String eigyousyoKana;

    public String eigyousyoYuubin;

    public String eigyousyoJyuusyo;

    public String eigyousyoTatemonoName;

    public String eigyousyoTel;

    public String eigyousyoFax;

    public String eigyousyoGroupCode;

    public int torihikiStatus;



}