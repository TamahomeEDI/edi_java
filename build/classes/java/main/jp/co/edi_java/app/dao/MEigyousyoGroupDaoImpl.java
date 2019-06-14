package jp.co.edi_java.app.dao;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:58.987+0900")
public class MEigyousyoGroupDaoImpl extends org.seasar.doma.internal.jdbc.dao.AbstractDao implements jp.co.edi_java.app.dao.MEigyousyoGroupDao {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.MEigyousyoGroupDao.class, "select", java.lang.String.class);

    private static final java.lang.reflect.Method __method2 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.MEigyousyoGroupDao.class, "selectAll");

    private static final java.lang.reflect.Method __method3 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.MEigyousyoGroupDao.class, "selectList", java.util.List.class);

    private static final java.lang.reflect.Method __method4 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.MEigyousyoGroupDao.class, "insert", jp.co.edi_java.app.entity.MEigyousyoGroupEntity.class);

    private static final java.lang.reflect.Method __method5 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.MEigyousyoGroupDao.class, "update", jp.co.edi_java.app.entity.MEigyousyoGroupEntity.class);

    private static final java.lang.reflect.Method __method6 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.MEigyousyoGroupDao.class, "deleteAll");

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    public MEigyousyoGroupDaoImpl(org.seasar.doma.jdbc.Config config) {
        super(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getInjectedConfig() {
        entering("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl", "getInjectedConfig");
        try {
            org.seasar.doma.jdbc.Config __result = jp.co.edi_java.app.dao.MEigyousyoGroupDao.super.getInjectedConfig();
            exiting("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl", "getInjectedConfig", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl", "getInjectedConfig", __e);
            throw __e;
        }
    }

    @Override
    public jp.co.edi_java.app.entity.MEigyousyoGroupEntity select(java.lang.String eigyousyoGroupCode) {
        entering("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl", "select", eigyousyoGroupCode);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method1);
            __query.setMethod(__method1);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/MEigyousyoGroupDao/select.sql");
            __query.setEntityType(jp.co.edi_java.app.entity._MEigyousyoGroupEntity.getSingletonInternal());
            __query.addParameter("eigyousyoGroupCode", java.lang.String.class, eigyousyoGroupCode);
            __query.setCallerClassName("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl");
            __query.setCallerMethodName("select");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<jp.co.edi_java.app.entity.MEigyousyoGroupEntity> __command = getCommandImplementors().createSelectCommand(__method1, __query, new org.seasar.doma.internal.jdbc.command.EntitySingleResultHandler<jp.co.edi_java.app.entity.MEigyousyoGroupEntity>(jp.co.edi_java.app.entity._MEigyousyoGroupEntity.getSingletonInternal()));
            jp.co.edi_java.app.entity.MEigyousyoGroupEntity __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl", "select", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl", "select", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.entity.MEigyousyoGroupEntity> selectAll() {
        entering("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl", "selectAll");
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method2);
            __query.setMethod(__method2);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/MEigyousyoGroupDao/selectAll.sql");
            __query.setEntityType(jp.co.edi_java.app.entity._MEigyousyoGroupEntity.getSingletonInternal());
            __query.setCallerClassName("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl");
            __query.setCallerMethodName("selectAll");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.entity.MEigyousyoGroupEntity>> __command = getCommandImplementors().createSelectCommand(__method2, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.entity.MEigyousyoGroupEntity>(jp.co.edi_java.app.entity._MEigyousyoGroupEntity.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.entity.MEigyousyoGroupEntity> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl", "selectAll", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl", "selectAll", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.entity.MEigyousyoGroupEntity> selectList(java.util.List<java.lang.String> groupCodeList) {
        entering("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl", "selectList", groupCodeList);
        try {
            if (groupCodeList == null) {
                throw new org.seasar.doma.DomaNullPointerException("groupCodeList");
            }
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method3);
            __query.setMethod(__method3);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/MEigyousyoGroupDao/selectList.sql");
            __query.setEntityType(jp.co.edi_java.app.entity._MEigyousyoGroupEntity.getSingletonInternal());
            __query.addParameter("groupCodeList", java.util.List.class, groupCodeList);
            __query.setCallerClassName("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl");
            __query.setCallerMethodName("selectList");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.entity.MEigyousyoGroupEntity>> __command = getCommandImplementors().createSelectCommand(__method3, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.entity.MEigyousyoGroupEntity>(jp.co.edi_java.app.entity._MEigyousyoGroupEntity.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.entity.MEigyousyoGroupEntity> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl", "selectList", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl", "selectList", __e);
            throw __e;
        }
    }

    @Override
    public int insert(jp.co.edi_java.app.entity.MEigyousyoGroupEntity entity) {
        entering("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl", "insert", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoInsertQuery<jp.co.edi_java.app.entity.MEigyousyoGroupEntity> __query = getQueryImplementors().createAutoInsertQuery(__method4, jp.co.edi_java.app.entity._MEigyousyoGroupEntity.getSingletonInternal());
            __query.setMethod(__method4);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl");
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
            exiting("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl", "insert", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl", "insert", __e);
            throw __e;
        }
    }

    @Override
    public int update(jp.co.edi_java.app.entity.MEigyousyoGroupEntity entity) {
        entering("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl", "update", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoUpdateQuery<jp.co.edi_java.app.entity.MEigyousyoGroupEntity> __query = getQueryImplementors().createAutoUpdateQuery(__method5, jp.co.edi_java.app.entity._MEigyousyoGroupEntity.getSingletonInternal());
            __query.setMethod(__method5);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl");
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
            exiting("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl", "update", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl", "update", __e);
            throw __e;
        }
    }

    @Override
    public int deleteAll() {
        entering("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl", "deleteAll");
        try {
            org.seasar.doma.jdbc.query.SqlFileDeleteQuery __query = getQueryImplementors().createSqlFileDeleteQuery(__method6);
            __query.setMethod(__method6);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/MEigyousyoGroupDao/deleteAll.sql");
            __query.setCallerClassName("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl");
            __query.setCallerMethodName("deleteAll");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setVersionIgnored(true);
            __query.setOptimisticLockExceptionSuppressed(false);
            __query.prepare();
            org.seasar.doma.jdbc.command.DeleteCommand __command = getCommandImplementors().createDeleteCommand(__method6, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl", "deleteAll", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.MEigyousyoGroupDaoImpl", "deleteAll", __e);
            throw __e;
        }
    }

}
