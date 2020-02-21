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
    List<TDeliveryEntity> selectList(String orderNumber, String remandFlg);

	@Select
    List<TDeliveryEntity> selectListByMultiOrder(List<String> orderNumberList, String remandFlg);

	@Select
    List<TDeliveryEntity> selectUnconfirmList(String deliveryDate);

	@Select
    List<TDeliveryEntity> checkUnconfirmList(String orderNumber);

	@Select
    List<TDeliveryEntity> selectUnconfirmListBySyain(String eigyousyoCode, String syainCode);

	@Select
    List<TDeliveryEntity> selectListForArchive(String gyousyaCode, String from, String to, String remandFlg);

	@Insert(excludeNull = true)
    int insert(TDeliveryEntity entity);

	@Update(excludeNull = true)
	int update(TDeliveryEntity entity);

	@Update()
	int updateWf(TDeliveryEntity entity);

	@Update()
	int softdelete(TDeliveryEntity entity);

	@Delete
	int delete(TDeliveryEntity entity);

}
