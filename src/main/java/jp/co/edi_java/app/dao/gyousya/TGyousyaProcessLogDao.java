package jp.co.edi_java.app.dao.gyousya;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.gyousya.TGyousyaProcessLogEntity;

@Dao
@ConfigAutowireable
public interface TGyousyaProcessLogDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	TGyousyaProcessLogEntity select(String gyousyaCode);

	@Insert(excludeNull = true)
    int insert(TGyousyaProcessLogEntity entity);

	@Update(excludeNull = true)
	int update(TGyousyaProcessLogEntity entity);

//	@Delete
//	int delete(TGyousyaProcessLogEntity entity);

}
