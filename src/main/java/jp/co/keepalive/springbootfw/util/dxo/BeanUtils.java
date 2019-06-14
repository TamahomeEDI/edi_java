/**************************************************************
 * システム名		:
 * サブシステム名	:
 * モジュール名		:BeanUtils.java
 *
 * 修正日			:修正者			:修正理由
 * ------------------------------------------
 * 2007/7/5			:成田　敦		:新規作成
 *
 **************************************************************/
package jp.co.keepalive.springbootfw.util.dxo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;

import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;
import jp.co.keepalive.springbootfw.util.lang.StringUtils;

/**
 * <b>[概要]</b><br>
 * <p>
 * JavaBeans の操作に関するユーティリティクラスです。
 * </p>
 *
 * @author Atsushi Narita
 */
public class BeanUtils {

	// isJavaBeansの結果のキャッシュ（キー：クラス名）
	private static Map<String, Boolean> isJavaBeansResult = new HashMap<String, Boolean>();

	static {
	    ConvertUtils.register(new SqlDateConverter(null), Date.class);
	    ConvertUtils.register(new SqlTimestampConverter(null), Timestamp.class);
	}
	/**
     * デフォルトコンストラクタは使用禁止です.
     */
	private BeanUtils() {
	}

	/**
     * <p>
     * Map 型オブジェクトから JavaBeans へ項目の転記を行います。
     * Map の各エントリ（キーと値のペア）と JavaBeans で、キーとプロパティ名が
     * 一致する場合に、Map の値を JavaBeans のプロパティに転記します。
     * </p>
     * <p>
     * サンプル：
     * </p>
     *
     * <pre>
     * BeanUtils.copyMapToBean(fromMapObject, toJavaBeans);
     * </pre>
     *
     * @param map
     *            転記元Map型オブジェクト
     * @param obj
     *            転記先JavaBeans
     * @return 転記済JavaBeans
     */
	public static Object copyMapToBean(Map map, Object obj) {
		try {
			org.apache.commons.beanutils.BeanUtils.copyProperties(obj, map);
		} catch (IllegalAccessException e) {
			throw new CoreRuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new CoreRuntimeException(e);
		}
		return obj;
	}

	/**
     * <p>
     * JavaBeans から Map 型オブジェクトへ項目の転記を行います。
     * JavaBeans のプロパティ名と値を Map のエントリ（キーと値のペア）として転記します。
     * JavaBeans の final フィールドは転記対象となりません。
     * </p>
     * <p>
     * サンプル：
     * </p>
     *
     * <pre>
     * BeanUtils.copyBeanToMap(fromJavaBeans, toMapObject);
     * </pre>
     *
     * @param obj
     *            転記元JavaBeans
     * @param map
     *            転記先Map型オブジェクト
     * @return 転記済Map型オブジェクト
     */
	public static Map<Object, Object> copyBeanToMap(Object obj, Map<Object, Object> map) {
		Field[] fields = getBeanFieldsExcludingFinal(obj.getClass());
		for (int i = 0; i < fields.length; i++) {
			map.put(fields[i].getName(), getPropertyValue(obj, fields[i]));
		}
		return map;
	}

	/**
	 * <p>
	 * プロパティの転記を行います。
	 * </p>
	 * <p>
	 * {@link org.apache.commons.beanutils.BeanUtils#copyProperties(Object, Object)}のラッパーです。
	 * </p>
	 *
	 * @param src 転記元オブジェクト
	 * @param dest 転記先オブジェクト
	 * @return 転記済オブジェクト
	 */
	public static Object copyProperties(Object src, Object dest){
		try {
			org.apache.commons.beanutils.BeanUtils.copyProperties(dest, src);
		} catch (IllegalAccessException e) {
			throw new CoreRuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new CoreRuntimeException(e);
		}
		return dest;
	}

	/**
	 * <p>
	 * オブジェクトの複製を行います。
	 * 複製にはプロパティの getter/setter メソッドが使用されるため
	 * 複製するオブジェクトは Cloneable インターフェースを実装する必要はありません。
	 * オブジェクトはシャローコピーになります。
	 * </p>
	 * <p>
	 * {@link org.apache.commons.beanutils.BeanUtils#cloneBean(Object)}のラッパーです。
	 * </p>
	 *
	 * @param obj 複製対象のオブジェクト
	 * @return 複製されたオブジェクト
	 */
	public static Object cloneBean(Object obj){
		try {
			return org.apache.commons.beanutils.BeanUtils.cloneBean(obj);
		} catch (IllegalAccessException e) {
			throw new CoreRuntimeException(e);
		} catch (InstantiationException e) {
			throw new CoreRuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new CoreRuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new CoreRuntimeException(e);
		}
	}

	/**
	 * <p>
	 * プロパティの値を取得します。
	 * <p/>
     * <p>
     * {@link PropertyUtils#getProperty(Object, String)}のラッパーです。
     * </p>
     *
     * @param target
     *            対象オブジェクト
     * @param field
     *            フィールド
     * @return フィールドの値
     */
	public static Object getPropertyValue(Object target, Field field) {
		return getPropertyValue(target, field.getName());
	}

	/**
	 * <p>
	 * プロパティの値を取得します。
	 * <p/>
     * <p>
     * {@link PropertyUtils#getProperty(Object, String)}のラッパーです。
     * </p>
     *
     * @param target
     *            対象オブジェクト
     * @param fieldName
     *            フィールド名
     * @return フィールドの値
     */
	public static Object getPropertyValue(Object target, String fieldName) {
		try {
			return PropertyUtils.getProperty(target, fieldName);
		} catch (IllegalAccessException e) {
			throw new CoreRuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new CoreRuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new CoreRuntimeException(e);
		}
	}

	/**
	 * <p>
	 * プロパティの値を取得します。発生した例外は無視されます。
	 * <p/>
     * <p>
     * {@link PropertyUtils#getProperty(Object, String)}のラッパーです。
     * </p>
     *
     * @param target
     *            対象オブジェクト
     * @param field フィールド
     * @return フィールドの値
     */
	public static Object getPropertyValueIgnoreException(Object target, Field field){
		return getPropertyValueIgnoreException(target, field.getName());
	}

	/**
	 * <p>
	 * プロパティの値を取得します。発生した例外は無視されます。
	 * <p/>
     * <p>
     * {@link PropertyUtils#getProperty(Object, String)}のラッパーです。
     * </p>
     *
     * @param target
     *            対象オブジェクト
     * @param fieldName
     *            フィールド名
     * @return フィールドの値
     */
	public static Object getPropertyValueIgnoreException(Object target, String fieldName){
		try {
			return PropertyUtils.getProperty(target, fieldName);
		} catch (NoSuchMethodException e) {
			return null;
		} catch (IllegalAccessException e) {
			return null;
		} catch (InvocationTargetException e) {
			return null;
		}
	}

	/**
	 * <p>
	 * プロパティの型を取得します。
	 * </p>
	 *
	 * @param obj 対象オブジェクト
	 * @param fieldname 対象フィールド
	 * @return 対象フィールドの型
	 */
	public static Class<?> getPropertyType(Object obj, String fieldname){
		try {
			return PropertyUtils.getPropertyType(obj, fieldname);
		} catch (IllegalAccessException e) {
			throw new CoreRuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new CoreRuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new CoreRuntimeException(e);
		}
	}

	/**
	 * <p>
	 * プロパティの型を設定します。
	 * </p>
	 * <p>
     * {@link PropertyUtils#setProperty(Object, String, Object)}のラッパーです。
     * </p>
	 *
	 * @param obj 対象オブジェクト
	 * @param fieldname フィールド名
	 * @param val
	 */
	public static void setProperty(Object obj, String fieldname, Object val){
		try {
			PropertyUtils.setProperty(obj, fieldname, val);
		} catch (IllegalAccessException e) {
			throw new CoreRuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new CoreRuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new CoreRuntimeException(e);
		}
	}

	/**
	 * <p>
	 * JavaBeansのフィールドを親クラスに遡って取得します.
	 * finalなフィールドは取得されません.
	 * </p>
	 *
	 * @param clazz 対象となるクラス
	 * @return 取得したフィールド
	 */
	public static Field[] getBeanFieldsExcludingFinal(Class<?> clazz){
		Field[] orig = getBeanFields(clazz);
		List<Field> list = new ArrayList<Field>();
		for(int i = 0; i < orig.length; i++){
			if(isFinalField(orig[i])){
				continue;
			}
			list.add(orig[i]);
		}
		return list.toArray(new Field[list.size()]);
	}

	/**
	 * <p>
	 * JavaBeansのフィールドを親クラスに遡って取得します.
	 * transientまたはfinalなフィールドは取得されません.
	 * </p>
	 *
	 * @param clazz 対象となるクラス
	 * @return 取得したフィールド
	 */
	public static Field[] getBeanFieldsExcludingTransientOrFinal(Class<?> clazz){
		Field[] orig = getBeanFields(clazz);
		List<Field> list = new ArrayList<Field>();
		for(int i = 0; i < orig.length; i++){
			if(isPossibleToCopy(orig[i])){
				list.add(orig[i]);
			}
		}
		return list.toArray(new Field[list.size()]);
	}

	/**
     * <p>
     * JavaBeansのフィールドを親クラスに遡って取得します.
     * </p>
     *
     * @param clazz 対象となるクラス
	 * @return 取得したフィールド
     */
	public static Field[] getBeanFields(Class<?> clazz) {
		List<Field> list = getBeanFieldsAsList(clazz);
		return list.toArray(new Field[list.size()]);
	}

	/**
     * <p>
     * JavaBeansのフィールドを親クラスに遡って取得します.
     * </p>
     *
     * @param clazz
     *            対象となるクラス
     * @return 取得したフィールドのリスト
     */
	public static List<Field> getBeanFieldsAsList(Class<?> clazz) {
		Class<?> currentClass = clazz;
		List<Field> fieldList = new ArrayList<Field>();
		while (currentClass != Object.class) {
			List<Field> list = getDeclaredFields(currentClass);
			for (int i = 0; i < list.size(); i++) {
				fieldList.add(list.get(i));
			}
			currentClass = currentClass.getSuperclass();
		}
		return fieldList;
	}

	/**
     * <p>
     * JavaBeansのフィールドを取得します.親クラスには遡りません.

     * </p>
     *
     * @param clazz
     *            対象となるクラス
     * @return 取得したフィールドの配列
     */
	public static List<Field> getDeclaredFields(Class<?> clazz) {
		List<Field> list = new ArrayList<Field>();
		try {
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0, s = fields.length; i < s; i++) {
				list.add(fields[i]);
			}
		} catch (Exception e) {
			throw new CoreRuntimeException(e);
		}

		return list;
	}

	/**
     * <p>
     * フィールドがシリアライズ可能なフィールドであるか調べます.<br/>
     * Serializableマーカの実装であるか、transient修飾子が付与されていない、或いは
     * プリミティブである場合、シリアライズ可能であると判断します.
     * </p>
     *
     * @param field フィールド
     * @return シリアライズ可能である場合true、そうでない場合false
     */
	public static boolean isSerializableField(Field field) {
		if ((containsInterface(field.getType(), Serializable.class) || field.getType().isPrimitive())
				&& !isTransientField(field)) {
			return true;
		}
		return false;
	}

	public static boolean containsInterface(Class<?> target, Class<?> clazz){
		Class<?>[] classes = target.getInterfaces();
		for(int i = 0; i < classes.length; i++){
			if(classes[i] == clazz){
				return true;
			}
		}
		return false;
	}

	/**
	 * <p>
	 * フィールドが転記可能なフィールドであるか調べます<br/>
	 * finalでなく、transientでない場合、転記可能であると判断します.
	 * </p>
	 * @param field フィールド
	 * @return 転記可能である場合true、そうでない場合false
	 */
	public static boolean isPossibleToCopy(Field field){
		if(!isFinalField(field) && !isTransientField(field)){
			return true;
		}
		return false;
	}

	/**
	 * <p>
	 * フィールドのアクセス修飾子にfinalが含まれるかを判断します
	 * </p>
	 * @param field フィールド
	 * @return finalが含まれる場合true、そうでない場合false
	 */
	public static boolean isFinalField(Field field){
		return Modifier.isFinal(field.getModifiers());
	}

	/**
	 * <p>
	 * フィールドのアクセス修飾子にtransientが含まれるかを判断します
	 * </p>
	 * @param field フィールド
	 * @return transientが含まれる場合true、そうでない場合false
	 */
	public static boolean isTransientField(Field field){
		return Modifier.isTransient(field.getModifiers());
	}

	/**
	 * <p>
	 * フィールドのアクセス修飾子にstaticが含まれるかを判断します
	 * </p>
	 * @param field フィールド
	 * @return staticが含まれる場合true、そうでない場合false
	 */
	public static boolean isStaticField(Field field){
		return Modifier.isStatic(field.getModifiers());
	}

	/**
	 * <p>
	 * フィールドのアクセス修飾子にprivateが含まれるかを判断します
	 * </p>
	 * @param field フィールド
	 * @return privateが含まれる場合true、そうでない場合false
	 */
	public static boolean isPrivateField(Field field){
		return Modifier.isPrivate(field.getModifiers());
	}

	/**
	 * <p>
	 * フィールドのアクセス修飾子にprotectedが含まれるかを判断します
	 * </p>
	 * @param field フィールド
	 * @return protectedが含まれる場合true、そうでない場合false
	 */
	public static boolean isProtectedField(Field field){
		return Modifier.isProtected(field.getModifiers());
	}

	/**
     * <p>
     * JavaBeansからJavaBeansへ項目の転記を行います.
     * </p>
     * <p>
     * サンプル：
     * </p>
     *
     * <pre>
     * BeanUtils.copyBeanToBean(fromJavaBeans, toJavaBeans);
     * </pre>
     *
     * @param from
     *            転記元JavaBeans
     * @param to
     *            転記先JavaBeans
     * @return 転記済JavaBeans
     */
	public static Object copyBeanToBean(Object from, Object to) {
		try {
			org.apache.commons.beanutils.BeanUtils.copyProperties(to, from);
		} catch (IllegalAccessException e) {
			throw new CoreRuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new CoreRuntimeException(e);
		}
		return to;
	}

	/**
     * <p>
     * MapからMapへ項目の転記を行います.
     * </p>
     * <p>
     * サンプル：
     * </p>
     *
     * <pre>
     * BeanUtils.copyBeanToBean(fromJavaBeans, toJavaBeans);
     * </pre>
     *
     * @param from
     *            転記元JavaBeans
     * @param to
     *            転記先JavaBeans
     * @return 転記済JavaBeans
     */
	@SuppressWarnings("unchecked")
	public static Map<Object, Object> copyMapToMap(Map<Object, Object> from) {
		return MapUtils.clone(from);
	}

	/**
     * <p>
     * 当該オブジェクトがJavaBeans形式かどうか継承クラスを遡り、正確に判定します<br>
     * JavaBeans形式の定義は以下の通りです.
     * </p>
     * <ul>
     * <li>static又はfinal修飾子の無いprivateフィールドが1つでも存在すること</li>
     * <li>static又はfinal修飾子の無いprivateフィールドに対し、アクセサが存在すること</li>
     * <li>デフォルトコンストラクタを持つこと</li>
     * <li>boolean型のxxxフィールドのgetterはisXxxとなること</li>
     * <li>指定クラスからjava.lang.Object型まで継承ツリーを辿り、1つでもJavaBeansとして適合する型が存在すればJavaBeansとみなします</li>
     * </ul>
     *
     * @param obj
     *            判定オブジェクト
     * @return JavaBeansである場合true, そうでない場合false
     */
	public static boolean isAccurateJavaBeans(Object obj) {
		if (obj == null) {
			return false;
		}
		return isAccurateJavaBeans(obj.getClass());
	}

	/**
     * <p>
     * 当該オブジェクトがJavaBeans形式かどうか継承クラスを遡り、正確に判定します<br>
     * JavaBeans形式の定義は以下の通りです.
     * </p>
     * <ul>
     * <li>static又はfinal修飾子の無いprivateフィールドが1つでも存在すること</li>
     * <li>static又はfinal修飾子の無いprivateフィールドに対し、アクセサが存在すること</li>
     * <li>デフォルトコンストラクタを持つこと</li>
     * <li>boolean型のxxxフィールドのgetterはisXxxとなること</li>
     * <li>指定クラスからjava.lang.Object型まで継承ツリーを辿り、1つでもJavaBeansとして適合する型が存在すればJavaBeansとみなします</li>
     * </ul>
     *
     * @param clazz
     *            判定オブジェクトのクラス
     * @return JavaBeansである場合true, そうでない場合false
     */
	public static boolean isAccurateJavaBeans(Class<?> clazz) {
		if (clazz == null) {
			return false;
		}
		Class<?> currentClass = clazz;
		while (currentClass != Object.class) {
			boolean judge = isDeclaredJavaBeans(currentClass);
			if (judge) {
				return true;
			}
			currentClass = currentClass.getSuperclass();
		}
		return false;
	}

	/**
     * <p>
     * 当該オブジェクトがJavaBeans形式かどうか判定します<br>
     * 当該オブジェクトの親クラスは判定対象になりません.<br>
     * JavaBeans形式の定義は以下の通りです.
     * </p>
     * <ul>
     * <li>static又はfinal修飾子の無いprivateかprotectedのフィールドが1つでも存在すること</li>
     * <li>static又はfinal修飾子の無いprivateかprotectedフィールドに対し、アクセサが存在すること</li>
     * <li>デフォルトコンストラクタを持つこと</li>
     * <li>boolean型のxxxフィールドのgetterはisXxxとなること</li>
     * </ul>
     *
     * @param obj
     *            判定オブジェクト
     * @return JavaBeansである場合true, そうでない場合false
     */
	public static boolean isJavaBeans(Object obj) {
		if (obj == null) {
			return false;
		}
		return isDeclaredJavaBeans(obj.getClass());
	}

	/**
     * <p>
     * 当該オブジェクトがJavaBeans形式かどうか判定します<br>
     * 当該オブジェクトの親クラスは判定対象になりません.<br>
     * JavaBeans形式の定義は以下の通りです.
     * </p>
     * <ul>
     * <li>static又はfinal修飾子の無いprivateかprotectedのフィールドが1つでも存在すること</li>
     * <li>static又はfinal修飾子の無いprivateかprotectedフィールドに対し、アクセサが存在すること</li>
     * <li>デフォルトコンストラクタを持つこと</li>
     * <li>boolean型のxxxフィールドのgetterはisXxxとなること</li>
     * </ul>
     *
     * @param clazz
     *            判定オブジェクトのクラス
     * @return JavaBeansである場合true, そうでない場合false
     */
	public static boolean isJavaBeans(Class<?> clazz) {
		if (clazz == null) {
			return false;
		}
		return isDeclaredJavaBeans(clazz);
	}

	private static boolean isDeclaredJavaBeans(Class<?> clazz) {
		int memberCount = 0;
		if (clazz == null) {
			return false;
		}

		String className = clazz.getName();
		Boolean result = isJavaBeansResult.get(className);
		if (result != null) {
			return result;
		}

		try {
			if (clazz.getConstructor(new Class[0]) == null) {
				return false;
			}
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				if (true/*(Modifier.isPrivate(fields[i].getModifiers()) || Modifier.isProtected(fields[i].getModifiers()))*/
						&& fields[i].getModifiers() != Modifier.STATIC && fields[i].getModifiers() != Modifier.FINAL) {
					String fieldName = fields[i].getName();
					Class<?> destType = fields[i].getType();
					String upperFieldName = StringUtils.capitalize(fieldName);
					String getterName = null;
					String setterName = null;
					if (destType != boolean.class) {
						getterName = "get" + upperFieldName;
						setterName = "set" + upperFieldName;
					} else {
						getterName = "is" + upperFieldName;
						setterName = "set" + upperFieldName;
					}
					Method getterMethod = null;
					Method setterMethod = null;
					try {
						getterMethod = clazz.getMethod(getterName, new Class[0]);
						setterMethod = clazz.getMethod(setterName, destType);
					} catch (NoSuchMethodException e) {
						continue;
					}
					if (getterMethod != null && setterMethod != null) {
						memberCount++;
						continue;
					}
				}
			}
		} catch (Exception e) {
			isJavaBeansResult.put(className, false);
			return false;
		}
		if (memberCount == 0) {
			isJavaBeansResult.put(className, false);
			return false;
		}
		isJavaBeansResult.put(className, true);
		return true;
	}

	/**
     * <p>
     * 同一オブジェクトの差分フィールドリストを抽出します.<br>
     * </p>
     */
	public static Map<String, String> getDiffedList(Class<?> clazz, Object from, Object to) {
	    Map<String, String> ret = new HashMap<>();
	    String className = clazz.getName();
        if (!from.getClass().getName().equals(className) &&
                !to.getClass().getName().equals(className) ) {
            return null;
        }

//        String className = clazz.getName();
//        Boolean result = isJavaBeansResult.get(className);
//        if (result != null) {
//            return result;
//        }

        try {
            if (clazz.getConstructor(new Class[0]) == null) {
                return null;
            }
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                if (true/*(Modifier.isPrivate(fields[i].getModifiers()) || Modifier.isProtected(fields[i].getModifiers()))*/
                        && fields[i].getModifiers() != Modifier.STATIC && fields[i].getModifiers() != Modifier.FINAL) {
                    String fieldName = fields[i].getName();
                    Class<?> destType = fields[i].getType();
                    String upperFieldName = StringUtils.capitalize(fieldName);
                    String getterName = null;
                    if (destType != boolean.class) {
                        getterName = "get" + upperFieldName;
                    } else {
                        getterName = "is" + upperFieldName;
                    }
                    Method getterMethod = null;
                    try {
                        getterMethod = clazz.getMethod(getterName, new Class[0]);
                    } catch (NoSuchMethodException e) {
                        continue;
                    }

                    Object fromObj = getterMethod.invoke(from);
                    Object toObj = getterMethod.invoke(to);

                    if (fromObj == null && toObj == null) {
                        continue;
                    } else if (fromObj != null && toObj == null) {
                        ret.put(fieldName, null);
                    } else if (fromObj == null && toObj != null) {
                        ret.put(fieldName, toObj.toString());
                    } else {
                        if(fromObj instanceof String) {
                            if(!((String)fromObj).equals((String)toObj)) {
                                ret.put(fieldName, (String)toObj);
                                continue;
                            }
                        }
                        if(fromObj instanceof java.sql.Date) {
                            if(((java.sql.Date)fromObj).compareTo((java.sql.Date)toObj) != 0) {
                                ret.put(fieldName, toObj.toString());
                                continue;
                            }
                        }
                        if(fromObj instanceof Timestamp) {
                            if(((Timestamp)fromObj).compareTo((Timestamp)toObj) != 0) {
                                ret.put(fieldName, toObj.toString());
                                continue;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new CoreRuntimeException(e);
        }
        return ret;
    }
}
