package jp.co.edi_java.app.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.TableGenerator;
import org.seasar.doma.jdbc.entity.NamingType;

import lombok.Getter;
import lombok.Setter;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "T_KAIKEI_KIJUN")
@Getter
@Setter
public class TKaikeiKijunEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @TableGenerator(pkColumnValue = "ID")
    public int id;

    public String gyousyaCode;

    public String hashCode;

    public String gyousyaName;

    public String staffName;

    public String mailaddress;

    public String kaikeiKijun;

    public String registDate;

    public String url;

}