package jp.co.edi_java.app.dao;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:59.002+0900")
public class MKoujiDaoImpl extends org.seasar.doma.internal.jdbc.dao.AbstractDao implements jp.co.edi_java.app.dao.MKoujiDao {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.MKoujiDao.class, "select", java.lang.String.class);

    private static final java.lang.reflect.Method __method2 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.MKoujiDao.class, "selectList", java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class);

    private static final java.lang.reflect.Method __method3 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.MKoujiDao.class, "insert", jp.co.edi_java.app.entity.MKoujiEntity.class);

    private static final java.lang.reflect.Method __method4 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.MKoujiDao.class, "update", jp.co.edi_java.app.entity.MKoujiEntity.class);

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    public MKoujiDaoImpl(org.seasar.doma.jdbc.Config config) {
        super(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getInjectedConfig() {
        entering("jp.co.edi_java.app.dao.MKoujiDaoImpl", "getInjectedConfig");
        try {
            org.seasar.doma.jdbc.Config __result = jp.co.edi_java.app.dao.MKoujiDao.super.getInjectedConfig();
            exiting("jp.co.edi_java.app.dao.MKoujiDaoImpl", "getInjectedConfig", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.MKoujiDaoImpl", "getInjectedConfig", __e);
            throw __e;
        }
    }

    @Override
    public jp.co.edi_java.app.entity.MKoujiEntity select(java.lang.String koujiCode) {
        entering("jp.co.edi_java.app.dao.MKoujiDaoImpl", "select", koujiCode);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method1);
            __query.setMethod(__method1);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/MKoujiDao/select.sql");
            __query.setEntityType(jp.co.edi_java.app.entity._MKoujiEntity.getSingletonInternal());
            __query.addParameter("koujiCode", java.lang.String.class, koujiCode);
            __query.setCallerClassName("jp.co.edi_java.app.dao.MKoujiDaoImpl");
            __query.setCallerMethodName("select");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<jp.co.edi_java.app.entity.MKoujiEntity> __command = getCommandImplementors().createSelectCommand(__method1, __query, new org.seasar.doma.internal.jdbc.command.EntitySingleResultHandler<jp.co.edi_java.app.entity.MKoujiEntity>(jp.co.edi_java.app.entity._MKoujiEntity.getSingletonInternal()));
            jp.co.edi_java.app.entity.MKoujiEntity __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.MKoujiDaoImpl", "select", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.MKoujiDaoImpl", "select", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.entity.MKoujiEntity> selectList(java.lang.String eigyousyoCode, java.lang.String syainCode, java.lang.String kanseiKbn, java.lang.String projectTypeCode) {
        entering("jp.co.edi_java.app.dao.MKoujiDaoImpl", "selectList", eigyousyoCode, syainCode, kanseiKbn, projectTypeCode);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method2);
            __query.setMethod(__method2);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/MKoujiDao/selectList.sql");
            __query.setEntityType(jp.co.edi_java.app.entity._MKoujiEntity.getSingletonInternal());
            __query.addParameter("eigyousyoCode", java.lang.String.class, eigyousyoCode);
            __query.addParameter("syainCode", java.lang.String.class, syainCode);
            __query.addParameter("kanseiKbn", java.lang.String.class, kanseiKbn);
            __query.addParameter("projectTypeCode", java.lang.String.class, projectTypeCode);
            __query.setCallerClassName("jp.co.edi_java.app.dao.MKoujiDaoImpl");
            __query.setCallerMethodName("selectList");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.entity.MKoujiEntity>> __command = getCommandImplementors().createSelectCommand(__method2, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.entity.MKoujiEntity>(jp.co.edi_java.app.entity._MKoujiEntity.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.entity.MKoujiEntity> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.MKoujiDaoImpl", "selectList", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.MKoujiDaoImpl", "selectList", __e);
            throw __e;
        }
    }

    @Override
    public int insert(jp.co.edi_java.app.entity.MKoujiEntity entity) {
        entering("jp.co.edi_java.app.dao.MKoujiDaoImpl", "insert", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoInsertQuery<jp.co.edi_java.app.entity.MKoujiEntity> __query = getQueryImplementors().createAutoInsertQuery(__method3, jp.co.edi_java.app.entity._MKoujiEntity.getSingletonInternal());
            __query.setMethod(__method3);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.MKoujiDaoImpl");
            __query.setCallerMethodName("insert");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setNullExcluded(true);
            __query.setIncludedPropertyNames();
            __query.setExcludedPropertyNames();
            __query.prepare();
            org.seasar.doma.jdbc.command.InsertCommand __command = getCommandImplementors().createInsertCommand(__method3, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.MKoujiDaoImpl", "insert", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.MKoujiDaoImpl", "insert", __e);
            throw __e;
        }
    }

    @Override
    public int update(jp.co.edi_java.app.entity.MKoujiEntity entity) {
        entering("jp.co.edi_java.app.dao.MKoujiDaoImpl", "update", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoUpdateQuery<jp.co.edi_java.app.entity.MKoujiEntity> __query = getQueryImplementors().createAutoUpdateQuery(__method4, jp.co.edi_java.app.entity._MKoujiEntity.getSingletonInternal());
            __query.setMethod(__method4);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.MKoujiDaoImpl");
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
            exiting("jp.co.edi_java.app.dao.MKoujiDaoImpl", "update", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.MKoujiDaoImpl", "update", __e);
            throw __e;
        }
    }

}
