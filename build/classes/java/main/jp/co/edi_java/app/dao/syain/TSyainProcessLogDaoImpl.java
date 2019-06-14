package jp.co.edi_java.app.dao.syain;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:59.080+0900")
public class TSyainProcessLogDaoImpl extends org.seasar.doma.internal.jdbc.dao.AbstractDao implements jp.co.edi_java.app.dao.syain.TSyainProcessLogDao {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.syain.TSyainProcessLogDao.class, "select", java.lang.String.class);

    private static final java.lang.reflect.Method __method2 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.syain.TSyainProcessLogDao.class, "insert", jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity.class);

    private static final java.lang.reflect.Method __method3 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.syain.TSyainProcessLogDao.class, "update", jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity.class);

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    public TSyainProcessLogDaoImpl(org.seasar.doma.jdbc.Config config) {
        super(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getInjectedConfig() {
        entering("jp.co.edi_java.app.dao.syain.TSyainProcessLogDaoImpl", "getInjectedConfig");
        try {
            org.seasar.doma.jdbc.Config __result = jp.co.edi_java.app.dao.syain.TSyainProcessLogDao.super.getInjectedConfig();
            exiting("jp.co.edi_java.app.dao.syain.TSyainProcessLogDaoImpl", "getInjectedConfig", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.syain.TSyainProcessLogDaoImpl", "getInjectedConfig", __e);
            throw __e;
        }
    }

    @Override
    public jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity select(java.lang.String syainCode) {
        entering("jp.co.edi_java.app.dao.syain.TSyainProcessLogDaoImpl", "select", syainCode);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method1);
            __query.setMethod(__method1);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/syain/TSyainProcessLogDao/select.sql");
            __query.setEntityType(jp.co.edi_java.app.entity.syain._TSyainProcessLogEntity.getSingletonInternal());
            __query.addParameter("syainCode", java.lang.String.class, syainCode);
            __query.setCallerClassName("jp.co.edi_java.app.dao.syain.TSyainProcessLogDaoImpl");
            __query.setCallerMethodName("select");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity> __command = getCommandImplementors().createSelectCommand(__method1, __query, new org.seasar.doma.internal.jdbc.command.EntitySingleResultHandler<jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity>(jp.co.edi_java.app.entity.syain._TSyainProcessLogEntity.getSingletonInternal()));
            jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.syain.TSyainProcessLogDaoImpl", "select", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.syain.TSyainProcessLogDaoImpl", "select", __e);
            throw __e;
        }
    }

    @Override
    public int insert(jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity entity) {
        entering("jp.co.edi_java.app.dao.syain.TSyainProcessLogDaoImpl", "insert", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoInsertQuery<jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity> __query = getQueryImplementors().createAutoInsertQuery(__method2, jp.co.edi_java.app.entity.syain._TSyainProcessLogEntity.getSingletonInternal());
            __query.setMethod(__method2);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.syain.TSyainProcessLogDaoImpl");
            __query.setCallerMethodName("insert");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setNullExcluded(true);
            __query.setIncludedPropertyNames();
            __query.setExcludedPropertyNames();
            __query.prepare();
            org.seasar.doma.jdbc.command.InsertCommand __command = getCommandImplementors().createInsertCommand(__method2, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.syain.TSyainProcessLogDaoImpl", "insert", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.syain.TSyainProcessLogDaoImpl", "insert", __e);
            throw __e;
        }
    }

    @Override
    public int update(jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity entity) {
        entering("jp.co.edi_java.app.dao.syain.TSyainProcessLogDaoImpl", "update", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoUpdateQuery<jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity> __query = getQueryImplementors().createAutoUpdateQuery(__method3, jp.co.edi_java.app.entity.syain._TSyainProcessLogEntity.getSingletonInternal());
            __query.setMethod(__method3);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.syain.TSyainProcessLogDaoImpl");
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
            org.seasar.doma.jdbc.command.UpdateCommand __command = getCommandImplementors().createUpdateCommand(__method3, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.syain.TSyainProcessLogDaoImpl", "update", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.syain.TSyainProcessLogDaoImpl", "update", __e);
            throw __e;
        }
    }

}
