package jp.co.edi_java.app.dao.jtm;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.config.db.jtm.JtmConfigAutowireable;
import jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity;

@Dao
@JtmConfigAutowireable
public interface VOrderEigyousyoGroupDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	int count();

	@Select
	List<VOrderEigyousyoGroupEntity> selectAll();

	@Select
	List<VOrderEigyousyoGroupEntity> selectPaging(int from, int to);

}
