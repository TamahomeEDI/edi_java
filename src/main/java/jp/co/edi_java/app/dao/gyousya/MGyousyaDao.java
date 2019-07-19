package jp.co.edi_java.app.dao.gyousya;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.dto.GyousyaAccountDto;
import jp.co.edi_java.app.dto.GyousyaDto;
import jp.co.edi_java.app.entity.gyousya.MGyousyaEntity;

@Dao
@ConfigAutowireable
public interface MGyousyaDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	MGyousyaEntity select(String gyousyaCode);

	@Select
	List<MGyousyaEntity> selectListByMultiCode(List<String> gyousyaCodeList);

	@Select
	List<MGyousyaEntity> selectList(String eigyousyoCode);

	@Select
	List<GyousyaDto> selectListByEigyousyoCode(String eigyousyoCode, String torihikiStatus);

	@Select
	List<MGyousyaEntity> selectAll();

	@Select
	List<MGyousyaEntity> selectListBySearch(String gyousyaCode, String eigyousyoCode);

	@Select
	int countByAccount(String gyousyaName, String saimokuKousyuCode, String registKbn);

	@Select
	List<GyousyaAccountDto> selectListByAccount(String gyousyaName, String saimokuKousyuCode, String registKbn, int limit, int offset);

	@Insert(excludeNull = true)
    int insert(MGyousyaEntity entity);

	@Update(excludeNull = true)
	int update(MGyousyaEntity entity);

	@Delete
	int delete(MGyousyaEntity entity);


}
