package jp.co.edi_java.app.dao.gyousya;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:58.881+0900")
public class TGyousyaMailaddressDaoImpl extends org.seasar.doma.internal.jdbc.dao.AbstractDao implements jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDao {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDao.class, "select", java.lang.String.class);

    private static final java.lang.reflect.Method __method2 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDao.class, "selectPasswordForget", java.lang.String.class, java.lang.String.class);

    private static final java.lang.reflect.Method __method3 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDao.class, "selectAll", java.lang.String.class);

    private static final java.lang.reflect.Method __method4 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDao.class, "countByMailaddress", java.lang.String.class, java.lang.String.class);

    private static final java.lang.reflect.Method __method5 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDao.class, "selectList", java.lang.String.class);

    private static final java.lang.reflect.Method __method6 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDao.class, "insert", jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity.class);

    private static final java.lang.reflect.Method __method7 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDao.class, "update", jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity.class);

    private static final java.lang.reflect.Method __method8 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDao.class, "delete", jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity.class);

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    public TGyousyaMailaddressDaoImpl(org.seasar.doma.jdbc.Config config) {
        super(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getInjectedConfig() {
        entering("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "getInjectedConfig");
        try {
            org.seasar.doma.jdbc.Config __result = jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDao.super.getInjectedConfig();
            exiting("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "getInjectedConfig", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "getInjectedConfig", __e);
            throw __e;
        }
    }

    @Override
    public jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity select(java.lang.String mailaddress) {
        entering("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "select", mailaddress);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method1);
            __query.setMethod(__method1);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/gyousya/TGyousyaMailaddressDao/select.sql");
            __query.setEntityType(jp.co.edi_java.app.entity.gyousya._TGyousyaMailaddressEntity.getSingletonInternal());
            __query.addParameter("mailaddress", java.lang.String.class, mailaddress);
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl");
            __query.setCallerMethodName("select");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity> __command = getCommandImplementors().createSelectCommand(__method1, __query, new org.seasar.doma.internal.jdbc.command.EntitySingleResultHandler<jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity>(jp.co.edi_java.app.entity.gyousya._TGyousyaMailaddressEntity.getSingletonInternal()));
            jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "select", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "select", __e);
            throw __e;
        }
    }

    @Override
    public jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity selectPasswordForget(java.lang.String gyousyaCode, java.lang.String mailaddress) {
        entering("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "selectPasswordForget", gyousyaCode, mailaddress);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method2);
            __query.setMethod(__method2);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/gyousya/TGyousyaMailaddressDao/selectPasswordForget.sql");
            __query.setEntityType(jp.co.edi_java.app.entity.gyousya._TGyousyaMailaddressEntity.getSingletonInternal());
            __query.addParameter("gyousyaCode", java.lang.String.class, gyousyaCode);
            __query.addParameter("mailaddress", java.lang.String.class, mailaddress);
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl");
            __query.setCallerMethodName("selectPasswordForget");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity> __command = getCommandImplementors().createSelectCommand(__method2, __query, new org.seasar.doma.internal.jdbc.command.EntitySingleResultHandler<jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity>(jp.co.edi_java.app.entity.gyousya._TGyousyaMailaddressEntity.getSingletonInternal()));
            jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "selectPasswordForget", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "selectPasswordForget", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity> selectAll(java.lang.String gyousyaCode) {
        entering("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "selectAll", gyousyaCode);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method3);
            __query.setMethod(__method3);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/gyousya/TGyousyaMailaddressDao/selectAll.sql");
            __query.setEntityType(jp.co.edi_java.app.entity.gyousya._TGyousyaMailaddressEntity.getSingletonInternal());
            __query.addParameter("gyousyaCode", java.lang.String.class, gyousyaCode);
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl");
            __query.setCallerMethodName("selectAll");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity>> __command = getCommandImplementors().createSelectCommand(__method3, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity>(jp.co.edi_java.app.entity.gyousya._TGyousyaMailaddressEntity.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "selectAll", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "selectAll", __e);
            throw __e;
        }
    }

    @Override
    public int countByMailaddress(java.lang.String mailaddress, java.lang.String gyousyaCode) {
        entering("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "countByMailaddress", mailaddress, gyousyaCode);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method4);
            __query.setMethod(__method4);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/gyousya/TGyousyaMailaddressDao/countByMailaddress.sql");
            __query.addParameter("mailaddress", java.lang.String.class, mailaddress);
            __query.addParameter("gyousyaCode", java.lang.String.class, gyousyaCode);
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl");
            __query.setCallerMethodName("countByMailaddress");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.lang.Integer> __command = getCommandImplementors().createSelectCommand(__method4, __query, new org.seasar.doma.internal.jdbc.command.BasicSingleResultHandler<java.lang.Integer>(org.seasar.doma.wrapper.IntegerWrapper::new, true));
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "countByMailaddress", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "countByMailaddress", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<java.lang.String> selectList(java.lang.String gyousyaCode) {
        entering("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "selectList", gyousyaCode);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method5);
            __query.setMethod(__method5);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/gyousya/TGyousyaMailaddressDao/selectList.sql");
            __query.addParameter("gyousyaCode", java.lang.String.class, gyousyaCode);
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl");
            __query.setCallerMethodName("selectList");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<java.lang.String>> __command = getCommandImplementors().createSelectCommand(__method5, __query, new org.seasar.doma.internal.jdbc.command.BasicResultListHandler<java.lang.String>(org.seasar.doma.wrapper.StringWrapper::new));
            java.util.List<java.lang.String> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "selectList", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "selectList", __e);
            throw __e;
        }
    }

    @Override
    public int insert(jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity entity) {
        entering("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "insert", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoInsertQuery<jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity> __query = getQueryImplementors().createAutoInsertQuery(__method6, jp.co.edi_java.app.entity.gyousya._TGyousyaMailaddressEntity.getSingletonInternal());
            __query.setMethod(__method6);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl");
            __query.setCallerMethodName("insert");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setNullExcluded(true);
            __query.setIncludedPropertyNames();
            __query.setExcludedPropertyNames();
            __query.prepare();
            org.seasar.doma.jdbc.command.InsertCommand __command = getCommandImplementors().createInsertCommand(__method6, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "insert", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "insert", __e);
            throw __e;
        }
    }

    @Override
    public int update(jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity entity) {
        entering("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "update", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoUpdateQuery<jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity> __query = getQueryImplementors().createAutoUpdateQuery(__method7, jp.co.edi_java.app.entity.gyousya._TGyousyaMailaddressEntity.getSingletonInternal());
            __query.setMethod(__method7);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl");
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
            org.seasar.doma.jdbc.command.UpdateCommand __command = getCommandImplementors().createUpdateCommand(__method7, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "update", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "update", __e);
            throw __e;
        }
    }

    @Override
    public int delete(jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity entity) {
        entering("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "delete", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoDeleteQuery<jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity> __query = getQueryImplementors().createAutoDeleteQuery(__method8, jp.co.edi_java.app.entity.gyousya._TGyousyaMailaddressEntity.getSingletonInternal());
            __query.setMethod(__method8);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl");
            __query.setCallerMethodName("delete");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setVersionIgnored(false);
            __query.setOptimisticLockExceptionSuppressed(false);
            __query.prepare();
            org.seasar.doma.jdbc.command.DeleteCommand __command = getCommandImplementors().createDeleteCommand(__method8, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "delete", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDaoImpl", "delete", __e);
            throw __e;
        }
    }

}
