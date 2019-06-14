package jp.co.edi_java.app.dao.syain;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:59.065+0900")
public class MSyainDaoImpl extends org.seasar.doma.internal.jdbc.dao.AbstractDao implements jp.co.edi_java.app.dao.syain.MSyainDao {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.syain.MSyainDao.class, "select", java.lang.String.class);

    private static final java.lang.reflect.Method __method2 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.syain.MSyainDao.class, "selectListByEigyousyo", java.lang.String.class);

    private static final java.lang.reflect.Method __method3 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.syain.MSyainDao.class, "selectListByEigyousyoList", java.util.List.class);

    private static final java.lang.reflect.Method __method4 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.syain.MSyainDao.class, "selectByLogin", java.lang.String.class, java.lang.String.class);

    private static final java.lang.reflect.Method __method5 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.syain.MSyainDao.class, "insert", jp.co.edi_java.app.entity.syain.MSyainEntity.class);

    private static final java.lang.reflect.Method __method6 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.syain.MSyainDao.class, "update", jp.co.edi_java.app.entity.syain.MSyainEntity.class);

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    public MSyainDaoImpl(org.seasar.doma.jdbc.Config config) {
        super(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getInjectedConfig() {
        entering("jp.co.edi_java.app.dao.syain.MSyainDaoImpl", "getInjectedConfig");
        try {
            org.seasar.doma.jdbc.Config __result = jp.co.edi_java.app.dao.syain.MSyainDao.super.getInjectedConfig();
            exiting("jp.co.edi_java.app.dao.syain.MSyainDaoImpl", "getInjectedConfig", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.syain.MSyainDaoImpl", "getInjectedConfig", __e);
            throw __e;
        }
    }

    @Override
    public jp.co.edi_java.app.entity.syain.MSyainEntity select(java.lang.String syainCode) {
        entering("jp.co.edi_java.app.dao.syain.MSyainDaoImpl", "select", syainCode);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method1);
            __query.setMethod(__method1);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/syain/MSyainDao/select.sql");
            __query.setEntityType(jp.co.edi_java.app.entity.syain._MSyainEntity.getSingletonInternal());
            __query.addParameter("syainCode", java.lang.String.class, syainCode);
            __query.setCallerClassName("jp.co.edi_java.app.dao.syain.MSyainDaoImpl");
            __query.setCallerMethodName("select");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<jp.co.edi_java.app.entity.syain.MSyainEntity> __command = getCommandImplementors().createSelectCommand(__method1, __query, new org.seasar.doma.internal.jdbc.command.EntitySingleResultHandler<jp.co.edi_java.app.entity.syain.MSyainEntity>(jp.co.edi_java.app.entity.syain._MSyainEntity.getSingletonInternal()));
            jp.co.edi_java.app.entity.syain.MSyainEntity __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.syain.MSyainDaoImpl", "select", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.syain.MSyainDaoImpl", "select", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.entity.syain.MSyainEntity> selectListByEigyousyo(java.lang.String eigyousyoCode) {
        entering("jp.co.edi_java.app.dao.syain.MSyainDaoImpl", "selectListByEigyousyo", eigyousyoCode);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method2);
            __query.setMethod(__method2);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/syain/MSyainDao/selectListByEigyousyo.sql");
            __query.setEntityType(jp.co.edi_java.app.entity.syain._MSyainEntity.getSingletonInternal());
            __query.addParameter("eigyousyoCode", java.lang.String.class, eigyousyoCode);
            __query.setCallerClassName("jp.co.edi_java.app.dao.syain.MSyainDaoImpl");
            __query.setCallerMethodName("selectListByEigyousyo");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.entity.syain.MSyainEntity>> __command = getCommandImplementors().createSelectCommand(__method2, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.entity.syain.MSyainEntity>(jp.co.edi_java.app.entity.syain._MSyainEntity.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.entity.syain.MSyainEntity> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.syain.MSyainDaoImpl", "selectListByEigyousyo", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.syain.MSyainDaoImpl", "selectListByEigyousyo", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.entity.syain.MSyainEntity> selectListByEigyousyoList(java.util.List<java.lang.String> eigyousyoCodeList) {
        entering("jp.co.edi_java.app.dao.syain.MSyainDaoImpl", "selectListByEigyousyoList", eigyousyoCodeList);
        try {
            if (eigyousyoCodeList == null) {
                throw new org.seasar.doma.DomaNullPointerException("eigyousyoCodeList");
            }
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method3);
            __query.setMethod(__method3);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/syain/MSyainDao/selectListByEigyousyoList.sql");
            __query.setEntityType(jp.co.edi_java.app.entity.syain._MSyainEntity.getSingletonInternal());
            __query.addParameter("eigyousyoCodeList", java.util.List.class, eigyousyoCodeList);
            __query.setCallerClassName("jp.co.edi_java.app.dao.syain.MSyainDaoImpl");
            __query.setCallerMethodName("selectListByEigyousyoList");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.entity.syain.MSyainEntity>> __command = getCommandImplementors().createSelectCommand(__method3, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.entity.syain.MSyainEntity>(jp.co.edi_java.app.entity.syain._MSyainEntity.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.entity.syain.MSyainEntity> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.syain.MSyainDaoImpl", "selectListByEigyousyoList", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.syain.MSyainDaoImpl", "selectListByEigyousyoList", __e);
            throw __e;
        }
    }

    @Override
    public jp.co.edi_java.app.entity.syain.MSyainEntity selectByLogin(java.lang.String syainCode, java.lang.String password) {
        entering("jp.co.edi_java.app.dao.syain.MSyainDaoImpl", "selectByLogin", syainCode, password);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method4);
            __query.setMethod(__method4);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/syain/MSyainDao/selectByLogin.sql");
            __query.setEntityType(jp.co.edi_java.app.entity.syain._MSyainEntity.getSingletonInternal());
            __query.addParameter("syainCode", java.lang.String.class, syainCode);
            __query.addParameter("password", java.lang.String.class, password);
            __query.setCallerClassName("jp.co.edi_java.app.dao.syain.MSyainDaoImpl");
            __query.setCallerMethodName("selectByLogin");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<jp.co.edi_java.app.entity.syain.MSyainEntity> __command = getCommandImplementors().createSelectCommand(__method4, __query, new org.seasar.doma.internal.jdbc.command.EntitySingleResultHandler<jp.co.edi_java.app.entity.syain.MSyainEntity>(jp.co.edi_java.app.entity.syain._MSyainEntity.getSingletonInternal()));
            jp.co.edi_java.app.entity.syain.MSyainEntity __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.syain.MSyainDaoImpl", "selectByLogin", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.syain.MSyainDaoImpl", "selectByLogin", __e);
            throw __e;
        }
    }

    @Override
    public int insert(jp.co.edi_java.app.entity.syain.MSyainEntity entity) {
        entering("jp.co.edi_java.app.dao.syain.MSyainDaoImpl", "insert", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoInsertQuery<jp.co.edi_java.app.entity.syain.MSyainEntity> __query = getQueryImplementors().createAutoInsertQuery(__method5, jp.co.edi_java.app.entity.syain._MSyainEntity.getSingletonInternal());
            __query.setMethod(__method5);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.syain.MSyainDaoImpl");
            __query.setCallerMethodName("insert");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setNullExcluded(true);
            __query.setIncludedPropertyNames();
            __query.setExcludedPropertyNames();
            __query.prepare();
            org.seasar.doma.jdbc.command.InsertCommand __command = getCommandImplementors().createInsertCommand(__method5, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.syain.MSyainDaoImpl", "insert", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.syain.MSyainDaoImpl", "insert", __e);
            throw __e;
        }
    }

    @Override
    public int update(jp.co.edi_java.app.entity.syain.MSyainEntity entity) {
        entering("jp.co.edi_java.app.dao.syain.MSyainDaoImpl", "update", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoUpdateQuery<jp.co.edi_java.app.entity.syain.MSyainEntity> __query = getQueryImplementors().createAutoUpdateQuery(__method6, jp.co.edi_java.app.entity.syain._MSyainEntity.getSingletonInternal());
            __query.setMethod(__method6);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.syain.MSyainDaoImpl");
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
            org.seasar.doma.jdbc.command.UpdateCommand __command = getCommandImplementors().createUpdateCommand(__method6, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.syain.MSyainDaoImpl", "update", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.syain.MSyainDaoImpl", "update", __e);
            throw __e;
        }
    }

}
