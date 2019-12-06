package jp.co.edi_java.app.entity;

import java.math.BigDecimal;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.TableGenerator;
import org.seasar.doma.jdbc.entity.NamingType;

import jp.co.edi_java.app.entity.listener.BaseEntityListener;
import lombok.Getter;
import lombok.Setter;

@Entity(listener = BaseEntityListener.class, naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "T_DELIVERY_ITEM")
@Getter
@Setter
public class TDeliveryItemEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @TableGenerator(pkColumnValue = "ID")
    public int id;

    public String deliveryNumber;

    public int itemNumber;

    public String itemName;

    public double orderQuantity;

    public double pastDeliveryQuantity;

    public double deliveryQuantity;

    public double deliveryRemainingQuantity;

    public String unit;

    public String completeDeliveryFlg;
    //JCO 明細番号
    public String jcoEbelp;

    // 発注金額
    public BigDecimal orderAmount;
    // 消費税額
    public BigDecimal orderAmountTax;
    // 単価
    public BigDecimal orderUnitPrice;
}