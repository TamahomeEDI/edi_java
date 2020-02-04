package jp.co.edi_java.app.entity.google;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.TableGenerator;
import org.seasar.doma.jdbc.entity.NamingType;

import jp.co.edi_java.app.entity.BaseEntity;
import jp.co.edi_java.app.entity.listener.BaseEntityListener;
import lombok.Getter;
import lombok.Setter;

@Entity(listener = BaseEntityListener.class, naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "T_ARCHIVE_FILE")
@Getter
@Setter
public class TArchiveFileEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @TableGenerator(pkColumnValue = "ID")
    // サロゲートID
    public long id;
    // ファイルID
    public String fileId;
    // 親フォルダID
    public String parentFolderId;
    // ファイル名
    public String fileName;
    // ファイルパス
    public String filePath;

}
