package jp.co.edi_java.app.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.TDeliveryItemEntity;

@Dao
@ConfigAutowireable
public interface TDeliveryItemDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
    List<TDeliveryItemEntity> selectAll(String deliveryNumber);

	@Insert(excludeNull = true)
    int insert(TDeliveryItemEntity entity);

	@Update(excludeNull = true)
	int update(TDeliveryItemEntity entity);

	@Update()
	int softdelete(TDeliveryItemEntity entity);

	@Delete
	int delete(TDeliveryItemEntity entity);

}
