package jp.co.edi_java.app.entity.copy;

import java.sql.Date;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.TableGenerator;
import org.seasar.doma.jdbc.entity.NamingType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "V_ORDER_GYOUSYA_EIGYOUSYO")
@Getter
@Setter
public class CopyMGyousyaEigyousyoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @TableGenerator(pkColumnValue = "ID")
    public int id;

    public String gyousyaCode;

    public String eigyousyoCode;

    public int torihikiStatus;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Date saisyuuKousinDate;

}