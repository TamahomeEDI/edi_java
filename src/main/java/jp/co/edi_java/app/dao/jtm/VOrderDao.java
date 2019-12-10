package jp.co.edi_java.app.dao.jtm;

import java.sql.Date;
import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.config.db.jtm.JtmConfigAutowireable;
import jp.co.edi_java.app.entity.jtm.VOrderEntity;

@Dao
@JtmConfigAutowireable
public interface VOrderDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	int count(Date prevDate);


	@Select
	List<VOrderEntity> selectAll();

	@Select
	List<VOrderEntity> selectByDate(Date prevDate);
	//List<VOrderEntity> selectByDate(Date prevDate, int from, int to);

	@Select
	List<VOrderEntity> selectListByOrderNumber(List<String> orderNumberList);

}
