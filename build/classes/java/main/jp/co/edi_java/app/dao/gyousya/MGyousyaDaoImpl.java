package jp.co.edi_java.app.dao.gyousya;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:58.846+0900")
public class MGyousyaDaoImpl extends org.seasar.doma.internal.jdbc.dao.AbstractDao implements jp.co.edi_java.app.dao.gyousya.MGyousyaDao {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.MGyousyaDao.class, "select", java.lang.String.class);

    private static final java.lang.reflect.Method __method2 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.MGyousyaDao.class, "selectList", java.lang.String.class);

    private static final java.lang.reflect.Method __method3 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.MGyousyaDao.class, "selectListByEigyousyoCode", java.lang.String.class, java.lang.String.class);

    private static final java.lang.reflect.Method __method4 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.MGyousyaDao.class, "selectAll");

    private static final java.lang.reflect.Method __method5 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.MGyousyaDao.class, "selectListBySearch", java.lang.String.class, java.lang.String.class);

    private static final java.lang.reflect.Method __method6 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.MGyousyaDao.class, "countByAccount", java.lang.String.class, java.lang.String.class, java.lang.String.class);

    private static final java.lang.reflect.Method __method7 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.MGyousyaDao.class, "selectListByAccount", java.lang.String.class, java.lang.String.class, java.lang.String.class, int.class, int.class);

    private static final java.lang.reflect.Method __method8 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.MGyousyaDao.class, "insert", jp.co.edi_java.app.entity.gyousya.MGyousyaEntity.class);

    private static final java.lang.reflect.Method __method9 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.MGyousyaDao.class, "update", jp.co.edi_java.app.entity.gyousya.MGyousyaEntity.class);

    private static final java.lang.reflect.Method __method10 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.MGyousyaDao.class, "delete", jp.co.edi_java.app.entity.gyousya.MGyousyaEntity.class);

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    public MGyousyaDaoImpl(org.seasar.doma.jdbc.Config config) {
        super(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getInjectedConfig() {
        entering("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "getInjectedConfig");
        try {
            org.seasar.doma.jdbc.Config __result = jp.co.edi_java.app.dao.gyousya.MGyousyaDao.super.getInjectedConfig();
            exiting("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "getInjectedConfig", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "getInjectedConfig", __e);
            throw __e;
        }
    }

    @Override
    public jp.co.edi_java.app.entity.gyousya.MGyousyaEntity select(java.lang.String gyousyaCode) {
        entering("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "select", gyousyaCode);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method1);
            __query.setMethod(__method1);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/gyousya/MGyousyaDao/select.sql");
            __query.setEntityType(jp.co.edi_java.app.entity.gyousya._MGyousyaEntity.getSingletonInternal());
            __query.addParameter("gyousyaCode", java.lang.String.class, gyousyaCode);
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl");
            __query.setCallerMethodName("select");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<jp.co.edi_java.app.entity.gyousya.MGyousyaEntity> __command = getCommandImplementors().createSelectCommand(__method1, __query, new org.seasar.doma.internal.jdbc.command.EntitySingleResultHandler<jp.co.edi_java.app.entity.gyousya.MGyousyaEntity>(jp.co.edi_java.app.entity.gyousya._MGyousyaEntity.getSingletonInternal()));
            jp.co.edi_java.app.entity.gyousya.MGyousyaEntity __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "select", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "select", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.entity.gyousya.MGyousyaEntity> selectList(java.lang.String eigyousyoCode) {
        entering("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "selectList", eigyousyoCode);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method2);
            __query.setMethod(__method2);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/gyousya/MGyousyaDao/selectList.sql");
            __query.setEntityType(jp.co.edi_java.app.entity.gyousya._MGyousyaEntity.getSingletonInternal());
            __query.addParameter("eigyousyoCode", java.lang.String.class, eigyousyoCode);
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl");
            __query.setCallerMethodName("selectList");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.entity.gyousya.MGyousyaEntity>> __command = getCommandImplementors().createSelectCommand(__method2, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.entity.gyousya.MGyousyaEntity>(jp.co.edi_java.app.entity.gyousya._MGyousyaEntity.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.entity.gyousya.MGyousyaEntity> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "selectList", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "selectList", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.dto.GyousyaDto> selectListByEigyousyoCode(java.lang.String eigyousyoCode, java.lang.String torihikiStatus) {
        entering("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "selectListByEigyousyoCode", eigyousyoCode, torihikiStatus);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method3);
            __query.setMethod(__method3);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/gyousya/MGyousyaDao/selectListByEigyousyoCode.sql");
            __query.setEntityType(jp.co.edi_java.app.dto._GyousyaDto.getSingletonInternal());
            __query.addParameter("eigyousyoCode", java.lang.String.class, eigyousyoCode);
            __query.addParameter("torihikiStatus", java.lang.String.class, torihikiStatus);
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl");
            __query.setCallerMethodName("selectListByEigyousyoCode");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.dto.GyousyaDto>> __command = getCommandImplementors().createSelectCommand(__method3, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.dto.GyousyaDto>(jp.co.edi_java.app.dto._GyousyaDto.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.dto.GyousyaDto> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "selectListByEigyousyoCode", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "selectListByEigyousyoCode", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.entity.gyousya.MGyousyaEntity> selectAll() {
        entering("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "selectAll");
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method4);
            __query.setMethod(__method4);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/gyousya/MGyousyaDao/selectAll.sql");
            __query.setEntityType(jp.co.edi_java.app.entity.gyousya._MGyousyaEntity.getSingletonInternal());
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl");
            __query.setCallerMethodName("selectAll");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.entity.gyousya.MGyousyaEntity>> __command = getCommandImplementors().createSelectCommand(__method4, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.entity.gyousya.MGyousyaEntity>(jp.co.edi_java.app.entity.gyousya._MGyousyaEntity.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.entity.gyousya.MGyousyaEntity> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "selectAll", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "selectAll", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.entity.gyousya.MGyousyaEntity> selectListBySearch(java.lang.String gyousyaCode, java.lang.String eigyousyoCode) {
        entering("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "selectListBySearch", gyousyaCode, eigyousyoCode);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method5);
            __query.setMethod(__method5);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/gyousya/MGyousyaDao/selectListBySearch.sql");
            __query.setEntityType(jp.co.edi_java.app.entity.gyousya._MGyousyaEntity.getSingletonInternal());
            __query.addParameter("gyousyaCode", java.lang.String.class, gyousyaCode);
            __query.addParameter("eigyousyoCode", java.lang.String.class, eigyousyoCode);
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl");
            __query.setCallerMethodName("selectListBySearch");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.entity.gyousya.MGyousyaEntity>> __command = getCommandImplementors().createSelectCommand(__method5, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.entity.gyousya.MGyousyaEntity>(jp.co.edi_java.app.entity.gyousya._MGyousyaEntity.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.entity.gyousya.MGyousyaEntity> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "selectListBySearch", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "selectListBySearch", __e);
            throw __e;
        }
    }

    @Override
    public int countByAccount(java.lang.String gyousyaName, java.lang.String saimokuKousyuCode, java.lang.String registKbn) {
        entering("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "countByAccount", gyousyaName, saimokuKousyuCode, registKbn);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method6);
            __query.setMethod(__method6);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/gyousya/MGyousyaDao/countByAccount.sql");
            __query.addParameter("gyousyaName", java.lang.String.class, gyousyaName);
            __query.addParameter("saimokuKousyuCode", java.lang.String.class, saimokuKousyuCode);
            __query.addParameter("registKbn", java.lang.String.class, registKbn);
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl");
            __query.setCallerMethodName("countByAccount");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.lang.Integer> __command = getCommandImplementors().createSelectCommand(__method6, __query, new org.seasar.doma.internal.jdbc.command.BasicSingleResultHandler<java.lang.Integer>(org.seasar.doma.wrapper.IntegerWrapper::new, true));
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "countByAccount", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "countByAccount", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.dto.GyousyaAccountDto> selectListByAccount(java.lang.String gyousyaName, java.lang.String saimokuKousyuCode, java.lang.String registKbn, int limit, int offset) {
        entering("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "selectListByAccount", gyousyaName, saimokuKousyuCode, registKbn, limit, offset);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method7);
            __query.setMethod(__method7);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/gyousya/MGyousyaDao/selectListByAccount.sql");
            __query.setEntityType(jp.co.edi_java.app.dto._GyousyaAccountDto.getSingletonInternal());
            __query.addParameter("gyousyaName", java.lang.String.class, gyousyaName);
            __query.addParameter("saimokuKousyuCode", java.lang.String.class, saimokuKousyuCode);
            __query.addParameter("registKbn", java.lang.String.class, registKbn);
            __query.addParameter("limit", int.class, limit);
            __query.addParameter("offset", int.class, offset);
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl");
            __query.setCallerMethodName("selectListByAccount");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.dto.GyousyaAccountDto>> __command = getCommandImplementors().createSelectCommand(__method7, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.dto.GyousyaAccountDto>(jp.co.edi_java.app.dto._GyousyaAccountDto.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.dto.GyousyaAccountDto> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "selectListByAccount", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "selectListByAccount", __e);
            throw __e;
        }
    }

    @Override
    public int insert(jp.co.edi_java.app.entity.gyousya.MGyousyaEntity entity) {
        entering("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "insert", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoInsertQuery<jp.co.edi_java.app.entity.gyousya.MGyousyaEntity> __query = getQueryImplementors().createAutoInsertQuery(__method8, jp.co.edi_java.app.entity.gyousya._MGyousyaEntity.getSingletonInternal());
            __query.setMethod(__method8);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl");
            __query.setCallerMethodName("insert");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setNullExcluded(true);
            __query.setIncludedPropertyNames();
            __query.setExcludedPropertyNames();
            __query.prepare();
            org.seasar.doma.jdbc.command.InsertCommand __command = getCommandImplementors().createInsertCommand(__method8, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "insert", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "insert", __e);
            throw __e;
        }
    }

    @Override
    public int update(jp.co.edi_java.app.entity.gyousya.MGyousyaEntity entity) {
        entering("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "update", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoUpdateQuery<jp.co.edi_java.app.entity.gyousya.MGyousyaEntity> __query = getQueryImplementors().createAutoUpdateQuery(__method9, jp.co.edi_java.app.entity.gyousya._MGyousyaEntity.getSingletonInternal());
            __query.setMethod(__method9);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl");
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
            org.seasar.doma.jdbc.command.UpdateCommand __command = getCommandImplementors().createUpdateCommand(__method9, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "update", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "update", __e);
            throw __e;
        }
    }

    @Override
    public int delete(jp.co.edi_java.app.entity.gyousya.MGyousyaEntity entity) {
        entering("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "delete", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoDeleteQuery<jp.co.edi_java.app.entity.gyousya.MGyousyaEntity> __query = getQueryImplementors().createAutoDeleteQuery(__method10, jp.co.edi_java.app.entity.gyousya._MGyousyaEntity.getSingletonInternal());
            __query.setMethod(__method10);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl");
            __query.setCallerMethodName("delete");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setVersionIgnored(false);
            __query.setOptimisticLockExceptionSuppressed(false);
            __query.prepare();
            org.seasar.doma.jdbc.command.DeleteCommand __command = getCommandImplementors().createDeleteCommand(__method10, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "delete", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.MGyousyaDaoImpl", "delete", __e);
            throw __e;
        }
    }

}
