package jp.co.edi_java.app.entity.syain;

/** */
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:58.560+0900")
public final class _TSyainProcessLogEntity extends org.seasar.doma.jdbc.entity.AbstractEntityType<jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity> {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final _TSyainProcessLogEntity __singleton = new _TSyainProcessLogEntity();

    private final org.seasar.doma.jdbc.entity.NamingType __namingType = org.seasar.doma.jdbc.entity.NamingType.SNAKE_LOWER_CASE;

    /** the insertDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity, java.sql.Timestamp, Object> $insertDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity.class, java.sql.Timestamp.class, java.sql.Timestamp.class, () -> new org.seasar.doma.wrapper.TimestampWrapper(), null, null, "insertDate", "", __namingType, true, true, false);

    /** the insertUser */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity, java.lang.String, Object> $insertUser = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "insertUser", "", __namingType, true, true, false);

    /** the updateDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity, java.sql.Timestamp, Object> $updateDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity.class, java.sql.Timestamp.class, java.sql.Timestamp.class, () -> new org.seasar.doma.wrapper.TimestampWrapper(), null, null, "updateDate", "", __namingType, true, true, false);

    /** the updateUser */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity, java.lang.String, Object> $updateUser = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "updateUser", "", __namingType, true, true, false);

    /** the deleteDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity, java.sql.Timestamp, Object> $deleteDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity.class, java.sql.Timestamp.class, java.sql.Timestamp.class, () -> new org.seasar.doma.wrapper.TimestampWrapper(), null, null, "deleteDate", "", __namingType, true, true, false);

    /** the deleteUser */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity, java.lang.String, Object> $deleteUser = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "deleteUser", "", __namingType, true, true, false);

    /** the deleteFlg */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity, java.lang.String, Object> $deleteFlg = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "deleteFlg", "", __namingType, true, true, false);

    /** the syainCode */
    public final org.seasar.doma.jdbc.entity.AssignedIdPropertyType<java.lang.Object, jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity, java.lang.String, Object> $syainCode = new org.seasar.doma.jdbc.entity.AssignedIdPropertyType<>(jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "syainCode", "", __namingType, false);

    /** the estimateCount */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity, java.lang.Integer, Object> $estimateCount = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity.class, java.lang.Integer.class, java.lang.Integer.class, () -> new org.seasar.doma.wrapper.IntegerWrapper(), null, null, "estimateCount", "", __namingType, true, true, false);

    /** the confirmationCount */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity, java.lang.Integer, Object> $confirmationCount = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity.class, java.lang.Integer.class, java.lang.Integer.class, () -> new org.seasar.doma.wrapper.IntegerWrapper(), null, null, "confirmationCount", "", __namingType, true, true, false);

    /** the deliveryCount */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity, java.lang.Integer, Object> $deliveryCount = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity.class, java.lang.Integer.class, java.lang.Integer.class, () -> new org.seasar.doma.wrapper.IntegerWrapper(), null, null, "deliveryCount", "", __namingType, true, true, false);

    /** the workReportCount */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity, java.lang.Integer, Object> $workReportCount = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity.class, java.lang.Integer.class, java.lang.Integer.class, () -> new org.seasar.doma.wrapper.IntegerWrapper(), null, null, "workReportCount", "", __namingType, true, true, false);

    /** the inspectionReceiptCount */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity, java.lang.Integer, Object> $inspectionReceiptCount = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity.class, java.lang.Integer.class, java.lang.Integer.class, () -> new org.seasar.doma.wrapper.IntegerWrapper(), null, null, "inspectionReceiptCount", "", __namingType, true, true, false);

    private final java.util.function.Supplier<jp.co.edi_java.app.entity.listener.BaseEntityListener<jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity>> __listenerSupplier;

    private final boolean __immutable;

    private final String __catalogName;

    private final String __schemaName;

    private final String __tableName;

    private final boolean __isQuoteRequired;

    private final String __name;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity, ?>> __idPropertyTypes;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity, ?>> __entityPropertyTypes;

    private final java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity, ?>> __entityPropertyTypeMap;

    private _TSyainProcessLogEntity() {
        __listenerSupplier = () -> ListenerHolder.listener;
        __immutable = false;
        __name = "TSyainProcessLogEntity";
        __catalogName = "";
        __schemaName = "";
        __tableName = "T_SYAIN_PROCESS_LOG";
        __isQuoteRequired = false;
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity, ?>> __idList = new java.util.ArrayList<>();
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity, ?>> __list = new java.util.ArrayList<>(13);
        java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity, ?>> __map = new java.util.HashMap<>(13);
        __list.add($insertDate);
        __map.put("insertDate", $insertDate);
        __list.add($insertUser);
        __map.put("insertUser", $insertUser);
        __list.add($updateDate);
        __map.put("updateDate", $updateDate);
        __list.add($updateUser);
        __map.put("updateUser", $updateUser);
        __list.add($deleteDate);
        __map.put("deleteDate", $deleteDate);
        __list.add($deleteUser);
        __map.put("deleteUser", $deleteUser);
        __list.add($deleteFlg);
        __map.put("deleteFlg", $deleteFlg);
        __idList.add($syainCode);
        __list.add($syainCode);
        __map.put("syainCode", $syainCode);
        __list.add($estimateCount);
        __map.put("estimateCount", $estimateCount);
        __list.add($confirmationCount);
        __map.put("confirmationCount", $confirmationCount);
        __list.add($deliveryCount);
        __map.put("deliveryCount", $deliveryCount);
        __list.add($workReportCount);
        __map.put("workReportCount", $workReportCount);
        __list.add($inspectionReceiptCount);
        __map.put("inspectionReceiptCount", $inspectionReceiptCount);
        __idPropertyTypes = java.util.Collections.unmodifiableList(__idList);
        __entityPropertyTypes = java.util.Collections.unmodifiableList(__list);
        __entityPropertyTypeMap = java.util.Collections.unmodifiableMap(__map);
    }

    @Override
    public org.seasar.doma.jdbc.entity.NamingType getNamingType() {
        return __namingType;
    }

    @Override
    public boolean isImmutable() {
        return __immutable;
    }

    @Override
    public String getName() {
        return __name;
    }

    @Override
    public String getCatalogName() {
        return __catalogName;
    }

    @Override
    public String getSchemaName() {
        return __schemaName;
    }

    @Override
    public String getTableName() {
        return getTableName(org.seasar.doma.jdbc.Naming.DEFAULT::apply);
    }

    @Override
    public String getTableName(java.util.function.BiFunction<org.seasar.doma.jdbc.entity.NamingType, String, String> namingFunction) {
        if (__tableName.isEmpty()) {
            return namingFunction.apply(__namingType, __name);
        }
        return __tableName;
    }

    @Override
    public boolean isQuoteRequired() {
        return __isQuoteRequired;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preInsert(jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity entity, org.seasar.doma.jdbc.entity.PreInsertContext<jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity> context) {
        Class __listenerClass = jp.co.edi_java.app.entity.listener.BaseEntityListener.class;
        jp.co.edi_java.app.entity.listener.BaseEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preUpdate(jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity entity, org.seasar.doma.jdbc.entity.PreUpdateContext<jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity> context) {
        Class __listenerClass = jp.co.edi_java.app.entity.listener.BaseEntityListener.class;
        jp.co.edi_java.app.entity.listener.BaseEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preDelete(jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity entity, org.seasar.doma.jdbc.entity.PreDeleteContext<jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity> context) {
        Class __listenerClass = jp.co.edi_java.app.entity.listener.BaseEntityListener.class;
        jp.co.edi_java.app.entity.listener.BaseEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preDelete(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postInsert(jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity entity, org.seasar.doma.jdbc.entity.PostInsertContext<jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity> context) {
        Class __listenerClass = jp.co.edi_java.app.entity.listener.BaseEntityListener.class;
        jp.co.edi_java.app.entity.listener.BaseEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postUpdate(jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity entity, org.seasar.doma.jdbc.entity.PostUpdateContext<jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity> context) {
        Class __listenerClass = jp.co.edi_java.app.entity.listener.BaseEntityListener.class;
        jp.co.edi_java.app.entity.listener.BaseEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postDelete(jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity entity, org.seasar.doma.jdbc.entity.PostDeleteContext<jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity> context) {
        Class __listenerClass = jp.co.edi_java.app.entity.listener.BaseEntityListener.class;
        jp.co.edi_java.app.entity.listener.BaseEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postDelete(entity, context);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity, ?>> getEntityPropertyTypes() {
        return __entityPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity, ?> getEntityPropertyType(String __name) {
        return __entityPropertyTypeMap.get(__name);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity, ?>> getIdPropertyTypes() {
        return __idPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<java.lang.Object, jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity, ?, ?> getGeneratedIdPropertyType() {
        return null;
    }

    @Override
    public org.seasar.doma.jdbc.entity.VersionPropertyType<java.lang.Object, jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity, ?, ?> getVersionPropertyType() {
        return null;
    }

    @Override
    public jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity newEntity(java.util.Map<String, org.seasar.doma.jdbc.entity.Property<jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity, ?>> __args) {
        jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity entity = new jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity();
        if (__args.get("insertDate") != null) __args.get("insertDate").save(entity);
        if (__args.get("insertUser") != null) __args.get("insertUser").save(entity);
        if (__args.get("updateDate") != null) __args.get("updateDate").save(entity);
        if (__args.get("updateUser") != null) __args.get("updateUser").save(entity);
        if (__args.get("deleteDate") != null) __args.get("deleteDate").save(entity);
        if (__args.get("deleteUser") != null) __args.get("deleteUser").save(entity);
        if (__args.get("deleteFlg") != null) __args.get("deleteFlg").save(entity);
        if (__args.get("syainCode") != null) __args.get("syainCode").save(entity);
        if (__args.get("estimateCount") != null) __args.get("estimateCount").save(entity);
        if (__args.get("confirmationCount") != null) __args.get("confirmationCount").save(entity);
        if (__args.get("deliveryCount") != null) __args.get("deliveryCount").save(entity);
        if (__args.get("workReportCount") != null) __args.get("workReportCount").save(entity);
        if (__args.get("inspectionReceiptCount") != null) __args.get("inspectionReceiptCount").save(entity);
        return entity;
    }

    @Override
    public Class<jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity> getEntityClass() {
        return jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity.class;
    }

    @Override
    public jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity getOriginalStates(jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity __entity) {
        return null;
    }

    @Override
    public void saveCurrentStates(jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity __entity) {
    }

    /**
     * @return the singleton
     */
    public static _TSyainProcessLogEntity getSingletonInternal() {
        return __singleton;
    }

    /**
     * @return the new instance
     */
    public static _TSyainProcessLogEntity newInstance() {
        return new _TSyainProcessLogEntity();
    }

    private static class ListenerHolder {
        private static jp.co.edi_java.app.entity.listener.BaseEntityListener<jp.co.edi_java.app.entity.syain.TSyainProcessLogEntity> listener = new jp.co.edi_java.app.entity.listener.BaseEntityListener<>();
    }

}
