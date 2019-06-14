package jp.co.edi_java.app.entity;

import java.sql.Timestamp;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.TableGenerator;
import org.seasar.doma.jdbc.entity.NamingType;

import com.fasterxml.jackson.annotation.JsonFormat;

import jp.co.edi_java.app.entity.listener.BaseEntityListener;
import lombok.Getter;
import lombok.Setter;

@Entity(listener = BaseEntityListener.class, naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "T_CLOUD_SIGN")
@Getter
@Setter
public class TCloudSignEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @TableGenerator(pkColumnValue = "ID")
    public int id;

    public String fileId;

    public String orderNumber;

    public String formType;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Timestamp applicationDate;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Timestamp executionDate;

}