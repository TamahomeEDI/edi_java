package jp.co.edi_java.app.entity.copy;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import jp.co.edi_java.app.entity.BaseEntity;
import jp.co.edi_java.app.entity.listener.BaseEntityListener;
import lombok.Getter;
import lombok.Setter;

@Entity(listener = BaseEntityListener.class, naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "COPY_T_ORDER")
@Getter
@Setter
public class CopyTOrderEntity extends BaseEntity {

    @Id
    public String orderNumber;

    public String koujiCode;

    public String userBikou;

    public String partnerBikou;

    public String userBikouCancel;

    public String partnerBikouCancel;

    public String fileIdOrder;

    public String fileIdCancel;

    public String orderType;

    public String confirmationFlg;

//    @JsonFormat(pattern = "yyyyMMdd")
    public Timestamp confirmationRequestDate;

//    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Timestamp confirmationAgreeDate;

    public String cancelFlg;

//    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Timestamp cancelRequestDate;

//    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Timestamp cancelAgreeDate;

    // 発注日
    public String orderDate;
    // 納入予定日
    public String deliveryDateY;
    // 発注金額
    public BigDecimal orderAmount;
    // 消費税額
    public BigDecimal orderAmountTax;
    // 業者コード
    public String gyousyaCode;
    // 細目工種コード
    public String saimokuKousyuCode;
    // 1:本体発注, 2:S発注, 3:A発注, 4:B発注, 5:C発注
 	public int yosanFlg; // YOSAN_FLG
 	// 発注書まとめ番号
 	public String groupOrderNumber;
 	// JTM キー
 	public int jtmHattyuuSyubetuFlg;

 	public int jtmHattyuuSeqNo;

 	public int jtmKanriSeqNo;

}

