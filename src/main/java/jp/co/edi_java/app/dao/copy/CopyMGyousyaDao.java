package jp.co.edi_java.app.dao.copy;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.copy.CopyMGyousyaEntity;

@Dao
@ConfigAutowireable
public interface CopyMGyousyaDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	CopyMGyousyaEntity select(String gyousyaCode);

	@Insert(sqlFile = true)
    int insertAll();

	@Delete(sqlFile = true, ignoreVersion = true)
	int deleteAll();

}
