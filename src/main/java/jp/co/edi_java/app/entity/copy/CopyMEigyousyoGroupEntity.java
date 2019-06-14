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
@Table(name = "V_ORDER_EIGYOUSYO_GROUP")
@Getter
@Setter
public class CopyMEigyousyoGroupEntity {

    @Id
    public String eigyousyoGroupCode;

    public String eigyousyoGroupName;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Date saisyuuKousinDate;

}