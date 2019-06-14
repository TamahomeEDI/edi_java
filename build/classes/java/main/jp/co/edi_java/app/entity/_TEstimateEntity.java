package jp.co.edi_java.app.entity;

/** */
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:58.603+0900")
public final class _TEstimateEntity extends org.seasar.doma.jdbc.entity.AbstractEntityType<jp.co.edi_java.app.entity.TEstimateEntity> {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final _TEstimateEntity __singleton = new _TEstimateEntity();

    private final org.seasar.doma.jdbc.entity.NamingType __namingType = org.seasar.doma.jdbc.entity.NamingType.SNAKE_LOWER_CASE;

    /** the insertDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TEstimateEntity, java.sql.Timestamp, Object> $insertDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TEstimateEntity.class, java.sql.Timestamp.class, java.sql.Timestamp.class, () -> new org.seasar.doma.wrapper.TimestampWrapper(), null, null, "insertDate", "", __namingType, true, true, false);

    /** the insertUser */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TEstimateEntity, java.lang.String, Object> $insertUser = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TEstimateEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "insertUser", "", __namingType, true, true, false);

    /** the updateDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TEstimateEntity, java.sql.Timestamp, Object> $updateDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TEstimateEntity.class, java.sql.Timestamp.class, java.sql.Timestamp.class, () -> new org.seasar.doma.wrapper.TimestampWrapper(), null, null, "updateDate", "", __namingType, true, true, false);

    /** the updateUser */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TEstimateEntity, java.lang.String, Object> $updateUser = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TEstimateEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "updateUser", "", __namingType, true, true, false);

    /** the deleteDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TEstimateEntity, java.sql.Timestamp, Object> $deleteDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TEstimateEntity.class, java.sql.Timestamp.class, java.sql.Timestamp.class, () -> new org.seasar.doma.wrapper.TimestampWrapper(), null, null, "deleteDate", "", __namingType, true, true, false);

    /** the deleteUser */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TEstimateEntity, java.lang.String, Object> $deleteUser = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TEstimateEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "deleteUser", "", __namingType, true, true, false);

    /** the deleteFlg */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TEstimateEntity, java.lang.String, Object> $deleteFlg = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TEstimateEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "deleteFlg", "", __namingType, true, true, false);

    /** the estimateNumber */
    public final org.seasar.doma.jdbc.entity.AssignedIdPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TEstimateEntity, java.lang.String, Object> $estimateNumber = new org.seasar.doma.jdbc.entity.AssignedIdPropertyType<>(jp.co.edi_java.app.entity.TEstimateEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "estimateNumber", "", __namingType, false);

    /** the koujiCode */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TEstimateEntity, java.lang.String, Object> $koujiCode = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TEstimateEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "koujiCode", "", __namingType, true, true, false);

    /** the estimateRequestNumber */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TEstimateEntity, java.lang.String, Object> $estimateRequestNumber = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TEstimateEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "estimateRequestNumber", "", __namingType, true, true, false);

    /** the gyousyaCode */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TEstimateEntity, java.lang.String, Object> $gyousyaCode = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TEstimateEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "gyousyaCode", "", __namingType, true, true, false);

    /** the estimateDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TEstimateEntity, java.sql.Timestamp, Object> $estimateDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TEstimateEntity.class, java.sql.Timestamp.class, java.sql.Timestamp.class, () -> new org.seasar.doma.wrapper.TimestampWrapper(), null, null, "estimateDate", "", __namingType, true, true, false);

    /** the unreadFlg */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TEstimateEntity, java.lang.String, Object> $unreadFlg = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TEstimateEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "unreadFlg", "", __namingType, true, true, false);

    /** the userBikou */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TEstimateEntity, java.lang.String, Object> $userBikou = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TEstimateEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "userBikou", "", __namingType, true, true, false);

    /** the partnerBikou */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TEstimateEntity, java.lang.String, Object> $partnerBikou = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TEstimateEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "partnerBikou", "", __namingType, true, true, false);

    private final java.util.function.Supplier<jp.co.edi_java.app.entity.listener.BaseEntityListener<jp.co.edi_java.app.entity.TEstimateEntity>> __listenerSupplier;

    private final boolean __immutable;

    private final String __catalogName;

    private final String __schemaName;

    private final String __tableName;

    private final boolean __isQuoteRequired;

    private final String __name;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TEstimateEntity, ?>> __idPropertyTypes;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TEstimateEntity, ?>> __entityPropertyTypes;

    private final java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TEstimateEntity, ?>> __entityPropertyTypeMap;

    private _TEstimateEntity() {
        __listenerSupplier = () -> ListenerHolder.listener;
        __immutable = false;
        __name = "TEstimateEntity";
        __catalogName = "";
        __schemaName = "";
        __tableName = "T_ESTIMATE";
        __isQuoteRequired = false;
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TEstimateEntity, ?>> __idList = new java.util.ArrayList<>();
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TEstimateEntity, ?>> __list = new java.util.ArrayList<>(15);
        java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TEstimateEntity, ?>> __map = new java.util.HashMap<>(15);
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
        __idList.add($estimateNumber);
        __list.add($estimateNumber);
        __map.put("estimateNumber", $estimateNumber);
        __list.add($koujiCode);
        __map.put("koujiCode", $koujiCode);
        __list.add($estimateRequestNumber);
        __map.put("estimateRequestNumber", $estimateRequestNumber);
        __list.add($gyousyaCode);
        __map.put("gyousyaCode", $gyousyaCode);
        __list.add($estimateDate);
        __map.put("estimateDate", $estimateDate);
        __list.add($unreadFlg);
        __map.put("unreadFlg", $unreadFlg);
        __list.add($userBikou);
        __map.put("userBikou", $userBikou);
        __list.add($partnerBikou);
        __map.put("partnerBikou", $partnerBikou);
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
    public void preInsert(jp.co.edi_java.app.entity.TEstimateEntity entity, org.seasar.doma.jdbc.entity.PreInsertContext<jp.co.edi_java.app.entity.TEstimateEntity> context) {
        Class __listenerClass = jp.co.edi_java.app.entity.listener.BaseEntityListener.class;
        jp.co.edi_java.app.entity.listener.BaseEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preUpdate(jp.co.edi_java.app.entity.TEstimateEntity entity, org.seasar.doma.jdbc.entity.PreUpdateContext<jp.co.edi_java.app.entity.TEstimateEntity> context) {
        Class __listenerClass = jp.co.edi_java.app.entity.listener.BaseEntityListener.class;
        jp.co.edi_java.app.entity.listener.BaseEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preDelete(jp.co.edi_java.app.entity.TEstimateEntity entity, org.seasar.doma.jdbc.entity.PreDeleteContext<jp.co.edi_java.app.entity.TEstimateEntity> context) {
        Class __listenerClass = jp.co.edi_java.app.entity.listener.BaseEntityListener.class;
        jp.co.edi_java.app.entity.listener.BaseEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preDelete(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postInsert(jp.co.edi_java.app.entity.TEstimateEntity entity, org.seasar.doma.jdbc.entity.PostInsertContext<jp.co.edi_java.app.entity.TEstimateEntity> context) {
        Class __listenerClass = jp.co.edi_java.app.entity.listener.BaseEntityListener.class;
        jp.co.edi_java.app.entity.listener.BaseEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postUpdate(jp.co.edi_java.app.entity.TEstimateEntity entity, org.seasar.doma.jdbc.entity.PostUpdateContext<jp.co.edi_java.app.entity.TEstimateEntity> context) {
        Class __listenerClass = jp.co.edi_java.app.entity.listener.BaseEntityListener.class;
        jp.co.edi_java.app.entity.listener.BaseEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postDelete(jp.co.edi_java.app.entity.TEstimateEntity entity, org.seasar.doma.jdbc.entity.PostDeleteContext<jp.co.edi_java.app.entity.TEstimateEntity> context) {
        Class __listenerClass = jp.co.edi_java.app.entity.listener.BaseEntityListener.class;
        jp.co.edi_java.app.entity.listener.BaseEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postDelete(entity, context);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TEstimateEntity, ?>> getEntityPropertyTypes() {
        return __entityPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TEstimateEntity, ?> getEntityPropertyType(String __name) {
        return __entityPropertyTypeMap.get(__name);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TEstimateEntity, ?>> getIdPropertyTypes() {
        return __idPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TEstimateEntity, ?, ?> getGeneratedIdPropertyType() {
        return null;
    }

    @Override
    public org.seasar.doma.jdbc.entity.VersionPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TEstimateEntity, ?, ?> getVersionPropertyType() {
        return null;
    }

    @Override
    public jp.co.edi_java.app.entity.TEstimateEntity newEntity(java.util.Map<String, org.seasar.doma.jdbc.entity.Property<jp.co.edi_java.app.entity.TEstimateEntity, ?>> __args) {
        jp.co.edi_java.app.entity.TEstimateEntity entity = new jp.co.edi_java.app.entity.TEstimateEntity();
        if (__args.get("insertDate") != null) __args.get("insertDate").save(entity);
        if (__args.get("insertUser") != null) __args.get("insertUser").save(entity);
        if (__args.get("updateDate") != null) __args.get("updateDate").save(entity);
        if (__args.get("updateUser") != null) __args.get("updateUser").save(entity);
        if (__args.get("deleteDate") != null) __args.get("deleteDate").save(entity);
        if (__args.get("deleteUser") != null) __args.get("deleteUser").save(entity);
        if (__args.get("deleteFlg") != null) __args.get("deleteFlg").save(entity);
        if (__args.get("estimateNumber") != null) __args.get("estimateNumber").save(entity);
        if (__args.get("koujiCode") != null) __args.get("koujiCode").save(entity);
        if (__args.get("estimateRequestNumber") != null) __args.get("estimateRequestNumber").save(entity);
        if (__args.get("gyousyaCode") != null) __args.get("gyousyaCode").save(entity);
        if (__args.get("estimateDate") != null) __args.get("estimateDate").save(entity);
        if (__args.get("unreadFlg") != null) __args.get("unreadFlg").save(entity);
        if (__args.get("userBikou") != null) __args.get("userBikou").save(entity);
        if (__args.get("partnerBikou") != null) __args.get("partnerBikou").save(entity);
        return entity;
    }

    @Override
    public Class<jp.co.edi_java.app.entity.TEstimateEntity> getEntityClass() {
        return jp.co.edi_java.app.entity.TEstimateEntity.class;
    }

    @Override
    public jp.co.edi_java.app.entity.TEstimateEntity getOriginalStates(jp.co.edi_java.app.entity.TEstimateEntity __entity) {
        return null;
    }

    @Override
    public void saveCurrentStates(jp.co.edi_java.app.entity.TEstimateEntity __entity) {
    }

    /**
     * @return the singleton
     */
    public static _TEstimateEntity getSingletonInternal() {
        return __singleton;
    }

    /**
     * @return the new instance
     */
    public static _TEstimateEntity newInstance() {
        return new _TEstimateEntity();
    }

    private static class ListenerHolder {
        private static jp.co.edi_java.app.entity.listener.BaseEntityListener<jp.co.edi_java.app.entity.TEstimateEntity> listener = new jp.co.edi_java.app.entity.listener.BaseEntityListener<>();
    }

}
