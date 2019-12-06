package jp.co.edi_java.app.dao.jtm;

import org.seasar.doma.Dao;
import org.seasar.doma.Update;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.config.db.jtm.JtmConfigAutowireable;

@Dao
@JtmConfigAutowireable
public interface DHattyuuKanriDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Update(sqlFile = true, ignoreVersion = true)
	int updateHattyuuDate(String orderDate, String syainCode, String koujiCode, String orderNumber, int jtmHattyuuSyubetuFlg, int jtmHattyuuSeqNo, int jtmKanriSeqNo);

	@Update(sqlFile = true, ignoreVersion = true)
	int commit();

}
