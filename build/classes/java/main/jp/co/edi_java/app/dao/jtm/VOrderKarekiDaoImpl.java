package jp.co.edi_java.app.dao.jtm;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:58.934+0900")
public class VOrderKarekiDaoImpl extends org.seasar.doma.internal.jdbc.dao.AbstractDao implements jp.co.edi_java.app.dao.jtm.VOrderKarekiDao {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.jtm.VOrderKarekiDao.class, "count");

    private static final java.lang.reflect.Method __method2 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.jtm.VOrderKarekiDao.class, "selectAll");

    private static final java.lang.reflect.Method __method3 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.jtm.VOrderKarekiDao.class, "selectPaging", int.class, int.class);

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    @jp.co.edi_java.app.config.db.jtm.Jtm()
    public VOrderKarekiDaoImpl(org.seasar.doma.jdbc.Config config) {
        super(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getInjectedConfig() {
        entering("jp.co.edi_java.app.dao.jtm.VOrderKarekiDaoImpl", "getInjectedConfig");
        try {
            org.seasar.doma.jdbc.Config __result = jp.co.edi_java.app.dao.jtm.VOrderKarekiDao.super.getInjectedConfig();
            exiting("jp.co.edi_java.app.dao.jtm.VOrderKarekiDaoImpl", "getInjectedConfig", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.jtm.VOrderKarekiDaoImpl", "getInjectedConfig", __e);
            throw __e;
        }
    }

    @Override
    public int count() {
        entering("jp.co.edi_java.app.dao.jtm.VOrderKarekiDaoImpl", "count");
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method1);
            __query.setMethod(__method1);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/jtm/VOrderKarekiDao/count.sql");
            __query.setCallerClassName("jp.co.edi_java.app.dao.jtm.VOrderKarekiDaoImpl");
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
            exiting("jp.co.edi_java.app.dao.jtm.VOrderKarekiDaoImpl", "count", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.jtm.VOrderKarekiDaoImpl", "count", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.entity.jtm.VOrderKarekiEntity> selectAll() {
        entering("jp.co.edi_java.app.dao.jtm.VOrderKarekiDaoImpl", "selectAll");
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method2);
            __query.setMethod(__method2);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/jtm/VOrderKarekiDao/selectAll.sql");
            __query.setEntityType(jp.co.edi_java.app.entity.jtm._VOrderKarekiEntity.getSingletonInternal());
            __query.setCallerClassName("jp.co.edi_java.app.dao.jtm.VOrderKarekiDaoImpl");
            __query.setCallerMethodName("selectAll");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.entity.jtm.VOrderKarekiEntity>> __command = getCommandImplementors().createSelectCommand(__method2, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.entity.jtm.VOrderKarekiEntity>(jp.co.edi_java.app.entity.jtm._VOrderKarekiEntity.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.entity.jtm.VOrderKarekiEntity> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.jtm.VOrderKarekiDaoImpl", "selectAll", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.jtm.VOrderKarekiDaoImpl", "selectAll", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.entity.jtm.VOrderKarekiEntity> selectPaging(int from, int to) {
        entering("jp.co.edi_java.app.dao.jtm.VOrderKarekiDaoImpl", "selectPaging", from, to);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method3);
            __query.setMethod(__method3);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/jtm/VOrderKarekiDao/selectPaging.sql");
            __query.setEntityType(jp.co.edi_java.app.entity.jtm._VOrderKarekiEntity.getSingletonInternal());
            __query.addParameter("from", int.class, from);
            __query.addParameter("to", int.class, to);
            __query.setCallerClassName("jp.co.edi_java.app.dao.jtm.VOrderKarekiDaoImpl");
            __query.setCallerMethodName("selectPaging");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.entity.jtm.VOrderKarekiEntity>> __command = getCommandImplementors().createSelectCommand(__method3, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.entity.jtm.VOrderKarekiEntity>(jp.co.edi_java.app.entity.jtm._VOrderKarekiEntity.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.entity.jtm.VOrderKarekiEntity> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.jtm.VOrderKarekiDaoImpl", "selectPaging", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.jtm.VOrderKarekiDaoImpl", "selectPaging", __e);
            throw __e;
        }
    }

}
