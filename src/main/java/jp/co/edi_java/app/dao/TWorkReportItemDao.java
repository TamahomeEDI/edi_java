package jp.co.edi_java.app.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.TWorkReportItemEntity;

@Dao
@ConfigAutowireable
public interface TWorkReportItemDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
    List<TWorkReportItemEntity> selectAll(String workReportNumber);

	@Insert(excludeNull = true)
    int insert(TWorkReportItemEntity entity);

	@Update(excludeNull = true)
	int update(TWorkReportItemEntity entity);

	@Delete
	int delete(TWorkReportItemEntity entity);

}
