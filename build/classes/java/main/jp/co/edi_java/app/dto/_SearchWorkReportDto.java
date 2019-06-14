package jp.co.edi_java.app.dto;

/** */
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:58.191+0900")
public final class _SearchWorkReportDto extends org.seasar.doma.jdbc.entity.AbstractEntityType<jp.co.edi_java.app.dto.SearchWorkReportDto> {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final _SearchWorkReportDto __singleton = new _SearchWorkReportDto();

    private final org.seasar.doma.jdbc.entity.NamingType __namingType = org.seasar.doma.jdbc.entity.NamingType.SNAKE_LOWER_CASE;

    /** the workReportNumber */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchWorkReportDto, java.lang.String, Object> $workReportNumber = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchWorkReportDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "workReportNumber", "", __namingType, true, true, false);

    /** the workReportDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchWorkReportDto, java.lang.String, Object> $workReportDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchWorkReportDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "workReportDate", "", __namingType, true, true, false);

    /** the workReportCount */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchWorkReportDto, java.lang.Integer, Object> $workReportCount = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchWorkReportDto.class, java.lang.Integer.class, java.lang.Integer.class, () -> new org.seasar.doma.wrapper.IntegerWrapper(), null, null, "workReportCount", "", __namingType, true, true, false);

    /** the orderNumber */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchWorkReportDto, java.lang.String, Object> $orderNumber = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchWorkReportDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "orderNumber", "", __namingType, true, true, false);

    /** the staffReceiptFlg */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchWorkReportDto, java.lang.String, Object> $staffReceiptFlg = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchWorkReportDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "staffReceiptFlg", "", __namingType, true, true, false);

    /** the clerkReceiptFlg */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchWorkReportDto, java.lang.String, Object> $clerkReceiptFlg = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchWorkReportDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "clerkReceiptFlg", "", __namingType, true, true, false);

    /** the managerReceiptFlg */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchWorkReportDto, java.lang.String, Object> $managerReceiptFlg = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchWorkReportDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "managerReceiptFlg", "", __namingType, true, true, false);

    /** the koujiCode */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchWorkReportDto, java.lang.String, Object> $koujiCode = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchWorkReportDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "koujiCode", "", __namingType, true, true, false);

    /** the koujiName */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchWorkReportDto, java.lang.String, Object> $koujiName = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchWorkReportDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "koujiName", "", __namingType, true, true, false);

    /** the koujiStatusName */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchWorkReportDto, java.lang.String, Object> $koujiStatusName = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchWorkReportDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "koujiStatusName", "", __namingType, true, true, false);

    /** the gyousyaName */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchWorkReportDto, java.lang.String, Object> $gyousyaName = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchWorkReportDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "gyousyaName", "", __namingType, true, true, false);

    /** the saimokuKousyuName */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchWorkReportDto, java.lang.String, Object> $saimokuKousyuName = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchWorkReportDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "saimokuKousyuName", "", __namingType, true, true, false);

    /** the eigyousyoName */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchWorkReportDto, java.lang.String, Object> $eigyousyoName = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchWorkReportDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "eigyousyoName", "", __namingType, true, true, false);

    /** the syainName */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchWorkReportDto, java.lang.String, Object> $syainName = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchWorkReportDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "syainName", "", __namingType, true, true, false);

    /** the workRate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchWorkReportDto, java.lang.Integer, Object> $workRate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchWorkReportDto.class, java.lang.Integer.class, java.lang.Integer.class, () -> new org.seasar.doma.wrapper.IntegerWrapper(), null, null, "workRate", "", __namingType, true, true, false);

    /** the remandFlg */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchWorkReportDto, java.lang.String, Object> $remandFlg = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchWorkReportDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "remandFlg", "", __namingType, true, true, false);

    /** the userBikou */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchWorkReportDto, java.lang.String, Object> $userBikou = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchWorkReportDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "userBikou", "", __namingType, true, true, false);

    /** the partnerBikou */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchWorkReportDto, java.lang.String, Object> $partnerBikou = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchWorkReportDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "partnerBikou", "", __namingType, true, true, false);

    /** the torihikiStatus */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchWorkReportDto, java.lang.Integer, Object> $torihikiStatus = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SearchWorkReportDto.class, java.lang.Integer.class, java.lang.Integer.class, () -> new org.seasar.doma.wrapper.IntegerWrapper(), null, null, "torihikiStatus", "", __namingType, true, true, false);

    private final java.util.function.Supplier<org.seasar.doma.jdbc.entity.NullEntityListener<jp.co.edi_java.app.dto.SearchWorkReportDto>> __listenerSupplier;

    private final boolean __immutable;

    private final String __catalogName;

    private final String __schemaName;

    private final String __tableName;

    private final boolean __isQuoteRequired;

    private final String __name;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SearchWorkReportDto, ?>> __idPropertyTypes;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SearchWorkReportDto, ?>> __entityPropertyTypes;

    private final java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SearchWorkReportDto, ?>> __entityPropertyTypeMap;

    private _SearchWorkReportDto() {
        __listenerSupplier = () -> ListenerHolder.listener;
        __immutable = false;
        __name = "SearchWorkReportDto";
        __catalogName = "";
        __schemaName = "";
        __tableName = "";
        __isQuoteRequired = false;
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SearchWorkReportDto, ?>> __idList = new java.util.ArrayList<>();
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SearchWorkReportDto, ?>> __list = new java.util.ArrayList<>(19);
        java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SearchWorkReportDto, ?>> __map = new java.util.HashMap<>(19);
        __list.add($workReportNumber);
        __map.put("workReportNumber", $workReportNumber);
        __list.add($workReportDate);
        __map.put("workReportDate", $workReportDate);
        __list.add($workReportCount);
        __map.put("workReportCount", $workReportCount);
        __list.add($orderNumber);
        __map.put("orderNumber", $orderNumber);
        __list.add($staffReceiptFlg);
        __map.put("staffReceiptFlg", $staffReceiptFlg);
        __list.add($clerkReceiptFlg);
        __map.put("clerkReceiptFlg", $clerkReceiptFlg);
        __list.add($managerReceiptFlg);
        __map.put("managerReceiptFlg", $managerReceiptFlg);
        __list.add($koujiCode);
        __map.put("koujiCode", $koujiCode);
        __list.add($koujiName);
        __map.put("koujiName", $koujiName);
        __list.add($koujiStatusName);
        __map.put("koujiStatusName", $koujiStatusName);
        __list.add($gyousyaName);
        __map.put("gyousyaName", $gyousyaName);
        __list.add($saimokuKousyuName);
        __map.put("saimokuKousyuName", $saimokuKousyuName);
        __list.add($eigyousyoName);
        __map.put("eigyousyoName", $eigyousyoName);
        __list.add($syainName);
        __map.put("syainName", $syainName);
        __list.add($workRate);
        __map.put("workRate", $workRate);
        __list.add($remandFlg);
        __map.put("remandFlg", $remandFlg);
        __list.add($userBikou);
        __map.put("userBikou", $userBikou);
        __list.add($partnerBikou);
        __map.put("partnerBikou", $partnerBikou);
        __list.add($torihikiStatus);
        __map.put("torihikiStatus", $torihikiStatus);
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
    public void preInsert(jp.co.edi_java.app.dto.SearchWorkReportDto entity, org.seasar.doma.jdbc.entity.PreInsertContext<jp.co.edi_java.app.dto.SearchWorkReportDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preUpdate(jp.co.edi_java.app.dto.SearchWorkReportDto entity, org.seasar.doma.jdbc.entity.PreUpdateContext<jp.co.edi_java.app.dto.SearchWorkReportDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preDelete(jp.co.edi_java.app.dto.SearchWorkReportDto entity, org.seasar.doma.jdbc.entity.PreDeleteContext<jp.co.edi_java.app.dto.SearchWorkReportDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preDelete(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postInsert(jp.co.edi_java.app.dto.SearchWorkReportDto entity, org.seasar.doma.jdbc.entity.PostInsertContext<jp.co.edi_java.app.dto.SearchWorkReportDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postUpdate(jp.co.edi_java.app.dto.SearchWorkReportDto entity, org.seasar.doma.jdbc.entity.PostUpdateContext<jp.co.edi_java.app.dto.SearchWorkReportDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postDelete(jp.co.edi_java.app.dto.SearchWorkReportDto entity, org.seasar.doma.jdbc.entity.PostDeleteContext<jp.co.edi_java.app.dto.SearchWorkReportDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postDelete(entity, context);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SearchWorkReportDto, ?>> getEntityPropertyTypes() {
        return __entityPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SearchWorkReportDto, ?> getEntityPropertyType(String __name) {
        return __entityPropertyTypeMap.get(__name);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SearchWorkReportDto, ?>> getIdPropertyTypes() {
        return __idPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchWorkReportDto, ?, ?> getGeneratedIdPropertyType() {
        return null;
    }

    @Override
    public org.seasar.doma.jdbc.entity.VersionPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SearchWorkReportDto, ?, ?> getVersionPropertyType() {
        return null;
    }

    @Override
    public jp.co.edi_java.app.dto.SearchWorkReportDto newEntity(java.util.Map<String, org.seasar.doma.jdbc.entity.Property<jp.co.edi_java.app.dto.SearchWorkReportDto, ?>> __args) {
        jp.co.edi_java.app.dto.SearchWorkReportDto entity = new jp.co.edi_java.app.dto.SearchWorkReportDto();
        if (__args.get("workReportNumber") != null) __args.get("workReportNumber").save(entity);
        if (__args.get("workReportDate") != null) __args.get("workReportDate").save(entity);
        if (__args.get("workReportCount") != null) __args.get("workReportCount").save(entity);
        if (__args.get("orderNumber") != null) __args.get("orderNumber").save(entity);
        if (__args.get("staffReceiptFlg") != null) __args.get("staffReceiptFlg").save(entity);
        if (__args.get("clerkReceiptFlg") != null) __args.get("clerkReceiptFlg").save(entity);
        if (__args.get("managerReceiptFlg") != null) __args.get("managerReceiptFlg").save(entity);
        if (__args.get("koujiCode") != null) __args.get("koujiCode").save(entity);
        if (__args.get("koujiName") != null) __args.get("koujiName").save(entity);
        if (__args.get("koujiStatusName") != null) __args.get("koujiStatusName").save(entity);
        if (__args.get("gyousyaName") != null) __args.get("gyousyaName").save(entity);
        if (__args.get("saimokuKousyuName") != null) __args.get("saimokuKousyuName").save(entity);
        if (__args.get("eigyousyoName") != null) __args.get("eigyousyoName").save(entity);
        if (__args.get("syainName") != null) __args.get("syainName").save(entity);
        if (__args.get("workRate") != null) __args.get("workRate").save(entity);
        if (__args.get("remandFlg") != null) __args.get("remandFlg").save(entity);
        if (__args.get("userBikou") != null) __args.get("userBikou").save(entity);
        if (__args.get("partnerBikou") != null) __args.get("partnerBikou").save(entity);
        if (__args.get("torihikiStatus") != null) __args.get("torihikiStatus").save(entity);
        return entity;
    }

    @Override
    public Class<jp.co.edi_java.app.dto.SearchWorkReportDto> getEntityClass() {
        return jp.co.edi_java.app.dto.SearchWorkReportDto.class;
    }

    @Override
    public jp.co.edi_java.app.dto.SearchWorkReportDto getOriginalStates(jp.co.edi_java.app.dto.SearchWorkReportDto __entity) {
        return null;
    }

    @Override
    public void saveCurrentStates(jp.co.edi_java.app.dto.SearchWorkReportDto __entity) {
    }

    /**
     * @return the singleton
     */
    public static _SearchWorkReportDto getSingletonInternal() {
        return __singleton;
    }

    /**
     * @return the new instance
     */
    public static _SearchWorkReportDto newInstance() {
        return new _SearchWorkReportDto();
    }

    private static class ListenerHolder {
        private static org.seasar.doma.jdbc.entity.NullEntityListener<jp.co.edi_java.app.dto.SearchWorkReportDto> listener = new org.seasar.doma.jdbc.entity.NullEntityListener<>();
    }

}
