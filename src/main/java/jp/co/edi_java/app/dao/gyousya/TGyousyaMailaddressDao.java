package jp.co.edi_java.app.dao.gyousya;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity;

@Dao
@ConfigAutowireable
public interface TGyousyaMailaddressDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
    TGyousyaMailaddressEntity select(String mailaddress);

	@Select
    TGyousyaMailaddressEntity selectPasswordForget(String gyousyaCode, String mailaddress);

	@Select
    List<TGyousyaMailaddressEntity> selectAll(String gyousyaCode);

	@Select
    List<TGyousyaMailaddressEntity> selectListByMultiCode(List<String> gyousyaCodeList);

	@Select
    int countByMailaddress(String mailaddress, String gyousyaCode);

	@Select
    List<String> selectList(String gyousyaCode);

	@Insert(excludeNull = true)
    int insert(TGyousyaMailaddressEntity entity);

	@Update(excludeNull = true)
	int update(TGyousyaMailaddressEntity entity);

	@Delete
	int delete(TGyousyaMailaddressEntity entity);

}
