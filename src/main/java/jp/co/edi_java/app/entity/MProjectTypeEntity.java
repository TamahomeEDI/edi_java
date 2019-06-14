package jp.co.edi_java.app.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import lombok.Getter;
import lombok.Setter;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "M_TEXT")
@Getter
@Setter
public class MProjectTypeEntity {

    @Id
    public int id;

    public String projectTypeCode;

    public String projectTypeName;

}