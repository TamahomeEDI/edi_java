package jp.co.edi_java.app.entity;

/** */
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:58.515+0900")
public final class _MSaimokuKousyuEntity extends org.seasar.doma.jdbc.entity.AbstractEntityType<jp.co.edi_java.app.entity.MSaimokuKousyuEntity> {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final _MSaimokuKousyuEntity __singleton = new _MSaimokuKousyuEntity();

    private final org.seasar.doma.jdbc.entity.NamingType __namingType = org.seasar.doma.jdbc.entity.NamingType.SNAKE_LOWER_CASE;

    /** the saimokuKousyuCode */
    public final org.seasar.doma.jdbc.entity.AssignedIdPropertyType<java.lang.Object, jp.co.edi_java.app.entity.MSaimokuKousyuEntity, java.lang.String, Object> $saimokuKousyuCode = new org.seasar.doma.jdbc.entity.AssignedIdPropertyType<>(jp.co.edi_java.app.entity.MSaimokuKousyuEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "saimokuKousyuCode", "", __namingType, false);

    /** the saimokuKousyuName */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.MSaimokuKousyuEntity, java.lang.String, Object> $saimokuKousyuName = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.MSaimokuKousyuEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "saimokuKousyuName", "", __namingType, true, true, false);

    /** the hattyuuSyubetuFlg */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.MSaimokuKousyuEntity, java.lang.String, Object> $hattyuuSyubetuFlg = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.MSaimokuKousyuEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "hattyuuSyubetuFlg", "", __namingType, true, true, false);

    /** the sakujyoFlg */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.MSaimokuKousyuEntity, java.lang.Integer, Object> $sakujyoFlg = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.MSaimokuKousyuEntity.class, java.lang.Integer.class, java.lang.Integer.class, () -> new org.seasar.doma.wrapper.IntegerWrapper(), null, null, "sakujyoFlg", "", __namingType, true, true, false);

    /** the saisyuuKousinDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.MSaimokuKousyuEntity, java.sql.Date, Object> $saisyuuKousinDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.MSaimokuKousyuEntity.class, java.sql.Date.class, java.sql.Date.class, () -> new org.seasar.doma.wrapper.DateWrapper(), null, null, "saisyuuKousinDate", "", __namingType, true, true, false);

    private final java.util.function.Supplier<org.seasar.doma.jdbc.entity.NullEntityListener<jp.co.edi_java.app.entity.MSaimokuKousyuEntity>> __listenerSupplier;

    private final boolean __immutable;

    private final String __catalogName;

    private final String __schemaName;

    private final String __tableName;

    private final boolean __isQuoteRequired;

    private final String __name;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.MSaimokuKousyuEntity, ?>> __idPropertyTypes;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.MSaimokuKousyuEntity, ?>> __entityPropertyTypes;

    private final java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.MSaimokuKousyuEntity, ?>> __entityPropertyTypeMap;

    private _MSaimokuKousyuEntity() {
        __listenerSupplier = () -> ListenerHolder.listener;
        __immutable = false;
        __name = "MSaimokuKousyuEntity";
        __catalogName = "";
        __schemaName = "";
        __tableName = "M_SAIMOKU_KOUSYU";
        __isQuoteRequired = false;
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.MSaimokuKousyuEntity, ?>> __idList = new java.util.ArrayList<>();
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.MSaimokuKousyuEntity, ?>> __list = new java.util.ArrayList<>(5);
        java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.MSaimokuKousyuEntity, ?>> __map = new java.util.HashMap<>(5);
        __idList.add($saimokuKousyuCode);
        __list.add($saimokuKousyuCode);
        __map.put("saimokuKousyuCode", $saimokuKousyuCode);
        __list.add($saimokuKousyuName);
        __map.put("saimokuKousyuName", $saimokuKousyuName);
        __list.add($hattyuuSyubetuFlg);
        __map.put("hattyuuSyubetuFlg", $hattyuuSyubetuFlg);
        __list.add($sakujyoFlg);
        __map.put("sakujyoFlg", $sakujyoFlg);
        __list.add($saisyuuKousinDate);
        __map.put("saisyuuKousinDate", $saisyuuKousinDate);
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
    public void preInsert(jp.co.edi_java.app.entity.MSaimokuKousyuEntity entity, org.seasar.doma.jdbc.entity.PreInsertContext<jp.co.edi_java.app.entity.MSaimokuKousyuEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preUpdate(jp.co.edi_java.app.entity.MSaimokuKousyuEntity entity, org.seasar.doma.jdbc.entity.PreUpdateContext<jp.co.edi_java.app.entity.MSaimokuKousyuEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preDelete(jp.co.edi_java.app.entity.MSaimokuKousyuEntity entity, org.seasar.doma.jdbc.entity.PreDeleteContext<jp.co.edi_java.app.entity.MSaimokuKousyuEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preDelete(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postInsert(jp.co.edi_java.app.entity.MSaimokuKousyuEntity entity, org.seasar.doma.jdbc.entity.PostInsertContext<jp.co.edi_java.app.entity.MSaimokuKousyuEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postUpdate(jp.co.edi_java.app.entity.MSaimokuKousyuEntity entity, org.seasar.doma.jdbc.entity.PostUpdateContext<jp.co.edi_java.app.entity.MSaimokuKousyuEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postDelete(jp.co.edi_java.app.entity.MSaimokuKousyuEntity entity, org.seasar.doma.jdbc.entity.PostDeleteContext<jp.co.edi_java.app.entity.MSaimokuKousyuEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postDelete(entity, context);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.MSaimokuKousyuEntity, ?>> getEntityPropertyTypes() {
        return __entityPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.MSaimokuKousyuEntity, ?> getEntityPropertyType(String __name) {
        return __entityPropertyTypeMap.get(__name);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.MSaimokuKousyuEntity, ?>> getIdPropertyTypes() {
        return __idPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<java.lang.Object, jp.co.edi_java.app.entity.MSaimokuKousyuEntity, ?, ?> getGeneratedIdPropertyType() {
        return null;
    }

    @Override
    public org.seasar.doma.jdbc.entity.VersionPropertyType<java.lang.Object, jp.co.edi_java.app.entity.MSaimokuKousyuEntity, ?, ?> getVersionPropertyType() {
        return null;
    }

    @Override
    public jp.co.edi_java.app.entity.MSaimokuKousyuEntity newEntity(java.util.Map<String, org.seasar.doma.jdbc.entity.Property<jp.co.edi_java.app.entity.MSaimokuKousyuEntity, ?>> __args) {
        jp.co.edi_java.app.entity.MSaimokuKousyuEntity entity = new jp.co.edi_java.app.entity.MSaimokuKousyuEntity();
        if (__args.get("saimokuKousyuCode") != null) __args.get("saimokuKousyuCode").save(entity);
        if (__args.get("saimokuKousyuName") != null) __args.get("saimokuKousyuName").save(entity);
        if (__args.get("hattyuuSyubetuFlg") != null) __args.get("hattyuuSyubetuFlg").save(entity);
        if (__args.get("sakujyoFlg") != null) __args.get("sakujyoFlg").save(entity);
        if (__args.get("saisyuuKousinDate") != null) __args.get("saisyuuKousinDate").save(entity);
        return entity;
    }

    @Override
    public Class<jp.co.edi_java.app.entity.MSaimokuKousyuEntity> getEntityClass() {
        return jp.co.edi_java.app.entity.MSaimokuKousyuEntity.class;
    }

    @Override
    public jp.co.edi_java.app.entity.MSaimokuKousyuEntity getOriginalStates(jp.co.edi_java.app.entity.MSaimokuKousyuEntity __entity) {
        return null;
    }

    @Override
    public void saveCurrentStates(jp.co.edi_java.app.entity.MSaimokuKousyuEntity __entity) {
    }

    /**
     * @return the singleton
     */
    public static _MSaimokuKousyuEntity getSingletonInternal() {
        return __singleton;
    }

    /**
     * @return the new instance
     */
    public static _MSaimokuKousyuEntity newInstance() {
        return new _MSaimokuKousyuEntity();
    }

    private static class ListenerHolder {
        private static org.seasar.doma.jdbc.entity.NullEntityListener<jp.co.edi_java.app.entity.MSaimokuKousyuEntity> listener = new org.seasar.doma.jdbc.entity.NullEntityListener<>();
    }

}
