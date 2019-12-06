package jp.co.edi_java.app.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import jp.co.edi_java.app.entity.listener.BaseEntityListener;
import lombok.Getter;
import lombok.Setter;

@Entity(listener = BaseEntityListener.class, naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "M_CONSTANTS")
@Getter
@Setter
public class MConstantsEntity  extends BaseEntity {

    @Id
    public int id;

    public String constantsKey;

    public String constantsValue;

}