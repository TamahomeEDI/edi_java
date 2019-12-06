package jp.co.edi_java.app.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import jp.co.edi_java.app.entity.listener.BaseEntityListener;
import lombok.Getter;
import lombok.Setter;

@Entity(listener = BaseEntityListener.class, naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "V_ORDER_STATUS")
@Getter
@Setter
public class VOrderStatusEntity extends BaseEntity {

    @Id
    public String orderNumber;

    public String koujiCode;

    public String koujiName;

    public String eigyousyoGroupCode;

    public String eigyousyoCode;

    public String eigyousyoName;

    public String eigyousyoJyuusyo;

    public String eigyousyoTel;

    public String eigyousyoFax;

    public String orderType;

    public String orderDate;

    public String gyousyaCode;

    public String gyousyaName;

    public String gyousyaTel;

    public String gyousyaJyuusyo;

    public String saimokuKousyuCode;

    public String saimokuKousyuName;

    public String syainCode;

    public String syainName;

    public String syainMail;

    public String syainKeitai;

    public String taisyokuFlg;

    public String koujiStatus;

    public String koujiStatusName;

    public String kentikutiYuubin;

    public String kentikutiJyuusyo;

    public int kanseiKubun;

    public String keiyakuDateY;

    public String keiyakuDate;

    public String tyakkouDateY;

    public String tyakkouDate;

    public String jyoutouDateY;

    public String jyoutouDate;

    public String syunkouDateY;

    public String syunkouDate;

    public String hikiwatasiDateY;

    public String hikiwatasiDate;

    public String userBikou;

    public String partnerBikou;

    public String userBikouCancel;

    public String partnerBikouCancel;

    public String fileIdOrder;

    public String fileIdCancel;

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

    public String orderStatus;

    public String workNumber;

//  @JsonFormat(pattern = "yyyyMMdd")
    public Timestamp deliveryRequestDate;

    public String staffReceiptFlg;

    public Timestamp staffReceiptDate;

    public String managerReceiptFlg;

    public Timestamp managerReceiptDate;

    public String remandFlg;

    // 納入予定日
    public String deliveryDateY;
    // 発注金額
    public BigDecimal orderAmount;
    // 消費税額
    public BigDecimal orderAmountTax;
    // 進捗
    public String process;

    // 1:本体発注, 2:S発注, 3:A発注, 4:B発注, 5:C発注
 	public int yosanFlg; // YOSAN_FLG

 	// 発注書まとめ番号
 	public String groupOrderNumber;

    public int count;

}

