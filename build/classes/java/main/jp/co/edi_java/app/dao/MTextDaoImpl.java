package jp.co.edi_java.app.dao;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:59.021+0900")
public class MTextDaoImpl extends org.seasar.doma.internal.jdbc.dao.AbstractDao implements jp.co.edi_java.app.dao.MTextDao {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.MTextDao.class, "selectList", java.lang.String.class);

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    public MTextDaoImpl(org.seasar.doma.jdbc.Config config) {
        super(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getInjectedConfig() {
        entering("jp.co.edi_java.app.dao.MTextDaoImpl", "getInjectedConfig");
        try {
            org.seasar.doma.jdbc.Config __result = jp.co.edi_java.app.dao.MTextDao.super.getInjectedConfig();
            exiting("jp.co.edi_java.app.dao.MTextDaoImpl", "getInjectedConfig", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.MTextDaoImpl", "getInjectedConfig", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.entity.MTextEntity> selectList(java.lang.String formType) {
        entering("jp.co.edi_java.app.dao.MTextDaoImpl", "selectList", formType);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method1);
            __query.setMethod(__method1);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/MTextDao/selectList.sql");
            __query.setEntityType(jp.co.edi_java.app.entity._MTextEntity.getSingletonInternal());
            __query.addParameter("formType", java.lang.String.class, formType);
            __query.setCallerClassName("jp.co.edi_java.app.dao.MTextDaoImpl");
            __query.setCallerMethodName("selectList");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.entity.MTextEntity>> __command = getCommandImplementors().createSelectCommand(__method1, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.entity.MTextEntity>(jp.co.edi_java.app.entity._MTextEntity.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.entity.MTextEntity> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.MTextDaoImpl", "selectList", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.MTextDaoImpl", "selectList", __e);
            throw __e;
        }
    }

}
