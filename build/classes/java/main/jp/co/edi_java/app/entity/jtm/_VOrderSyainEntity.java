package jp.co.edi_java.app.entity.jtm;

/** */
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:58.468+0900")
public final class _VOrderSyainEntity extends org.seasar.doma.jdbc.entity.AbstractEntityType<jp.co.edi_java.app.entity.jtm.VOrderSyainEntity> {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final _VOrderSyainEntity __singleton = new _VOrderSyainEntity();

    private final org.seasar.doma.jdbc.entity.NamingType __namingType = org.seasar.doma.jdbc.entity.NamingType.SNAKE_LOWER_CASE;

    /** the syainCode */
    public final org.seasar.doma.jdbc.entity.AssignedIdPropertyType<java.lang.Object, jp.co.edi_java.app.entity.jtm.VOrderSyainEntity, java.lang.String, Object> $syainCode = new org.seasar.doma.jdbc.entity.AssignedIdPropertyType<>(jp.co.edi_java.app.entity.jtm.VOrderSyainEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "syainCode", "", __namingType, false);

    /** the rownumber */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.jtm.VOrderSyainEntity, java.lang.Integer, Object> $rownumber = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.jtm.VOrderSyainEntity.class, java.lang.Integer.class, java.lang.Integer.class, () -> new org.seasar.doma.wrapper.IntegerWrapper(), null, null, "rownumber", "", __namingType, true, true, false);

    /** the syainName */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.jtm.VOrderSyainEntity, java.lang.String, Object> $syainName = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.jtm.VOrderSyainEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "syainName", "", __namingType, true, true, false);

    /** the syainKana */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.jtm.VOrderSyainEntity, java.lang.String, Object> $syainKana = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.jtm.VOrderSyainEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "syainKana", "", __namingType, true, true, false);

    /** the syainPassword */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.jtm.VOrderSyainEntity, java.lang.String, Object> $syainPassword = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.jtm.VOrderSyainEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "syainPassword", "", __namingType, true, true, false);

    /** the syainMail */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.jtm.VOrderSyainEntity, java.lang.String, Object> $syainMail = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.jtm.VOrderSyainEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "syainMail", "", __namingType, true, true, false);

    /** the syainKeitai */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.jtm.VOrderSyainEntity, java.lang.String, Object> $syainKeitai = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.jtm.VOrderSyainEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "syainKeitai", "", __namingType, true, true, false);

    /** the taisyokuFlg */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.jtm.VOrderSyainEntity, java.lang.Integer, Object> $taisyokuFlg = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.jtm.VOrderSyainEntity.class, java.lang.Integer.class, java.lang.Integer.class, () -> new org.seasar.doma.wrapper.IntegerWrapper(), null, null, "taisyokuFlg", "", __namingType, true, true, false);

    /** the syokusyuFlg */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.jtm.VOrderSyainEntity, java.lang.Integer, Object> $syokusyuFlg = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.jtm.VOrderSyainEntity.class, java.lang.Integer.class, java.lang.Integer.class, () -> new org.seasar.doma.wrapper.IntegerWrapper(), null, null, "syokusyuFlg", "", __namingType, true, true, false);

    /** the yakusyokuCode */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.jtm.VOrderSyainEntity, java.lang.String, Object> $yakusyokuCode = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.jtm.VOrderSyainEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "yakusyokuCode", "", __namingType, true, true, false);

    /** the yakusyokuName */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.jtm.VOrderSyainEntity, java.lang.String, Object> $yakusyokuName = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.jtm.VOrderSyainEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "yakusyokuName", "", __namingType, true, true, false);

    /** the saisyuuKousinDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.jtm.VOrderSyainEntity, java.sql.Date, Object> $saisyuuKousinDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.jtm.VOrderSyainEntity.class, java.sql.Date.class, java.sql.Date.class, () -> new org.seasar.doma.wrapper.DateWrapper(), null, null, "saisyuuKousinDate", "", __namingType, true, true, false);

    private final java.util.function.Supplier<org.seasar.doma.jdbc.entity.NullEntityListener<jp.co.edi_java.app.entity.jtm.VOrderSyainEntity>> __listenerSupplier;

    private final boolean __immutable;

    private final String __catalogName;

    private final String __schemaName;

    private final String __tableName;

    private final boolean __isQuoteRequired;

    private final String __name;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.jtm.VOrderSyainEntity, ?>> __idPropertyTypes;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.jtm.VOrderSyainEntity, ?>> __entityPropertyTypes;

    private final java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.jtm.VOrderSyainEntity, ?>> __entityPropertyTypeMap;

    private _VOrderSyainEntity() {
        __listenerSupplier = () -> ListenerHolder.listener;
        __immutable = false;
        __name = "VOrderSyainEntity";
        __catalogName = "";
        __schemaName = "";
        __tableName = "V_ORDER_SYAIN";
        __isQuoteRequired = false;
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.jtm.VOrderSyainEntity, ?>> __idList = new java.util.ArrayList<>();
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.jtm.VOrderSyainEntity, ?>> __list = new java.util.ArrayList<>(12);
        java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.jtm.VOrderSyainEntity, ?>> __map = new java.util.HashMap<>(12);
        __idList.add($syainCode);
        __list.add($syainCode);
        __map.put("syainCode", $syainCode);
        __list.add($rownumber);
        __map.put("rownumber", $rownumber);
        __list.add($syainName);
        __map.put("syainName", $syainName);
        __list.add($syainKana);
        __map.put("syainKana", $syainKana);
        __list.add($syainPassword);
        __map.put("syainPassword", $syainPassword);
        __list.add($syainMail);
        __map.put("syainMail", $syainMail);
        __list.add($syainKeitai);
        __map.put("syainKeitai", $syainKeitai);
        __list.add($taisyokuFlg);
        __map.put("taisyokuFlg", $taisyokuFlg);
        __list.add($syokusyuFlg);
        __map.put("syokusyuFlg", $syokusyuFlg);
        __list.add($yakusyokuCode);
        __map.put("yakusyokuCode", $yakusyokuCode);
        __list.add($yakusyokuName);
        __map.put("yakusyokuName", $yakusyokuName);
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
    public void preInsert(jp.co.edi_java.app.entity.jtm.VOrderSyainEntity entity, org.seasar.doma.jdbc.entity.PreInsertContext<jp.co.edi_java.app.entity.jtm.VOrderSyainEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preUpdate(jp.co.edi_java.app.entity.jtm.VOrderSyainEntity entity, org.seasar.doma.jdbc.entity.PreUpdateContext<jp.co.edi_java.app.entity.jtm.VOrderSyainEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preDelete(jp.co.edi_java.app.entity.jtm.VOrderSyainEntity entity, org.seasar.doma.jdbc.entity.PreDeleteContext<jp.co.edi_java.app.entity.jtm.VOrderSyainEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preDelete(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postInsert(jp.co.edi_java.app.entity.jtm.VOrderSyainEntity entity, org.seasar.doma.jdbc.entity.PostInsertContext<jp.co.edi_java.app.entity.jtm.VOrderSyainEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postUpdate(jp.co.edi_java.app.entity.jtm.VOrderSyainEntity entity, org.seasar.doma.jdbc.entity.PostUpdateContext<jp.co.edi_java.app.entity.jtm.VOrderSyainEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postDelete(jp.co.edi_java.app.entity.jtm.VOrderSyainEntity entity, org.seasar.doma.jdbc.entity.PostDeleteContext<jp.co.edi_java.app.entity.jtm.VOrderSyainEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postDelete(entity, context);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.jtm.VOrderSyainEntity, ?>> getEntityPropertyTypes() {
        return __entityPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.jtm.VOrderSyainEntity, ?> getEntityPropertyType(String __name) {
        return __entityPropertyTypeMap.get(__name);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.jtm.VOrderSyainEntity, ?>> getIdPropertyTypes() {
        return __idPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<java.lang.Object, jp.co.edi_java.app.entity.jtm.VOrderSyainEntity, ?, ?> getGeneratedIdPropertyType() {
        return null;
    }

    @Override
    public org.seasar.doma.jdbc.entity.VersionPropertyType<java.lang.Object, jp.co.edi_java.app.entity.jtm.VOrderSyainEntity, ?, ?> getVersionPropertyType() {
        return null;
    }

    @Override
    public jp.co.edi_java.app.entity.jtm.VOrderSyainEntity newEntity(java.util.Map<String, org.seasar.doma.jdbc.entity.Property<jp.co.edi_java.app.entity.jtm.VOrderSyainEntity, ?>> __args) {
        jp.co.edi_java.app.entity.jtm.VOrderSyainEntity entity = new jp.co.edi_java.app.entity.jtm.VOrderSyainEntity();
        if (__args.get("syainCode") != null) __args.get("syainCode").save(entity);
        if (__args.get("rownumber") != null) __args.get("rownumber").save(entity);
        if (__args.get("syainName") != null) __args.get("syainName").save(entity);
        if (__args.get("syainKana") != null) __args.get("syainKana").save(entity);
        if (__args.get("syainPassword") != null) __args.get("syainPassword").save(entity);
        if (__args.get("syainMail") != null) __args.get("syainMail").save(entity);
        if (__args.get("syainKeitai") != null) __args.get("syainKeitai").save(entity);
        if (__args.get("taisyokuFlg") != null) __args.get("taisyokuFlg").save(entity);
        if (__args.get("syokusyuFlg") != null) __args.get("syokusyuFlg").save(entity);
        if (__args.get("yakusyokuCode") != null) __args.get("yakusyokuCode").save(entity);
        if (__args.get("yakusyokuName") != null) __args.get("yakusyokuName").save(entity);
        if (__args.get("saisyuuKousinDate") != null) __args.get("saisyuuKousinDate").save(entity);
        return entity;
    }

    @Override
    public Class<jp.co.edi_java.app.entity.jtm.VOrderSyainEntity> getEntityClass() {
        return jp.co.edi_java.app.entity.jtm.VOrderSyainEntity.class;
    }

    @Override
    public jp.co.edi_java.app.entity.jtm.VOrderSyainEntity getOriginalStates(jp.co.edi_java.app.entity.jtm.VOrderSyainEntity __entity) {
        return null;
    }

    @Override
    public void saveCurrentStates(jp.co.edi_java.app.entity.jtm.VOrderSyainEntity __entity) {
    }

    /**
     * @return the singleton
     */
    public static _VOrderSyainEntity getSingletonInternal() {
        return __singleton;
    }

    /**
     * @return the new instance
     */
    public static _VOrderSyainEntity newInstance() {
        return new _VOrderSyainEntity();
    }

    private static class ListenerHolder {
        private static org.seasar.doma.jdbc.entity.NullEntityListener<jp.co.edi_java.app.entity.jtm.VOrderSyainEntity> listener = new org.seasar.doma.jdbc.entity.NullEntityListener<>();
    }

}
