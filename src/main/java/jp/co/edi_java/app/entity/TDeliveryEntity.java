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
@Table(name = "T_DELIVERY")
@Getter
@Setter
public class TDeliveryEntity extends BaseEntity {

    @Id
    public String deliveryNumber;

    public String orderNumber;

    public String koujiCode;

    public String gyousyaCode;

    public String saimokuKousyuCode;

    public String deliveryDate;

    public int deliveryCount;

    public String staffReceiptFlg;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Timestamp staffReceiptDate;

    public String clerkReceiptFlg;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Timestamp clerkReceiptDate;

    public String managerReceiptFlg;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Timestamp managerReceiptDate;

    public String userBikou;

    public String partnerBikou;

    public String remandFlg;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Timestamp remandDate;

    public String fileId;

}