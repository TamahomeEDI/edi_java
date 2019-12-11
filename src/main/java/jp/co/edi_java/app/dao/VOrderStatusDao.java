package jp.co.edi_java.app.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.VOrderStatusEntity;

@Dao
@ConfigAutowireable
public interface VOrderStatusDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	VOrderStatusEntity select(String orderNumber);

	@Select
	List<VOrderStatusEntity> selectList(List<String> orderNumberList);

}
