package jp.co.edi_java.app.entity;

/** */
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:58.626+0900")
public final class _TKaikeiKijunEntity extends org.seasar.doma.jdbc.entity.AbstractEntityType<jp.co.edi_java.app.entity.TKaikeiKijunEntity> {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final _TKaikeiKijunEntity __singleton = new _TKaikeiKijunEntity();

    private final org.seasar.doma.jdbc.entity.NamingType __namingType = org.seasar.doma.jdbc.entity.NamingType.SNAKE_LOWER_CASE;

    private final org.seasar.doma.jdbc.id.BuiltinTableIdGenerator __idGenerator = new org.seasar.doma.jdbc.id.BuiltinTableIdGenerator();
    {
        __idGenerator.setQualifiedTableName("ID_GENERATOR");
        __idGenerator.setInitialValue(1);
        __idGenerator.setAllocationSize(1);
        __idGenerator.setPkColumnName("PK");
        __idGenerator.setPkColumnValue("ID");
        __idGenerator.setValueColumnName("VALUE");
        __idGenerator.initialize();
    }

    /** the id */
    public final org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TKaikeiKijunEntity, java.lang.Integer, Object> $id = new org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<>(jp.co.edi_java.app.entity.TKaikeiKijunEntity.class, java.lang.Integer.class, java.lang.Integer.class, () -> new org.seasar.doma.wrapper.IntegerWrapper(), null, null, "id", "", __namingType, false, __idGenerator);

    /** the gyousyaCode */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TKaikeiKijunEntity, java.lang.String, Object> $gyousyaCode = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TKaikeiKijunEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "gyousyaCode", "", __namingType, true, true, false);

    /** the hashCode */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TKaikeiKijunEntity, java.lang.String, Object> $hashCode = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TKaikeiKijunEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "hashCode", "", __namingType, true, true, false);

    /** the gyousyaName */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TKaikeiKijunEntity, java.lang.String, Object> $gyousyaName = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TKaikeiKijunEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "gyousyaName", "", __namingType, true, true, false);

    /** the staffName */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TKaikeiKijunEntity, java.lang.String, Object> $staffName = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TKaikeiKijunEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "staffName", "", __namingType, true, true, false);

    /** the mailaddress */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TKaikeiKijunEntity, java.lang.String, Object> $mailaddress = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TKaikeiKijunEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "mailaddress", "", __namingType, true, true, false);

    /** the kaikeiKijun */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TKaikeiKijunEntity, java.lang.String, Object> $kaikeiKijun = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TKaikeiKijunEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "kaikeiKijun", "", __namingType, true, true, false);

    /** the registDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TKaikeiKijunEntity, java.lang.String, Object> $registDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TKaikeiKijunEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "registDate", "", __namingType, true, true, false);

    /** the url */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TKaikeiKijunEntity, java.lang.String, Object> $url = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TKaikeiKijunEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "url", "", __namingType, true, true, false);

    private final java.util.function.Supplier<org.seasar.doma.jdbc.entity.NullEntityListener<jp.co.edi_java.app.entity.TKaikeiKijunEntity>> __listenerSupplier;

    private final boolean __immutable;

    private final String __catalogName;

    private final String __schemaName;

    private final String __tableName;

    private final boolean __isQuoteRequired;

    private final String __name;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TKaikeiKijunEntity, ?>> __idPropertyTypes;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TKaikeiKijunEntity, ?>> __entityPropertyTypes;

    private final java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TKaikeiKijunEntity, ?>> __entityPropertyTypeMap;

    private _TKaikeiKijunEntity() {
        __listenerSupplier = () -> ListenerHolder.listener;
        __immutable = false;
        __name = "TKaikeiKijunEntity";
        __catalogName = "";
        __schemaName = "";
        __tableName = "T_KAIKEI_KIJUN";
        __isQuoteRequired = false;
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TKaikeiKijunEntity, ?>> __idList = new java.util.ArrayList<>();
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TKaikeiKijunEntity, ?>> __list = new java.util.ArrayList<>(9);
        java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TKaikeiKijunEntity, ?>> __map = new java.util.HashMap<>(9);
        __idList.add($id);
        __list.add($id);
        __map.put("id", $id);
        __list.add($gyousyaCode);
        __map.put("gyousyaCode", $gyousyaCode);
        __list.add($hashCode);
        __map.put("hashCode", $hashCode);
        __list.add($gyousyaName);
        __map.put("gyousyaName", $gyousyaName);
        __list.add($staffName);
        __map.put("staffName", $staffName);
        __list.add($mailaddress);
        __map.put("mailaddress", $mailaddress);
        __list.add($kaikeiKijun);
        __map.put("kaikeiKijun", $kaikeiKijun);
        __list.add($registDate);
        __map.put("registDate", $registDate);
        __list.add($url);
        __map.put("url", $url);
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
    public void preInsert(jp.co.edi_java.app.entity.TKaikeiKijunEntity entity, org.seasar.doma.jdbc.entity.PreInsertContext<jp.co.edi_java.app.entity.TKaikeiKijunEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preUpdate(jp.co.edi_java.app.entity.TKaikeiKijunEntity entity, org.seasar.doma.jdbc.entity.PreUpdateContext<jp.co.edi_java.app.entity.TKaikeiKijunEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preDelete(jp.co.edi_java.app.entity.TKaikeiKijunEntity entity, org.seasar.doma.jdbc.entity.PreDeleteContext<jp.co.edi_java.app.entity.TKaikeiKijunEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preDelete(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postInsert(jp.co.edi_java.app.entity.TKaikeiKijunEntity entity, org.seasar.doma.jdbc.entity.PostInsertContext<jp.co.edi_java.app.entity.TKaikeiKijunEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postUpdate(jp.co.edi_java.app.entity.TKaikeiKijunEntity entity, org.seasar.doma.jdbc.entity.PostUpdateContext<jp.co.edi_java.app.entity.TKaikeiKijunEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postDelete(jp.co.edi_java.app.entity.TKaikeiKijunEntity entity, org.seasar.doma.jdbc.entity.PostDeleteContext<jp.co.edi_java.app.entity.TKaikeiKijunEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postDelete(entity, context);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TKaikeiKijunEntity, ?>> getEntityPropertyTypes() {
        return __entityPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TKaikeiKijunEntity, ?> getEntityPropertyType(String __name) {
        return __entityPropertyTypeMap.get(__name);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TKaikeiKijunEntity, ?>> getIdPropertyTypes() {
        return __idPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TKaikeiKijunEntity, ?, ?> getGeneratedIdPropertyType() {
        return $id;
    }

    @Override
    public org.seasar.doma.jdbc.entity.VersionPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TKaikeiKijunEntity, ?, ?> getVersionPropertyType() {
        return null;
    }

    @Override
    public jp.co.edi_java.app.entity.TKaikeiKijunEntity newEntity(java.util.Map<String, org.seasar.doma.jdbc.entity.Property<jp.co.edi_java.app.entity.TKaikeiKijunEntity, ?>> __args) {
        jp.co.edi_java.app.entity.TKaikeiKijunEntity entity = new jp.co.edi_java.app.entity.TKaikeiKijunEntity();
        if (__args.get("id") != null) __args.get("id").save(entity);
        if (__args.get("gyousyaCode") != null) __args.get("gyousyaCode").save(entity);
        if (__args.get("hashCode") != null) __args.get("hashCode").save(entity);
        if (__args.get("gyousyaName") != null) __args.get("gyousyaName").save(entity);
        if (__args.get("staffName") != null) __args.get("staffName").save(entity);
        if (__args.get("mailaddress") != null) __args.get("mailaddress").save(entity);
        if (__args.get("kaikeiKijun") != null) __args.get("kaikeiKijun").save(entity);
        if (__args.get("registDate") != null) __args.get("registDate").save(entity);
        if (__args.get("url") != null) __args.get("url").save(entity);
        return entity;
    }

    @Override
    public Class<jp.co.edi_java.app.entity.TKaikeiKijunEntity> getEntityClass() {
        return jp.co.edi_java.app.entity.TKaikeiKijunEntity.class;
    }

    @Override
    public jp.co.edi_java.app.entity.TKaikeiKijunEntity getOriginalStates(jp.co.edi_java.app.entity.TKaikeiKijunEntity __entity) {
        return null;
    }

    @Override
    public void saveCurrentStates(jp.co.edi_java.app.entity.TKaikeiKijunEntity __entity) {
    }

    /**
     * @return the singleton
     */
    public static _TKaikeiKijunEntity getSingletonInternal() {
        return __singleton;
    }

    /**
     * @return the new instance
     */
    public static _TKaikeiKijunEntity newInstance() {
        return new _TKaikeiKijunEntity();
    }

    private static class ListenerHolder {
        private static org.seasar.doma.jdbc.entity.NullEntityListener<jp.co.edi_java.app.entity.TKaikeiKijunEntity> listener = new org.seasar.doma.jdbc.entity.NullEntityListener<>();
    }

}
