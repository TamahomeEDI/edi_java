package jp.co.edi_java.app.dao;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:59.150+0900")
public class TWorkReportItemDaoImpl extends org.seasar.doma.internal.jdbc.dao.AbstractDao implements jp.co.edi_java.app.dao.TWorkReportItemDao {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.TWorkReportItemDao.class, "selectAll", java.lang.String.class);

    private static final java.lang.reflect.Method __method2 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.TWorkReportItemDao.class, "insert", jp.co.edi_java.app.entity.TWorkReportItemEntity.class);

    private static final java.lang.reflect.Method __method3 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.TWorkReportItemDao.class, "update", jp.co.edi_java.app.entity.TWorkReportItemEntity.class);

    private static final java.lang.reflect.Method __method4 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.TWorkReportItemDao.class, "delete", jp.co.edi_java.app.entity.TWorkReportItemEntity.class);

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    public TWorkReportItemDaoImpl(org.seasar.doma.jdbc.Config config) {
        super(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getInjectedConfig() {
        entering("jp.co.edi_java.app.dao.TWorkReportItemDaoImpl", "getInjectedConfig");
        try {
            org.seasar.doma.jdbc.Config __result = jp.co.edi_java.app.dao.TWorkReportItemDao.super.getInjectedConfig();
            exiting("jp.co.edi_java.app.dao.TWorkReportItemDaoImpl", "getInjectedConfig", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.TWorkReportItemDaoImpl", "getInjectedConfig", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.entity.TWorkReportItemEntity> selectAll(java.lang.String workReportNumber) {
        entering("jp.co.edi_java.app.dao.TWorkReportItemDaoImpl", "selectAll", workReportNumber);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method1);
            __query.setMethod(__method1);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/TWorkReportItemDao/selectAll.sql");
            __query.setEntityType(jp.co.edi_java.app.entity._TWorkReportItemEntity.getSingletonInternal());
            __query.addParameter("workReportNumber", java.lang.String.class, workReportNumber);
            __query.setCallerClassName("jp.co.edi_java.app.dao.TWorkReportItemDaoImpl");
            __query.setCallerMethodName("selectAll");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.entity.TWorkReportItemEntity>> __command = getCommandImplementors().createSelectCommand(__method1, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.entity.TWorkReportItemEntity>(jp.co.edi_java.app.entity._TWorkReportItemEntity.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.entity.TWorkReportItemEntity> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.TWorkReportItemDaoImpl", "selectAll", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.TWorkReportItemDaoImpl", "selectAll", __e);
            throw __e;
        }
    }

    @Override
    public int insert(jp.co.edi_java.app.entity.TWorkReportItemEntity entity) {
        entering("jp.co.edi_java.app.dao.TWorkReportItemDaoImpl", "insert", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoInsertQuery<jp.co.edi_java.app.entity.TWorkReportItemEntity> __query = getQueryImplementors().createAutoInsertQuery(__method2, jp.co.edi_java.app.entity._TWorkReportItemEntity.getSingletonInternal());
            __query.setMethod(__method2);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.TWorkReportItemDaoImpl");
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
            exiting("jp.co.edi_java.app.dao.TWorkReportItemDaoImpl", "insert", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.TWorkReportItemDaoImpl", "insert", __e);
            throw __e;
        }
    }

    @Override
    public int update(jp.co.edi_java.app.entity.TWorkReportItemEntity entity) {
        entering("jp.co.edi_java.app.dao.TWorkReportItemDaoImpl", "update", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoUpdateQuery<jp.co.edi_java.app.entity.TWorkReportItemEntity> __query = getQueryImplementors().createAutoUpdateQuery(__method3, jp.co.edi_java.app.entity._TWorkReportItemEntity.getSingletonInternal());
            __query.setMethod(__method3);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.TWorkReportItemDaoImpl");
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
            exiting("jp.co.edi_java.app.dao.TWorkReportItemDaoImpl", "update", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.TWorkReportItemDaoImpl", "update", __e);
            throw __e;
        }
    }

    @Override
    public int delete(jp.co.edi_java.app.entity.TWorkReportItemEntity entity) {
        entering("jp.co.edi_java.app.dao.TWorkReportItemDaoImpl", "delete", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoDeleteQuery<jp.co.edi_java.app.entity.TWorkReportItemEntity> __query = getQueryImplementors().createAutoDeleteQuery(__method4, jp.co.edi_java.app.entity._TWorkReportItemEntity.getSingletonInternal());
            __query.setMethod(__method4);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.TWorkReportItemDaoImpl");
            __query.setCallerMethodName("delete");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setVersionIgnored(false);
            __query.setOptimisticLockExceptionSuppressed(false);
            __query.prepare();
            org.seasar.doma.jdbc.command.DeleteCommand __command = getCommandImplementors().createDeleteCommand(__method4, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.TWorkReportItemDaoImpl", "delete", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.TWorkReportItemDaoImpl", "delete", __e);
            throw __e;
        }
    }

}
