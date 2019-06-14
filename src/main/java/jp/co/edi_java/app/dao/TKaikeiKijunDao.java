package jp.co.edi_java.app.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.TKaikeiKijunEntity;

@Dao
@ConfigAutowireable
public interface TKaikeiKijunDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	TKaikeiKijunEntity select(String gyousyaCode);

	@Select
	TKaikeiKijunEntity selectByHashCode(String hashCode);

	@Select
	List<TKaikeiKijunEntity> selectAll();

	@Update(excludeNull = true)
	int update(TKaikeiKijunEntity entity);

}
