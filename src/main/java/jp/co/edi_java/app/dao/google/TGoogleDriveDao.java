package jp.co.edi_java.app.dao.google;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.google.TGoogleDriveEntity;

@Dao
@ConfigAutowireable
public interface TGoogleDriveDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
    int countByFilePath(String filePath);

	@Select
	List<TGoogleDriveEntity> selectAll();

	@Select
	TGoogleDriveEntity selectByFileId(String fileId);

	@Select
	TGoogleDriveEntity selectByFilePath(String filePath); // file path is unique


	@Insert(excludeNull = true)
    int insert(TGoogleDriveEntity entity);

	@Update(excludeNull = true)
	int update(TGoogleDriveEntity entity);

	@Delete(sqlFile = true, ignoreVersion = true)
	int deleteByFileId(String fileId);

	@Delete
	int delete(TGoogleDriveEntity entity);
}
