package jp.co.edi_java.app.dao;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:58.824+0900")
public class FileOrderRelDaoImpl extends org.seasar.doma.internal.jdbc.dao.AbstractDao implements jp.co.edi_java.app.dao.FileOrderRelDao {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.FileOrderRelDao.class, "selectList", java.lang.String.class);

    private static final java.lang.reflect.Method __method2 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.FileOrderRelDao.class, "insert", jp.co.edi_java.app.entity.FileOrderRelEntity.class);

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    public FileOrderRelDaoImpl(org.seasar.doma.jdbc.Config config) {
        super(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getInjectedConfig() {
        entering("jp.co.edi_java.app.dao.FileOrderRelDaoImpl", "getInjectedConfig");
        try {
            org.seasar.doma.jdbc.Config __result = jp.co.edi_java.app.dao.FileOrderRelDao.super.getInjectedConfig();
            exiting("jp.co.edi_java.app.dao.FileOrderRelDaoImpl", "getInjectedConfig", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.FileOrderRelDaoImpl", "getInjectedConfig", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.entity.FileOrderRelEntity> selectList(java.lang.String orderNumber) {
        entering("jp.co.edi_java.app.dao.FileOrderRelDaoImpl", "selectList", orderNumber);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method1);
            __query.setMethod(__method1);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/FileOrderRelDao/selectList.sql");
            __query.setEntityType(jp.co.edi_java.app.entity._FileOrderRelEntity.getSingletonInternal());
            __query.addParameter("orderNumber", java.lang.String.class, orderNumber);
            __query.setCallerClassName("jp.co.edi_java.app.dao.FileOrderRelDaoImpl");
            __query.setCallerMethodName("selectList");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.entity.FileOrderRelEntity>> __command = getCommandImplementors().createSelectCommand(__method1, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.entity.FileOrderRelEntity>(jp.co.edi_java.app.entity._FileOrderRelEntity.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.entity.FileOrderRelEntity> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.FileOrderRelDaoImpl", "selectList", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.FileOrderRelDaoImpl", "selectList", __e);
            throw __e;
        }
    }

    @Override
    public int insert(jp.co.edi_java.app.entity.FileOrderRelEntity entity) {
        entering("jp.co.edi_java.app.dao.FileOrderRelDaoImpl", "insert", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoInsertQuery<jp.co.edi_java.app.entity.FileOrderRelEntity> __query = getQueryImplementors().createAutoInsertQuery(__method2, jp.co.edi_java.app.entity._FileOrderRelEntity.getSingletonInternal());
            __query.setMethod(__method2);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.FileOrderRelDaoImpl");
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
            exiting("jp.co.edi_java.app.dao.FileOrderRelDaoImpl", "insert", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.FileOrderRelDaoImpl", "insert", __e);
            throw __e;
        }
    }

}
