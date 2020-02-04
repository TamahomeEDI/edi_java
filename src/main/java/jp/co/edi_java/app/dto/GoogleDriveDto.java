package jp.co.edi_java.app.dto;

import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

import lombok.Getter;
import lombok.Setter;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Getter
@Setter
public class GoogleDriveDto {

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

    public String reportYearMonth;

    public String gyousyaCode;

}
