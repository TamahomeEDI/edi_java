package jp.co.edi_java.app.entity;

import java.sql.Timestamp;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import jp.co.edi_java.app.entity.listener.BaseEntityListener;
import lombok.Getter;
import lombok.Setter;

@Entity(listener = BaseEntityListener.class, naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "T_ORDER")
@Getter
@Setter
public class TOrderEntity extends BaseEntity {

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

}