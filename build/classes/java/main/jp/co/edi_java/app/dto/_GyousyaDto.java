package jp.co.edi_java.app.dto;

/** */
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:58.116+0900")
public final class _GyousyaDto extends org.seasar.doma.jdbc.entity.AbstractEntityType<jp.co.edi_java.app.dto.GyousyaDto> {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final _GyousyaDto __singleton = new _GyousyaDto();

    private final org.seasar.doma.jdbc.entity.NamingType __namingType = org.seasar.doma.jdbc.entity.NamingType.SNAKE_LOWER_CASE;

    /** the gyousyaCode */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.GyousyaDto, java.lang.String, Object> $gyousyaCode = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.GyousyaDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "gyousyaCode", "", __namingType, true, true, false);

    /** the gyousyaName */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.GyousyaDto, java.lang.String, Object> $gyousyaName = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.GyousyaDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "gyousyaName", "", __namingType, true, true, false);

    /** the gyousyaKana */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.GyousyaDto, java.lang.String, Object> $gyousyaKana = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.GyousyaDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "gyousyaKana", "", __namingType, true, true, false);

    /** the torihikiStatus */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.GyousyaDto, java.lang.Integer, Object> $torihikiStatus = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.GyousyaDto.class, java.lang.Integer.class, java.lang.Integer.class, () -> new org.seasar.doma.wrapper.IntegerWrapper(), null, null, "torihikiStatus", "", __namingType, true, true, false);

    private final java.util.function.Supplier<org.seasar.doma.jdbc.entity.NullEntityListener<jp.co.edi_java.app.dto.GyousyaDto>> __listenerSupplier;

    private final boolean __immutable;

    private final String __catalogName;

    private final String __schemaName;

    private final String __tableName;

    private final boolean __isQuoteRequired;

    private final String __name;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.GyousyaDto, ?>> __idPropertyTypes;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.GyousyaDto, ?>> __entityPropertyTypes;

    private final java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.GyousyaDto, ?>> __entityPropertyTypeMap;

    private _GyousyaDto() {
        __listenerSupplier = () -> ListenerHolder.listener;
        __immutable = false;
        __name = "GyousyaDto";
        __catalogName = "";
        __schemaName = "";
        __tableName = "";
        __isQuoteRequired = false;
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.GyousyaDto, ?>> __idList = new java.util.ArrayList<>();
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.GyousyaDto, ?>> __list = new java.util.ArrayList<>(4);
        java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.GyousyaDto, ?>> __map = new java.util.HashMap<>(4);
        __list.add($gyousyaCode);
        __map.put("gyousyaCode", $gyousyaCode);
        __list.add($gyousyaName);
        __map.put("gyousyaName", $gyousyaName);
        __list.add($gyousyaKana);
        __map.put("gyousyaKana", $gyousyaKana);
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
    public void preInsert(jp.co.edi_java.app.dto.GyousyaDto entity, org.seasar.doma.jdbc.entity.PreInsertContext<jp.co.edi_java.app.dto.GyousyaDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preUpdate(jp.co.edi_java.app.dto.GyousyaDto entity, org.seasar.doma.jdbc.entity.PreUpdateContext<jp.co.edi_java.app.dto.GyousyaDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preDelete(jp.co.edi_java.app.dto.GyousyaDto entity, org.seasar.doma.jdbc.entity.PreDeleteContext<jp.co.edi_java.app.dto.GyousyaDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preDelete(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postInsert(jp.co.edi_java.app.dto.GyousyaDto entity, org.seasar.doma.jdbc.entity.PostInsertContext<jp.co.edi_java.app.dto.GyousyaDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postUpdate(jp.co.edi_java.app.dto.GyousyaDto entity, org.seasar.doma.jdbc.entity.PostUpdateContext<jp.co.edi_java.app.dto.GyousyaDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postDelete(jp.co.edi_java.app.dto.GyousyaDto entity, org.seasar.doma.jdbc.entity.PostDeleteContext<jp.co.edi_java.app.dto.GyousyaDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postDelete(entity, context);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.GyousyaDto, ?>> getEntityPropertyTypes() {
        return __entityPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.GyousyaDto, ?> getEntityPropertyType(String __name) {
        return __entityPropertyTypeMap.get(__name);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.GyousyaDto, ?>> getIdPropertyTypes() {
        return __idPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<java.lang.Object, jp.co.edi_java.app.dto.GyousyaDto, ?, ?> getGeneratedIdPropertyType() {
        return null;
    }

    @Override
    public org.seasar.doma.jdbc.entity.VersionPropertyType<java.lang.Object, jp.co.edi_java.app.dto.GyousyaDto, ?, ?> getVersionPropertyType() {
        return null;
    }

    @Override
    public jp.co.edi_java.app.dto.GyousyaDto newEntity(java.util.Map<String, org.seasar.doma.jdbc.entity.Property<jp.co.edi_java.app.dto.GyousyaDto, ?>> __args) {
        jp.co.edi_java.app.dto.GyousyaDto entity = new jp.co.edi_java.app.dto.GyousyaDto();
        if (__args.get("gyousyaCode") != null) __args.get("gyousyaCode").save(entity);
        if (__args.get("gyousyaName") != null) __args.get("gyousyaName").save(entity);
        if (__args.get("gyousyaKana") != null) __args.get("gyousyaKana").save(entity);
        if (__args.get("torihikiStatus") != null) __args.get("torihikiStatus").save(entity);
        return entity;
    }

    @Override
    public Class<jp.co.edi_java.app.dto.GyousyaDto> getEntityClass() {
        return jp.co.edi_java.app.dto.GyousyaDto.class;
    }

    @Override
    public jp.co.edi_java.app.dto.GyousyaDto getOriginalStates(jp.co.edi_java.app.dto.GyousyaDto __entity) {
        return null;
    }

    @Override
    public void saveCurrentStates(jp.co.edi_java.app.dto.GyousyaDto __entity) {
    }

    /**
     * @return the singleton
     */
    public static _GyousyaDto getSingletonInternal() {
        return __singleton;
    }

    /**
     * @return the new instance
     */
    public static _GyousyaDto newInstance() {
        return new _GyousyaDto();
    }

    private static class ListenerHolder {
        private static org.seasar.doma.jdbc.entity.NullEntityListener<jp.co.edi_java.app.dto.GyousyaDto> listener = new org.seasar.doma.jdbc.entity.NullEntityListener<>();
    }

}
