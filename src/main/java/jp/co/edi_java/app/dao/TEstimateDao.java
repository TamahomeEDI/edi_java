package jp.co.edi_java.app.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.dto.EstimateDto;
import jp.co.edi_java.app.dto.MailEstimateRegistDto;
import jp.co.edi_java.app.entity.TEstimateEntity;

@Dao
@ConfigAutowireable
public interface TEstimateDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	TEstimateEntity select(String estimateNumber);

	@Select
	List<EstimateDto> selectListByEstimateRequestNumber(String estimateRequestNumber);

	@Select
	MailEstimateRegistDto selectMailDto(String estimateNumber);

	@Insert(excludeNull = true)
    int insert(TEstimateEntity entity);

	@Update(excludeNull = true)
	int update(TEstimateEntity entity);

	@Delete
	int delete(TEstimateEntity entity);

}
