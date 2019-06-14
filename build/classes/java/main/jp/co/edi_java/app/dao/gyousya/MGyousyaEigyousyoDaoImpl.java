package jp.co.edi_java.app.dao.gyousya;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:58.856+0900")
public class MGyousyaEigyousyoDaoImpl extends org.seasar.doma.internal.jdbc.dao.AbstractDao implements jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDao {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDao.class, "selectList", java.lang.String.class);

    private static final java.lang.reflect.Method __method2 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDao.class, "selectGroupList", java.lang.String.class);

    private static final java.lang.reflect.Method __method3 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDao.class, "insert", jp.co.edi_java.app.entity.gyousya.MGyousyaEigyousyoEntity.class);

    private static final java.lang.reflect.Method __method4 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDao.class, "update", jp.co.edi_java.app.entity.gyousya.MGyousyaEigyousyoEntity.class);

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    public MGyousyaEigyousyoDaoImpl(org.seasar.doma.jdbc.Config config) {
        super(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getInjectedConfig() {
        entering("jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDaoImpl", "getInjectedConfig");
        try {
            org.seasar.doma.jdbc.Config __result = jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDao.super.getInjectedConfig();
            exiting("jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDaoImpl", "getInjectedConfig", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDaoImpl", "getInjectedConfig", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.entity.gyousya.MGyousyaEigyousyoEntity> selectList(java.lang.String gyousyaCode) {
        entering("jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDaoImpl", "selectList", gyousyaCode);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method1);
            __query.setMethod(__method1);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/gyousya/MGyousyaEigyousyoDao/selectList.sql");
            __query.setEntityType(jp.co.edi_java.app.entity.gyousya._MGyousyaEigyousyoEntity.getSingletonInternal());
            __query.addParameter("gyousyaCode", java.lang.String.class, gyousyaCode);
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDaoImpl");
            __query.setCallerMethodName("selectList");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.entity.gyousya.MGyousyaEigyousyoEntity>> __command = getCommandImplementors().createSelectCommand(__method1, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.entity.gyousya.MGyousyaEigyousyoEntity>(jp.co.edi_java.app.entity.gyousya._MGyousyaEigyousyoEntity.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.entity.gyousya.MGyousyaEigyousyoEntity> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDaoImpl", "selectList", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDaoImpl", "selectList", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.entity.MEigyousyoGroupEntity> selectGroupList(java.lang.String gyousyaCode) {
        entering("jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDaoImpl", "selectGroupList", gyousyaCode);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method2);
            __query.setMethod(__method2);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/gyousya/MGyousyaEigyousyoDao/selectGroupList.sql");
            __query.setEntityType(jp.co.edi_java.app.entity._MEigyousyoGroupEntity.getSingletonInternal());
            __query.addParameter("gyousyaCode", java.lang.String.class, gyousyaCode);
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDaoImpl");
            __query.setCallerMethodName("selectGroupList");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.entity.MEigyousyoGroupEntity>> __command = getCommandImplementors().createSelectCommand(__method2, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.entity.MEigyousyoGroupEntity>(jp.co.edi_java.app.entity._MEigyousyoGroupEntity.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.entity.MEigyousyoGroupEntity> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDaoImpl", "selectGroupList", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDaoImpl", "selectGroupList", __e);
            throw __e;
        }
    }

    @Override
    public int insert(jp.co.edi_java.app.entity.gyousya.MGyousyaEigyousyoEntity entity) {
        entering("jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDaoImpl", "insert", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoInsertQuery<jp.co.edi_java.app.entity.gyousya.MGyousyaEigyousyoEntity> __query = getQueryImplementors().createAutoInsertQuery(__method3, jp.co.edi_java.app.entity.gyousya._MGyousyaEigyousyoEntity.getSingletonInternal());
            __query.setMethod(__method3);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDaoImpl");
            __query.setCallerMethodName("insert");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setNullExcluded(true);
            __query.setIncludedPropertyNames();
            __query.setExcludedPropertyNames();
            __query.prepare();
            org.seasar.doma.jdbc.command.InsertCommand __command = getCommandImplementors().createInsertCommand(__method3, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDaoImpl", "insert", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDaoImpl", "insert", __e);
            throw __e;
        }
    }

    @Override
    public int update(jp.co.edi_java.app.entity.gyousya.MGyousyaEigyousyoEntity entity) {
        entering("jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDaoImpl", "update", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoUpdateQuery<jp.co.edi_java.app.entity.gyousya.MGyousyaEigyousyoEntity> __query = getQueryImplementors().createAutoUpdateQuery(__method4, jp.co.edi_java.app.entity.gyousya._MGyousyaEigyousyoEntity.getSingletonInternal());
            __query.setMethod(__method4);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDaoImpl");
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
            org.seasar.doma.jdbc.command.UpdateCommand __command = getCommandImplementors().createUpdateCommand(__method4, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDaoImpl", "update", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDaoImpl", "update", __e);
            throw __e;
        }
    }

}
