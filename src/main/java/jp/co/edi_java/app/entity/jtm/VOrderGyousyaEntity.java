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
@Table(name = "V_ORDER_GYOUSYA")
@Getter
@Setter
public class VOrderGyousyaEntity {

    @Id
    public String gyousyaCode;

    public int rownumber;

    public String gyousyaName;

    public String gyousyaKana;

    public String yuubin;

    public String jyuusyo;

    public String tatemonoName;

    public String tel;

    public String fax;

    public String keitaiTel;

    public String mail;

    public String eigyousyoCode;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Date saisyuuKousinDate;

}