package jp.co.edi_java.app.dao;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:59.129+0900")
public class TKaikeiKijunDaoImpl extends org.seasar.doma.internal.jdbc.dao.AbstractDao implements jp.co.edi_java.app.dao.TKaikeiKijunDao {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.TKaikeiKijunDao.class, "select", java.lang.String.class);

    private static final java.lang.reflect.Method __method2 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.TKaikeiKijunDao.class, "selectByHashCode", java.lang.String.class);

    private static final java.lang.reflect.Method __method3 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.TKaikeiKijunDao.class, "selectAll");

    private static final java.lang.reflect.Method __method4 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.TKaikeiKijunDao.class, "update", jp.co.edi_java.app.entity.TKaikeiKijunEntity.class);

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    public TKaikeiKijunDaoImpl(org.seasar.doma.jdbc.Config config) {
        super(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getInjectedConfig() {
        entering("jp.co.edi_java.app.dao.TKaikeiKijunDaoImpl", "getInjectedConfig");
        try {
            org.seasar.doma.jdbc.Config __result = jp.co.edi_java.app.dao.TKaikeiKijunDao.super.getInjectedConfig();
            exiting("jp.co.edi_java.app.dao.TKaikeiKijunDaoImpl", "getInjectedConfig", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.TKaikeiKijunDaoImpl", "getInjectedConfig", __e);
            throw __e;
        }
    }

    @Override
    public jp.co.edi_java.app.entity.TKaikeiKijunEntity select(java.lang.String gyousyaCode) {
        entering("jp.co.edi_java.app.dao.TKaikeiKijunDaoImpl", "select", gyousyaCode);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method1);
            __query.setMethod(__method1);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/TKaikeiKijunDao/select.sql");
            __query.setEntityType(jp.co.edi_java.app.entity._TKaikeiKijunEntity.getSingletonInternal());
            __query.addParameter("gyousyaCode", java.lang.String.class, gyousyaCode);
            __query.setCallerClassName("jp.co.edi_java.app.dao.TKaikeiKijunDaoImpl");
            __query.setCallerMethodName("select");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<jp.co.edi_java.app.entity.TKaikeiKijunEntity> __command = getCommandImplementors().createSelectCommand(__method1, __query, new org.seasar.doma.internal.jdbc.command.EntitySingleResultHandler<jp.co.edi_java.app.entity.TKaikeiKijunEntity>(jp.co.edi_java.app.entity._TKaikeiKijunEntity.getSingletonInternal()));
            jp.co.edi_java.app.entity.TKaikeiKijunEntity __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.TKaikeiKijunDaoImpl", "select", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.TKaikeiKijunDaoImpl", "select", __e);
            throw __e;
        }
    }

    @Override
    public jp.co.edi_java.app.entity.TKaikeiKijunEntity selectByHashCode(java.lang.String hashCode) {
        entering("jp.co.edi_java.app.dao.TKaikeiKijunDaoImpl", "selectByHashCode", hashCode);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method2);
            __query.setMethod(__method2);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/TKaikeiKijunDao/selectByHashCode.sql");
            __query.setEntityType(jp.co.edi_java.app.entity._TKaikeiKijunEntity.getSingletonInternal());
            __query.addParameter("hashCode", java.lang.String.class, hashCode);
            __query.setCallerClassName("jp.co.edi_java.app.dao.TKaikeiKijunDaoImpl");
            __query.setCallerMethodName("selectByHashCode");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<jp.co.edi_java.app.entity.TKaikeiKijunEntity> __command = getCommandImplementors().createSelectCommand(__method2, __query, new org.seasar.doma.internal.jdbc.command.EntitySingleResultHandler<jp.co.edi_java.app.entity.TKaikeiKijunEntity>(jp.co.edi_java.app.entity._TKaikeiKijunEntity.getSingletonInternal()));
            jp.co.edi_java.app.entity.TKaikeiKijunEntity __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.TKaikeiKijunDaoImpl", "selectByHashCode", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.TKaikeiKijunDaoImpl", "selectByHashCode", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.entity.TKaikeiKijunEntity> selectAll() {
        entering("jp.co.edi_java.app.dao.TKaikeiKijunDaoImpl", "selectAll");
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method3);
            __query.setMethod(__method3);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/TKaikeiKijunDao/selectAll.sql");
            __query.setEntityType(jp.co.edi_java.app.entity._TKaikeiKijunEntity.getSingletonInternal());
            __query.setCallerClassName("jp.co.edi_java.app.dao.TKaikeiKijunDaoImpl");
            __query.setCallerMethodName("selectAll");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.entity.TKaikeiKijunEntity>> __command = getCommandImplementors().createSelectCommand(__method3, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.entity.TKaikeiKijunEntity>(jp.co.edi_java.app.entity._TKaikeiKijunEntity.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.entity.TKaikeiKijunEntity> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.TKaikeiKijunDaoImpl", "selectAll", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.TKaikeiKijunDaoImpl", "selectAll", __e);
            throw __e;
        }
    }

    @Override
    public int update(jp.co.edi_java.app.entity.TKaikeiKijunEntity entity) {
        entering("jp.co.edi_java.app.dao.TKaikeiKijunDaoImpl", "update", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoUpdateQuery<jp.co.edi_java.app.entity.TKaikeiKijunEntity> __query = getQueryImplementors().createAutoUpdateQuery(__method4, jp.co.edi_java.app.entity._TKaikeiKijunEntity.getSingletonInternal());
            __query.setMethod(__method4);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.TKaikeiKijunDaoImpl");
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
            org.seasar.doma.jdbc.command.UpdateCommand __command = getCommandImplementors().createUpdateCommand(__method4, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.TKaikeiKijunDaoImpl", "update", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.TKaikeiKijunDaoImpl", "update", __e);
            throw __e;
        }
    }

}
