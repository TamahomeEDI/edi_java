package jp.co.edi_java.app.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.MConstantsEntity;

@Dao
@ConfigAutowireable
public interface MConstantsDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	MConstantsEntity select(String constantsKey);

	@Insert(excludeNull = true)
    int insert(MConstantsEntity entity);

	@Update()
	int update(MConstantsEntity entity);

	@Delete
	int delete(MConstantsEntity entity);

}
