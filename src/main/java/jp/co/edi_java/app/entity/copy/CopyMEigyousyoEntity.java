package jp.co.edi_java.app.entity.copy;

import java.sql.Date;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "COPY_M_EIGYOUSYO")
@Getter
@Setter
public class CopyMEigyousyoEntity {

    @Id
    public String eigyousyoCode;

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