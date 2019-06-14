package jp.co.edi_java.app.dao.jtm;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:58.919+0900")
public class VOrderGyousyaEigyousyoDaoImpl extends org.seasar.doma.internal.jdbc.dao.AbstractDao implements jp.co.edi_java.app.dao.jtm.VOrderGyousyaEigyousyoDao {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.jtm.VOrderGyousyaEigyousyoDao.class, "count");

    private static final java.lang.reflect.Method __method2 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.jtm.VOrderGyousyaEigyousyoDao.class, "selectAll");

    private static final java.lang.reflect.Method __method3 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.jtm.VOrderGyousyaEigyousyoDao.class, "selectPaging", int.class, int.class);

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    @jp.co.edi_java.app.config.db.jtm.Jtm()
    public VOrderGyousyaEigyousyoDaoImpl(org.seasar.doma.jdbc.Config config) {
        super(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getInjectedConfig() {
        entering("jp.co.edi_java.app.dao.jtm.VOrderGyousyaEigyousyoDaoImpl", "getInjectedConfig");
        try {
            org.seasar.doma.jdbc.Config __result = jp.co.edi_java.app.dao.jtm.VOrderGyousyaEigyousyoDao.super.getInjectedConfig();
            exiting("jp.co.edi_java.app.dao.jtm.VOrderGyousyaEigyousyoDaoImpl", "getInjectedConfig", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.jtm.VOrderGyousyaEigyousyoDaoImpl", "getInjectedConfig", __e);
            throw __e;
        }
    }

    @Override
    public int count() {
        entering("jp.co.edi_java.app.dao.jtm.VOrderGyousyaEigyousyoDaoImpl", "count");
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method1);
            __query.setMethod(__method1);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/jtm/VOrderGyousyaEigyousyoDao/count.sql");
            __query.setCallerClassName("jp.co.edi_java.app.dao.jtm.VOrderGyousyaEigyousyoDaoImpl");
            __query.setCallerMethodName("count");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.lang.Integer> __command = getCommandImplementors().createSelectCommand(__method1, __query, new org.seasar.doma.internal.jdbc.command.BasicSingleResultHandler<java.lang.Integer>(org.seasar.doma.wrapper.IntegerWrapper::new, true));
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.jtm.VOrderGyousyaEigyousyoDaoImpl", "count", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.jtm.VOrderGyousyaEigyousyoDaoImpl", "count", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.entity.jtm.VOrderGyousyaEigyousyoEntity> selectAll() {
        entering("jp.co.edi_java.app.dao.jtm.VOrderGyousyaEigyousyoDaoImpl", "selectAll");
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method2);
            __query.setMethod(__method2);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/jtm/VOrderGyousyaEigyousyoDao/selectAll.sql");
            __query.setEntityType(jp.co.edi_java.app.entity.jtm._VOrderGyousyaEigyousyoEntity.getSingletonInternal());
            __query.setCallerClassName("jp.co.edi_java.app.dao.jtm.VOrderGyousyaEigyousyoDaoImpl");
            __query.setCallerMethodName("selectAll");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.entity.jtm.VOrderGyousyaEigyousyoEntity>> __command = getCommandImplementors().createSelectCommand(__method2, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.entity.jtm.VOrderGyousyaEigyousyoEntity>(jp.co.edi_java.app.entity.jtm._VOrderGyousyaEigyousyoEntity.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.entity.jtm.VOrderGyousyaEigyousyoEntity> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.jtm.VOrderGyousyaEigyousyoDaoImpl", "selectAll", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.jtm.VOrderGyousyaEigyousyoDaoImpl", "selectAll", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.entity.jtm.VOrderGyousyaEigyousyoEntity> selectPaging(int from, int to) {
        entering("jp.co.edi_java.app.dao.jtm.VOrderGyousyaEigyousyoDaoImpl", "selectPaging", from, to);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method3);
            __query.setMethod(__method3);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/jtm/VOrderGyousyaEigyousyoDao/selectPaging.sql");
            __query.setEntityType(jp.co.edi_java.app.entity.jtm._VOrderGyousyaEigyousyoEntity.getSingletonInternal());
            __query.addParameter("from", int.class, from);
            __query.addParameter("to", int.class, to);
            __query.setCallerClassName("jp.co.edi_java.app.dao.jtm.VOrderGyousyaEigyousyoDaoImpl");
            __query.setCallerMethodName("selectPaging");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.entity.jtm.VOrderGyousyaEigyousyoEntity>> __command = getCommandImplementors().createSelectCommand(__method3, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.entity.jtm.VOrderGyousyaEigyousyoEntity>(jp.co.edi_java.app.entity.jtm._VOrderGyousyaEigyousyoEntity.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.entity.jtm.VOrderGyousyaEigyousyoEntity> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.jtm.VOrderGyousyaEigyousyoDaoImpl", "selectPaging", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.jtm.VOrderGyousyaEigyousyoDaoImpl", "selectPaging", __e);
            throw __e;
        }
    }

}
