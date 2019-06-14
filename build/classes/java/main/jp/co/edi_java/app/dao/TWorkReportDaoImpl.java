package jp.co.edi_java.app.dao;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:59.143+0900")
public class TWorkReportDaoImpl extends org.seasar.doma.internal.jdbc.dao.AbstractDao implements jp.co.edi_java.app.dao.TWorkReportDao {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.TWorkReportDao.class, "select", java.lang.String.class);

    private static final java.lang.reflect.Method __method2 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.TWorkReportDao.class, "selectByOrderNumber", java.lang.String.class, int.class);

    private static final java.lang.reflect.Method __method3 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.TWorkReportDao.class, "selectAll", java.lang.String.class, java.lang.String.class);

    private static final java.lang.reflect.Method __method4 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.TWorkReportDao.class, "insert", jp.co.edi_java.app.entity.TWorkReportEntity.class);

    private static final java.lang.reflect.Method __method5 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.TWorkReportDao.class, "update", jp.co.edi_java.app.entity.TWorkReportEntity.class);

    private static final java.lang.reflect.Method __method6 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.TWorkReportDao.class, "delete", jp.co.edi_java.app.entity.TWorkReportEntity.class);

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    public TWorkReportDaoImpl(org.seasar.doma.jdbc.Config config) {
        super(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getInjectedConfig() {
        entering("jp.co.edi_java.app.dao.TWorkReportDaoImpl", "getInjectedConfig");
        try {
            org.seasar.doma.jdbc.Config __result = jp.co.edi_java.app.dao.TWorkReportDao.super.getInjectedConfig();
            exiting("jp.co.edi_java.app.dao.TWorkReportDaoImpl", "getInjectedConfig", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.TWorkReportDaoImpl", "getInjectedConfig", __e);
            throw __e;
        }
    }

    @Override
    public jp.co.edi_java.app.entity.TWorkReportEntity select(java.lang.String workReportNumber) {
        entering("jp.co.edi_java.app.dao.TWorkReportDaoImpl", "select", workReportNumber);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method1);
            __query.setMethod(__method1);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/TWorkReportDao/select.sql");
            __query.setEntityType(jp.co.edi_java.app.entity._TWorkReportEntity.getSingletonInternal());
            __query.addParameter("workReportNumber", java.lang.String.class, workReportNumber);
            __query.setCallerClassName("jp.co.edi_java.app.dao.TWorkReportDaoImpl");
            __query.setCallerMethodName("select");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<jp.co.edi_java.app.entity.TWorkReportEntity> __command = getCommandImplementors().createSelectCommand(__method1, __query, new org.seasar.doma.internal.jdbc.command.EntitySingleResultHandler<jp.co.edi_java.app.entity.TWorkReportEntity>(jp.co.edi_java.app.entity._TWorkReportEntity.getSingletonInternal()));
            jp.co.edi_java.app.entity.TWorkReportEntity __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.TWorkReportDaoImpl", "select", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.TWorkReportDaoImpl", "select", __e);
            throw __e;
        }
    }

    @Override
    public jp.co.edi_java.app.entity.TWorkReportEntity selectByOrderNumber(java.lang.String orderNumber, int workReportCount) {
        entering("jp.co.edi_java.app.dao.TWorkReportDaoImpl", "selectByOrderNumber", orderNumber, workReportCount);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method2);
            __query.setMethod(__method2);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/TWorkReportDao/selectByOrderNumber.sql");
            __query.setEntityType(jp.co.edi_java.app.entity._TWorkReportEntity.getSingletonInternal());
            __query.addParameter("orderNumber", java.lang.String.class, orderNumber);
            __query.addParameter("workReportCount", int.class, workReportCount);
            __query.setCallerClassName("jp.co.edi_java.app.dao.TWorkReportDaoImpl");
            __query.setCallerMethodName("selectByOrderNumber");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<jp.co.edi_java.app.entity.TWorkReportEntity> __command = getCommandImplementors().createSelectCommand(__method2, __query, new org.seasar.doma.internal.jdbc.command.EntitySingleResultHandler<jp.co.edi_java.app.entity.TWorkReportEntity>(jp.co.edi_java.app.entity._TWorkReportEntity.getSingletonInternal()));
            jp.co.edi_java.app.entity.TWorkReportEntity __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.TWorkReportDaoImpl", "selectByOrderNumber", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.TWorkReportDaoImpl", "selectByOrderNumber", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.entity.TWorkReportEntity> selectAll(java.lang.String orderNumber, java.lang.String remandFlg) {
        entering("jp.co.edi_java.app.dao.TWorkReportDaoImpl", "selectAll", orderNumber, remandFlg);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method3);
            __query.setMethod(__method3);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/TWorkReportDao/selectAll.sql");
            __query.setEntityType(jp.co.edi_java.app.entity._TWorkReportEntity.getSingletonInternal());
            __query.addParameter("orderNumber", java.lang.String.class, orderNumber);
            __query.addParameter("remandFlg", java.lang.String.class, remandFlg);
            __query.setCallerClassName("jp.co.edi_java.app.dao.TWorkReportDaoImpl");
            __query.setCallerMethodName("selectAll");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.entity.TWorkReportEntity>> __command = getCommandImplementors().createSelectCommand(__method3, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.entity.TWorkReportEntity>(jp.co.edi_java.app.entity._TWorkReportEntity.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.entity.TWorkReportEntity> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.TWorkReportDaoImpl", "selectAll", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.TWorkReportDaoImpl", "selectAll", __e);
            throw __e;
        }
    }

    @Override
    public int insert(jp.co.edi_java.app.entity.TWorkReportEntity entity) {
        entering("jp.co.edi_java.app.dao.TWorkReportDaoImpl", "insert", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoInsertQuery<jp.co.edi_java.app.entity.TWorkReportEntity> __query = getQueryImplementors().createAutoInsertQuery(__method4, jp.co.edi_java.app.entity._TWorkReportEntity.getSingletonInternal());
            __query.setMethod(__method4);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.TWorkReportDaoImpl");
            __query.setCallerMethodName("insert");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setNullExcluded(true);
            __query.setIncludedPropertyNames();
            __query.setExcludedPropertyNames();
            __query.prepare();
            org.seasar.doma.jdbc.command.InsertCommand __command = getCommandImplementors().createInsertCommand(__method4, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.TWorkReportDaoImpl", "insert", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.TWorkReportDaoImpl", "insert", __e);
            throw __e;
        }
    }

    @Override
    public int update(jp.co.edi_java.app.entity.TWorkReportEntity entity) {
        entering("jp.co.edi_java.app.dao.TWorkReportDaoImpl", "update", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoUpdateQuery<jp.co.edi_java.app.entity.TWorkReportEntity> __query = getQueryImplementors().createAutoUpdateQuery(__method5, jp.co.edi_java.app.entity._TWorkReportEntity.getSingletonInternal());
            __query.setMethod(__method5);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.TWorkReportDaoImpl");
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
            org.seasar.doma.jdbc.command.UpdateCommand __command = getCommandImplementors().createUpdateCommand(__method5, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.TWorkReportDaoImpl", "update", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.TWorkReportDaoImpl", "update", __e);
            throw __e;
        }
    }

    @Override
    public int delete(jp.co.edi_java.app.entity.TWorkReportEntity entity) {
        entering("jp.co.edi_java.app.dao.TWorkReportDaoImpl", "delete", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoDeleteQuery<jp.co.edi_java.app.entity.TWorkReportEntity> __query = getQueryImplementors().createAutoDeleteQuery(__method6, jp.co.edi_java.app.entity._TWorkReportEntity.getSingletonInternal());
            __query.setMethod(__method6);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.TWorkReportDaoImpl");
            __query.setCallerMethodName("delete");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setVersionIgnored(false);
            __query.setOptimisticLockExceptionSuppressed(false);
            __query.prepare();
            org.seasar.doma.jdbc.command.DeleteCommand __command = getCommandImplementors().createDeleteCommand(__method6, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.TWorkReportDaoImpl", "delete", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.TWorkReportDaoImpl", "delete", __e);
            throw __e;
        }
    }

}
