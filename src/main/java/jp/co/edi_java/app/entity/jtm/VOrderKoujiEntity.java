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
@Table(name = "V_ORDER_KOUJI")
@Getter
@Setter
public class VOrderKoujiEntity {

    @Id
    public String koujiCode;

    public int rownumber;

    public String koujiName;

    public String keiyakusyaName;

    public String keiyakusyaKana;

    public String eigyousyoGroupCode;

    public String eigyousyoCode;

    public String tantouSyainCode;

    public String koujiStatus;

    public String koujiStatusName;

    public String kentikutiYuubin;

    public String kentikutiJyuusyo;

    public int kanseiKubun;

    public String projectType;

    public String keiyakuDateY;

    public String keiyakuDate;

    public String tyakkouDateY;

    public String tyakkouDate;

    public String jyoutouDateY;

    public String jyoutouDate;

    public String syunkouDateY;

    public String syunkouDate;

    public String hikiwatasiDateY;

    public String hikiwatasiDate;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Date saisyuuKousinDate;

}