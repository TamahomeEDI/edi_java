package jp.co.edi_java.app.entity.syain;

import java.sql.Date;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "M_SYAIN")
@Getter
@Setter
public class MSyainEntity {

    @Id
    public String syainCode;

    public String syainName;

    public String syainKana;

    public String syainPassword;

    public String syainMail;

    public String syainKeitai;

    public int taisyokuFlg;

    public int syokusyuFlg;

    public String yakusyokuCode;

    public String yakusyokuName;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Date saisyuuKousinDate;

}