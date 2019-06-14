package jp.co.edi_java.app.entity.syain;

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
@Table(name = "M_SYAIN_EIGYOUSYO")
@Getter
@Setter
public class MSyainEigyousyoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @TableGenerator(pkColumnValue = "ID")
    public int id;

    public String syainCode;

    public String eigyousyoCode;

    public String eigyousyoGroupCode;

    public String syumuFlg;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Date saisyuuKousinDate;
}