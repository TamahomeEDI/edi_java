package jp.co.edi_java.app.entity.jtm;

/** */
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:58.405+0900")
public final class _VOrderEigyousyoGroupEntity extends org.seasar.doma.jdbc.entity.AbstractEntityType<jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity> {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final _VOrderEigyousyoGroupEntity __singleton = new _VOrderEigyousyoGroupEntity();

    private final org.seasar.doma.jdbc.entity.NamingType __namingType = org.seasar.doma.jdbc.entity.NamingType.SNAKE_LOWER_CASE;

    /** the eigyousyoGroupCode */
    public final org.seasar.doma.jdbc.entity.AssignedIdPropertyType<java.lang.Object, jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity, java.lang.String, Object> $eigyousyoGroupCode = new org.seasar.doma.jdbc.entity.AssignedIdPropertyType<>(jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "eigyousyoGroupCode", "", __namingType, false);

    /** the rownumber */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity, java.lang.Integer, Object> $rownumber = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity.class, java.lang.Integer.class, java.lang.Integer.class, () -> new org.seasar.doma.wrapper.IntegerWrapper(), null, null, "rownumber", "", __namingType, true, true, false);

    /** the eigyousyoGroupName */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity, java.lang.String, Object> $eigyousyoGroupName = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "eigyousyoGroupName", "", __namingType, true, true, false);

    /** the saisyuuKousinDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity, java.sql.Date, Object> $saisyuuKousinDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity.class, java.sql.Date.class, java.sql.Date.class, () -> new org.seasar.doma.wrapper.DateWrapper(), null, null, "saisyuuKousinDate", "", __namingType, true, true, false);

    private final java.util.function.Supplier<org.seasar.doma.jdbc.entity.NullEntityListener<jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity>> __listenerSupplier;

    private final boolean __immutable;

    private final String __catalogName;

    private final String __schemaName;

    private final String __tableName;

    private final boolean __isQuoteRequired;

    private final String __name;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity, ?>> __idPropertyTypes;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity, ?>> __entityPropertyTypes;

    private final java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity, ?>> __entityPropertyTypeMap;

    private _VOrderEigyousyoGroupEntity() {
        __listenerSupplier = () -> ListenerHolder.listener;
        __immutable = false;
        __name = "VOrderEigyousyoGroupEntity";
        __catalogName = "";
        __schemaName = "";
        __tableName = "V_ORDER_EIGYOUSYO_GROUP";
        __isQuoteRequired = false;
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity, ?>> __idList = new java.util.ArrayList<>();
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity, ?>> __list = new java.util.ArrayList<>(4);
        java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity, ?>> __map = new java.util.HashMap<>(4);
        __idList.add($eigyousyoGroupCode);
        __list.add($eigyousyoGroupCode);
        __map.put("eigyousyoGroupCode", $eigyousyoGroupCode);
        __list.add($rownumber);
        __map.put("rownumber", $rownumber);
        __list.add($eigyousyoGroupName);
        __map.put("eigyousyoGroupName", $eigyousyoGroupName);
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
    public void preInsert(jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity entity, org.seasar.doma.jdbc.entity.PreInsertContext<jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preUpdate(jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity entity, org.seasar.doma.jdbc.entity.PreUpdateContext<jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preDelete(jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity entity, org.seasar.doma.jdbc.entity.PreDeleteContext<jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preDelete(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postInsert(jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity entity, org.seasar.doma.jdbc.entity.PostInsertContext<jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postUpdate(jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity entity, org.seasar.doma.jdbc.entity.PostUpdateContext<jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postDelete(jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity entity, org.seasar.doma.jdbc.entity.PostDeleteContext<jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postDelete(entity, context);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity, ?>> getEntityPropertyTypes() {
        return __entityPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity, ?> getEntityPropertyType(String __name) {
        return __entityPropertyTypeMap.get(__name);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity, ?>> getIdPropertyTypes() {
        return __idPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<java.lang.Object, jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity, ?, ?> getGeneratedIdPropertyType() {
        return null;
    }

    @Override
    public org.seasar.doma.jdbc.entity.VersionPropertyType<java.lang.Object, jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity, ?, ?> getVersionPropertyType() {
        return null;
    }

    @Override
    public jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity newEntity(java.util.Map<String, org.seasar.doma.jdbc.entity.Property<jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity, ?>> __args) {
        jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity entity = new jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity();
        if (__args.get("eigyousyoGroupCode") != null) __args.get("eigyousyoGroupCode").save(entity);
        if (__args.get("rownumber") != null) __args.get("rownumber").save(entity);
        if (__args.get("eigyousyoGroupName") != null) __args.get("eigyousyoGroupName").save(entity);
        if (__args.get("saisyuuKousinDate") != null) __args.get("saisyuuKousinDate").save(entity);
        return entity;
    }

    @Override
    public Class<jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity> getEntityClass() {
        return jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity.class;
    }

    @Override
    public jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity getOriginalStates(jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity __entity) {
        return null;
    }

    @Override
    public void saveCurrentStates(jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity __entity) {
    }

    /**
     * @return the singleton
     */
    public static _VOrderEigyousyoGroupEntity getSingletonInternal() {
        return __singleton;
    }

    /**
     * @return the new instance
     */
    public static _VOrderEigyousyoGroupEntity newInstance() {
        return new _VOrderEigyousyoGroupEntity();
    }

    private static class ListenerHolder {
        private static org.seasar.doma.jdbc.entity.NullEntityListener<jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity> listener = new org.seasar.doma.jdbc.entity.NullEntityListener<>();
    }

}
