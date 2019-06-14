package jp.co.edi_java.app.dto;

/** */
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:58.107+0900")
public final class _GyousyaAccountDto extends org.seasar.doma.jdbc.entity.AbstractEntityType<jp.co.edi_java.app.dto.GyousyaAccountDto> {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final _GyousyaAccountDto __singleton = new _GyousyaAccountDto();

    private final org.seasar.doma.jdbc.entity.NamingType __namingType = org.seasar.doma.jdbc.entity.NamingType.SNAKE_LOWER_CASE;

    /** the gyousyaCode */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.GyousyaAccountDto, java.lang.String, Object> $gyousyaCode = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.GyousyaAccountDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "gyousyaCode", "", __namingType, true, true, false);

    /** the gyousyaName */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.GyousyaAccountDto, java.lang.String, Object> $gyousyaName = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.GyousyaAccountDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "gyousyaName", "", __namingType, true, true, false);

    /** the saimokuNameList */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.GyousyaAccountDto, java.lang.String, Object> $saimokuNameList = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.GyousyaAccountDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "saimokuNameList", "", __namingType, true, true, false);

    /** the registFlg */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.GyousyaAccountDto, java.lang.String, Object> $registFlg = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.GyousyaAccountDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "registFlg", "", __namingType, true, true, false);

    /** the authFlg */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.GyousyaAccountDto, java.lang.String, Object> $authFlg = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.GyousyaAccountDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "authFlg", "", __namingType, true, true, false);

    private final java.util.function.Supplier<org.seasar.doma.jdbc.entity.NullEntityListener<jp.co.edi_java.app.dto.GyousyaAccountDto>> __listenerSupplier;

    private final boolean __immutable;

    private final String __catalogName;

    private final String __schemaName;

    private final String __tableName;

    private final boolean __isQuoteRequired;

    private final String __name;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.GyousyaAccountDto, ?>> __idPropertyTypes;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.GyousyaAccountDto, ?>> __entityPropertyTypes;

    private final java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.GyousyaAccountDto, ?>> __entityPropertyTypeMap;

    private _GyousyaAccountDto() {
        __listenerSupplier = () -> ListenerHolder.listener;
        __immutable = false;
        __name = "GyousyaAccountDto";
        __catalogName = "";
        __schemaName = "";
        __tableName = "";
        __isQuoteRequired = false;
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.GyousyaAccountDto, ?>> __idList = new java.util.ArrayList<>();
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.GyousyaAccountDto, ?>> __list = new java.util.ArrayList<>(5);
        java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.GyousyaAccountDto, ?>> __map = new java.util.HashMap<>(5);
        __list.add($gyousyaCode);
        __map.put("gyousyaCode", $gyousyaCode);
        __list.add($gyousyaName);
        __map.put("gyousyaName", $gyousyaName);
        __list.add($saimokuNameList);
        __map.put("saimokuNameList", $saimokuNameList);
        __list.add($registFlg);
        __map.put("registFlg", $registFlg);
        __list.add($authFlg);
        __map.put("authFlg", $authFlg);
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
    public void preInsert(jp.co.edi_java.app.dto.GyousyaAccountDto entity, org.seasar.doma.jdbc.entity.PreInsertContext<jp.co.edi_java.app.dto.GyousyaAccountDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preUpdate(jp.co.edi_java.app.dto.GyousyaAccountDto entity, org.seasar.doma.jdbc.entity.PreUpdateContext<jp.co.edi_java.app.dto.GyousyaAccountDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preDelete(jp.co.edi_java.app.dto.GyousyaAccountDto entity, org.seasar.doma.jdbc.entity.PreDeleteContext<jp.co.edi_java.app.dto.GyousyaAccountDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preDelete(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postInsert(jp.co.edi_java.app.dto.GyousyaAccountDto entity, org.seasar.doma.jdbc.entity.PostInsertContext<jp.co.edi_java.app.dto.GyousyaAccountDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postUpdate(jp.co.edi_java.app.dto.GyousyaAccountDto entity, org.seasar.doma.jdbc.entity.PostUpdateContext<jp.co.edi_java.app.dto.GyousyaAccountDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postDelete(jp.co.edi_java.app.dto.GyousyaAccountDto entity, org.seasar.doma.jdbc.entity.PostDeleteContext<jp.co.edi_java.app.dto.GyousyaAccountDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postDelete(entity, context);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.GyousyaAccountDto, ?>> getEntityPropertyTypes() {
        return __entityPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.GyousyaAccountDto, ?> getEntityPropertyType(String __name) {
        return __entityPropertyTypeMap.get(__name);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.GyousyaAccountDto, ?>> getIdPropertyTypes() {
        return __idPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<java.lang.Object, jp.co.edi_java.app.dto.GyousyaAccountDto, ?, ?> getGeneratedIdPropertyType() {
        return null;
    }

    @Override
    public org.seasar.doma.jdbc.entity.VersionPropertyType<java.lang.Object, jp.co.edi_java.app.dto.GyousyaAccountDto, ?, ?> getVersionPropertyType() {
        return null;
    }

    @Override
    public jp.co.edi_java.app.dto.GyousyaAccountDto newEntity(java.util.Map<String, org.seasar.doma.jdbc.entity.Property<jp.co.edi_java.app.dto.GyousyaAccountDto, ?>> __args) {
        jp.co.edi_java.app.dto.GyousyaAccountDto entity = new jp.co.edi_java.app.dto.GyousyaAccountDto();
        if (__args.get("gyousyaCode") != null) __args.get("gyousyaCode").save(entity);
        if (__args.get("gyousyaName") != null) __args.get("gyousyaName").save(entity);
        if (__args.get("saimokuNameList") != null) __args.get("saimokuNameList").save(entity);
        if (__args.get("registFlg") != null) __args.get("registFlg").save(entity);
        if (__args.get("authFlg") != null) __args.get("authFlg").save(entity);
        return entity;
    }

    @Override
    public Class<jp.co.edi_java.app.dto.GyousyaAccountDto> getEntityClass() {
        return jp.co.edi_java.app.dto.GyousyaAccountDto.class;
    }

    @Override
    public jp.co.edi_java.app.dto.GyousyaAccountDto getOriginalStates(jp.co.edi_java.app.dto.GyousyaAccountDto __entity) {
        return null;
    }

    @Override
    public void saveCurrentStates(jp.co.edi_java.app.dto.GyousyaAccountDto __entity) {
    }

    /**
     * @return the singleton
     */
    public static _GyousyaAccountDto getSingletonInternal() {
        return __singleton;
    }

    /**
     * @return the new instance
     */
    public static _GyousyaAccountDto newInstance() {
        return new _GyousyaAccountDto();
    }

    private static class ListenerHolder {
        private static org.seasar.doma.jdbc.entity.NullEntityListener<jp.co.edi_java.app.dto.GyousyaAccountDto> listener = new org.seasar.doma.jdbc.entity.NullEntityListener<>();
    }

}
