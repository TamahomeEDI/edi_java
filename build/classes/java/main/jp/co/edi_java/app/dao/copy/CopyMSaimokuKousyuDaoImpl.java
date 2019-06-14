package jp.co.edi_java.app.dao.copy;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:58.782+0900")
public class CopyMSaimokuKousyuDaoImpl extends org.seasar.doma.internal.jdbc.dao.AbstractDao implements jp.co.edi_java.app.dao.copy.CopyMSaimokuKousyuDao {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.copy.CopyMSaimokuKousyuDao.class, "select", java.lang.String.class);

    private static final java.lang.reflect.Method __method2 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.copy.CopyMSaimokuKousyuDao.class, "insertAll");

    private static final java.lang.reflect.Method __method3 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.copy.CopyMSaimokuKousyuDao.class, "deleteAll");

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    public CopyMSaimokuKousyuDaoImpl(org.seasar.doma.jdbc.Config config) {
        super(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getInjectedConfig() {
        entering("jp.co.edi_java.app.dao.copy.CopyMSaimokuKousyuDaoImpl", "getInjectedConfig");
        try {
            org.seasar.doma.jdbc.Config __result = jp.co.edi_java.app.dao.copy.CopyMSaimokuKousyuDao.super.getInjectedConfig();
            exiting("jp.co.edi_java.app.dao.copy.CopyMSaimokuKousyuDaoImpl", "getInjectedConfig", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.copy.CopyMSaimokuKousyuDaoImpl", "getInjectedConfig", __e);
            throw __e;
        }
    }

    @Override
    public jp.co.edi_java.app.entity.copy.CopyMSaimokuKousyuEntity select(java.lang.String saimokuKousyuCode) {
        entering("jp.co.edi_java.app.dao.copy.CopyMSaimokuKousyuDaoImpl", "select", saimokuKousyuCode);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method1);
            __query.setMethod(__method1);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/copy/CopyMSaimokuKousyuDao/select.sql");
            __query.setEntityType(jp.co.edi_java.app.entity.copy._CopyMSaimokuKousyuEntity.getSingletonInternal());
            __query.addParameter("saimokuKousyuCode", java.lang.String.class, saimokuKousyuCode);
            __query.setCallerClassName("jp.co.edi_java.app.dao.copy.CopyMSaimokuKousyuDaoImpl");
            __query.setCallerMethodName("select");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<jp.co.edi_java.app.entity.copy.CopyMSaimokuKousyuEntity> __command = getCommandImplementors().createSelectCommand(__method1, __query, new org.seasar.doma.internal.jdbc.command.EntitySingleResultHandler<jp.co.edi_java.app.entity.copy.CopyMSaimokuKousyuEntity>(jp.co.edi_java.app.entity.copy._CopyMSaimokuKousyuEntity.getSingletonInternal()));
            jp.co.edi_java.app.entity.copy.CopyMSaimokuKousyuEntity __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.copy.CopyMSaimokuKousyuDaoImpl", "select", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.copy.CopyMSaimokuKousyuDaoImpl", "select", __e);
            throw __e;
        }
    }

    @Override
    public int insertAll() {
        entering("jp.co.edi_java.app.dao.copy.CopyMSaimokuKousyuDaoImpl", "insertAll");
        try {
            org.seasar.doma.jdbc.query.SqlFileInsertQuery __query = getQueryImplementors().createSqlFileInsertQuery(__method2);
            __query.setMethod(__method2);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/copy/CopyMSaimokuKousyuDao/insertAll.sql");
            __query.setCallerClassName("jp.co.edi_java.app.dao.copy.CopyMSaimokuKousyuDaoImpl");
            __query.setCallerMethodName("insertAll");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setNullExcluded(false);
            __query.setIncludedPropertyNames();
            __query.setExcludedPropertyNames();
            __query.prepare();
            org.seasar.doma.jdbc.command.InsertCommand __command = getCommandImplementors().createInsertCommand(__method2, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.copy.CopyMSaimokuKousyuDaoImpl", "insertAll", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.copy.CopyMSaimokuKousyuDaoImpl", "insertAll", __e);
            throw __e;
        }
    }

    @Override
    public int deleteAll() {
        entering("jp.co.edi_java.app.dao.copy.CopyMSaimokuKousyuDaoImpl", "deleteAll");
        try {
            org.seasar.doma.jdbc.query.SqlFileDeleteQuery __query = getQueryImplementors().createSqlFileDeleteQuery(__method3);
            __query.setMethod(__method3);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/copy/CopyMSaimokuKousyuDao/deleteAll.sql");
            __query.setCallerClassName("jp.co.edi_java.app.dao.copy.CopyMSaimokuKousyuDaoImpl");
            __query.setCallerMethodName("deleteAll");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setVersionIgnored(true);
            __query.setOptimisticLockExceptionSuppressed(false);
            __query.prepare();
            org.seasar.doma.jdbc.command.DeleteCommand __command = getCommandImplementors().createDeleteCommand(__method3, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.copy.CopyMSaimokuKousyuDaoImpl", "deleteAll", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.copy.CopyMSaimokuKousyuDaoImpl", "deleteAll", __e);
            throw __e;
        }
    }

}
