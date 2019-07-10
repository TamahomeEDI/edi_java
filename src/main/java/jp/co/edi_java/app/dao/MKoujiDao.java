package jp.co.edi_java.app.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.MKoujiEntity;

@Dao
@ConfigAutowireable
public interface MKoujiDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	MKoujiEntity select(String koujiCode);

	@Select
	List<MKoujiEntity> selectListByMultiCode(List<String> koujiCodeList);

	@Select
	List<MKoujiEntity> selectList(String eigyousyoCode, String syainCode, String kanseiKbn, String projectTypeCode);

	@Insert(excludeNull = true)
    int insert(MKoujiEntity entity);

	@Update(excludeNull = true)
    int update(MKoujiEntity entity);

}
