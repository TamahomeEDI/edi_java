package jp.co.edi_java.app.entity;

import java.sql.Timestamp;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import com.fasterxml.jackson.annotation.JsonFormat;

import jp.co.edi_java.app.entity.listener.BaseEntityListener;
import lombok.Getter;
import lombok.Setter;

@Entity(listener = BaseEntityListener.class, naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "T_ESTIMATE")
@Getter
@Setter
public class TEstimateEntity extends BaseEntity {

    @Id
    public String estimateNumber;

    public String koujiCode;

    public String estimateRequestNumber;

    public String gyousyaCode;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Timestamp estimateDate;

    public String unreadFlg;

    public String userBikou;

    public String partnerBikou;


}