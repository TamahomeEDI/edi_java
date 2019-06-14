package jp.co.edi_java.app.entity.listener;


import java.sql.Timestamp;

import org.seasar.doma.jdbc.entity.EntityListener;
import org.seasar.doma.jdbc.entity.PreDeleteContext;
import org.seasar.doma.jdbc.entity.PreInsertContext;
import org.seasar.doma.jdbc.entity.PreUpdateContext;

import jp.co.edi_java.app.entity.BaseEntity;
import jp.co.keepalive.springbootfw.util.lang.StringUtils;

public class BaseEntityListener<T extends BaseEntity> implements EntityListener<T> {

	@Override
	public void preInsert(T entity, PreInsertContext<T> context) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
        entity.updateDate = now;
        entity.insertDate = now;
        if(StringUtils.isNullString(entity.insertUser)) entity.insertUser = BaseEntity.DEFAULT_USER_NAME;
        if(StringUtils.isNullString(entity.updateUser)) entity.updateUser = BaseEntity.DEFAULT_USER_NAME;
    }

	@Override
    public void preUpdate(T entity, PreUpdateContext<T> context) {
        entity.updateDate = new Timestamp(System.currentTimeMillis());
    }

	@Override
    public void preDelete(T entity, PreDeleteContext<T> context) {
		entity.deleteDate = new Timestamp(System.currentTimeMillis());
        entity.deleteFlg = "1";
    }


}
