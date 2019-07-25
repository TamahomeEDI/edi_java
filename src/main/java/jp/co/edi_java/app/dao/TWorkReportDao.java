package jp.co.edi_java.app.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.entity.TWorkReportEntity;

@Dao
@ConfigAutowireable
public interface TWorkReportDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	TWorkReportEntity select(String workReportNumber);

	@Select
	TWorkReportEntity selectByOrderNumber(String orderNumber, int workReportCount);

	@Select
    List<TWorkReportEntity> selectAll(String orderNumber, String remandFlg);

	@Select
	List<TWorkReportEntity> selectUnconfirmList();

	@Insert(excludeNull = true)
    int insert(TWorkReportEntity entity);

	@Update(excludeNull = true)
	int update(TWorkReportEntity entity);

	@Delete
	int delete(TWorkReportEntity entity);

}
