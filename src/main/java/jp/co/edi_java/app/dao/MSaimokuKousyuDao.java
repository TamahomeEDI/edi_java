package jp.co.edi_java.app.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.MSaimokuKousyuEntity;

@Dao
@ConfigAutowireable
public interface MSaimokuKousyuDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	MSaimokuKousyuEntity select(String saimokuKousyuCode);

	@Select
	List<MSaimokuKousyuEntity> selectAll();

	@Insert(excludeNull = true)
    int insert(MSaimokuKousyuEntity entity);

	@Update(excludeNull = true)
    int update(MSaimokuKousyuEntity entity);


}
