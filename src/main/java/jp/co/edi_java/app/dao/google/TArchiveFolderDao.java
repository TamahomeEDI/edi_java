package jp.co.edi_java.app.dao.google;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.google.TArchiveFolderEntity;

@Dao
@ConfigAutowireable
public interface TArchiveFolderDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
    int countByFolderPath(String folderPath);

	@Select
	TArchiveFolderEntity selectByFolderId(String folderId);

	@Select
	List<TArchiveFolderEntity> selectList(TArchiveFolderEntity entity);

	@Insert(excludeNull = true)
    int insert(TArchiveFolderEntity entity);

	@Update(excludeNull = true)
	int update(TArchiveFolderEntity entity);

	@Delete(sqlFile = true, ignoreVersion = true)
	int deleteByYearMonth(String year, String month);

	@Delete(sqlFile = true, ignoreVersion = true)
	int deleteByFolderId(String folderId);
}
