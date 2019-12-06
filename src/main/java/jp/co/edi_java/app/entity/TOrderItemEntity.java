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
@Table(name = "T_ORDER_ITEM")
@Getter
@Setter
public class TOrderItemEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @TableGenerator(pkColumnValue = "ID")
    // サロゲートID
    public int id;
    // 発注番号
    public String orderNumber;
    // 明細No (EDI上の連番)
    public int itemNumber;
    // 品名
    public String itemName;
    // 発注数量
    public double orderQuantity;
    // 単位
    public String unit;
    // 発注金額
    public BigDecimal orderAmount;
    // 消費税額
    public BigDecimal orderAmountTax;
    // 単価
    public BigDecimal orderUnitPrice;
    // 納品数量
    public double deliveryQuantity;
    // 納品金額
    public BigDecimal deliveryAmount;
    // 発注残数量
    public double remainQuantity;
    // 発注残金額
    public BigDecimal remainAmount;
    // 明細No (JTM上の連番)
    public int jtmMeisaiSeqNo;
}