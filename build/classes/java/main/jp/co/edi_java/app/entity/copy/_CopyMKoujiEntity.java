package jp.co.edi_java.app.entity.copy;

/** */
@javax.annotation.Generated(value = { "Doma", "2.16.1" }, date = "2019-05-31T17:13:58.282+0900")
public final class _CopyMKoujiEntity extends org.seasar.doma.jdbc.entity.AbstractEntityType<jp.co.edi_java.app.entity.copy.CopyMKoujiEntity> {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.16.1");
    }

    private static final _CopyMKoujiEntity __singleton = new _CopyMKoujiEntity();

    private final org.seasar.doma.jdbc.entity.NamingType __namingType = org.seasar.doma.jdbc.entity.NamingType.SNAKE_LOWER_CASE;

    /** the koujiCode */
    public final org.seasar.doma.jdbc.entity.AssignedIdPropertyType<java.lang.Object, jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, java.lang.String, Object> $koujiCode = new org.seasar.doma.jdbc.entity.AssignedIdPropertyType<>(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "koujiCode", "", __namingType, false);

    /** the koujiName */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, java.lang.String, Object> $koujiName = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "koujiName", "", __namingType, true, true, false);

    /** the keiyakusyaName */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, java.lang.String, Object> $keiyakusyaName = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "keiyakusyaName", "", __namingType, true, true, false);

    /** the keiyakusyaKana */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, java.lang.String, Object> $keiyakusyaKana = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "keiyakusyaKana", "", __namingType, true, true, false);

    /** the eigyousyoGroupCode */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, java.lang.String, Object> $eigyousyoGroupCode = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "eigyousyoGroupCode", "", __namingType, true, true, false);

    /** the eigyousyoCode */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, java.lang.String, Object> $eigyousyoCode = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "eigyousyoCode", "", __namingType, true, true, false);

    /** the tantouSyainCode */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, java.lang.String, Object> $tantouSyainCode = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "tantouSyainCode", "", __namingType, true, true, false);

    /** the koujiStatus */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, java.lang.String, Object> $koujiStatus = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "koujiStatus", "", __namingType, true, true, false);

    /** the koujiStatusName */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, java.lang.String, Object> $koujiStatusName = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "koujiStatusName", "", __namingType, true, true, false);

    /** the kentikutiYuubin */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, java.lang.String, Object> $kentikutiYuubin = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "kentikutiYuubin", "", __namingType, true, true, false);

    /** the kentikutiJyuusyo */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, java.lang.String, Object> $kentikutiJyuusyo = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "kentikutiJyuusyo", "", __namingType, true, true, false);

    /** the kanseiKubun */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, java.lang.Integer, Object> $kanseiKubun = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity.class, java.lang.Integer.class, java.lang.Integer.class, () -> new org.seasar.doma.wrapper.IntegerWrapper(), null, null, "kanseiKubun", "", __namingType, true, true, false);

    /** the projectType */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, java.lang.String, Object> $projectType = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "projectType", "", __namingType, true, true, false);

    /** the keiyakuDateY */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, java.lang.String, Object> $keiyakuDateY = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "keiyakuDateY", "", __namingType, true, true, false);

    /** the keiyakuDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, java.lang.String, Object> $keiyakuDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "keiyakuDate", "", __namingType, true, true, false);

    /** the tyakkouDateY */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, java.lang.String, Object> $tyakkouDateY = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "tyakkouDateY", "", __namingType, true, true, false);

    /** the tyakkouDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, java.lang.String, Object> $tyakkouDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "tyakkouDate", "", __namingType, true, true, false);

    /** the jyoutouDateY */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, java.lang.String, Object> $jyoutouDateY = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "jyoutouDateY", "", __namingType, true, true, false);

    /** the jyoutouDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, java.lang.String, Object> $jyoutouDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "jyoutouDate", "", __namingType, true, true, false);

    /** the syunkouDateY */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, java.lang.String, Object> $syunkouDateY = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "syunkouDateY", "", __namingType, true, true, false);

    /** the syunkouDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, java.lang.String, Object> $syunkouDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "syunkouDate", "", __namingType, true, true, false);

    /** the hikiwatasiDateY */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, java.lang.String, Object> $hikiwatasiDateY = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "hikiwatasiDateY", "", __namingType, true, true, false);

    /** the hikiwatasiDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, java.lang.String, Object> $hikiwatasiDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "hikiwatasiDate", "", __namingType, true, true, false);

    /** the saisyuuKousinDate */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, java.sql.Date, Object> $saisyuuKousinDate = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity.class, java.sql.Date.class, java.sql.Date.class, () -> new org.seasar.doma.wrapper.DateWrapper(), null, null, "saisyuuKousinDate", "", __namingType, true, true, false);

    private final java.util.function.Supplier<org.seasar.doma.jdbc.entity.NullEntityListener<jp.co.edi_java.app.entity.copy.CopyMKoujiEntity>> __listenerSupplier;

    private final boolean __immutable;

    private final String __catalogName;

    private final String __schemaName;

    private final String __tableName;

    private final boolean __isQuoteRequired;

    private final String __name;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, ?>> __idPropertyTypes;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, ?>> __entityPropertyTypes;

    private final java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, ?>> __entityPropertyTypeMap;

    private _CopyMKoujiEntity() {
        __listenerSupplier = () -> ListenerHolder.listener;
        __immutable = false;
        __name = "CopyMKoujiEntity";
        __catalogName = "";
        __schemaName = "";
        __tableName = "V_ORDER_KOUJI";
        __isQuoteRequired = false;
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, ?>> __idList = new java.util.ArrayList<>();
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, ?>> __list = new java.util.ArrayList<>(24);
        java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, ?>> __map = new java.util.HashMap<>(24);
        __idList.add($koujiCode);
        __list.add($koujiCode);
        __map.put("koujiCode", $koujiCode);
        __list.add($koujiName);
        __map.put("koujiName", $koujiName);
        __list.add($keiyakusyaName);
        __map.put("keiyakusyaName", $keiyakusyaName);
        __list.add($keiyakusyaKana);
        __map.put("keiyakusyaKana", $keiyakusyaKana);
        __list.add($eigyousyoGroupCode);
        __map.put("eigyousyoGroupCode", $eigyousyoGroupCode);
        __list.add($eigyousyoCode);
        __map.put("eigyousyoCode", $eigyousyoCode);
        __list.add($tantouSyainCode);
        __map.put("tantouSyainCode", $tantouSyainCode);
        __list.add($koujiStatus);
        __map.put("koujiStatus", $koujiStatus);
        __list.add($koujiStatusName);
        __map.put("koujiStatusName", $koujiStatusName);
        __list.add($kentikutiYuubin);
        __map.put("kentikutiYuubin", $kentikutiYuubin);
        __list.add($kentikutiJyuusyo);
        __map.put("kentikutiJyuusyo", $kentikutiJyuusyo);
        __list.add($kanseiKubun);
        __map.put("kanseiKubun", $kanseiKubun);
        __list.add($projectType);
        __map.put("projectType", $projectType);
        __list.add($keiyakuDateY);
        __map.put("keiyakuDateY", $keiyakuDateY);
        __list.add($keiyakuDate);
        __map.put("keiyakuDate", $keiyakuDate);
        __list.add($tyakkouDateY);
        __map.put("tyakkouDateY", $tyakkouDateY);
        __list.add($tyakkouDate);
        __map.put("tyakkouDate", $tyakkouDate);
        __list.add($jyoutouDateY);
        __map.put("jyoutouDateY", $jyoutouDateY);
        __list.add($jyoutouDate);
        __map.put("jyoutouDate", $jyoutouDate);
        __list.add($syunkouDateY);
        __map.put("syunkouDateY", $syunkouDateY);
        __list.add($syunkouDate);
        __map.put("syunkouDate", $syunkouDate);
        __list.add($hikiwatasiDateY);
        __map.put("hikiwatasiDateY", $hikiwatasiDateY);
        __list.add($hikiwatasiDate);
        __map.put("hikiwatasiDate", $hikiwatasiDate);
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
    public void preInsert(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity entity, org.seasar.doma.jdbc.entity.PreInsertContext<jp.co.edi_java.app.entity.copy.CopyMKoujiEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preUpdate(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity entity, org.seasar.doma.jdbc.entity.PreUpdateContext<jp.co.edi_java.app.entity.copy.CopyMKoujiEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preDelete(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity entity, org.seasar.doma.jdbc.entity.PreDeleteContext<jp.co.edi_java.app.entity.copy.CopyMKoujiEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preDelete(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postInsert(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity entity, org.seasar.doma.jdbc.entity.PostInsertContext<jp.co.edi_java.app.entity.copy.CopyMKoujiEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postUpdate(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity entity, org.seasar.doma.jdbc.entity.PostUpdateContext<jp.co.edi_java.app.entity.copy.CopyMKoujiEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postDelete(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity entity, org.seasar.doma.jdbc.entity.PostDeleteContext<jp.co.edi_java.app.entity.copy.CopyMKoujiEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postDelete(entity, context);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, ?>> getEntityPropertyTypes() {
        return __entityPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, ?> getEntityPropertyType(String __name) {
        return __entityPropertyTypeMap.get(__name);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, ?>> getIdPropertyTypes() {
        return __idPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<java.lang.Object, jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, ?, ?> getGeneratedIdPropertyType() {
        return null;
    }

    @Override
    public org.seasar.doma.jdbc.entity.VersionPropertyType<java.lang.Object, jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, ?, ?> getVersionPropertyType() {
        return null;
    }

    @Override
    public jp.co.edi_java.app.entity.copy.CopyMKoujiEntity newEntity(java.util.Map<String, org.seasar.doma.jdbc.entity.Property<jp.co.edi_java.app.entity.copy.CopyMKoujiEntity, ?>> __args) {
        jp.co.edi_java.app.entity.copy.CopyMKoujiEntity entity = new jp.co.edi_java.app.entity.copy.CopyMKoujiEntity();
        if (__args.get("koujiCode") != null) __args.get("koujiCode").save(entity);
        if (__args.get("koujiName") != null) __args.get("koujiName").save(entity);
        if (__args.get("keiyakusyaName") != null) __args.get("keiyakusyaName").save(entity);
        if (__args.get("keiyakusyaKana") != null) __args.get("keiyakusyaKana").save(entity);
        if (__args.get("eigyousyoGroupCode") != null) __args.get("eigyousyoGroupCode").save(entity);
        if (__args.get("eigyousyoCode") != null) __args.get("eigyousyoCode").save(entity);
        if (__args.get("tantouSyainCode") != null) __args.get("tantouSyainCode").save(entity);
        if (__args.get("koujiStatus") != null) __args.get("koujiStatus").save(entity);
        if (__args.get("koujiStatusName") != null) __args.get("koujiStatusName").save(entity);
        if (__args.get("kentikutiYuubin") != null) __args.get("kentikutiYuubin").save(entity);
        if (__args.get("kentikutiJyuusyo") != null) __args.get("kentikutiJyuusyo").save(entity);
        if (__args.get("kanseiKubun") != null) __args.get("kanseiKubun").save(entity);
        if (__args.get("projectType") != null) __args.get("projectType").save(entity);
        if (__args.get("keiyakuDateY") != null) __args.get("keiyakuDateY").save(entity);
        if (__args.get("keiyakuDate") != null) __args.get("keiyakuDate").save(entity);
        if (__args.get("tyakkouDateY") != null) __args.get("tyakkouDateY").save(entity);
        if (__args.get("tyakkouDate") != null) __args.get("tyakkouDate").save(entity);
        if (__args.get("jyoutouDateY") != null) __args.get("jyoutouDateY").save(entity);
        if (__args.get("jyoutouDate") != null) __args.get("jyoutouDate").save(entity);
        if (__args.get("syunkouDateY") != null) __args.get("syunkouDateY").save(entity);
        if (__args.get("syunkouDate") != null) __args.get("syunkouDate").save(entity);
        if (__args.get("hikiwatasiDateY") != null) __args.get("hikiwatasiDateY").save(entity);
        if (__args.get("hikiwatasiDate") != null) __args.get("hikiwatasiDate").save(entity);
        if (__args.get("saisyuuKousinDate") != null) __args.get("saisyuuKousinDate").save(entity);
        return entity;
    }

    @Override
    public Class<jp.co.edi_java.app.entity.copy.CopyMKoujiEntity> getEntityClass() {
        return jp.co.edi_java.app.entity.copy.CopyMKoujiEntity.class;
    }

    @Override
    public jp.co.edi_java.app.entity.copy.CopyMKoujiEntity getOriginalStates(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity __entity) {
        return null;
    }

    @Override
    public void saveCurrentStates(jp.co.edi_java.app.entity.copy.CopyMKoujiEntity __entity) {
    }

    /**
     * @return the singleton
     */
    public static _CopyMKoujiEntity getSingletonInternal() {
        return __singleton;
    }

    /**
     * @return the new instance
     */
    public static _CopyMKoujiEntity newInstance() {
        return new _CopyMKoujiEntity();
    }

    private static class ListenerHolder {
        private static org.seasar.doma.jdbc.entity.NullEntityListener<jp.co.edi_java.app.entity.copy.CopyMKoujiEntity> listener = new org.seasar.doma.jdbc.entity.NullEntityListener<>();
    }

}
