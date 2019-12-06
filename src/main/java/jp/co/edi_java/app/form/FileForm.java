package jp.co.edi_java.app.form;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileForm extends BaseForm {

    public String koujiCode;

    public String toshoCode;

    public String fileCode;

    public String fileNo;

    public String filePath;

    public String fileName;

    public String registSyainCode;

    public String fileType;

    public String fileId;

    /** 請書、納品出来高報告書 一括ダウンロード用 */
    public List<String> orderNumberList;

    /** [1:請書]または[2:納品出来高報告書] */
    public String downloadType;

    /** ダウンロード用 フォルダ */
    public String folderPath;
}
