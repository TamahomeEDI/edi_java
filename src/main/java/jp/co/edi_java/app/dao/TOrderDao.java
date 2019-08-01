package jp.co.edi_java.app.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.TOrderEntity;

@Dao
@ConfigAutowireable
public interface TOrderDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	TOrderEntity select(String orderNumber);

	@Select
	List<TOrderEntity> selectList(List<String> orderNumberList);

	@Select
	List<TOrderEntity> selectListByOrdered();

//	@Select
//	TOrderEntity search(String orderNumber, String cancelKbn);

	@Insert(excludeNull = true)
    int insert(TOrderEntity entity);

	@Update()
	int update(TOrderEntity entity);

	@Delete
	int delete(TOrderEntity entity);

}
