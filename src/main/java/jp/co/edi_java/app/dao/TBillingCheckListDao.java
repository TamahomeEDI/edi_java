package jp.co.edi_java.app.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.TBillingCheckListEntity;

@Dao
@ConfigAutowireable
public interface TBillingCheckListDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	List<TBillingCheckListEntity> selectList(TBillingCheckListEntity entity);

	@Insert(excludeNull = true)
    int insert(TBillingCheckListEntity entity);

	@Update()
	int update(TBillingCheckListEntity entity);

	@Delete
	int delete(TBillingCheckListEntity entity);

}
