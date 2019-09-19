package jp.co.edi_java.app.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.TDeliveryEntity;

@Dao
@ConfigAutowireable
public interface TDeliveryDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	TDeliveryEntity select(String deliveryNumber);

	@Select
	TDeliveryEntity selectByOrderNumber(String orderNumber, int deliveryCount);

	@Select
    List<TDeliveryEntity> selectAll(String orderNumber, String remandFlg);

	@Select
    List<TDeliveryEntity> selectUnconfirmList();

	@Insert(excludeNull = true)
    int insert(TDeliveryEntity entity);

	@Update(excludeNull = true)
	int update(TDeliveryEntity entity);

	@Update()
	int updateWf(TDeliveryEntity entity);

	@Delete
	int delete(TDeliveryEntity entity);

}
