package jp.co.edi_java.app.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.TEstimateRequestEntity;

@Dao
@ConfigAutowireable
public interface TEstimateRequestDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	TEstimateRequestEntity select(String estimateRequestNumber);

	@Select
	int count(String eigyousyoCode, String from, String to);

	@Insert(excludeNull = true)
    int insert(TEstimateRequestEntity entity);

	@Update(excludeNull = true)
	int update(TEstimateRequestEntity entity);

	@Delete
	int delete(TEstimateRequestEntity entity);

}
