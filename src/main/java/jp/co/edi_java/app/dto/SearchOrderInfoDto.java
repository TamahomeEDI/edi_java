package jp.co.edi_java.app.dto;

import java.sql.Timestamp;

import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Getter
@Setter
public class SearchOrderInfoDto {

    public String orderNumber;

    public String orderType;

    public String confirmationFlg;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Timestamp confirmationRequestDate;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Timestamp confirmationAgreeDate;

    public String cancelFlg;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Timestamp cancelRequestDate;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Timestamp cancelAgreeDate;

    public String workNumber;

    public String managerReceiptFlg;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Timestamp managerReceiptDate;

    public String remandFlg;

    public String orderStatus;


}