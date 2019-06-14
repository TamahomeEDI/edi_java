package jp.co.edi_java.app.dao;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:58.975+0900")
public class MEigyousyoDaoImpl extends org.seasar.doma.internal.jdbc.dao.AbstractDao implements jp.co.edi_java.app.dao.MEigyousyoDao {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.MEigyousyoDao.class, "select", java.lang.String.class);

    private static final java.lang.reflect.Method __method2 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.MEigyousyoDao.class, "selectList", java.util.List.class);

    private static final java.lang.reflect.Method __method3 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.MEigyousyoDao.class, "selectListByPartner", java.lang.String.class, java.lang.String.class);

    private static final java.lang.reflect.Method __method4 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.MEigyousyoDao.class, "selectListByGroup", java.lang.String.class);

    private static final java.lang.reflect.Method __method5 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.MEigyousyoDao.class, "insert", jp.co.edi_java.app.entity.MEigyousyoEntity.class);

    private static final java.lang.reflect.Method __method6 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.MEigyousyoDao.class, "update", jp.co.edi_java.app.entity.MEigyousyoEntity.class);

    private static final java.lang.reflect.Method __method7 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.MEigyousyoDao.class, "deleteAll");

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    public MEigyousyoDaoImpl(org.seasar.doma.jdbc.Config config) {
        super(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getInjectedConfig() {
        entering("jp.co.edi_java.app.dao.MEigyousyoDaoImpl", "getInjectedConfig");
        try {
            org.seasar.doma.jdbc.Config __result = jp.co.edi_java.app.dao.MEigyousyoDao.super.getInjectedConfig();
            exiting("jp.co.edi_java.app.dao.MEigyousyoDaoImpl", "getInjectedConfig", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.MEigyousyoDaoImpl", "getInjectedConfig", __e);
            throw __e;
        }
    }

    @Override
    public jp.co.edi_java.app.entity.MEigyousyoEntity select(java.lang.String eigyousyoCode) {
        entering("jp.co.edi_java.app.dao.MEigyousyoDaoImpl", "select", eigyousyoCode);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method1);
            __query.setMethod(__method1);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/MEigyousyoDao/select.sql");
            __query.setEntityType(jp.co.edi_java.app.entity._MEigyousyoEntity.getSingletonInternal());
            __query.addParameter("eigyousyoCode", java.lang.String.class, eigyousyoCode);
            __query.setCallerClassName("jp.co.edi_java.app.dao.MEigyousyoDaoImpl");
            __query.setCallerMethodName("select");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<jp.co.edi_java.app.entity.MEigyousyoEntity> __command = getCommandImplementors().createSelectCommand(__method1, __query, new org.seasar.doma.internal.jdbc.command.EntitySingleResultHandler<jp.co.edi_java.app.entity.MEigyousyoEntity>(jp.co.edi_java.app.entity._MEigyousyoEntity.getSingletonInternal()));
            jp.co.edi_java.app.entity.MEigyousyoEntity __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.MEigyousyoDaoImpl", "select", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.MEigyousyoDaoImpl", "select", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.entity.MEigyousyoEntity> selectList(java.util.List<java.lang.String> eigyousyoList) {
        entering("jp.co.edi_java.app.dao.MEigyousyoDaoImpl", "selectList", eigyousyoList);
        try {
            if (eigyousyoList == null) {
                throw new org.seasar.doma.DomaNullPointerException("eigyousyoList");
            }
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method2);
            __query.setMethod(__method2);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/MEigyousyoDao/selectList.sql");
            __query.setEntityType(jp.co.edi_java.app.entity._MEigyousyoEntity.getSingletonInternal());
            __query.addParameter("eigyousyoList", java.util.List.class, eigyousyoList);
            __query.setCallerClassName("jp.co.edi_java.app.dao.MEigyousyoDaoImpl");
            __query.setCallerMethodName("selectList");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.entity.MEigyousyoEntity>> __command = getCommandImplementors().createSelectCommand(__method2, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.entity.MEigyousyoEntity>(jp.co.edi_java.app.entity._MEigyousyoEntity.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.entity.MEigyousyoEntity> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.MEigyousyoDaoImpl", "selectList", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.MEigyousyoDaoImpl", "selectList", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.dto.GyousyaEigyousyoDto> selectListByPartner(java.lang.String gyousyaCode, java.lang.String eigyousyoGroupCode) {
        entering("jp.co.edi_java.app.dao.MEigyousyoDaoImpl", "selectListByPartner", gyousyaCode, eigyousyoGroupCode);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method3);
            __query.setMethod(__method3);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/MEigyousyoDao/selectListByPartner.sql");
            __query.setEntityType(jp.co.edi_java.app.dto._GyousyaEigyousyoDto.getSingletonInternal());
            __query.addParameter("gyousyaCode", java.lang.String.class, gyousyaCode);
            __query.addParameter("eigyousyoGroupCode", java.lang.String.class, eigyousyoGroupCode);
            __query.setCallerClassName("jp.co.edi_java.app.dao.MEigyousyoDaoImpl");
            __query.setCallerMethodName("selectListByPartner");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.dto.GyousyaEigyousyoDto>> __command = getCommandImplementors().createSelectCommand(__method3, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.dto.GyousyaEigyousyoDto>(jp.co.edi_java.app.dto._GyousyaEigyousyoDto.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.dto.GyousyaEigyousyoDto> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.MEigyousyoDaoImpl", "selectListByPartner", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.MEigyousyoDaoImpl", "selectListByPartner", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.entity.MEigyousyoEntity> selectListByGroup(java.lang.String eigyousyoGroupCode) {
        entering("jp.co.edi_java.app.dao.MEigyousyoDaoImpl", "selectListByGroup", eigyousyoGroupCode);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method4);
            __query.setMethod(__method4);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/MEigyousyoDao/selectListByGroup.sql");
            __query.setEntityType(jp.co.edi_java.app.entity._MEigyousyoEntity.getSingletonInternal());
            __query.addParameter("eigyousyoGroupCode", java.lang.String.class, eigyousyoGroupCode);
            __query.setCallerClassName("jp.co.edi_java.app.dao.MEigyousyoDaoImpl");
            __query.setCallerMethodName("selectListByGroup");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.entity.MEigyousyoEntity>> __command = getCommandImplementors().createSelectCommand(__method4, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.entity.MEigyousyoEntity>(jp.co.edi_java.app.entity._MEigyousyoEntity.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.entity.MEigyousyoEntity> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.MEigyousyoDaoImpl", "selectListByGroup", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.MEigyousyoDaoImpl", "selectListByGroup", __e);
            throw __e;
        }
    }

    @Override
    public int insert(jp.co.edi_java.app.entity.MEigyousyoEntity entity) {
        entering("jp.co.edi_java.app.dao.MEigyousyoDaoImpl", "insert", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoInsertQuery<jp.co.edi_java.app.entity.MEigyousyoEntity> __query = getQueryImplementors().createAutoInsertQuery(__method5, jp.co.edi_java.app.entity._MEigyousyoEntity.getSingletonInternal());
            __query.setMethod(__method5);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.MEigyousyoDaoImpl");
            __query.setCallerMethodName("insert");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setNullExcluded(true);
            __query.setIncludedPropertyNames();
            __query.setExcludedPropertyNames();
            __query.prepare();
            org.seasar.doma.jdbc.command.InsertCommand __command = getCommandImplementors().createInsertCommand(__method5, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.MEigyousyoDaoImpl", "insert", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.MEigyousyoDaoImpl", "insert", __e);
            throw __e;
        }
    }

    @Override
    public int update(jp.co.edi_java.app.entity.MEigyousyoEntity entity) {
        entering("jp.co.edi_java.app.dao.MEigyousyoDaoImpl", "update", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoUpdateQuery<jp.co.edi_java.app.entity.MEigyousyoEntity> __query = getQueryImplementors().createAutoUpdateQuery(__method6, jp.co.edi_java.app.entity._MEigyousyoEntity.getSingletonInternal());
            __query.setMethod(__method6);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.MEigyousyoDaoImpl");
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
            org.seasar.doma.jdbc.command.UpdateCommand __command = getCommandImplementors().createUpdateCommand(__method6, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.MEigyousyoDaoImpl", "update", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.MEigyousyoDaoImpl", "update", __e);
            throw __e;
        }
    }

    @Override
    public int deleteAll() {
        entering("jp.co.edi_java.app.dao.MEigyousyoDaoImpl", "deleteAll");
        try {
            org.seasar.doma.jdbc.query.SqlFileDeleteQuery __query = getQueryImplementors().createSqlFileDeleteQuery(__method7);
            __query.setMethod(__method7);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/MEigyousyoDao/deleteAll.sql");
            __query.setCallerClassName("jp.co.edi_java.app.dao.MEigyousyoDaoImpl");
            __query.setCallerMethodName("deleteAll");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setVersionIgnored(true);
            __query.setOptimisticLockExceptionSuppressed(false);
            __query.prepare();
            org.seasar.doma.jdbc.command.DeleteCommand __command = getCommandImplementors().createDeleteCommand(__method7, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.MEigyousyoDaoImpl", "deleteAll", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.MEigyousyoDaoImpl", "deleteAll", __e);
            throw __e;
        }
    }

}
