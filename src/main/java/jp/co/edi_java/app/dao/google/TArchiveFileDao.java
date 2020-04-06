package jp.co.edi_java.app.dao.google;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.dto.GoogleDriveDto;
import jp.co.edi_java.app.entity.google.TArchiveFileEntity;

@Dao
@ConfigAutowireable
public interface TArchiveFileDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
    int countByFilePath(String filePath);

	@Select
	TArchiveFileEntity selectByFileId(String fileId);

	@Select
	TArchiveFileEntity selectByFileIdWithGyousyaCode(String fileId, String gyousyaCode);

	@Select
	List<GoogleDriveDto> selectListByTerm(String fromYearMonth, String toYearMonth, String reportType, String gyousyaCode);

	@Insert(excludeNull = true)
    int insert(TArchiveFileEntity entity);

	@Update(excludeNull = true)
	int update(TArchiveFileEntity entity);

	@Delete(sqlFile = true, ignoreVersion = true)
	int deleteByYearMonth(String year, String month);

	@Delete(sqlFile = true, ignoreVersion = true)
	int deleteByFolderId(String folderId);

	@Delete(sqlFile = true, ignoreVersion = true)
	int deleteByFileId(String fileId);
}
