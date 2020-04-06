package jp.co.edi_java.app.entity.google;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.TableGenerator;
import org.seasar.doma.Transient;
import org.seasar.doma.jdbc.entity.NamingType;

import jp.co.edi_java.app.entity.BaseEntity;
import jp.co.edi_java.app.entity.listener.BaseEntityListener;
import lombok.Getter;
import lombok.Setter;

@Entity(listener = BaseEntityListener.class, naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "T_ARCHIVE_FOLDER")
@Getter
@Setter
public class TArchiveFolderEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @TableGenerator(pkColumnValue = "ID")
    // サロゲートID
    public long id;
    // GoogleDriveファイルID
    public String folderId;
    // フォルダ名
    public String folderName;
    // フォルダパス
    public String folderPath;
    // 業者コード
    public String gyousyaCode;
    // 帳票タイプ（10: 発注書兼請書, 20: 納品書, 30: 出来高報告書）
    public String reportType;
    // 帳票作成年
    public String reportYear;
    // 帳票作成月
    public String reportMonth;
    // 帳票作成年月（検索用）
    public String reportYearMonth;
    // アーカイブファイル未作成分 検索用
    @Transient
    public String ignoreArchiveFinished;

}