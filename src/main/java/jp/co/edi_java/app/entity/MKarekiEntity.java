package jp.co.edi_java.app.entity;

import java.sql.Date;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "M_KAREKI")
@Getter
@Setter
public class MKarekiEntity {

    @Id
    public String karekiBoxCode;

    public String karekiBoxName;

    public String toshoCode;

    public String fileCode;

    public String fileNo;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Date saisyuuKousinDate;

}