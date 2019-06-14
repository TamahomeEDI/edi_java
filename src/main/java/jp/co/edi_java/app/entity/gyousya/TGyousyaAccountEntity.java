package jp.co.edi_java.app.entity.gyousya;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import jp.co.edi_java.app.entity.BaseEntity;
import jp.co.edi_java.app.entity.listener.BaseEntityListener;
import lombok.Getter;
import lombok.Setter;

@Entity(listener = BaseEntityListener.class, naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "T_GYOUSYA_ACCOUNT")
@Getter
@Setter
public class TGyousyaAccountEntity extends BaseEntity {

    @Id
    public String gyousyaCode;

    public String password;

    public String authFlg;

    public String token;

}