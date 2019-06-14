package jp.co.edi_java.app.dto;

/** */
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:58.201+0900")
public final class _SyainEigyousyoDto extends org.seasar.doma.jdbc.entity.AbstractEntityType<jp.co.edi_java.app.dto.SyainEigyousyoDto> {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final _SyainEigyousyoDto __singleton = new _SyainEigyousyoDto();

    private final org.seasar.doma.jdbc.entity.NamingType __namingType = org.seasar.doma.jdbc.entity.NamingType.SNAKE_LOWER_CASE;

    /** the syainCode */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SyainEigyousyoDto, java.lang.String, Object> $syainCode = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SyainEigyousyoDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "syainCode", "", __namingType, true, true, false);

    /** the eigyousyoGroupCode */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SyainEigyousyoDto, java.lang.String, Object> $eigyousyoGroupCode = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SyainEigyousyoDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "eigyousyoGroupCode", "", __namingType, true, true, false);

    /** the syumuFlg */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SyainEigyousyoDto, java.lang.String, Object> $syumuFlg = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SyainEigyousyoDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "syumuFlg", "", __namingType, true, true, false);

    /** the eigyousyoCode */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SyainEigyousyoDto, java.lang.String, Object> $eigyousyoCode = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SyainEigyousyoDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "eigyousyoCode", "", __namingType, true, true, false);

    /** the eigyousyoName */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SyainEigyousyoDto, java.lang.String, Object> $eigyousyoName = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SyainEigyousyoDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "eigyousyoName", "", __namingType, true, true, false);

    /** the eigyousyoKana */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SyainEigyousyoDto, java.lang.String, Object> $eigyousyoKana = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SyainEigyousyoDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "eigyousyoKana", "", __namingType, true, true, false);

    /** the eigyousyoYuubin */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SyainEigyousyoDto, java.lang.String, Object> $eigyousyoYuubin = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SyainEigyousyoDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "eigyousyoYuubin", "", __namingType, true, true, false);

    /** the eigyousyoJyuusyo */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SyainEigyousyoDto, java.lang.String, Object> $eigyousyoJyuusyo = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SyainEigyousyoDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "eigyousyoJyuusyo", "", __namingType, true, true, false);

    /** the eigyousyoTatemonoName */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SyainEigyousyoDto, java.lang.String, Object> $eigyousyoTatemonoName = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SyainEigyousyoDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "eigyousyoTatemonoName", "", __namingType, true, true, false);

    /** the eigyousyoTel */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SyainEigyousyoDto, java.lang.String, Object> $eigyousyoTel = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SyainEigyousyoDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "eigyousyoTel", "", __namingType, true, true, false);

    /** the eigyousyoFax */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SyainEigyousyoDto, java.lang.String, Object> $eigyousyoFax = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.dto.SyainEigyousyoDto.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "eigyousyoFax", "", __namingType, true, true, false);

    private final java.util.function.Supplier<org.seasar.doma.jdbc.entity.NullEntityListener<jp.co.edi_java.app.dto.SyainEigyousyoDto>> __listenerSupplier;

    private final boolean __immutable;

    private final String __catalogName;

    private final String __schemaName;

    private final String __tableName;

    private final boolean __isQuoteRequired;

    private final String __name;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SyainEigyousyoDto, ?>> __idPropertyTypes;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SyainEigyousyoDto, ?>> __entityPropertyTypes;

    private final java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SyainEigyousyoDto, ?>> __entityPropertyTypeMap;

    private _SyainEigyousyoDto() {
        __listenerSupplier = () -> ListenerHolder.listener;
        __immutable = false;
        __name = "SyainEigyousyoDto";
        __catalogName = "";
        __schemaName = "";
        __tableName = "";
        __isQuoteRequired = false;
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SyainEigyousyoDto, ?>> __idList = new java.util.ArrayList<>();
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SyainEigyousyoDto, ?>> __list = new java.util.ArrayList<>(11);
        java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SyainEigyousyoDto, ?>> __map = new java.util.HashMap<>(11);
        __list.add($syainCode);
        __map.put("syainCode", $syainCode);
        __list.add($eigyousyoGroupCode);
        __map.put("eigyousyoGroupCode", $eigyousyoGroupCode);
        __list.add($syumuFlg);
        __map.put("syumuFlg", $syumuFlg);
        __list.add($eigyousyoCode);
        __map.put("eigyousyoCode", $eigyousyoCode);
        __list.add($eigyousyoName);
        __map.put("eigyousyoName", $eigyousyoName);
        __list.add($eigyousyoKana);
        __map.put("eigyousyoKana", $eigyousyoKana);
        __list.add($eigyousyoYuubin);
        __map.put("eigyousyoYuubin", $eigyousyoYuubin);
        __list.add($eigyousyoJyuusyo);
        __map.put("eigyousyoJyuusyo", $eigyousyoJyuusyo);
        __list.add($eigyousyoTatemonoName);
        __map.put("eigyousyoTatemonoName", $eigyousyoTatemonoName);
        __list.add($eigyousyoTel);
        __map.put("eigyousyoTel", $eigyousyoTel);
        __list.add($eigyousyoFax);
        __map.put("eigyousyoFax", $eigyousyoFax);
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
    public void preInsert(jp.co.edi_java.app.dto.SyainEigyousyoDto entity, org.seasar.doma.jdbc.entity.PreInsertContext<jp.co.edi_java.app.dto.SyainEigyousyoDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preUpdate(jp.co.edi_java.app.dto.SyainEigyousyoDto entity, org.seasar.doma.jdbc.entity.PreUpdateContext<jp.co.edi_java.app.dto.SyainEigyousyoDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preDelete(jp.co.edi_java.app.dto.SyainEigyousyoDto entity, org.seasar.doma.jdbc.entity.PreDeleteContext<jp.co.edi_java.app.dto.SyainEigyousyoDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preDelete(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postInsert(jp.co.edi_java.app.dto.SyainEigyousyoDto entity, org.seasar.doma.jdbc.entity.PostInsertContext<jp.co.edi_java.app.dto.SyainEigyousyoDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postUpdate(jp.co.edi_java.app.dto.SyainEigyousyoDto entity, org.seasar.doma.jdbc.entity.PostUpdateContext<jp.co.edi_java.app.dto.SyainEigyousyoDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postDelete(jp.co.edi_java.app.dto.SyainEigyousyoDto entity, org.seasar.doma.jdbc.entity.PostDeleteContext<jp.co.edi_java.app.dto.SyainEigyousyoDto> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postDelete(entity, context);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SyainEigyousyoDto, ?>> getEntityPropertyTypes() {
        return __entityPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SyainEigyousyoDto, ?> getEntityPropertyType(String __name) {
        return __entityPropertyTypeMap.get(__name);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.dto.SyainEigyousyoDto, ?>> getIdPropertyTypes() {
        return __idPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SyainEigyousyoDto, ?, ?> getGeneratedIdPropertyType() {
        return null;
    }

    @Override
    public org.seasar.doma.jdbc.entity.VersionPropertyType<java.lang.Object, jp.co.edi_java.app.dto.SyainEigyousyoDto, ?, ?> getVersionPropertyType() {
        return null;
    }

    @Override
    public jp.co.edi_java.app.dto.SyainEigyousyoDto newEntity(java.util.Map<String, org.seasar.doma.jdbc.entity.Property<jp.co.edi_java.app.dto.SyainEigyousyoDto, ?>> __args) {
        jp.co.edi_java.app.dto.SyainEigyousyoDto entity = new jp.co.edi_java.app.dto.SyainEigyousyoDto();
        if (__args.get("syainCode") != null) __args.get("syainCode").save(entity);
        if (__args.get("eigyousyoGroupCode") != null) __args.get("eigyousyoGroupCode").save(entity);
        if (__args.get("syumuFlg") != null) __args.get("syumuFlg").save(entity);
        if (__args.get("eigyousyoCode") != null) __args.get("eigyousyoCode").save(entity);
        if (__args.get("eigyousyoName") != null) __args.get("eigyousyoName").save(entity);
        if (__args.get("eigyousyoKana") != null) __args.get("eigyousyoKana").save(entity);
        if (__args.get("eigyousyoYuubin") != null) __args.get("eigyousyoYuubin").save(entity);
        if (__args.get("eigyousyoJyuusyo") != null) __args.get("eigyousyoJyuusyo").save(entity);
        if (__args.get("eigyousyoTatemonoName") != null) __args.get("eigyousyoTatemonoName").save(entity);
        if (__args.get("eigyousyoTel") != null) __args.get("eigyousyoTel").save(entity);
        if (__args.get("eigyousyoFax") != null) __args.get("eigyousyoFax").save(entity);
        return entity;
    }

    @Override
    public Class<jp.co.edi_java.app.dto.SyainEigyousyoDto> getEntityClass() {
        return jp.co.edi_java.app.dto.SyainEigyousyoDto.class;
    }

    @Override
    public jp.co.edi_java.app.dto.SyainEigyousyoDto getOriginalStates(jp.co.edi_java.app.dto.SyainEigyousyoDto __entity) {
        return null;
    }

    @Override
    public void saveCurrentStates(jp.co.edi_java.app.dto.SyainEigyousyoDto __entity) {
    }

    /**
     * @return the singleton
     */
    public static _SyainEigyousyoDto getSingletonInternal() {
        return __singleton;
    }

    /**
     * @return the new instance
     */
    public static _SyainEigyousyoDto newInstance() {
        return new _SyainEigyousyoDto();
    }

    private static class ListenerHolder {
        private static org.seasar.doma.jdbc.entity.NullEntityListener<jp.co.edi_java.app.dto.SyainEigyousyoDto> listener = new org.seasar.doma.jdbc.entity.NullEntityListener<>();
    }

}
