package jp.co.edi_java.app.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.MKarekiEntity;

@Dao
@ConfigAutowireable
public interface MKarekiDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	List<MKarekiEntity> selectList(String fileCode);

	@Insert(excludeNull = true)
    int insert(MKarekiEntity entity);

	@Update(excludeNull = true)
    int update(MKarekiEntity entity);

}
