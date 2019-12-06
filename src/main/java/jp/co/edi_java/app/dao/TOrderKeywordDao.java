package jp.co.edi_java.app.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

@Dao
@ConfigAutowireable
public interface TOrderKeywordDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Insert(sqlFile = true)
    int insert(String orderNumber);

	@Delete(sqlFile = true, ignoreVersion = true)
	int delete(String orderNumber);

}
