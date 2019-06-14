package jp.co.edi_java.app.entity;

import java.sql.Timestamp;

import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Getter
@Setter
public class BaseEntity {

	public static final String DEFAULT_USER_NAME = "system";

	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Timestamp insertDate;

    public String insertUser;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Timestamp updateDate;

    public String updateUser;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Timestamp deleteDate;

    public String deleteUser;

    public String deleteFlg;

}
