package jp.co.edi_java.app.dao.syain;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity;

@Dao
@ConfigAutowireable
public interface TSyainProcessLogDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	TSyainProcessLogEntity select(String syainCode);

	@Insert(excludeNull = true)
    int insert(TSyainProcessLogEntity entity);

	@Update(excludeNull = true)
	int update(TSyainProcessLogEntity entity);

//	@Delete
//	int delete(TSyainProcessLogEntity entity);

}
