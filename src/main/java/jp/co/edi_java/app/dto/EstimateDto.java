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
public class EstimateDto {

    public String estimateNumber;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Timestamp estimateDate;

}