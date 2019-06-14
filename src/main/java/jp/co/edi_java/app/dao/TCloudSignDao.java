package jp.co.edi_java.app.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.TCloudSignEntity;

@Dao
@ConfigAutowireable
public interface TCloudSignDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	TCloudSignEntity select(String fileId);

	@Select
	List<TCloudSignEntity> selectNotAgreeList(String formType);

	@Insert(excludeNull = true)
    int insert(TCloudSignEntity entity);

	@Update(excludeNull = true)
	int update(TCloudSignEntity entity);

	@Update(excludeNull = true)
	int delete(TCloudSignEntity entity);

}
