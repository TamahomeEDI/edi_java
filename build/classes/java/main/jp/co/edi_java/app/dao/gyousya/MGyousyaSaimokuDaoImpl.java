package jp.co.edi_java.app.dao.gyousya;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:58.861+0900")
public class MGyousyaSaimokuDaoImpl extends org.seasar.doma.internal.jdbc.dao.AbstractDao implements jp.co.edi_java.app.dao.gyousya.MGyousyaSaimokuDao {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.MGyousyaSaimokuDao.class, "insert", jp.co.edi_java.app.entity.gyousya.MGyousyaSaimokuEntity.class);

    private static final java.lang.reflect.Method __method2 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.MGyousyaSaimokuDao.class, "update", jp.co.edi_java.app.entity.gyousya.MGyousyaSaimokuEntity.class);

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    public MGyousyaSaimokuDaoImpl(org.seasar.doma.jdbc.Config config) {
        super(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getInjectedConfig() {
        entering("jp.co.edi_java.app.dao.gyousya.MGyousyaSaimokuDaoImpl", "getInjectedConfig");
        try {
            org.seasar.doma.jdbc.Config __result = jp.co.edi_java.app.dao.gyousya.MGyousyaSaimokuDao.super.getInjectedConfig();
            exiting("jp.co.edi_java.app.dao.gyousya.MGyousyaSaimokuDaoImpl", "getInjectedConfig", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.MGyousyaSaimokuDaoImpl", "getInjectedConfig", __e);
            throw __e;
        }
    }

    @Override
    public int insert(jp.co.edi_java.app.entity.gyousya.MGyousyaSaimokuEntity entity) {
        entering("jp.co.edi_java.app.dao.gyousya.MGyousyaSaimokuDaoImpl", "insert", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoInsertQuery<jp.co.edi_java.app.entity.gyousya.MGyousyaSaimokuEntity> __query = getQueryImplementors().createAutoInsertQuery(__method1, jp.co.edi_java.app.entity.gyousya._MGyousyaSaimokuEntity.getSingletonInternal());
            __query.setMethod(__method1);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.MGyousyaSaimokuDaoImpl");
            __query.setCallerMethodName("insert");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setNullExcluded(true);
            __query.setIncludedPropertyNames();
            __query.setExcludedPropertyNames();
            __query.prepare();
            org.seasar.doma.jdbc.command.InsertCommand __command = getCommandImplementors().createInsertCommand(__method1, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.gyousya.MGyousyaSaimokuDaoImpl", "insert", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.MGyousyaSaimokuDaoImpl", "insert", __e);
            throw __e;
        }
    }

    @Override
    public int update(jp.co.edi_java.app.entity.gyousya.MGyousyaSaimokuEntity entity) {
        entering("jp.co.edi_java.app.dao.gyousya.MGyousyaSaimokuDaoImpl", "update", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoUpdateQuery<jp.co.edi_java.app.entity.gyousya.MGyousyaSaimokuEntity> __query = getQueryImplementors().createAutoUpdateQuery(__method2, jp.co.edi_java.app.entity.gyousya._MGyousyaSaimokuEntity.getSingletonInternal());
            __query.setMethod(__method2);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.MGyousyaSaimokuDaoImpl");
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
            org.seasar.doma.jdbc.command.UpdateCommand __command = getCommandImplementors().createUpdateCommand(__method2, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.gyousya.MGyousyaSaimokuDaoImpl", "update", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.MGyousyaSaimokuDaoImpl", "update", __e);
            throw __e;
        }
    }

}
