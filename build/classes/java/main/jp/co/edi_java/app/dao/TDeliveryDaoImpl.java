package jp.co.edi_java.app.dao;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:59.097+0900")
public class TDeliveryDaoImpl extends org.seasar.doma.internal.jdbc.dao.AbstractDao implements jp.co.edi_java.app.dao.TDeliveryDao {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.TDeliveryDao.class, "select", java.lang.String.class);

    private static final java.lang.reflect.Method __method2 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.TDeliveryDao.class, "selectByOrderNumber", java.lang.String.class, int.class);

    private static final java.lang.reflect.Method __method3 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.TDeliveryDao.class, "selectAll", java.lang.String.class, java.lang.String.class);

    private static final java.lang.reflect.Method __method4 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.TDeliveryDao.class, "insert", jp.co.edi_java.app.entity.TDeliveryEntity.class);

    private static final java.lang.reflect.Method __method5 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.TDeliveryDao.class, "update", jp.co.edi_java.app.entity.TDeliveryEntity.class);

    private static final java.lang.reflect.Method __method6 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.TDeliveryDao.class, "delete", jp.co.edi_java.app.entity.TDeliveryEntity.class);

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    public TDeliveryDaoImpl(org.seasar.doma.jdbc.Config config) {
        super(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getInjectedConfig() {
        entering("jp.co.edi_java.app.dao.TDeliveryDaoImpl", "getInjectedConfig");
        try {
            org.seasar.doma.jdbc.Config __result = jp.co.edi_java.app.dao.TDeliveryDao.super.getInjectedConfig();
            exiting("jp.co.edi_java.app.dao.TDeliveryDaoImpl", "getInjectedConfig", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.TDeliveryDaoImpl", "getInjectedConfig", __e);
            throw __e;
        }
    }

    @Override
    public jp.co.edi_java.app.entity.TDeliveryEntity select(java.lang.String deliveryNumber) {
        entering("jp.co.edi_java.app.dao.TDeliveryDaoImpl", "select", deliveryNumber);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method1);
            __query.setMethod(__method1);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/TDeliveryDao/select.sql");
            __query.setEntityType(jp.co.edi_java.app.entity._TDeliveryEntity.getSingletonInternal());
            __query.addParameter("deliveryNumber", java.lang.String.class, deliveryNumber);
            __query.setCallerClassName("jp.co.edi_java.app.dao.TDeliveryDaoImpl");
            __query.setCallerMethodName("select");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<jp.co.edi_java.app.entity.TDeliveryEntity> __command = getCommandImplementors().createSelectCommand(__method1, __query, new org.seasar.doma.internal.jdbc.command.EntitySingleResultHandler<jp.co.edi_java.app.entity.TDeliveryEntity>(jp.co.edi_java.app.entity._TDeliveryEntity.getSingletonInternal()));
            jp.co.edi_java.app.entity.TDeliveryEntity __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.TDeliveryDaoImpl", "select", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.TDeliveryDaoImpl", "select", __e);
            throw __e;
        }
    }

    @Override
    public jp.co.edi_java.app.entity.TDeliveryEntity selectByOrderNumber(java.lang.String orderNumber, int deliveryCount) {
        entering("jp.co.edi_java.app.dao.TDeliveryDaoImpl", "selectByOrderNumber", orderNumber, deliveryCount);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method2);
            __query.setMethod(__method2);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/TDeliveryDao/selectByOrderNumber.sql");
            __query.setEntityType(jp.co.edi_java.app.entity._TDeliveryEntity.getSingletonInternal());
            __query.addParameter("orderNumber", java.lang.String.class, orderNumber);
            __query.addParameter("deliveryCount", int.class, deliveryCount);
            __query.setCallerClassName("jp.co.edi_java.app.dao.TDeliveryDaoImpl");
            __query.setCallerMethodName("selectByOrderNumber");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<jp.co.edi_java.app.entity.TDeliveryEntity> __command = getCommandImplementors().createSelectCommand(__method2, __query, new org.seasar.doma.internal.jdbc.command.EntitySingleResultHandler<jp.co.edi_java.app.entity.TDeliveryEntity>(jp.co.edi_java.app.entity._TDeliveryEntity.getSingletonInternal()));
            jp.co.edi_java.app.entity.TDeliveryEntity __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.TDeliveryDaoImpl", "selectByOrderNumber", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.TDeliveryDaoImpl", "selectByOrderNumber", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.entity.TDeliveryEntity> selectAll(java.lang.String orderNumber, java.lang.String remandFlg) {
        entering("jp.co.edi_java.app.dao.TDeliveryDaoImpl", "selectAll", orderNumber, remandFlg);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method3);
            __query.setMethod(__method3);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/TDeliveryDao/selectAll.sql");
            __query.setEntityType(jp.co.edi_java.app.entity._TDeliveryEntity.getSingletonInternal());
            __query.addParameter("orderNumber", java.lang.String.class, orderNumber);
            __query.addParameter("remandFlg", java.lang.String.class, remandFlg);
            __query.setCallerClassName("jp.co.edi_java.app.dao.TDeliveryDaoImpl");
            __query.setCallerMethodName("selectAll");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.entity.TDeliveryEntity>> __command = getCommandImplementors().createSelectCommand(__method3, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.entity.TDeliveryEntity>(jp.co.edi_java.app.entity._TDeliveryEntity.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.entity.TDeliveryEntity> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.TDeliveryDaoImpl", "selectAll", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.TDeliveryDaoImpl", "selectAll", __e);
            throw __e;
        }
    }

    @Override
    public int insert(jp.co.edi_java.app.entity.TDeliveryEntity entity) {
        entering("jp.co.edi_java.app.dao.TDeliveryDaoImpl", "insert", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoInsertQuery<jp.co.edi_java.app.entity.TDeliveryEntity> __query = getQueryImplementors().createAutoInsertQuery(__method4, jp.co.edi_java.app.entity._TDeliveryEntity.getSingletonInternal());
            __query.setMethod(__method4);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.TDeliveryDaoImpl");
            __query.setCallerMethodName("insert");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setNullExcluded(true);
            __query.setIncludedPropertyNames();
            __query.setExcludedPropertyNames();
            __query.prepare();
            org.seasar.doma.jdbc.command.InsertCommand __command = getCommandImplementors().createInsertCommand(__method4, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.TDeliveryDaoImpl", "insert", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.TDeliveryDaoImpl", "insert", __e);
            throw __e;
        }
    }

    @Override
    public int update(jp.co.edi_java.app.entity.TDeliveryEntity entity) {
        entering("jp.co.edi_java.app.dao.TDeliveryDaoImpl", "update", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoUpdateQuery<jp.co.edi_java.app.entity.TDeliveryEntity> __query = getQueryImplementors().createAutoUpdateQuery(__method5, jp.co.edi_java.app.entity._TDeliveryEntity.getSingletonInternal());
            __query.setMethod(__method5);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.TDeliveryDaoImpl");
            __query.setCallerMethodName("update");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setNullExcluded(true);
            __query.setVersionIgnored(false);
            __query.setIncludedPropertyNames();
            __query.setExcludedPropertyNames();
            __query.setUnchangedPropertyIncluded(false);
            __query.setOptimisticLockExceptionSuppressed(false);
            __query.prepare();
            org.seasar.doma.jdbc.command.UpdateCommand __command = getCommandImplementors().createUpdateCommand(__method5, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.TDeliveryDaoImpl", "update", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.TDeliveryDaoImpl", "update", __e);
            throw __e;
        }
    }

    @Override
    public int delete(jp.co.edi_java.app.entity.TDeliveryEntity entity) {
        entering("jp.co.edi_java.app.dao.TDeliveryDaoImpl", "delete", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoDeleteQuery<jp.co.edi_java.app.entity.TDeliveryEntity> __query = getQueryImplementors().createAutoDeleteQuery(__method6, jp.co.edi_java.app.entity._TDeliveryEntity.getSingletonInternal());
            __query.setMethod(__method6);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.TDeliveryDaoImpl");
            __query.setCallerMethodName("delete");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setVersionIgnored(false);
            __query.setOptimisticLockExceptionSuppressed(false);
            __query.prepare();
            org.seasar.doma.jdbc.command.DeleteCommand __command = getCommandImplementors().createDeleteCommand(__method6, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.TDeliveryDaoImpl", "delete", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.TDeliveryDaoImpl", "delete", __e);
            throw __e;
        }
    }

}
