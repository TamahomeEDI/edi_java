package jp.co.edi_java.app.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.TExclusiveEntity;

@Dao
@ConfigAutowireable
public interface TExclusiveDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }


	@Select
	List<TExclusiveEntity> selectLock(TExclusiveEntity entity);

	@Select
	List<TExclusiveEntity> selectForUpdate(TExclusiveEntity entity);

	@Insert(excludeNull = true)
    int insert(TExclusiveEntity entity);

	@Update(excludeNull = true)
	int update(TExclusiveEntity entity);

	@Delete(sqlFile = true, ignoreVersion = true, suppressOptimisticLockException = true)
	int delete(TExclusiveEntity entity);

	@Update(sqlFile = true, ignoreVersion = true)
	int commit();

	@Update(sqlFile = true, ignoreVersion = true)
	int lockTableWriteOn();

	@Update(sqlFile = true, ignoreVersion = true)
	int unlockTable();
}
