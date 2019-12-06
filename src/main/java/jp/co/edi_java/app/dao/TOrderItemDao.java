package jp.co.edi_java.app.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.TOrderItemEntity;

@Dao
@ConfigAutowireable
public interface TOrderItemDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
    List<TOrderItemEntity> selectAll(String orderNumber);

	@Insert(excludeNull = true)
    int insert(TOrderItemEntity entity);

	@Update(excludeNull = true)
	int update(TOrderItemEntity entity);

	@Update()
	int softdelete(TOrderItemEntity entity);

	@Delete
	int delete(TOrderItemEntity entity);

	@Delete(sqlFile = true, ignoreVersion = true)
	int deleteByOrderNumber(String orderNumber);

}
