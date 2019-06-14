package jp.co.edi_java.app.dao.gyousya;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.gyousya.MGyousyaSaimokuEntity;

@Dao
@ConfigAutowireable
public interface MGyousyaSaimokuDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

//	@Select
//	List<MGyousyaSaimokuEntity> selectList(String gyousyaCode);

	@Insert(excludeNull = true)
    int insert(MGyousyaSaimokuEntity entity);

	@Update(excludeNull = true)
    int update(MGyousyaSaimokuEntity entity);


}
