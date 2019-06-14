package jp.co.edi_java.app.dao;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:59.055+0900")
public class SearchDaoImpl extends org.seasar.doma.internal.jdbc.dao.AbstractDao implements jp.co.edi_java.app.dao.SearchDao {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.SearchDao.class, "selectEstimate", jp.co.edi_java.app.form.SearchForm.class, int.class, int.class);

    private static final java.lang.reflect.Method __method2 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.SearchDao.class, "countEstimate", jp.co.edi_java.app.form.SearchForm.class);

    private static final java.lang.reflect.Method __method3 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.SearchDao.class, "selectOrderInfo", java.lang.String.class, jp.co.edi_java.app.form.SearchForm.class, java.lang.String.class);

    private static final java.lang.reflect.Method __method4 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.SearchDao.class, "getOrderInfo", java.lang.String.class, java.lang.String.class);

    private static final java.lang.reflect.Method __method5 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.SearchDao.class, "selectKoujiInfo", java.lang.String.class, java.lang.String.class);

    private static final java.lang.reflect.Method __method6 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.SearchDao.class, "selectDelivery", jp.co.edi_java.app.form.SearchForm.class, int.class, int.class);

    private static final java.lang.reflect.Method __method7 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.SearchDao.class, "selectWorkReport", jp.co.edi_java.app.form.SearchForm.class, int.class, int.class);

    private static final java.lang.reflect.Method __method8 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(jp.co.edi_java.app.dao.SearchDao.class, "selectInspectionReceipt");

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    public SearchDaoImpl(org.seasar.doma.jdbc.Config config) {
        super(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getInjectedConfig() {
        entering("jp.co.edi_java.app.dao.SearchDaoImpl", "getInjectedConfig");
        try {
            org.seasar.doma.jdbc.Config __result = jp.co.edi_java.app.dao.SearchDao.super.getInjectedConfig();
            exiting("jp.co.edi_java.app.dao.SearchDaoImpl", "getInjectedConfig", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.SearchDaoImpl", "getInjectedConfig", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.dto.SearchEstimateDto> selectEstimate(jp.co.edi_java.app.form.SearchForm params, int limit, int offset) {
        entering("jp.co.edi_java.app.dao.SearchDaoImpl", "selectEstimate", params, limit, offset);
        try {
            if (params == null) {
                throw new org.seasar.doma.DomaNullPointerException("params");
            }
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method1);
            __query.setMethod(__method1);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/SearchDao/selectEstimate.sql");
            __query.setEntityType(jp.co.edi_java.app.dto._SearchEstimateDto.getSingletonInternal());
            __query.addParameter("params", jp.co.edi_java.app.form.SearchForm.class, params);
            __query.addParameter("limit", int.class, limit);
            __query.addParameter("offset", int.class, offset);
            __query.setCallerClassName("jp.co.edi_java.app.dao.SearchDaoImpl");
            __query.setCallerMethodName("selectEstimate");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.dto.SearchEstimateDto>> __command = getCommandImplementors().createSelectCommand(__method1, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.dto.SearchEstimateDto>(jp.co.edi_java.app.dto._SearchEstimateDto.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.dto.SearchEstimateDto> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.SearchDaoImpl", "selectEstimate", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.SearchDaoImpl", "selectEstimate", __e);
            throw __e;
        }
    }

    @Override
    public int countEstimate(jp.co.edi_java.app.form.SearchForm params) {
        entering("jp.co.edi_java.app.dao.SearchDaoImpl", "countEstimate", params);
        try {
            if (params == null) {
                throw new org.seasar.doma.DomaNullPointerException("params");
            }
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method2);
            __query.setMethod(__method2);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/SearchDao/countEstimate.sql");
            __query.addParameter("params", jp.co.edi_java.app.form.SearchForm.class, params);
            __query.setCallerClassName("jp.co.edi_java.app.dao.SearchDaoImpl");
            __query.setCallerMethodName("countEstimate");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.lang.Integer> __command = getCommandImplementors().createSelectCommand(__method2, __query, new org.seasar.doma.internal.jdbc.command.BasicSingleResultHandler<java.lang.Integer>(org.seasar.doma.wrapper.IntegerWrapper::new, true));
            int __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.SearchDaoImpl", "countEstimate", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.SearchDaoImpl", "countEstimate", __e);
            throw __e;
        }
    }

    @Override
    public jp.co.edi_java.app.dto.SearchOrderInfoDto selectOrderInfo(java.lang.String orderNumber, jp.co.edi_java.app.form.SearchForm params, java.lang.String orderDate) {
        entering("jp.co.edi_java.app.dao.SearchDaoImpl", "selectOrderInfo", orderNumber, params, orderDate);
        try {
            if (params == null) {
                throw new org.seasar.doma.DomaNullPointerException("params");
            }
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method3);
            __query.setMethod(__method3);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/SearchDao/selectOrderInfo.sql");
            __query.setEntityType(jp.co.edi_java.app.dto._SearchOrderInfoDto.getSingletonInternal());
            __query.addParameter("orderNumber", java.lang.String.class, orderNumber);
            __query.addParameter("params", jp.co.edi_java.app.form.SearchForm.class, params);
            __query.addParameter("orderDate", java.lang.String.class, orderDate);
            __query.setCallerClassName("jp.co.edi_java.app.dao.SearchDaoImpl");
            __query.setCallerMethodName("selectOrderInfo");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<jp.co.edi_java.app.dto.SearchOrderInfoDto> __command = getCommandImplementors().createSelectCommand(__method3, __query, new org.seasar.doma.internal.jdbc.command.EntitySingleResultHandler<jp.co.edi_java.app.dto.SearchOrderInfoDto>(jp.co.edi_java.app.dto._SearchOrderInfoDto.getSingletonInternal()));
            jp.co.edi_java.app.dto.SearchOrderInfoDto __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.SearchDaoImpl", "selectOrderInfo", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.SearchDaoImpl", "selectOrderInfo", __e);
            throw __e;
        }
    }

    @Override
    public jp.co.edi_java.app.dto.SearchOrderInfoDto getOrderInfo(java.lang.String orderNumber, java.lang.String orderDate) {
        entering("jp.co.edi_java.app.dao.SearchDaoImpl", "getOrderInfo", orderNumber, orderDate);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method4);
            __query.setMethod(__method4);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/SearchDao/getOrderInfo.sql");
            __query.setEntityType(jp.co.edi_java.app.dto._SearchOrderInfoDto.getSingletonInternal());
            __query.addParameter("orderNumber", java.lang.String.class, orderNumber);
            __query.addParameter("orderDate", java.lang.String.class, orderDate);
            __query.setCallerClassName("jp.co.edi_java.app.dao.SearchDaoImpl");
            __query.setCallerMethodName("getOrderInfo");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<jp.co.edi_java.app.dto.SearchOrderInfoDto> __command = getCommandImplementors().createSelectCommand(__method4, __query, new org.seasar.doma.internal.jdbc.command.EntitySingleResultHandler<jp.co.edi_java.app.dto.SearchOrderInfoDto>(jp.co.edi_java.app.dto._SearchOrderInfoDto.getSingletonInternal()));
            jp.co.edi_java.app.dto.SearchOrderInfoDto __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.SearchDaoImpl", "getOrderInfo", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.SearchDaoImpl", "getOrderInfo", __e);
            throw __e;
        }
    }

    @Override
    public jp.co.edi_java.app.dto.SearchKoujiInfoDto selectKoujiInfo(java.lang.String koujiCode, java.lang.String koujiName) {
        entering("jp.co.edi_java.app.dao.SearchDaoImpl", "selectKoujiInfo", koujiCode, koujiName);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method5);
            __query.setMethod(__method5);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/SearchDao/selectKoujiInfo.sql");
            __query.setEntityType(jp.co.edi_java.app.dto._SearchKoujiInfoDto.getSingletonInternal());
            __query.addParameter("koujiCode", java.lang.String.class, koujiCode);
            __query.addParameter("koujiName", java.lang.String.class, koujiName);
            __query.setCallerClassName("jp.co.edi_java.app.dao.SearchDaoImpl");
            __query.setCallerMethodName("selectKoujiInfo");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<jp.co.edi_java.app.dto.SearchKoujiInfoDto> __command = getCommandImplementors().createSelectCommand(__method5, __query, new org.seasar.doma.internal.jdbc.command.EntitySingleResultHandler<jp.co.edi_java.app.dto.SearchKoujiInfoDto>(jp.co.edi_java.app.dto._SearchKoujiInfoDto.getSingletonInternal()));
            jp.co.edi_java.app.dto.SearchKoujiInfoDto __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.SearchDaoImpl", "selectKoujiInfo", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.SearchDaoImpl", "selectKoujiInfo", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.dto.SearchDeliveryDto> selectDelivery(jp.co.edi_java.app.form.SearchForm params, int limit, int offset) {
        entering("jp.co.edi_java.app.dao.SearchDaoImpl", "selectDelivery", params, limit, offset);
        try {
            if (params == null) {
                throw new org.seasar.doma.DomaNullPointerException("params");
            }
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method6);
            __query.setMethod(__method6);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/SearchDao/selectDelivery.sql");
            __query.setEntityType(jp.co.edi_java.app.dto._SearchDeliveryDto.getSingletonInternal());
            __query.addParameter("params", jp.co.edi_java.app.form.SearchForm.class, params);
            __query.addParameter("limit", int.class, limit);
            __query.addParameter("offset", int.class, offset);
            __query.setCallerClassName("jp.co.edi_java.app.dao.SearchDaoImpl");
            __query.setCallerMethodName("selectDelivery");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.dto.SearchDeliveryDto>> __command = getCommandImplementors().createSelectCommand(__method6, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.dto.SearchDeliveryDto>(jp.co.edi_java.app.dto._SearchDeliveryDto.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.dto.SearchDeliveryDto> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.SearchDaoImpl", "selectDelivery", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.SearchDaoImpl", "selectDelivery", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.dto.SearchWorkReportDto> selectWorkReport(jp.co.edi_java.app.form.SearchForm params, int limit, int offset) {
        entering("jp.co.edi_java.app.dao.SearchDaoImpl", "selectWorkReport", params, limit, offset);
        try {
            if (params == null) {
                throw new org.seasar.doma.DomaNullPointerException("params");
            }
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method7);
            __query.setMethod(__method7);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/SearchDao/selectWorkReport.sql");
            __query.setEntityType(jp.co.edi_java.app.dto._SearchWorkReportDto.getSingletonInternal());
            __query.addParameter("params", jp.co.edi_java.app.form.SearchForm.class, params);
            __query.addParameter("limit", int.class, limit);
            __query.addParameter("offset", int.class, offset);
            __query.setCallerClassName("jp.co.edi_java.app.dao.SearchDaoImpl");
            __query.setCallerMethodName("selectWorkReport");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.dto.SearchWorkReportDto>> __command = getCommandImplementors().createSelectCommand(__method7, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.dto.SearchWorkReportDto>(jp.co.edi_java.app.dto._SearchWorkReportDto.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.dto.SearchWorkReportDto> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.SearchDaoImpl", "selectWorkReport", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.SearchDaoImpl", "selectWorkReport", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<jp.co.edi_java.app.entity.TestEntity> selectInspectionReceipt() {
        entering("jp.co.edi_java.app.dao.SearchDaoImpl", "selectInspectionReceipt");
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method8);
            __query.setMethod(__method8);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/jp/co/edi_java/app/dao/SearchDao/selectInspectionReceipt.sql");
            __query.setEntityType(jp.co.edi_java.app.entity._TestEntity.getSingletonInternal());
            __query.setCallerClassName("jp.co.edi_java.app.dao.SearchDaoImpl");
            __query.setCallerMethodName("selectInspectionReceipt");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<jp.co.edi_java.app.entity.TestEntity>> __command = getCommandImplementors().createSelectCommand(__method8, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<jp.co.edi_java.app.entity.TestEntity>(jp.co.edi_java.app.entity._TestEntity.getSingletonInternal()));
            java.util.List<jp.co.edi_java.app.entity.TestEntity> __result = __command.execute();
            __query.complete();
            exiting("jp.co.edi_java.app.dao.SearchDaoImpl", "selectInspectionReceipt", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("jp.co.edi_java.app.dao.SearchDaoImpl", "selectInspectionReceipt", __e);
            throw __e;
        }
    }

}
