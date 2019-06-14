package jp.co.edi_java.app.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.TableGenerator;

import jp.co.edi_java.app.entity.listener.BaseEntityListener;
import lombok.Getter;
import lombok.Setter;

@Entity(listener = BaseEntityListener.class)
@Table(name = "T_Test")
@Getter
@Setter
public class SapOrderEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @TableGenerator(pkColumnValue = "TestSeq")
    public int testSeq;

    @Column(name = "Id")
    public String id;

    @Column(name = "Name")
    public String name;

    @Column(name = "Email")
    public String email;

}