package jp.co.edi_java.app.entity.gyousya;

/** */
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:58.371+0900")
public final class _TGyousyaAccountEntity extends org.seasar.doma.jdbc.entity.AbstractEntityType<jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity> {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final _TGyousyaAccountEntity __singleton = new _TGyousyaAccountEntity();

    private final org.seasar.doma.jdbc.entity.NamingType __namingType = org.seasar.doma.jdbc.entity.NamingType.SNAKE_LOWER_CASE;

    /** the insertDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity, java.sql.Timestamp, Object> $insertDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity.class, java.sql.Timestamp.class, java.sql.Timestamp.class, () -> new org.seasar.doma.wrapper.TimestampWrapper(), null, null, "insertDate", "", __namingType, true, true, false);

    /** the insertUser */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity, java.lang.String, Object> $insertUser = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "insertUser", "", __namingType, true, true, false);

    /** the updateDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity, java.sql.Timestamp, Object> $updateDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity.class, java.sql.Timestamp.class, java.sql.Timestamp.class, () -> new org.seasar.doma.wrapper.TimestampWrapper(), null, null, "updateDate", "", __namingType, true, true, false);

    /** the updateUser */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity, java.lang.String, Object> $updateUser = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "updateUser", "", __namingType, true, true, false);

    /** the deleteDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity, java.sql.Timestamp, Object> $deleteDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity.class, java.sql.Timestamp.class, java.sql.Timestamp.class, () -> new org.seasar.doma.wrapper.TimestampWrapper(), null, null, "deleteDate", "", __namingType, true, true, false);

    /** the deleteUser */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity, java.lang.String, Object> $deleteUser = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "deleteUser", "", __namingType, true, true, false);

    /** the deleteFlg */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity, java.lang.String, Object> $deleteFlg = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "deleteFlg", "", __namingType, true, true, false);

    /** the gyousyaCode */
    public final org.seasar.doma.jdbc.entity.AssignedIdPropertyType<java.lang.Object, jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity, java.lang.String, Object> $gyousyaCode = new org.seasar.doma.jdbc.entity.AssignedIdPropertyType<>(jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "gyousyaCode", "", __namingType, false);

    /** the password */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity, java.lang.String, Object> $password = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "password", "", __namingType, true, true, false);

    /** the authFlg */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity, java.lang.String, Object> $authFlg = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "authFlg", "", __namingType, true, true, false);

    /** the token */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity, java.lang.String, Object> $token = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "token", "", __namingType, true, true, false);

    private final java.util.function.Supplier<jp.co.edi_java.app.entity.listener.BaseEntityListener<jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity>> __listenerSupplier;

    private final boolean __immutable;

    private final String __catalogName;

    private final String __schemaName;

    private final String __tableName;

    private final boolean __isQuoteRequired;

    private final String __name;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity, ?>> __idPropertyTypes;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity, ?>> __entityPropertyTypes;

    private final java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity, ?>> __entityPropertyTypeMap;

    private _TGyousyaAccountEntity() {
        __listenerSupplier = () -> ListenerHolder.listener;
        __immutable = false;
        __name = "TGyousyaAccountEntity";
        __catalogName = "";
        __schemaName = "";
        __tableName = "T_GYOUSYA_ACCOUNT";
        __isQuoteRequired = false;
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity, ?>> __idList = new java.util.ArrayList<>();
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity, ?>> __list = new java.util.ArrayList<>(11);
        java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity, ?>> __map = new java.util.HashMap<>(11);
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
        __idList.add($gyousyaCode);
        __list.add($gyousyaCode);
        __map.put("gyousyaCode", $gyousyaCode);
        __list.add($password);
        __map.put("password", $password);
        __list.add($authFlg);
        __map.put("authFlg", $authFlg);
        __list.add($token);
        __map.put("token", $token);
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
    public void preInsert(jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity entity, org.seasar.doma.jdbc.entity.PreInsertContext<jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity> context) {
        Class __listenerClass = jp.co.edi_java.app.entity.listener.BaseEntityListener.class;
        jp.co.edi_java.app.entity.listener.BaseEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preUpdate(jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity entity, org.seasar.doma.jdbc.entity.PreUpdateContext<jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity> context) {
        Class __listenerClass = jp.co.edi_java.app.entity.listener.BaseEntityListener.class;
        jp.co.edi_java.app.entity.listener.BaseEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preDelete(jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity entity, org.seasar.doma.jdbc.entity.PreDeleteContext<jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity> context) {
        Class __listenerClass = jp.co.edi_java.app.entity.listener.BaseEntityListener.class;
        jp.co.edi_java.app.entity.listener.BaseEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preDelete(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postInsert(jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity entity, org.seasar.doma.jdbc.entity.PostInsertContext<jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity> context) {
        Class __listenerClass = jp.co.edi_java.app.entity.listener.BaseEntityListener.class;
        jp.co.edi_java.app.entity.listener.BaseEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postUpdate(jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity entity, org.seasar.doma.jdbc.entity.PostUpdateContext<jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity> context) {
        Class __listenerClass = jp.co.edi_java.app.entity.listener.BaseEntityListener.class;
        jp.co.edi_java.app.entity.listener.BaseEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postDelete(jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity entity, org.seasar.doma.jdbc.entity.PostDeleteContext<jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity> context) {
        Class __listenerClass = jp.co.edi_java.app.entity.listener.BaseEntityListener.class;
        jp.co.edi_java.app.entity.listener.BaseEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postDelete(entity, context);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity, ?>> getEntityPropertyTypes() {
        return __entityPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity, ?> getEntityPropertyType(String __name) {
        return __entityPropertyTypeMap.get(__name);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity, ?>> getIdPropertyTypes() {
        return __idPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<java.lang.Object, jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity, ?, ?> getGeneratedIdPropertyType() {
        return null;
    }

    @Override
    public org.seasar.doma.jdbc.entity.VersionPropertyType<java.lang.Object, jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity, ?, ?> getVersionPropertyType() {
        return null;
    }

    @Override
    public jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity newEntity(java.util.Map<String, org.seasar.doma.jdbc.entity.Property<jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity, ?>> __args) {
        jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity entity = new jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity();
        if (__args.get("insertDate") != null) __args.get("insertDate").save(entity);
        if (__args.get("insertUser") != null) __args.get("insertUser").save(entity);
        if (__args.get("updateDate") != null) __args.get("updateDate").save(entity);
        if (__args.get("updateUser") != null) __args.get("updateUser").save(entity);
        if (__args.get("deleteDate") != null) __args.get("deleteDate").save(entity);
        if (__args.get("deleteUser") != null) __args.get("deleteUser").save(entity);
        if (__args.get("deleteFlg") != null) __args.get("deleteFlg").save(entity);
        if (__args.get("gyousyaCode") != null) __args.get("gyousyaCode").save(entity);
        if (__args.get("password") != null) __args.get("password").save(entity);
        if (__args.get("authFlg") != null) __args.get("authFlg").save(entity);
        if (__args.get("token") != null) __args.get("token").save(entity);
        return entity;
    }

    @Override
    public Class<jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity> getEntityClass() {
        return jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity.class;
    }

    @Override
    public jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity getOriginalStates(jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity __entity) {
        return null;
    }

    @Override
    public void saveCurrentStates(jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity __entity) {
    }

    /**
     * @return the singleton
     */
    public static _TGyousyaAccountEntity getSingletonInternal() {
        return __singleton;
    }

    /**
     * @return the new instance
     */
    public static _TGyousyaAccountEntity newInstance() {
        return new _TGyousyaAccountEntity();
    }

    private static class ListenerHolder {
        private static jp.co.edi_java.app.entity.listener.BaseEntityListener<jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity> listener = new jp.co.edi_java.app.entity.listener.BaseEntityListener<>();
    }

}
