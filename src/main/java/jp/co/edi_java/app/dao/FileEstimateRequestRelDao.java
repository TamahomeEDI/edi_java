package jp.co.edi_java.app.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.FileEstimateRequestRelEntity;

@Dao
@ConfigAutowireable
public interface FileEstimateRequestRelDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	List<FileEstimateRequestRelEntity> selectList(String estimateRequestNumber);

	@Insert(excludeNull = true)
    int insert(FileEstimateRequestRelEntity entity);

}
