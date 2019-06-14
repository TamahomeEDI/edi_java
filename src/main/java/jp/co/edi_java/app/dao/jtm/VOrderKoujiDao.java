package jp.co.edi_java.app.dao.jtm;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.config.db.jtm.JtmConfigAutowireable;
import jp.co.edi_java.app.entity.jtm.VOrderKoujiEntity;

@Dao
@JtmConfigAutowireable
public interface VOrderKoujiDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	int count();

	@Select
	List<VOrderKoujiEntity> selectAll();

	@Select
	List<VOrderKoujiEntity> selectPaging(int from, int to);

}
