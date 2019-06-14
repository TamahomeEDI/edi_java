package jp.co.edi_java.app.dto;

/** */
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:58.179+0900")
public final class _SearchOrderInfoDto extends org.seasar.doma.jdbc.entity.AbstractEntityType<jp.co.edi_java.app.dto.SearchOrderInfoDto> {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final _SearchOrderInfoDto __singleton = new _SearchOrderInfoDto();

    private final org.seasar.doma.jdbc.entity.NamingType __namingType = org.seasar.doma.jdbc.entity.NamingType.SNAKE_LOWER_CASE;

    /** the orderNumber */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchOrderInfoDto, java.lang.String, Object> $orderNumber = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchOrderInfoDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "orderNumber", "", __namingType, true, true, false);

    /** the orderType */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchOrderInfoDto, java.lang.String, Object> $orderType = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchOrderInfoDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "orderType", "", __namingType, true, true, false);

    /** the confirmationFlg */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchOrderInfoDto, java.lang.String, Object> $confirmationFlg = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchOrderInfoDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "confirmationFlg", "", __namingType, true, true, false);

    /** the confirmationRequestDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchOrderInfoDto, java.sql.Timestamp, Object> $confirmationRequestDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchOrderInfoDto.class, java.sql.Timestamp.class, java.sql.Timestamp.class, () -> new org.seasar.doma.wrapper.TimestampWrapper(), null, null, "confirmationRequestDate", "", __namingType, true, true, false);

    /** the confirmationAgreeDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchOrderInfoDto, java.sql.Timestamp, Object> $confirmationAgreeDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchOrderInfoDto.class, java.sql.Timestamp.class, java.sql.Timestamp.class, () -> new org.seasar.doma.wrapper.TimestampWrapper(), null, null, "confirmationAgreeDate", "", __namingType, true, true, false);

    /** the cancelFlg */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchOrderInfoDto, java.lang.String, Object> $cancelFlg = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchOrderInfoDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "cancelFlg", "", __namingType, true, true, false);

    /** the cancelRequestDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchOrderInfoDto, java.sql.Timestamp, Object> $cancelRequestDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchOrderInfoDto.class, java.sql.Timestamp.class, java.sql.Timestamp.class, () -> new org.seasar.doma.wrapper.TimestampWrapper(), null, null, "cancelRequestDate", "", __namingType, true, true, false);

    /** the cancelAgreeDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchOrderInfoDto, java.sql.Timestamp, Object> $cancelAgreeDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchOrderInfoDto.class, java.sql.Timestamp.class, java.sql.Timestamp.class, () -> new org.seasar.doma.wrapper.TimestampWrapper(), null, null, "cancelAgreeDate", "", __namingType, true, true, false);

    /** the workNumber */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchOrderInfoDto, java.lang.String, Object> $workNumber = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchOrderInfoDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "workNumber", "", __namingType, true, true, false);

    /** the staffReceiptFlg */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchOrderInfoDto, java.lang.String, Object> $staffReceiptFlg = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchOrderInfoDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "staffReceiptFlg", "", __namingType, true, true, false);

    /** the staffReceiptDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchOrderInfoDto, java.sql.Timestamp, Object> $staffReceiptDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchOrderInfoDto.class, java.sql.Timestamp.class, java.sql.Timestamp.class, () -> new org.seasar.doma.wrapper.TimestampWrapper(), null, null, "staffReceiptDate", "", __namingType, true, true, false);

    /** the remandFlg */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchOrderInfoDto, java.lang.String, Object> $remandFlg = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchOrderInfoDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "remandFlg", "", __namingType, true, true, false);

    /** the orderStatus */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchOrderInfoDto, java.lang.String, Object> $orderStatus = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchOrderInfoDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "orderStatus", "", __namingType, true, true, false);

    private final java.util.function.Supplier<org.seasar.doma.jdbc.entity.NullEntityListener<jp.co.edi_java.app.dto.SearchOrderInfoDto>> __listenerSupplier;

    private final boolean __immutable;

    private final String __catalogName;

    private final String __schemaName;

    private final String __tableName;

    private final boolean __isQuoteRequired;

    private final String __name;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SearchOrderInfoDto, ?>> __idPropertyTypes;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SearchOrderInfoDto, ?>> __entityPropertyTypes;

    private final java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SearchOrderInfoDto, ?>> __entityPropertyTypeMap;

    private _SearchOrderInfoDto() {
        __listenerSupplier = () -> ListenerHolder.listener;
        __immutable = false;
        __name = "SearchOrderInfoDto";
        __catalogName = "";
        __schemaName = "";
        __tableName = "";
        __isQuoteRequired = false;
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SearchOrderInfoDto, ?>> __idList = new java.util.ArrayList<>();
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SearchOrderInfoDto, ?>> __list = new java.util.ArrayList<>(13);
        java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SearchOrderInfoDto, ?>> __map = new java.util.HashMap<>(13);
        __list.add($orderNumber);
        __map.put("orderNumber", $orderNumber);
        __list.add($orderType);
        __map.put("orderType", $orderType);
        __list.add($confirmationFlg);
        __map.put("confirmationFlg", $confirmationFlg);
        __list.add($confirmationRequestDate);
        __map.put("confirmationRequestDate", $confirmationRequestDate);
        __list.add($confirmationAgreeDate);
        __map.put("confirmationAgreeDate", $confirmationAgreeDate);
        __list.add($cancelFlg);
        __map.put("cancelFlg", $cancelFlg);
        __list.add($cancelRequestDate);
        __map.put("cancelRequestDate", $cancelRequestDate);
        __list.add($cancelAgreeDate);
        __map.put("cancelAgreeDate", $cancelAgreeDate);
        __list.add($workNumber);
        __map.put("workNumber", $workNumber);
        __list.add($staffReceiptFlg);
        __map.put("staffReceiptFlg", $staffReceiptFlg);
        __list.add($staffReceiptDate);
        __map.put("staffReceiptDate", $staffReceiptDate);
        __list.add($remandFlg);
        __map.put("remandFlg", $remandFlg);
        __list.add($orderStatus);
        __map.put("orderStatus", $orderStatus);
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
    public void preInsert(jp.co.edi_java.app.dto.SearchOrderInfoDto entity, org.seasar.doma.jdbc.entity.PreInsertContext<jp.co.edi_java.app.dto.SearchOrderInfoDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preUpdate(jp.co.edi_java.app.dto.SearchOrderInfoDto entity, org.seasar.doma.jdbc.entity.PreUpdateContext<jp.co.edi_java.app.dto.SearchOrderInfoDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preDelete(jp.co.edi_java.app.dto.SearchOrderInfoDto entity, org.seasar.doma.jdbc.entity.PreDeleteContext<jp.co.edi_java.app.dto.SearchOrderInfoDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preDelete(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postInsert(jp.co.edi_java.app.dto.SearchOrderInfoDto entity, org.seasar.doma.jdbc.entity.PostInsertContext<jp.co.edi_java.app.dto.SearchOrderInfoDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postUpdate(jp.co.edi_java.app.dto.SearchOrderInfoDto entity, org.seasar.doma.jdbc.entity.PostUpdateContext<jp.co.edi_java.app.dto.SearchOrderInfoDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postDelete(jp.co.edi_java.app.dto.SearchOrderInfoDto entity, org.seasar.doma.jdbc.entity.PostDeleteContext<jp.co.edi_java.app.dto.SearchOrderInfoDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postDelete(entity, context);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SearchOrderInfoDto, ?>> getEntityPropertyTypes() {
        return __entityPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SearchOrderInfoDto, ?> getEntityPropertyType(String __name) {
        return __entityPropertyTypeMap.get(__name);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SearchOrderInfoDto, ?>> getIdPropertyTypes() {
        return __idPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchOrderInfoDto, ?, ?> getGeneratedIdPropertyType() {
        return null;
    }

    @Override
    public org.seasar.doma.jdbc.entity.VersionPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchOrderInfoDto, ?, ?> getVersionPropertyType() {
        return null;
    }

    @Override
    public jp.co.edi_java.app.dto.SearchOrderInfoDto newEntity(java.util.Map<String, org.seasar.doma.jdbc.entity.Property<jp.co.edi_java.app.dto.SearchOrderInfoDto, ?>> __args) {
        jp.co.edi_java.app.dto.SearchOrderInfoDto entity = new jp.co.edi_java.app.dto.SearchOrderInfoDto();
        if (__args.get("orderNumber") != null) __args.get("orderNumber").save(entity);
        if (__args.get("orderType") != null) __args.get("orderType").save(entity);
        if (__args.get("confirmationFlg") != null) __args.get("confirmationFlg").save(entity);
        if (__args.get("confirmationRequestDate") != null) __args.get("confirmationRequestDate").save(entity);
        if (__args.get("confirmationAgreeDate") != null) __args.get("confirmationAgreeDate").save(entity);
        if (__args.get("cancelFlg") != null) __args.get("cancelFlg").save(entity);
        if (__args.get("cancelRequestDate") != null) __args.get("cancelRequestDate").save(entity);
        if (__args.get("cancelAgreeDate") != null) __args.get("cancelAgreeDate").save(entity);
        if (__args.get("workNumber") != null) __args.get("workNumber").save(entity);
        if (__args.get("staffReceiptFlg") != null) __args.get("staffReceiptFlg").save(entity);
        if (__args.get("staffReceiptDate") != null) __args.get("staffReceiptDate").save(entity);
        if (__args.get("remandFlg") != null) __args.get("remandFlg").save(entity);
        if (__args.get("orderStatus") != null) __args.get("orderStatus").save(entity);
        return entity;
    }

    @Override
    public Class<jp.co.edi_java.app.dto.SearchOrderInfoDto> getEntityClass() {
        return jp.co.edi_java.app.dto.SearchOrderInfoDto.class;
    }

    @Override
    public jp.co.edi_java.app.dto.SearchOrderInfoDto getOriginalStates(jp.co.edi_java.app.dto.SearchOrderInfoDto __entity) {
        return null;
    }

    @Override
    public void saveCurrentStates(jp.co.edi_java.app.dto.SearchOrderInfoDto __entity) {
    }

    /**
     * @return the singleton
     */
    public static _SearchOrderInfoDto getSingletonInternal() {
        return __singleton;
    }

    /**
     * @return the new instance
     */
    public static _SearchOrderInfoDto newInstance() {
        return new _SearchOrderInfoDto();
    }

    private static class ListenerHolder {
        private static org.seasar.doma.jdbc.entity.NullEntityListener<jp.co.edi_java.app.dto.SearchOrderInfoDto> listener = new org.seasar.doma.jdbc.entity.NullEntityListener<>();
    }

}
