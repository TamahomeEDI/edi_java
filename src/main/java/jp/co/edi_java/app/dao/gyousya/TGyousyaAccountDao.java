package jp.co.edi_java.app.dao.gyousya;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity;

@Dao
@ConfigAutowireable
public interface TGyousyaAccountDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	List<TGyousyaAccountEntity> selectAll();

	@Select
	TGyousyaAccountEntity select(String gyousyaCode);

	@Select
	List<TGyousyaAccountEntity> selectListByMultiCode(List<String> gyousyaCodeList);

	@Select
	TGyousyaAccountEntity selectByToken(String token);

	@Select
	TGyousyaAccountEntity selectByLogin(String gyousyaCode, String password);

	@Insert(excludeNull = true)
	int insert(TGyousyaAccountEntity entity);

	@Update(excludeNull = true)
	int update(TGyousyaAccountEntity entity);

}
