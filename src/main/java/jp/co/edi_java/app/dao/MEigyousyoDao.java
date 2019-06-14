package jp.co.edi_java.app.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.dto.GyousyaEigyousyoDto;
import jp.co.edi_java.app.entity.MEigyousyoEntity;

@Dao
@ConfigAutowireable
public interface MEigyousyoDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	MEigyousyoEntity select(String eigyousyoCode);

	@Select
	List<MEigyousyoEntity> selectList(List<String> eigyousyoList);

	@Select
	List<GyousyaEigyousyoDto> selectListByPartner(String gyousyaCode, String eigyousyoGroupCode);

	@Select
	List<MEigyousyoEntity> selectListByGroup(String eigyousyoGroupCode);

	@Insert(excludeNull = true)
    int insert(MEigyousyoEntity entity);

	@Update(excludeNull = true)
    int update(MEigyousyoEntity entity);

	@Delete(sqlFile = true, ignoreVersion = true)
	int deleteAll();

}
