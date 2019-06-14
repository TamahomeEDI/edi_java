package jp.co.edi_java.app.entity.jtm;

import java.sql.Date;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "V_ORDER_EIGYOUSYO")
@Getter
@Setter
public class VOrderEigyousyoEntity {

    @Id
    public String eigyousyoCode;

    public int rownumber;

    public String eigyousyoName;

    public String eigyousyoKana;

    public String eigyousyoYuubin;

    public String eigyousyoJyuusyo;

    public String eigyousyoTatemonoName;

    public String eigyousyoTel;

    public String eigyousyoFax;

    public String eigyousyoGroupCode;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Date saisyuuKousinDate;

}