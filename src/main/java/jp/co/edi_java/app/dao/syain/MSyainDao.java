package jp.co.edi_java.app.dao.syain;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.syain.MSyainEntity;

@Dao
@ConfigAutowireable
public interface MSyainDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	MSyainEntity select(String syainCode);

	@Select
	List<MSyainEntity> selectListByMultiCode(List<String> syainCodeList);

	@Select
	List<MSyainEntity> selectListByEigyousyo(String eigyousyoCode);

	@Select
	List<MSyainEntity> selectListByEigyousyoList(List<String> eigyousyoCodeList);

	@Select
	List<MSyainEntity> selectListBySyokusyu3(String eigyousyoCode);

	@Select
	MSyainEntity selectByLogin(String syainCode, String password);

	@Insert(excludeNull = true)
    int insert(MSyainEntity entity);

	@Update(excludeNull = true)
    int update(MSyainEntity entity);

//	@Delete(sqlFile = true, ignoreVersion = true)
//	int deleteAll();

}
