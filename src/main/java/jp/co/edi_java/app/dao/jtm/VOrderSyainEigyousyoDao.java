package jp.co.edi_java.app.dao.jtm;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.config.db.jtm.JtmConfigAutowireable;
import jp.co.edi_java.app.entity.jtm.VOrderSyainEigyousyoEntity;

@Dao
@JtmConfigAutowireable
public interface VOrderSyainEigyousyoDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	int count();

	@Select
	List<VOrderSyainEigyousyoEntity> selectAll();

	@Select
	List<VOrderSyainEigyousyoEntity> selectPaging(int from, int to);

}
