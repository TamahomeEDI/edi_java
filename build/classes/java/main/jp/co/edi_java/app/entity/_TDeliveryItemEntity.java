package jp.co.edi_java.app.entity;

/** */
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:58.587+0900")
public final class _TDeliveryItemEntity extends org.seasar.doma.jdbc.entity.AbstractEntityType<jp.co.edi_java.app.entity.TDeliveryItemEntity> {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final _TDeliveryItemEntity __singleton = new _TDeliveryItemEntity();

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

    /** the insertDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TDeliveryItemEntity, java.sql.Timestamp, Object> $insertDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TDeliveryItemEntity.class, java.sql.Timestamp.class, java.sql.Timestamp.class, () -> new org.seasar.doma.wrapper.TimestampWrapper(), null, null, "insertDate", "", __namingType, true, true, false);

    /** the insertUser */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TDeliveryItemEntity, java.lang.String, Object> $insertUser = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TDeliveryItemEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "insertUser", "", __namingType, true, true, false);

    /** the updateDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TDeliveryItemEntity, java.sql.Timestamp, Object> $updateDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TDeliveryItemEntity.class, java.sql.Timestamp.class, java.sql.Timestamp.class, () -> new org.seasar.doma.wrapper.TimestampWrapper(), null, null, "updateDate", "", __namingType, true, true, false);

    /** the updateUser */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TDeliveryItemEntity, java.lang.String, Object> $updateUser = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TDeliveryItemEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "updateUser", "", __namingType, true, true, false);

    /** the deleteDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TDeliveryItemEntity, java.sql.Timestamp, Object> $deleteDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TDeliveryItemEntity.class, java.sql.Timestamp.class, java.sql.Timestamp.class, () -> new org.seasar.doma.wrapper.TimestampWrapper(), null, null, "deleteDate", "", __namingType, true, true, false);

    /** the deleteUser */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TDeliveryItemEntity, java.lang.String, Object> $deleteUser = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TDeliveryItemEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "deleteUser", "", __namingType, true, true, false);

    /** the deleteFlg */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TDeliveryItemEntity, java.lang.String, Object> $deleteFlg = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TDeliveryItemEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "deleteFlg", "", __namingType, true, true, false);

    /** the id */
    public final org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TDeliveryItemEntity, java.lang.Integer, Object> $id = new org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<>(jp.co.edi_java.app.entity.TDeliveryItemEntity.class, java.lang.Integer.class, java.lang.Integer.class, () -> new org.seasar.doma.wrapper.IntegerWrapper(), null, null, "id", "", __namingType, false, __idGenerator);

    /** the deliveryNumber */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TDeliveryItemEntity, java.lang.String, Object> $deliveryNumber = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TDeliveryItemEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "deliveryNumber", "", __namingType, true, true, false);

    /** the itemNumber */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TDeliveryItemEntity, java.lang.Integer, Object> $itemNumber = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TDeliveryItemEntity.class, java.lang.Integer.class, java.lang.Integer.class, () -> new org.seasar.doma.wrapper.IntegerWrapper(), null, null, "itemNumber", "", __namingType, true, true, false);

    /** the itemName */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TDeliveryItemEntity, java.lang.String, Object> $itemName = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TDeliveryItemEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "itemName", "", __namingType, true, true, false);

    /** the orderQuantity */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TDeliveryItemEntity, java.lang.Double, Object> $orderQuantity = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TDeliveryItemEntity.class, java.lang.Double.class, java.lang.Double.class, () -> new org.seasar.doma.wrapper.DoubleWrapper(), null, null, "orderQuantity", "", __namingType, true, true, false);

    /** the deliveryQuantity */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TDeliveryItemEntity, java.lang.Double, Object> $deliveryQuantity = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TDeliveryItemEntity.class, java.lang.Double.class, java.lang.Double.class, () -> new org.seasar.doma.wrapper.DoubleWrapper(), null, null, "deliveryQuantity", "", __namingType, true, true, false);

    /** the deliveryRemainingQuantity */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TDeliveryItemEntity, java.lang.Double, Object> $deliveryRemainingQuantity = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TDeliveryItemEntity.class, java.lang.Double.class, java.lang.Double.class, () -> new org.seasar.doma.wrapper.DoubleWrapper(), null, null, "deliveryRemainingQuantity", "", __namingType, true, true, false);

    /** the unit */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TDeliveryItemEntity, java.lang.String, Object> $unit = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TDeliveryItemEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "unit", "", __namingType, true, true, false);

    /** the completeDeliveryFlg */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TDeliveryItemEntity, java.lang.String, Object> $completeDeliveryFlg = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.TDeliveryItemEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "completeDeliveryFlg", "", __namingType, true, true, false);

    private final java.util.function.Supplier<jp.co.edi_java.app.entity.listener.BaseEntityListener<jp.co.edi_java.app.entity.TDeliveryItemEntity>> __listenerSupplier;

    private final boolean __immutable;

    private final String __catalogName;

    private final String __schemaName;

    private final String __tableName;

    private final boolean __isQuoteRequired;

    private final String __name;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TDeliveryItemEntity, ?>> __idPropertyTypes;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TDeliveryItemEntity, ?>> __entityPropertyTypes;

    private final java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TDeliveryItemEntity, ?>> __entityPropertyTypeMap;

    private _TDeliveryItemEntity() {
        __listenerSupplier = () -> ListenerHolder.listener;
        __immutable = false;
        __name = "TDeliveryItemEntity";
        __catalogName = "";
        __schemaName = "";
        __tableName = "T_DELIVERY_ITEM";
        __isQuoteRequired = false;
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TDeliveryItemEntity, ?>> __idList = new java.util.ArrayList<>();
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TDeliveryItemEntity, ?>> __list = new java.util.ArrayList<>(16);
        java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TDeliveryItemEntity, ?>> __map = new java.util.HashMap<>(16);
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
        __idList.add($id);
        __list.add($id);
        __map.put("id", $id);
        __list.add($deliveryNumber);
        __map.put("deliveryNumber", $deliveryNumber);
        __list.add($itemNumber);
        __map.put("itemNumber", $itemNumber);
        __list.add($itemName);
        __map.put("itemName", $itemName);
        __list.add($orderQuantity);
        __map.put("orderQuantity", $orderQuantity);
        __list.add($deliveryQuantity);
        __map.put("deliveryQuantity", $deliveryQuantity);
        __list.add($deliveryRemainingQuantity);
        __map.put("deliveryRemainingQuantity", $deliveryRemainingQuantity);
        __list.add($unit);
        __map.put("unit", $unit);
        __list.add($completeDeliveryFlg);
        __map.put("completeDeliveryFlg", $completeDeliveryFlg);
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
    public void preInsert(jp.co.edi_java.app.entity.TDeliveryItemEntity entity, org.seasar.doma.jdbc.entity.PreInsertContext<jp.co.edi_java.app.entity.TDeliveryItemEntity> context) {
        Class __listenerClass = jp.co.edi_java.app.entity.listener.BaseEntityListener.class;
        jp.co.edi_java.app.entity.listener.BaseEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preUpdate(jp.co.edi_java.app.entity.TDeliveryItemEntity entity, org.seasar.doma.jdbc.entity.PreUpdateContext<jp.co.edi_java.app.entity.TDeliveryItemEntity> context) {
        Class __listenerClass = jp.co.edi_java.app.entity.listener.BaseEntityListener.class;
        jp.co.edi_java.app.entity.listener.BaseEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preDelete(jp.co.edi_java.app.entity.TDeliveryItemEntity entity, org.seasar.doma.jdbc.entity.PreDeleteContext<jp.co.edi_java.app.entity.TDeliveryItemEntity> context) {
        Class __listenerClass = jp.co.edi_java.app.entity.listener.BaseEntityListener.class;
        jp.co.edi_java.app.entity.listener.BaseEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preDelete(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postInsert(jp.co.edi_java.app.entity.TDeliveryItemEntity entity, org.seasar.doma.jdbc.entity.PostInsertContext<jp.co.edi_java.app.entity.TDeliveryItemEntity> context) {
        Class __listenerClass = jp.co.edi_java.app.entity.listener.BaseEntityListener.class;
        jp.co.edi_java.app.entity.listener.BaseEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postUpdate(jp.co.edi_java.app.entity.TDeliveryItemEntity entity, org.seasar.doma.jdbc.entity.PostUpdateContext<jp.co.edi_java.app.entity.TDeliveryItemEntity> context) {
        Class __listenerClass = jp.co.edi_java.app.entity.listener.BaseEntityListener.class;
        jp.co.edi_java.app.entity.listener.BaseEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postDelete(jp.co.edi_java.app.entity.TDeliveryItemEntity entity, org.seasar.doma.jdbc.entity.PostDeleteContext<jp.co.edi_java.app.entity.TDeliveryItemEntity> context) {
        Class __listenerClass = jp.co.edi_java.app.entity.listener.BaseEntityListener.class;
        jp.co.edi_java.app.entity.listener.BaseEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postDelete(entity, context);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TDeliveryItemEntity, ?>> getEntityPropertyTypes() {
        return __entityPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TDeliveryItemEntity, ?> getEntityPropertyType(String __name) {
        return __entityPropertyTypeMap.get(__name);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.TDeliveryItemEntity, ?>> getIdPropertyTypes() {
        return __idPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TDeliveryItemEntity, ?, ?> getGeneratedIdPropertyType() {
        return $id;
    }

    @Override
    public org.seasar.doma.jdbc.entity.VersionPropertyType<java.lang.Object, jp.co.edi_java.app.entity.TDeliveryItemEntity, ?, ?> getVersionPropertyType() {
        return null;
    }

    @Override
    public jp.co.edi_java.app.entity.TDeliveryItemEntity newEntity(java.util.Map<String, org.seasar.doma.jdbc.entity.Property<jp.co.edi_java.app.entity.TDeliveryItemEntity, ?>> __args) {
        jp.co.edi_java.app.entity.TDeliveryItemEntity entity = new jp.co.edi_java.app.entity.TDeliveryItemEntity();
        if (__args.get("insertDate") != null) __args.get("insertDate").save(entity);
        if (__args.get("insertUser") != null) __args.get("insertUser").save(entity);
        if (__args.get("updateDate") != null) __args.get("updateDate").save(entity);
        if (__args.get("updateUser") != null) __args.get("updateUser").save(entity);
        if (__args.get("deleteDate") != null) __args.get("deleteDate").save(entity);
        if (__args.get("deleteUser") != null) __args.get("deleteUser").save(entity);
        if (__args.get("deleteFlg") != null) __args.get("deleteFlg").save(entity);
        if (__args.get("id") != null) __args.get("id").save(entity);
        if (__args.get("deliveryNumber") != null) __args.get("deliveryNumber").save(entity);
        if (__args.get("itemNumber") != null) __args.get("itemNumber").save(entity);
        if (__args.get("itemName") != null) __args.get("itemName").save(entity);
        if (__args.get("orderQuantity") != null) __args.get("orderQuantity").save(entity);
        if (__args.get("deliveryQuantity") != null) __args.get("deliveryQuantity").save(entity);
        if (__args.get("deliveryRemainingQuantity") != null) __args.get("deliveryRemainingQuantity").save(entity);
        if (__args.get("unit") != null) __args.get("unit").save(entity);
        if (__args.get("completeDeliveryFlg") != null) __args.get("completeDeliveryFlg").save(entity);
        return entity;
    }

    @Override
    public Class<jp.co.edi_java.app.entity.TDeliveryItemEntity> getEntityClass() {
        return jp.co.edi_java.app.entity.TDeliveryItemEntity.class;
    }

    @Override
    public jp.co.edi_java.app.entity.TDeliveryItemEntity getOriginalStates(jp.co.edi_java.app.entity.TDeliveryItemEntity __entity) {
        return null;
    }

    @Override
    public void saveCurrentStates(jp.co.edi_java.app.entity.TDeliveryItemEntity __entity) {
    }

    /**
     * @return the singleton
     */
    public static _TDeliveryItemEntity getSingletonInternal() {
        return __singleton;
    }

    /**
     * @return the new instance
     */
    public static _TDeliveryItemEntity newInstance() {
        return new _TDeliveryItemEntity();
    }

    private static class ListenerHolder {
        private static jp.co.edi_java.app.entity.listener.BaseEntityListener<jp.co.edi_java.app.entity.TDeliveryItemEntity> listener = new jp.co.edi_java.app.entity.listener.BaseEntityListener<>();
    }

}
