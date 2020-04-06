package jp.co.edi_java.app.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.TLocalDocumentListEntity;

@Dao
@ConfigAutowireable
public interface TLocalDocumentListDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	List<TLocalDocumentListEntity> selectList(TLocalDocumentListEntity entity);

	@Insert(excludeNull = true)
    int insert(TLocalDocumentListEntity entity);

	@Update()
	int update(TLocalDocumentListEntity entity);

	@Delete
	int delete(TLocalDocumentListEntity entity);

}
