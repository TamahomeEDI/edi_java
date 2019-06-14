package jp.co.edi_java.app.dao.copy;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:58.773+0900")
public class CopyMKoujiDaoImpl extends org.seasar.doma.internal.jdbc.dao.AbstractDao implements jp.co.edi_java.app.dao.copy.CopyMKoujiDao {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.copy.CopyMKoujiDao.class, "select", java.lang.String.class);

    private static final java.lang.reflect.Method __method2 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.copy.CopyMKoujiDao.class, "insertAll");

    private static final java.lang.reflect.Method __method3 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.copy.CopyMKoujiDao.class, "deleteAll");

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    public CopyMKoujiDaoImpl(org.seasar.doma.jdbc.Config config) {
        super(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getInjectedConfig() {
        entering("jp.co.edi_java.app.dao.copy.CopyMKoujiDaoImpl", "getInjectedConfig");
        try {
            org.seasar.doma.jdbc.Config __result = jp.co.edi_java.app.dao.copy.CopyMKoujiDao.super.getInjectedConfig();
            exiting("jp.co.edi_java.app.dao.copy.CopyMKoujiDaoImpl", "getInjectedConfig", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.copy.CopyMKoujiDaoImpl", "getInjectedConfig", __e);
            throw __e;
        }
    }

    @Override
    public jp.co.edi_java.app.entity.copy.CopyMKoujiEntity select(java.lang.String koujiCode) {
        entering("jp.co.edi_java.app.dao.copy.CopyMKoujiDaoImpl", "select", koujiCode);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method1);
            __query.setMethod(__method1);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/copy/CopyMKoujiDao/select.sql");
            __query.setEntityType(jp.co.edi_java.app.entity.copy._CopyMKoujiEntity.getSingletonInternal());
            __query.addParameter("koujiCode", java.lang.String.class, koujiCode);
            __query.setCallerClassName("jp.co.edi_java.app.dao.copy.CopyMKoujiDaoImpl");
            __query.setCallerMethodName("select");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<jp.co.edi_java.app.entity.copy.CopyMKoujiEntity> __command = getCommandImplementors().createSelectCommand(__method1, __query, new org.seasar.doma.internal.jdbc.command.EntitySingleResultHandler<jp.co.edi_java.app.entity.copy.CopyMKoujiEntity>(jp.co.edi_java.app.entity.copy._CopyMKoujiEntity.getSingletonInternal()));
            jp.co.edi_java.app.entity.copy.CopyMKoujiEntity __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.copy.CopyMKoujiDaoImpl", "select", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.copy.CopyMKoujiDaoImpl", "select", __e);
            throw __e;
        }
    }

    @Override
    public int insertAll() {
        entering("jp.co.edi_java.app.dao.copy.CopyMKoujiDaoImpl", "insertAll");
        try {
            org.seasar.doma.jdbc.query.SqlFileInsertQuery __query = getQueryImplementors().createSqlFileInsertQuery(__method2);
            __query.setMethod(__method2);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/copy/CopyMKoujiDao/insertAll.sql");
            __query.setCallerClassName("jp.co.edi_java.app.dao.copy.CopyMKoujiDaoImpl");
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
            exiting("jp.co.edi_java.app.dao.copy.CopyMKoujiDaoImpl", "insertAll", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.copy.CopyMKoujiDaoImpl", "insertAll", __e);
            throw __e;
        }
    }

    @Override
    public int deleteAll() {
        entering("jp.co.edi_java.app.dao.copy.CopyMKoujiDaoImpl", "deleteAll");
        try {
            org.seasar.doma.jdbc.query.SqlFileDeleteQuery __query = getQueryImplementors().createSqlFileDeleteQuery(__method3);
            __query.setMethod(__method3);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/copy/CopyMKoujiDao/deleteAll.sql");
            __query.setCallerClassName("jp.co.edi_java.app.dao.copy.CopyMKoujiDaoImpl");
            __query.setCallerMethodName("deleteAll");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setVersionIgnored(true);
            __query.setOptimisticLockExceptionSuppressed(false);
            __query.prepare();
            org.seasar.doma.jdbc.command.DeleteCommand __command = getCommandImplementors().createDeleteCommand(__method3, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.copy.CopyMKoujiDaoImpl", "deleteAll", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.copy.CopyMKoujiDaoImpl", "deleteAll", __e);
            throw __e;
        }
    }

}
