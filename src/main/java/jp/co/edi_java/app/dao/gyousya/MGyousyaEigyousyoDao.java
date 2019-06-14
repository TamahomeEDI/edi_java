package jp.co.edi_java.app.dao.gyousya;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.MEigyousyoGroupEntity;
import jp.co.edi_java.app.entity.gyousya.MGyousyaEigyousyoEntity;

@Dao
@ConfigAutowireable
public interface MGyousyaEigyousyoDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	List<MGyousyaEigyousyoEntity> selectList(String gyousyaCode);

	@Select
	List<MEigyousyoGroupEntity> selectGroupList(String gyousyaCode);

	@Insert(excludeNull = true)
    int insert(MGyousyaEigyousyoEntity entity);

	@Update(excludeNull = true)
    int update(MGyousyaEigyousyoEntity entity);


}
