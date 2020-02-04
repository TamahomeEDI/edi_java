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
@Table(name = "T_GOOGLE_DRIVE")
@Getter
@Setter
public class TGoogleDriveEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @TableGenerator(pkColumnValue = "ID")
    // サロゲートID
    public long id;
    // ファイルID
    public String fileId;
    // ファイル名
    public String fileName;
    // フォルダパス
    public String folderPath;
    // ファイルパス(フルパス)
    public String filePath;
    // ファイルタイプ（0: フォルダ, 1: ファイル）
    public String fileType;
    // 親ファイルID
    public String parentFileId;

}