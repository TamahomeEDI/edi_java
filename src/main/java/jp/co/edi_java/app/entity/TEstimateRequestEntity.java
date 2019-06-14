package jp.co.edi_java.app.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import jp.co.edi_java.app.entity.listener.BaseEntityListener;
import lombok.Getter;
import lombok.Setter;

@Entity(listener = BaseEntityListener.class, naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "T_ESTIMATE_REQUEST")
@Getter
@Setter
public class TEstimateRequestEntity extends BaseEntity {

    @Id
    public String estimateRequestNumber;

    public String koujiCode;

    public String gyousyaCode;

    public String registSyainCode;

    public String estimateRequestDate;

    public String userBikou;

    public String partnerBikou;

}