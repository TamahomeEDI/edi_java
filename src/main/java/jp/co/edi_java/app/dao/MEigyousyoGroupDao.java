package jp.co.edi_java.app.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.MEigyousyoGroupEntity;

@Dao
@ConfigAutowireable
public interface MEigyousyoGroupDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	MEigyousyoGroupEntity select(String eigyousyoGroupCode);

	@Select
	List<MEigyousyoGroupEntity> selectAll();

	@Select
	List<MEigyousyoGroupEntity> selectList(List<String> groupCodeList);

	@Insert(excludeNull = true)
    int insert(MEigyousyoGroupEntity entity);

	@Update(excludeNull = true)
    int update(MEigyousyoGroupEntity entity);

	@Delete(sqlFile = true, ignoreVersion = true)
	int deleteAll();

}
