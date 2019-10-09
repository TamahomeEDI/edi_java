package jp.co.edi_java.app.entity.syain;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import jp.co.edi_java.app.entity.BaseEntity;
import jp.co.edi_java.app.entity.listener.BaseEntityListener;
import lombok.Getter;
import lombok.Setter;

@Entity(listener = BaseEntityListener.class, naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "T_SYAIN_PROCESS_LOG")
@Getter
@Setter
public class TSyainProcessLogEntity extends BaseEntity {

    @Id
    public String syainCode;

    public int estimateCount;

    public int confirmationCount;

    public int deliveryCount;

    public int workReportCount;

    public int inspectionReceiptCount;

}