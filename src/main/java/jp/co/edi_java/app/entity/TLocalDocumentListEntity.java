package jp.co.edi_java.app.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.TableGenerator;
import org.seasar.doma.Transient;
import org.seasar.doma.jdbc.entity.NamingType;

import jp.co.edi_java.app.entity.listener.BaseEntityListener;
import lombok.Getter;
import lombok.Setter;

@Entity(listener = BaseEntityListener.class, naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "T_LOCAL_DOCUMENT_LIST")
@Getter
@Setter
public class TLocalDocumentListEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @TableGenerator(pkColumnValue = "ID")
    // サロゲートID
    public long id;
    // ファイル名
    public String fileName;
    // ファイルパス
    public String filePath;
    // 業者コード
    public String gyousyaCode;
    // 帳票作成年
    public String reportYear;
    // 帳票作成月
    public String reportMonth;
    // 帳票作成年月（検索用）
    public String reportYearMonth;
 // 処理済フラグ
    public String documentType;
    // 処理済フラグ
    public String completeFlg;
    // アーカイブファイル未作成分 検索用 db項目ではない
    @Transient
    public String ignoreArchiveFinished;

}