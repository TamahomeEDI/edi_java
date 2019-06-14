package jp.co.edi_java.app.dao.gyousya;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:58.889+0900")
public class TGyousyaProcessLogDaoImpl extends org.seasar.doma.internal.jdbc.dao.AbstractDao implements jp.co.edi_java.app.dao.gyousya.TGyousyaProcessLogDao {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.TGyousyaProcessLogDao.class, "select", java.lang.String.class);

    private static final java.lang.reflect.Method __method2 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.TGyousyaProcessLogDao.class, "insert", jp.co.edi_java.app.entity.gyousya.TGyousyaProcessLogEntity.class);

    private static final java.lang.reflect.Method __method3 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.TGyousyaProcessLogDao.class, "update", jp.co.edi_java.app.entity.gyousya.TGyousyaProcessLogEntity.class);

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    public TGyousyaProcessLogDaoImpl(org.seasar.doma.jdbc.Config config) {
        super(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getInjectedConfig() {
        entering("jp.co.edi_java.app.dao.gyousya.TGyousyaProcessLogDaoImpl", "getInjectedConfig");
        try {
            org.seasar.doma.jdbc.Config __result = jp.co.edi_java.app.dao.gyousya.TGyousyaProcessLogDao.super.getInjectedConfig();
            exiting("jp.co.edi_java.app.dao.gyousya.TGyousyaProcessLogDaoImpl", "getInjectedConfig", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.TGyousyaProcessLogDaoImpl", "getInjectedConfig", __e);
            throw __e;
        }
    }

    @Override
    public jp.co.edi_java.app.entity.gyousya.TGyousyaProcessLogEntity select(java.lang.String gyousyaCode) {
        entering("jp.co.edi_java.app.dao.gyousya.TGyousyaProcessLogDaoImpl", "select", gyousyaCode);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method1);
            __query.setMethod(__method1);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/gyousya/TGyousyaProcessLogDao/select.sql");
            __query.setEntityType(jp.co.edi_java.app.entity.gyousya._TGyousyaProcessLogEntity.getSingletonInternal());
            __query.addParameter("gyousyaCode", java.lang.String.class, gyousyaCode);
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.TGyousyaProcessLogDaoImpl");
            __query.setCallerMethodName("select");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<jp.co.edi_java.app.entity.gyousya.TGyousyaProcessLogEntity> __command = getCommandImplementors().createSelectCommand(__method1, __query, new org.seasar.doma.internal.jdbc.command.EntitySingleResultHandler<jp.co.edi_java.app.entity.gyousya.TGyousyaProcessLogEntity>(jp.co.edi_java.app.entity.gyousya._TGyousyaProcessLogEntity.getSingletonInternal()));
            jp.co.edi_java.app.entity.gyousya.TGyousyaProcessLogEntity __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.gyousya.TGyousyaProcessLogDaoImpl", "select", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.TGyousyaProcessLogDaoImpl", "select", __e);
            throw __e;
        }
    }

    @Override
    public int insert(jp.co.edi_java.app.entity.gyousya.TGyousyaProcessLogEntity entity) {
        entering("jp.co.edi_java.app.dao.gyousya.TGyousyaProcessLogDaoImpl", "insert", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoInsertQuery<jp.co.edi_java.app.entity.gyousya.TGyousyaProcessLogEntity> __query = getQueryImplementors().createAutoInsertQuery(__method2, jp.co.edi_java.app.entity.gyousya._TGyousyaProcessLogEntity.getSingletonInternal());
            __query.setMethod(__method2);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.TGyousyaProcessLogDaoImpl");
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
            exiting("jp.co.edi_java.app.dao.gyousya.TGyousyaProcessLogDaoImpl", "insert", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.TGyousyaProcessLogDaoImpl", "insert", __e);
            throw __e;
        }
    }

    @Override
    public int update(jp.co.edi_java.app.entity.gyousya.TGyousyaProcessLogEntity entity) {
        entering("jp.co.edi_java.app.dao.gyousya.TGyousyaProcessLogDaoImpl", "update", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoUpdateQuery<jp.co.edi_java.app.entity.gyousya.TGyousyaProcessLogEntity> __query = getQueryImplementors().createAutoUpdateQuery(__method3, jp.co.edi_java.app.entity.gyousya._TGyousyaProcessLogEntity.getSingletonInternal());
            __query.setMethod(__method3);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.TGyousyaProcessLogDaoImpl");
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
            exiting("jp.co.edi_java.app.dao.gyousya.TGyousyaProcessLogDaoImpl", "update", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.TGyousyaProcessLogDaoImpl", "update", __e);
            throw __e;
        }
    }

}
