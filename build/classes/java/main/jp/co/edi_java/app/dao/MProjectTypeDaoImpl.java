package jp.co.edi_java.app.dao;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:59.008+0900")
public class MProjectTypeDaoImpl extends org.seasar.doma.internal.jdbc.dao.AbstractDao implements jp.co.edi_java.app.dao.MProjectTypeDao {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.MProjectTypeDao.class, "selectAll");

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    public MProjectTypeDaoImpl(org.seasar.doma.jdbc.Config config) {
        super(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getInjectedConfig() {
        entering("jp.co.edi_java.app.dao.MProjectTypeDaoImpl", "getInjectedConfig");
        try {
            org.seasar.doma.jdbc.Config __result = jp.co.edi_java.app.dao.MProjectTypeDao.super.getInjectedConfig();
            exiting("jp.co.edi_java.app.dao.MProjectTypeDaoImpl", "getInjectedConfig", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.MProjectTypeDaoImpl", "getInjectedConfig", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.entity.MProjectTypeEntity> selectAll() {
        entering("jp.co.edi_java.app.dao.MProjectTypeDaoImpl", "selectAll");
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method1);
            __query.setMethod(__method1);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/MProjectTypeDao/selectAll.sql");
            __query.setEntityType(jp.co.edi_java.app.entity._MProjectTypeEntity.getSingletonInternal());
            __query.setCallerClassName("jp.co.edi_java.app.dao.MProjectTypeDaoImpl");
            __query.setCallerMethodName("selectAll");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.entity.MProjectTypeEntity>> __command = getCommandImplementors().createSelectCommand(__method1, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.entity.MProjectTypeEntity>(jp.co.edi_java.app.entity._MProjectTypeEntity.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.entity.MProjectTypeEntity> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.MProjectTypeDaoImpl", "selectAll", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.MProjectTypeDaoImpl", "selectAll", __e);
            throw __e;
        }
    }

}
