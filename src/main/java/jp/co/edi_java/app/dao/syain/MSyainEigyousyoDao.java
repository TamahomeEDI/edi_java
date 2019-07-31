package jp.co.edi_java.app.dao.syain;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.dto.SyainEigyousyoDto;
import jp.co.edi_java.app.entity.syain.MSyainEigyousyoEntity;

@Dao
@ConfigAutowireable
public interface MSyainEigyousyoDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	List<SyainEigyousyoDto> selectList(String syainCode);

	@Select
	List<SyainEigyousyoDto> selectListByHeadOffice(String syainCode);

	@Select
	List<String> selectGroupCodeList(String syainCode);

	@Insert(excludeNull = true)
    int insert(MSyainEigyousyoEntity entity);

	@Update(excludeNull = true)
    int update(MSyainEigyousyoEntity entity);

	@Delete(sqlFile = true, ignoreVersion = true)
	int deleteAll();


}
