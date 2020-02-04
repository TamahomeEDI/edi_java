package jp.co.edi_java.app.form;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleDriveForm extends BaseForm {

    public String folderId;

    public String fileId;

    public String parentFolderId;

    public String folderPath;

    public String folderName;

    public String filePath;

    public String fileName;

    public String reportType;

    public String reportYear;

    public String reportMonth;

    public String gyousyaCode;

    public List<String> folderIdList;

    public List<String> fileIdList;

    public int prevMonthCount;

    /** ダウンロード用 フォルダ */
    public String downloadPath;
}
