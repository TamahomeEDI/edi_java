package jp.co.edi_java.app.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import jp.co.edi_java.app.entity.listener.BaseEntityListener;
import lombok.Getter;
import lombok.Setter;

@Entity(listener = BaseEntityListener.class, naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "T_INSPECTION_RECEIPT")
@Getter
@Setter
public class TInspecrionReceiptEntity extends BaseEntity {

    @Id
    public String orderNumber;

    public String userBikou;

    public String partnerBikou;

    public String fileId;

}