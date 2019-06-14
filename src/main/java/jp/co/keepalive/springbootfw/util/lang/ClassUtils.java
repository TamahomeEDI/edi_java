/**************************************************************
 * システム名		:
 * サブシステム名	:
 * モジュール名		:ClassUtils.java
 *
 * 修正日			:修正者			:修正理由
 * ------------------------------------------
 * 2007/06/28	:成田　敦			:新規作成
 * 2018/03/13	:内田　薫			:Pitlock側DB分離作業に伴う共通メソッド追加
 *
 **************************************************************/
package jp.co.keepalive.springbootfw.util.lang;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;
import jp.co.keepalive.springbootfw.util.date.DateUtils;

/**
 * <b>[概要]</b><br>
 * <p>
 * Class を操作するためのユーティリティクラスです。
 * </p>
 *
 * @author Atsushi Narita
 * @author Kaoru Uchida
 */
public class ClassUtils {

	private static Log LOGGER = LogFactory.getLog(ClassUtils.class);

	/** Setterメソッド名先頭文字 */
	private static final String SETTER_PREFIX = "set";

	/** Getterメソッド名先頭文字 */
	private static final String GETTER_PREFIX = "get";

	/**
	 * <p>
	 * リフレクションを使用して public メソッドを実行します.
	 * </p>
	 *
	 * @param methodName
	 *            実行するメソッド名
	 * @param params
	 *            メソッドの引数
	 * @param target
	 *            対象のインスタンス
	 * @return 実行したメソッドの戻り値
	 */
	public static Object invokePublicMethod(String methodName, Class<?>[] classes, Object[] params, Object target) {
		try {
			Method method = target.getClass().getMethod(methodName, classes);
			return method.invoke(target, params);
		} catch (SecurityException e) {
			throw new CoreRuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new CoreRuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new CoreRuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new CoreRuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new CoreRuntimeException(e);
		}
	}

	public static Object invokePublicMethod(String methodName, Class<?>[] classes, Object[] params, Class<?> target) {
		try {
			Method method = target.getMethod(methodName, classes);
			return method.invoke(target, params);
		} catch (SecurityException e) {
			throw new CoreRuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new CoreRuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new CoreRuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new CoreRuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new CoreRuntimeException(e);
		}
	}

	/**
	 * <p>
	 * リフレクションを使用して private メソッドを実行します。
	 * </p>
	 *
	 * @param methodName
	 *            実行するメソッド名
	 * @param params
	 *            メソッドの引数
	 * @param target
	 *            対象のインスタンス
	 * @return 実行したメソッドの戻り値
	 */
	public static Object invokePrivateMethod(String methodName, Class<?>[] classes, Object[] params, Object target) {
		try {
			Method method = target.getClass().getDeclaredMethod(methodName, classes);
			method.setAccessible(true);
			return method.invoke(target, params);
		} catch (SecurityException e) {
			throw new CoreRuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new CoreRuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new CoreRuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new CoreRuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new CoreRuntimeException(e);
		}
	}

	/**
	 * <p>
	 * クラス名からそのクラスのインスタンスを生成します。
	 * </p>
	 *
	 * @param className
	 *            クラスの完全修飾名
	 * @return クラスのインスタンス
	 */
	public static Object createInstance(String className) {

		try {
			return Class.forName(className).newInstance();
		} catch (ClassNotFoundException ex) {
			try {
				return Class.forName(className, true, Thread.currentThread().getContextClassLoader()).newInstance();
			} catch (Exception ex2) {
				if (ex2 instanceof InstanceCreateFailureException) {
					throw (InstanceCreateFailureException) ex2;
				}
				throw new InstanceCreateFailureException(ex);
			}
		} catch (Exception ex) {
			throw new InstanceCreateFailureException(ex);
		}
	}

	/**
	 * <p>
	 * クラスローダを指定して、クラス名からそのクラスのインスタンスを生成します。
	 * </p>
	 *
	 * @param className
	 *            クラスの完全修飾名
	 * @param loader
	 *            クラスローダ
	 * @return クラスのインスタンス
	 */
	public static Object createInstance(String className, ClassLoader loader) {

		try {
			if (loader == null) {
				return createInstance(className);
			}
			return Class.forName(className, true, loader).newInstance();
		} catch (Exception ex) {
			if (ex instanceof InstanceCreateFailureException) {
				throw (InstanceCreateFailureException) ex;
			}
			throw new InstanceCreateFailureException(ex);
		}
	}

	/**
	 * <p>
	 * コンストラクタの引数を指定して、クラス名からそのクラスのインスタンスを生成します。
	 * </p>
	 *
	 * @param className
	 *            クラスの完全修飾名
	 * @param args
	 *            コンストラクタ引数の配列
	 * @return クラスのインスタンス
	 */
	public static Object createInstance(String className, Object[] args) {

		try {
			return createInstance(getClass(className), args);
		} catch (Exception ex) {
			if (ex instanceof InstanceCreateFailureException) {
				throw (InstanceCreateFailureException) ex;
			}
			throw new InstanceCreateFailureException(ex);
		}
	}

	/**
	 * <p>
	 * コンストラクタの引数を指定して、クラスオブジェクトからそのクラスのインスタンスを生成します。
	 * </p>
	 *
	 * @param objClass
	 *            クラスオブジェクト
	 * @param args
	 *            コンストラクタ引数の配列
	 * @return クラスのインスタンス
	 */
	public static Object createInstance(Class<?> objClass, Object[] args) {

		try {
			Class<?>[] argClasses = new Class[args.length];
			for (int i = 0; i < args.length; i++) {
				argClasses[i] = args[i].getClass();
			}
			Constructor<?> con = objClass.getConstructor(argClasses);
			return con.newInstance(args);
		} catch (Exception ex) {
			throw new InstanceCreateFailureException(ex);
		}

	}

	/**
	 * <p>
	 * クラス名からそのクラスのクラスオブジェクトを生成します。
	 * </p>
	 *
	 * @param className
	 *            クラスの完全修飾名
	 * @return 対象クラスのクラスオブジェクト
	 * @throws ClassNotFoundException
	 *             指定したクラスが存在しない場合
	 */
	public static Class<?> getClass(String className) {

		try {
			return Class.forName(className);
		} catch (ClassNotFoundException ex) {
			LOGGER.warn("Class name=" + className + "が見つかりませんでした。" + "Thread.currentThread().getContextClassLoader()からClassを探します。");
			return getClass(className, Thread.currentThread().getContextClassLoader());
		} catch (Error error) {
			LOGGER.error("Class name" + className + "の初期化中にエラーが発生しました。", error);
			throw error;
		}
	}

	/**
	 * <p>
	 * クラスローダを指定して、クラス名からそのクラスのクラスオブジェクトを生成します.
	 * </p>
	 *
	 * @param className
	 *            クラスの完全修飾名
	 * @param loader
	 *            指定するクラスローダ
	 * @return 対象クラスのクラスオブジェクト
	 * @throws ClassNotFoundException
	 *             指定したクラスが存在しない場合
	 */
	public static Class<?> getClass(String className, ClassLoader loader) {

		try {
			return Class.forName(className, true, loader);
		} catch (ClassNotFoundException ex) {
			LOGGER.warn("Class name=" + className + "が見つかりませんでした。");
			throw new CoreRuntimeException(ex);
		} catch (Error error) {
			LOGGER.error("Class name" + className + "の初期化中にエラーが発生しました。", error);
			throw error;
		}
	}

	/**
	 * <p>
	 * 正規表現付のメソッド名を呼出します. あいまい検索の結果、複数検出した場合、検出できなかった場合はNoSuchMethodExceptionを原因とした フレームワーク実行時例外が投げられます.
	 * </p>
	 *
	 * @param regexMethodName
	 *            正規表現付きのメソッド名
	 * @param paramTypes
	 *            パラメータ型
	 * @param params
	 *            パラメータ
	 * @param target
	 *            対象インスタンス
	 * @return
	 */
	public static Object invokeRegexPublicMethod(String regexMethodName, Class<?>[] paramTypes, Object[] params, Object target) {
		try {
			return getRegexMethod(regexMethodName, paramTypes, target.getClass()).invoke(target, params);
		} catch (IllegalArgumentException e) {
			throw new CoreRuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new CoreRuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new CoreRuntimeException(e);
		}
	}

	/**
	 * <p>
	 * メソッドを曖昧検索します. あいまい検索の結果、複数検出した場合、検出できなかった場合はNoSuchMethodExceptionを原因とした フレームワーク実行時例外が投げられます.
	 * </p>
	 *
	 * @param regexMethodName
	 *            正規表現付きのメソッド名
	 * @param paramTypes
	 *            パラメータ型
	 * @param target
	 *            対象クラス
	 * @return 単一のメソッド
	 */
	public static Method getRegexMethod(String regexMethodName, Class<?>[] paramTypes, Class<?> target) {
		Method[] methods = target.getMethods();
		List<Method> methodList = new ArrayList<Method>();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().matches(regexMethodName)) {
				Class<?>[] destTypes = methods[i].getParameterTypes();
				if (destTypes.length == paramTypes.length) {
					for (int j = 0; j < destTypes.length; j++) {
						if (destTypes[j].isAssignableFrom(paramTypes[j])) {
							methodList.add(methods[i]);
							break;
						}
					}
				}
			}
		}
		if (methodList.size() == 1) {
			return methodList.get(0);
		} else if (methodList.size() > 1) {
			throw new CoreRuntimeException(new NoSuchMethodException("検索文字列：" + regexMethodName + "に対し複数のメソッドが検出されました."));
		} else {
			throw new CoreRuntimeException(new NoSuchMethodException("検索文字列：" + regexMethodName + "でメソッドが見つかりませんでした."));
		}
	}

	/**
	 * 指定された名前のpublicメソッドを親クラスに遡って取得する
	 *
	 * @param obj
	 *            メソッドを持っているオブジェクト
	 * @param methodName
	 *            メソッド名
	 * @param fieldTypes
	 *            メソッドのパラメータのタイプ
	 * @return 見つからなかった場合はnull
	 */
	public static Method getMethod(Object obj, String methodName, Class<?>... fieldTypes) {
		Method method = null;
		for (Class<?> cls = obj.getClass(); method == null && cls != null; cls = cls.getSuperclass()) {
			try {
				method = cls.getMethod(methodName, fieldTypes);
			} catch (NoSuchMethodException nsme) {
				continue;
			}
		}
		return method;
	}

	/**
	 * 指定された名前のフィールドを親クラスに遡って取得する<br/>
	 * ※フィールドのアクセス属性は限定していないので、privateフィールドの場合もある。
	 *
	 * @param obj
	 *            フィールドを持っているオブジェクト
	 * @param fieldName
	 *            フィールド名
	 * @return 見つからなかった場合はnull
	 */
	public static Field getField(Object obj, String fieldName) {
		Field field = null;
		for (Class<?> cls = obj.getClass(); field == null && cls != null; cls = cls.getSuperclass()) {
			try {
				field = cls.getDeclaredField(fieldName);
			} catch (NoSuchFieldException nsme) {
				continue;
			}
		}
		return field;
	}

	/**
	 * 指定された名前のSetterを親クラスに遡って取得する<br/>
	 * 見つかってもアクセスできない場合はExceptionを投げている。
	 *
	 * @param obj
	 *            Setterを持っているオブジェクト
	 * @param setterName
	 *            Setterメソッド名
	 * @param fieldType
	 *            Setterでセットするオブジェクトのタイプ
	 * @return 見つからなかった場合はnull
	 */
	public static Method getSetter(Object obj, String setterName, Class<?> fieldType) {
		if (obj == null) {
			throw new IllegalArgumentException();
		}
		return getMethod(obj, setterName, fieldType);
	}

	/**
	 * 指定された名前のGetterを親クラスに遡って取得する<br/>
	 * 見つかってもアクセスできない場合はExceptionを投げている。
	 *
	 * @param obj
	 *            Getterを持っているオブジェクト
	 * @param getterName
	 *            Getterメソッド名
	 * @param fieldType
	 *            Getterでセットするオブジェクトのタイプ
	 * @return 見つからなかった場合はnull
	 */
	public static Method getGetter(Object obj, String getterName) {
		if (obj == null) {
			throw new IllegalArgumentException();
		}
		return getMethod(obj, getterName);
	}

	/**
	 * Setterを使用して対象オブジェクトの指定されたフィールドに値をセットする <br/>
	 * ※Setterメソッドはpublicであること。
	 *
	 * @param thisObj
	 *            対象オブジェクト
	 * @param fieldName
	 *            対象フィールド名
	 * @param fieldType
	 *            対象フィールドの型
	 * @param value
	 *            セットする値
	 * @param listElementTypeName
	 *            List&lt;?&gt;タイプの場合の要素の型（List&lt;?&gt;タイプでなければnullを渡す）
	 * @param outputLogIfNotFound
	 *            setterが見つからなかった場合、あるいはフィールドにセットできなかった場合にエラーログを出すかどうかを指示する
	 *
	 */
	public static void setValueWithSetter(Object thisObj, String fieldName, Class<?> fieldType, Object value, String listElementTypeName, boolean outputLogIfNotFound) {
		if (thisObj == null) {
			throw new IllegalArgumentException();
		}
		try {
			Method setter = getSetter(thisObj, getSetterName(fieldName), fieldType);
			if (setter == null) {
				if (outputLogIfNotFound) {
					LOGGER.error("指定されたフィールドに対するSetterが定義されてません。クラス名：" + thisObj.getClass().getName() + "、フィールド名：" + fieldName + "、フィールドタイプ：" + fieldType.getName());
				}
				throw new CoreRuntimeException();
			}
			Object targetValue = null;
			if (value != null) {
				if (value instanceof java.lang.String) {
					targetValue = convert(fieldType.getName(), (String) value);

				} else if (value instanceof java.lang.Byte) {
					targetValue = convert(fieldType.getName(), (Byte) value);

				} else if (value instanceof java.lang.Short) {
					targetValue = convert(fieldType.getName(), (Short) value);

				} else if (value instanceof java.lang.Integer) {
					targetValue = convert(fieldType.getName(), (Integer) value);

				} else if (value instanceof java.lang.Long) {
					targetValue = convert(fieldType.getName(), (Long) value);

				} else if (value instanceof java.util.Date) {
					targetValue = convert(fieldType.getName(), (java.util.Date) value);

				} else if (value instanceof java.lang.Float) {
					targetValue = convert(fieldType.getName(), (Float) value);

				} else if (value instanceof java.lang.Double) {
					targetValue = convert(fieldType.getName(), (Double) value);

				} else if (value instanceof java.lang.Boolean) {
					targetValue = convert(fieldType.getName(), (Boolean) value);

				} else if (value instanceof java.util.List<?>) {
					targetValue = convertToList((List<?>) value, listElementTypeName);

				} else if (fieldType.isArray()) {

					Class<?> valueType = value.getClass();
					if (valueType.isArray() == false) {
						LOGGER.error("指定されたフィールドと設定する値の型が一致していません。フィールド名：" + fieldName);
						throw new CoreRuntimeException();
					}

					if (valueType.getName().equals("java.lang.String") == false) {
						LOGGER.error("未対応のフィールドクラスです。クラス：" + valueType.getName());
						throw new CoreRuntimeException();
					}

					targetValue = convert(fieldType.getComponentType().getName(), (String[]) value);
				}
			}

			setter.invoke(thisObj, targetValue);

		} catch (Exception e) {
			if (outputLogIfNotFound) {
				LOGGER.fatal("指定されたフィールドに対するSetterが定義されていないか、アクセスできません。クラス名：" + thisObj.getClass().getName() + "、フィールド名：" + fieldName + "、フィールドタイプ：" + fieldType.getName(), e);
			}
			throw new CoreRuntimeException(e);
		}
	}

	/**
	 * Getterを使用して対象オブジェクトから指定されたフィールドの値を取得する<br/>
	 * ※Getterメソッドはpublicであること。
	 *
	 * @param thisObj
	 *            対象オブジェクト
	 * @param fieldName
	 *            対象フィールド名
	 * @param outputLogIfNotFound
	 *            getterが見つからなかった場合、あるいはフィールドから取得できなかった場合にエラーログを出すかどうかを指示する
	 * @return 対象フィールドの値
	 */
	public static Object getValueWithGetter(Object thisObj, String fieldName, boolean outputLogIfNotFound) {
		return getValueWithGetter(thisObj, fieldName, false, outputLogIfNotFound);
	}

	/**
	 * Getterを使用して対象オブジェクトから指定されたフィールドの値を変換して取得する<br/>
	 * ※Getterメソッドはpublicであること。<br/>
	 * ※現状はBoolean値を"1"/"0"に変換する処理のみ実装。
	 *
	 * @param thisObj
	 *            対象オブジェクト
	 * @param fieldName
	 *            対象フィールド名
	 * @param outputLogIfNotFound
	 *            getterが見つからなかった場合、あるいはフィールドから取得できなかった場合にエラーログを出すかどうかを指示する
	 * @return 対象フィールドの値
	 */
	public static Object getConvertedValueWithGetter(Object thisObj, String fieldName, boolean outputLogIfNotFound) {
		return getValueWithGetter(thisObj, fieldName, true, outputLogIfNotFound);
	}

	/**
	 * Getterを使用して対象オブジェクトから指定されたフィールドの値を取得する<br/>
	 * ※Getterメソッドはpublicであること。
	 *
	 * @param thisObj
	 *            対象オブジェクト
	 * @param fieldName
	 *            対象フィールド名
	 * @param doConversion
	 *            フィールドの値を変換するかどうか？ （現状はBoolean値を"1"/"0"に変換する処理のみ実装。）
	 * @param outputLogIfNotFound
	 *            getterが見つからなかった場合、あるいはフィールドから取得できなかった場合にエラーログを出すかどうかを指示する
	 * @return 対象フィールドの値
	 */
	private static Object getValueWithGetter(Object thisObj, String fieldName, boolean doConversion, boolean outputLogIfNotFound) {
		if (thisObj == null) {
			LOGGER.warn("指定されたフィールドの親オブジェクトが渡されていません。フィールド名：" + fieldName);
			throw new IllegalArgumentException();
		}
		Object value = null;
		try {
			Method getter = getGetter(thisObj, getGetterName(fieldName));
			if (getter == null) {
				if (outputLogIfNotFound) {
					LOGGER.error("指定されたフィールドに対するGetterが定義されてません。フィールド名：" + fieldName);
				}
				return null;
			}
			Object rawValue = getter.invoke(thisObj);
			if (doConversion) {
				switch (getter.getReturnType().getName()) {
				case "java.lang.Boolean":
				case "boolean":
					value = fromBooleanToStr((Boolean) rawValue);
					break;
				default:
					value = rawValue;
					break;
				}
			} else {
				value = rawValue;
			}
		} catch (Exception e) {
			if (outputLogIfNotFound) {
				LOGGER.warn("指定されたフィールドに対するGetterが定義されていないか、アクセスできません。フィールド名：" + fieldName, e);
			}
			return null;
		}
		return value;
	}

	/**
	 * Setterメソッド名を作成する
	 *
	 * @param fieldName
	 *            対象フィールド名
	 * @return Setterメソッド名
	 */
	private static String getSetterName(String fieldName) {
		return getSetterGetterName(SETTER_PREFIX, fieldName);
	}

	/**
	 * Getterメソッド名を作成する
	 *
	 * @param fieldName
	 *            対象フィールド名
	 * @return Getterメソッド名
	 */
	private static String getGetterName(String fieldName) {
		return getSetterGetterName(GETTER_PREFIX, fieldName);
	}

	/**
	 * Setter・Getterメソッド名を作成する
	 *
	 * @param getSet
	 *            "set" or "get"
	 * @param fieldName
	 *            対象フィールド名
	 * @return Setter・Getterメソッド名
	 */
	private static String getSetterGetterName(String getSet, String fieldName) {
		StringBuilder sb = new StringBuilder(fieldName.length() + 3);
		return sb.append(getSet).append(Character.toUpperCase(fieldName.charAt(0))).append(fieldName.substring(1)).toString();
	}

	/**
	 * Setter/Getterメソッド名から対応するフィールド名を作成する
	 *
	 * @param setterGetterName
	 *            Setter/Getterメソッド名
	 * @return フィールド名。Setter/Getterメソッド名でない場合はnull。
	 */
	public static String getFieldNameFromSetterGetter(String setterGetterName) {
		String fieldName = null;
		if (setterGetterName.startsWith(GETTER_PREFIX)) {
			fieldName = setterGetterName.substring(GETTER_PREFIX.length());
		} else if (setterGetterName.startsWith(SETTER_PREFIX)) {
			fieldName = setterGetterName.substring(SETTER_PREFIX.length());
		}
		if (fieldName == null || fieldName.length() == 0) {
			return null;
		}
		StringBuilder sb = new StringBuilder(fieldName.length());
		return sb.append(Character.toLowerCase(fieldName.charAt(0))).append(fieldName.substring(1)).toString();
	}

	/**
	 * methodがGetterかどうかチェックする
	 *
	 * @param method
	 * @return
	 */
	public static boolean isSetter(Method method) {
		String methodName = method.getName();
		if (methodName.startsWith(SETTER_PREFIX) == false || methodName.equals(SETTER_PREFIX)) {
			return false;
		}
		if (Character.isLowerCase(methodName.charAt(SETTER_PREFIX.length()))) {
			return false;
		}
		if (method.getParameterTypes().length != 1) {
			return false;
		}
		if (method.getReturnType().equals(Void.TYPE) == false) {
			return false;
		}
		return true;
	}

	/**
	 * methodがSetterかどうかチェックする
	 *
	 * @param method
	 * @return
	 */
	public static boolean isGetter(Method method) {
		String methodName = method.getName();
		if (methodName.startsWith(GETTER_PREFIX) == false || methodName.equals(GETTER_PREFIX)) {
			return false;
		}
		if (Character.isLowerCase(methodName.charAt(GETTER_PREFIX.length()))) {
			return false;
		}
		if (method.getParameterTypes().length != 0) {
			return false;
		}
		if (method.getReturnType().equals(Void.TYPE)) {
			return false;
		}
		return true;
	}

	/**
	 * objが持っているすべてのフィールドを収集する。（親クラスのフィールドも含む）
	 *
	 * @param obj
	 * @return
	 */
	public static List<Field> collectAllFields(Object obj) {
		List<Field> fieldArray = new ArrayList<Field>();

		Class<?> cls = obj.getClass();
		while (cls != null) {
			for (Field field : cls.getDeclaredFields()) {
				fieldArray.add(field);
			}
			cls = cls.getSuperclass();
		}
		return fieldArray;
	}

	/**
	 * {@link ClassUtils#collectAllPublicMethods(Object, MethodFilter)}で使用するメソッドフィルター
	 */
	public static interface MethodFilter {
		/**
		 * メソッドが対象かどうかを判定し結果を返す
		 *
		 * @param method
		 *            判定対象のメソッド
		 * @return 対象となるメソッドの場合、trueを返す
		 */
		boolean filter(Method method);
	}

	/**
	 * objが持っているすべてのpublicメソッドを収集する。（親クラスのメソッドも含む）
	 *
	 * @param obj
	 * @return
	 */
	public static List<Method> collectAllPublicMethods(Object obj) {
		return collectAllPublicMethods(obj, null);
	}

	/**
	 * objが持っているすべてのpublicメソッドを収集する。（親クラスのメソッドも含む）
	 *
	 * @param obj
	 * @param filter
	 *            対象メソッドを限定したい場合に渡す
	 * @return
	 */
	public static List<Method> collectAllPublicMethods(Object obj, MethodFilter filter) {
		List<Method> methodArray = new ArrayList<Method>();

		Class<?> cls = obj.getClass();
		while (cls != null) {
			for (Method method : cls.getMethods()) {
				if (filter == null || filter.filter(method)) {
					methodArray.add(method);
				}
			}
			cls = cls.getSuperclass();
		}
		return methodArray;
	}

	/**
	 * objが持っているすべてのSetter（publicメソッド）を収集する。（親クラスのメソッドも含む）
	 *
	 * @param obj
	 * @return
	 */
	public static List<Method> collectAllSetters(Object obj) {
		return collectAllPublicMethods(obj, new MethodFilter() {

			@Override
			public boolean filter(Method method) {
				return isSetter(method);
			}
		});

	}

	/**
	 * objが持っているすべてのGetter（publicメソッド）を収集する。（親クラスのメソッドも含む）
	 *
	 * @param obj
	 * @return
	 */
	public static List<Method> collectAllGetters(Object obj) {
		return collectAllPublicMethods(obj, new MethodFilter() {

			@Override
			public boolean filter(Method method) {
				return isGetter(method);
			}
		});

	}

	/**
	 * fromObjのフィールドの値をtoObjのフィールドにコピーする。<br/>
	 * フィールドの対応はCopyFieldアノテーションで指定する。このアノテーションがない場合は、フィールド名、タイプとも同じと仮定する。<br/>
	 * CopyFieldアノテーションはフィールドにのみ付加できる。SetterやGetterには付加できない。
	 * <ul>
	 * <li>フィールド名、フィールドタイプが異なっていてもコピーができる。この場合、CopyFiledアノテーションでコピー先のフィールド名、タイプを指定しないといけない。</li>
	 * <li>但し、フィールドタイプが異なる場合、対応可能なタイプは限定される。{@link ClassUtils#setValueWithSetter(Object, String, Class, Object, String)}を参照。</li>
	 * <li>コピー元になるフィールドは、フィールド自体がアクセス可能、あるいはGetterがアクセス可能で、かつ、コピー先のフィールド自体がアクセス可能、あるいはSetterがアクセス可能なもののみコピー対象になる</li>
	 * <li>List<?>インターフェースオブジェクトの場合はCopyFiledアノテーションで配列要素のタイプを指定しないといけない。（実行時に要素タイプが取れないため）</li>
	 * </ul>
	 *
	 * @param fromObj
	 * @param toObj
	 */
	public static void copyFieldValues(Object fromObj, Object toObj) {
		if (fromObj == null || toObj == null) {
			throw new IllegalArgumentException();
		}
		CopyField targetSetting;
		String targetFieldName, fieldName, listElementTypeName;
		Object value;
		Class<?> targetClass;

		// フィールド
		List<Field> fields = collectAllFields(fromObj);
		for (Field field : fields) {
			targetSetting = field.getAnnotation(CopyField.class);

			fieldName = field.getName();
			if (targetSetting == null || (targetFieldName = targetSetting.targetFieldName()).trim().length() == 0) {
				// コピー先フィールド名の指定がない場合はフィールド名は同じものと仮定する
				targetFieldName = fieldName;
			}

			if (targetSetting != null && targetSetting.outOfTarget()) {
				// コピー対象外の指定がされていれば対象外
				continue;
			}

			Field targetField;
			try {
				targetField = getField(toObj, targetFieldName);
			} catch (Exception e1) {
				// フィールドが見つからなかった場合は対象外とする
				continue;
			}

			// コピー先のタイプ指定を取得
			if (targetSetting == null || (targetClass = targetSetting.targetClass()).equals(Void.class)) {
				// タイプが指定されていない場合はコピー先フィールドのタイプとする
				targetClass = targetField.getType();
			}

			// コピー元フィールドの値を取得する
			try {
				value = getValueWithGetter(fromObj, fieldName, false);
			} catch (Exception e2) {
				// コピー元フィールドの値が取れない場合、対象外とする
				LOGGER.warn("getterを使用した値の取得に失敗したのでコピー対象外にします。フィールド名： " + fieldName);
				continue;
			}

			try {
				if (targetSetting != null) {
					listElementTypeName = targetSetting.listElementClass().getName();
				} else {
					listElementTypeName = null;
				}

				// Setterを使用して値をセット
				setValueWithSetter(toObj, targetFieldName, targetClass, value, listElementTypeName, false);

			} catch (CoreRuntimeException cre) {
				// Setterが見つからない場合
				try {
					// 直接、フィールドに値をセット
					targetField.set(toObj, value);
				} catch (Exception e) {
					// コピー対象を限定しているわけではないので、コピーを想定していないフィールドも対象になってしまう可能性もある。
					// それを考慮し、コピーに失敗したとしてもエラー終了にはせず、次に進むものとする
					continue;
				}
			}
		}

	}

	/**
	 * 下記規則により文字列をBooleanオブジェクトに変換する。
	 * <ul>
	 * <li>"0"の場合はBoolean.FALSEに変換する</li>
	 * <li>"1"の場合はBoolean.TRUEに変換する</li>
	 * <li>"false"（大文字・小文字無視）の場合はBoolean.FALSEに変換する</li>
	 * <li>"true"（大文字・小文字無視）の場合はBoolean.TRUEに変換する</li>
	 * <li>上記以外の場合はnullを返す。</li>
	 * <li>nullの場合はnullを返す。</li>
	 * </ul>
	 *
	 * @param value
	 *            文字列値
	 * @return 変換後のBooleanオブジェクト
	 */
	public static Boolean toBoolean(String value) {
		if (value != null) {
			switch (value) {
			case "0":
				return Boolean.FALSE;
			case "1":
				return Boolean.TRUE;
			default:
				if ("true".equals(value.toLowerCase())) {
					return Boolean.TRUE;
				} else if ("false".equals(value.toLowerCase())) {
					return Boolean.FALSE;
				}
				break;
			}
		}
		return null;
	}

	/**
	 * 下記規則によりIntegerをBooleanオブジェクトに変換する。
	 * <ul>
	 * <li>0の場合はBoolean.FALSEに変換する</li>
	 * <li>1の場合はBoolean.TRUEに変換する</li>
	 * <li>nullの場合はnullを返す。</li>
	 * <li>0、1以外の場合はnullを返す。</li>
	 * </ul>
	 *
	 * @param value
	 *            整数値
	 * @return 変換後のBooleanオブジェクト
	 */
	public static Boolean toBoolean(Integer value) {
		if (value != null) {
			switch (value) {
			case 0:
				return Boolean.FALSE;
			case 1:
				return Boolean.TRUE;
			}
		}
		return null;
	}

	/**
	 * 下記規則によりBooleanオブジェクトを文字列に変換する。
	 * <ul>
	 * <li>Boolean.FALSEの場合は"0"に変換する</li>
	 * <li>Boolean.TRUEの場合は"1"に変換する</li>
	 * <li>nullの場合はnullを返す。</li>
	 * </ul>
	 *
	 * @param value
	 *            Booleanオブジェクト
	 * @return 変換後の文字列
	 */
	public static String fromBooleanToStr(Boolean value) {
		if (value != null) {
			if (value.equals(Boolean.FALSE)) {
				return "0";
			} else if (value.equals(Boolean.TRUE)) {
				return "1";
			}
		}
		return null;
	}

	/**
	 * 下記規則によりBooleanオブジェクトをIntegerに変換する。
	 * <ul>
	 * <li>Boolean.FALSEの場合は0に変換する</li>
	 * <li>Boolean.TRUEの場合は1に変換する</li>
	 * <li>nullの場合はnullを返す。</li>
	 * </ul>
	 *
	 * @param value
	 *            Booleanオブジェクト
	 * @return 変換後の文字列
	 */
	public static Integer fromBooleanToInt(Boolean value) {
		if (value != null) {
			if (value.equals(Boolean.FALSE)) {
				return new Integer(0);
			} else if (value.equals(Boolean.TRUE)) {
				return new Integer(1);
			}
		}
		return null;
	}

	/**
	 * 文字列値をtypeNameで指定されたタイプのオブジェクトに変換する
	 *
	 * @param typeName
	 *            変換タイプ
	 * @param value
	 *            変換対象の文字列値
	 * @return 変換後のオブジェクト
	 */
	public static Object convert(String typeName, String value) {
		switch (typeName) {
		case "java.lang.String":
			return value;

		case "java.lang.Integer":
		case "int":
			return Integer.valueOf(value);

		case "java.lang.Short":
		case "short":
			return Short.valueOf(value);

		case "java.lang.Long":
		case "long":
			return Long.valueOf(value);

		case "java.lang.Byte":
		case "byte":
			return Byte.valueOf(value);

		case "java.lang.Float":
		case "float":
			return Float.valueOf(value);

		case "java.lang.Double":
		case "double":
			return Double.valueOf(value);

		case "java.lang.Boolean":
		case "boolean":
			return toBoolean(value);

		case "java.util.Date":
			return DateUtils.strToDate(value);

		default:
			return strToObject(typeName, value);
		}
	}

	/**
	 * Byte値をtypeNameで指定されたタイプのオブジェクトに変換する<br/>
	 * <strong>※タイプによってはキャストなどにより値が不正確になる場合もある。</strong>
	 *
	 * @param typeName
	 *            変換タイプ
	 * @param value
	 *            変換対象のByte値
	 * @return 変換後のオブジェクト
	 */
	public static Object convert(String typeName, Byte value) {
		Object convertedValue = convertLongToSpecifiedObject(typeName, value.longValue());
		if (convertedValue == null) {
			return byteToObject(typeName, value);
		}
		return convertedValue;
	}

	/**
	 * Short値をtypeNameで指定されたタイプのオブジェクトに変換する<br/>
	 * <strong>※タイプによってはキャストなどにより値が不正確になる場合もある。</strong>
	 *
	 * @param typeName
	 *            変換タイプ
	 * @param value
	 *            変換対象のShort値
	 * @return 変換後のオブジェクト
	 */
	public static Object convert(String typeName, Short value) {
		Object convertedValue = convertLongToSpecifiedObject(typeName, value.longValue());
		if (convertedValue == null) {
			return shortToObject(typeName, value);
		}
		return convertedValue;
	}

	/**
	 * Integer値をtypeNameで指定されたタイプのオブジェクトに変換する<br/>
	 * <strong>※タイプによってはキャストなどにより値が不正確になる場合もある。</strong>
	 *
	 * @param typeName
	 *            変換タイプ
	 * @param value
	 *            変換対象のInteger値
	 * @return 変換後のオブジェクト
	 */
	public static Object convert(String typeName, Integer value) {
		Object convertedValue = convertLongToSpecifiedObject(typeName, value.longValue());
		if (convertedValue == null) {
			return intToObject(typeName, value);
		}
		return convertedValue;
	}

	/**
	 * Long値をtypeNameで指定されたタイプのオブジェクトに変換する<br/>
	 * <strong>※タイプによってはキャストなどにより値が不正確になる場合もある。</strong>
	 *
	 * @param typeName
	 *            変換タイプ
	 * @param value
	 *            変換対象のLong値
	 * @return 変換後のオブジェクト
	 */
	public static Object convert(String typeName, Long value) {
		Object convertedValue = convertLongToSpecifiedObject(typeName, value);
		if (convertedValue == null) {
			return longToObject(typeName, value);
		}
		return convertedValue;
	}

	/**
	 * Long値をtypeNameで指定されたタイプのオブジェクトに変換する<br/>
	 * typeNameが下記の型以外の場合はnullを返す。 <br/>
	 * <strong>java.lang.String、 java.lang.Byte、 byte、 java.lang.Short、 short、 java.lang.Integer、 int、 java.lang.Long、 long、 java.lang.Float、 float、 java.lang.Double、 double、 java.lang.Boolean、
	 * boolean、 java.util.Date </strong>
	 *
	 * @param typeName
	 *            変換タイプ
	 * @param value
	 *            変換対象のLong値
	 * @return 変換後のオブジェクト
	 */
	private static Object convertLongToSpecifiedObject(String typeName, Long value) {
		switch (typeName) {
		case "java.lang.String":
			return value.toString();

		case "java.lang.Byte":
		case "byte":
			return value.byteValue();

		case "java.lang.Short":
		case "short":
			return value.shortValue();

		case "java.lang.Integer":
		case "int":
			return value.intValue();

		case "java.lang.Long":
		case "long":
			return value;

		case "java.lang.Float":
		case "float":
			return Float.valueOf(value);

		case "java.lang.Double":
		case "double":
			return Double.valueOf(value);

		case "java.lang.Boolean":
		case "boolean":
			return toBoolean(value.intValue());

		case "java.util.Date":
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(value);
			return c.getTime();

		default:
			return null;
		}
	}

	/**
	 * Float値をtypeNameで指定されたタイプのオブジェクトに変換する<br/>
	 * <strong>※タイプによってはキャストなどにより値が不正確になる場合もある。</strong>
	 *
	 * @param typeName
	 *            変換タイプ
	 * @param value
	 *            変換対象のFloat値
	 * @return 変換後のオブジェクト
	 */
	public static Object convert(String typeName, Float value) {
		Object convertedValue = convertDoubleToSpecifiedObject(typeName, value.doubleValue());
		if (convertedValue == null) {
			return floatToObject(typeName, value);
		}
		return convertedValue;
	}

	/**
	 * Double値をtypeNameで指定されたタイプのオブジェクトに変換する<br/>
	 * <strong>※タイプによってはキャストなどにより値が不正確になる場合もある。</strong>
	 *
	 * @param typeName
	 *            変換タイプ
	 * @param value
	 *            変換対象のDouble値
	 * @return 変換後のオブジェクト
	 */
	public static Object convert(String typeName, Double value) {
		Object convertedValue = convertDoubleToSpecifiedObject(typeName, value);
		if (convertedValue == null) {
			return doubleToObject(typeName, value);
		}
		return convertedValue;
	}

	/**
	 * Double値をtypeNameで指定されたタイプのオブジェクトに変換する<br/>
	 * typeNameが下記の型以外の場合はnullを返す。 <br/>
	 * <strong>java.lang.String、 java.lang.Byte、 byte、 java.lang.Short、 short、 java.lang.Integer、 int、 java.lang.Long、 long、 java.lang.Float、 float、 java.lang.Double、 double、 java.lang.Boolean、
	 * boolean、 java.util.Date </strong>
	 *
	 * @param typeName
	 *            変換タイプ
	 * @param value
	 *            変換対象のDouble値
	 * @return 変換後のオブジェクト
	 */
	private static Object convertDoubleToSpecifiedObject(String typeName, Double value) {
		switch (typeName) {
		case "java.lang.String":
			return value.toString();

		case "java.lang.Byte":
		case "byte":
			return value.byteValue();

		case "java.lang.Short":
		case "short":
			return value.shortValue();

		case "java.lang.Integer":
		case "int":
			return value.intValue();

		case "java.lang.Long":
		case "long":
			return value.longValue();

		case "java.lang.Float":
		case "float":
			return value.floatValue();

		case "java.lang.Double":
		case "double":
			return value;

		case "java.lang.Boolean":
		case "boolean":
			return toBoolean(value.intValue());

		case "java.util.Date":
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(value.longValue());
			return c.getTime();

		default:
			return null;
		}
	}

	/**
	 * Date値をtypeNameで指定されたタイプのオブジェクトに変換する<br/>
	 * 下記のタイプは変換不能としてIllegalArgumentExceptionがスローされる。<br/>
	 * <strong>java.lang.Byte、byte、java.lang.Short、short、java.lang.Integer、int、java.lang.Boolean、boolean</strong>
	 *
	 * @param typeName
	 *            変換タイプ
	 * @param value
	 *            変換対象のDate値
	 * @return 変換後のオブジェクト
	 */
	public static Object convert(String typeName, java.util.Date value) {
		switch (typeName) {
		case "java.lang.String":
			return value.toString();

		case "java.lang.Byte":
		case "byte":
			throw new IllegalArgumentException("変換できないタイプです。変換元タイプ：java.util.Date、変換後のタイプ：" + typeName);

		case "java.lang.Short":
		case "short":
			throw new IllegalArgumentException("変換できないタイプです。変換元タイプ：java.util.Date、変換後のタイプ：" + typeName);

		case "java.lang.Integer":
		case "int":
			throw new IllegalArgumentException("変換できないタイプです。変換元タイプ：java.util.Date、変換後のタイプ：" + typeName);

		case "java.lang.Long":
		case "long":
			return value.getTime();

		case "java.lang.Float":
		case "float":
			return value.getTime();

		case "java.lang.Double":
		case "double":
			return value.getTime();

		case "java.lang.Boolean":
		case "boolean":
			throw new IllegalArgumentException("変換できないタイプです。変換元タイプ：java.util.Date、変換後のタイプ：" + typeName);

		case "java.util.Date":
			return value;

		default:
			return null;
		}
	}

	/**
	 * Boolean値をtypeNameで指定されたタイプのオブジェクトに変換する<br/>
	 * 下記のタイプは変換不能としてIllegalArgumentExceptionがスローされる。<br/>
	 * <strong>java.util.Date</strong>
	 *
	 * @param typeName
	 *            変換タイプ
	 * @param value
	 *            変換対象のBoolean値
	 * @return 変換後のオブジェクト
	 */
	public static Object convert(String typeName, Boolean value) {
		switch (typeName) {
		case "java.lang.String":
			return fromBooleanToStr(value);

		case "java.lang.Byte":
		case "byte":
			return fromBooleanToInt(value);

		case "java.lang.Short":
		case "short":
			return fromBooleanToInt(value);

		case "java.lang.Integer":
		case "int":
			return fromBooleanToInt(value);

		case "java.lang.Long":
		case "long":
			return fromBooleanToInt(value);

		case "java.lang.Float":
		case "float":
			return fromBooleanToInt(value);

		case "java.lang.Double":
		case "double":
			return fromBooleanToInt(value);

		case "java.lang.Boolean":
		case "boolean":
			return value;

		case "java.util.Date":
			throw new IllegalArgumentException("変換できないタイプです。変換元タイプ：Boolean、変換後のタイプ：" + typeName);

		default:
			return null;
		}
	}

	/**
	 * 文字列配列をtypeNameで指定されたタイプのオブジェクト配列に変換する
	 *
	 * @param typeName
	 *            変換後のタイプ
	 * @param strArray
	 *            変換対象の配列
	 * @return 変換後の配列
	 */
	public static Object convert(String typeName, String[] strArray) {
		Object[] targetArray;
		switch (typeName) {
		case "java.lang.String":
			return strArray;

		case "java.lang.Integer":
		case "int":
			targetArray = new Integer[strArray.length];
			for (int i = 0; i < strArray.length; ++i) {
				targetArray[i] = Integer.valueOf(strArray[i]);
			}
			return targetArray;

		case "java.lang.Short":
		case "short":
			targetArray = new Short[strArray.length];
			for (int i = 0; i < strArray.length; ++i) {
				targetArray[i] = Short.valueOf(strArray[i]);
			}
			return targetArray;

		case "java.lang.Long":
		case "long":
			targetArray = new Long[strArray.length];
			for (int i = 0; i < strArray.length; ++i) {
				targetArray[i] = Long.valueOf(strArray[i]);
			}
			return targetArray;

		case "java.lang.Byte":
		case "byte":
			targetArray = new Byte[strArray.length];
			for (int i = 0; i < strArray.length; ++i) {
				targetArray[i] = Byte.valueOf(strArray[i]);
			}
			return targetArray;

		case "java.lang.Float":
		case "float":
			targetArray = new Float[strArray.length];
			for (int i = 0; i < strArray.length; ++i) {
				targetArray[i] = Float.valueOf(strArray[i]);
			}
			return targetArray;

		case "java.lang.Double":
		case "double":
			targetArray = new Double[strArray.length];
			for (int i = 0; i < strArray.length; ++i) {
				targetArray[i] = Double.valueOf(strArray[i]);
			}
			return targetArray;

		case "java.lang.Boolean":
		case "boolean":
			targetArray = new Boolean[strArray.length];
			for (int i = 0; i < strArray.length; ++i) {
				targetArray[i] = toBoolean(strArray[i]);
			}
			return targetArray;

		case "java.util.Date":
			targetArray = new Date[strArray.length];
			for (int i = 0; i < strArray.length; ++i) {
				targetArray[i] = DateUtils.strToDate(strArray[i]);
			}
			return targetArray;

		default:
			try {
				Class<?> cls = Class.forName(typeName);
				Object array = Array.newInstance(cls, strArray.length);
				for (int i = 0; i < strArray.length; ++i) {
					Array.set(array, i, strToObject(cls, strArray[i]));
				}
				return array;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * listを指定されたタイプのオブジェクト配列に変換する<br/>
	 * ※異なるタイプの要素の配列に変換するときに使用する。
	 *
	 * @param list
	 *            List<?>オブジェクト
	 * @param elementTypeName
	 *            変換後の要素のタイプ
	 * @return elementTypeName型の要素の配列
	 */
	public static List<?> convertToList(List<?> list, String elementTypeName) {
		Object value;
		switch (elementTypeName) {
		case "java.lang.String":
			List<String> strList = new ArrayList<String>();
			for (int i = 0; i < list.size(); ++i) {
				strList.add((value = list.get(i)) == null ? null : value.toString());
			}
			return strList;

		case "java.lang.Integer":
		case "int":
			List<Integer> intList = new ArrayList<Integer>();
			for (int i = 0; i < list.size(); ++i) {
				intList.add((value = list.get(i)) == null ? null : Integer.valueOf(value.toString()));
			}
			return intList;

		case "java.lang.Short":
		case "short":
			List<Short> shortList = new ArrayList<Short>();
			for (int i = 0; i < list.size(); ++i) {
				shortList.add((value = list.get(i)) == null ? null : Short.valueOf(value.toString()));
			}
			return shortList;

		case "java.lang.Long":
		case "long":
			List<Long> longList = new ArrayList<Long>();
			for (int i = 0; i < list.size(); ++i) {
				longList.add((value = list.get(i)) == null ? null : Long.valueOf(value.toString()));
			}
			return longList;

		case "java.lang.Byte":
		case "byte":
			List<Byte> byteList = new ArrayList<Byte>();
			for (int i = 0; i < list.size(); ++i) {
				byteList.add((value = list.get(i)) == null ? null : Byte.valueOf(value.toString()));
			}
			return byteList;

		case "java.lang.Float":
		case "float":
			List<Float> floatList = new ArrayList<Float>();
			for (int i = 0; i < list.size(); ++i) {
				floatList.add((value = list.get(i)) == null ? null : Float.valueOf(value.toString()));
			}
			return floatList;

		case "java.lang.Double":
		case "double":
			List<Double> doubleList = new ArrayList<Double>();
			for (int i = 0; i < list.size(); ++i) {
				doubleList.add((value = list.get(i)) == null ? null : Double.valueOf(value.toString()));
			}
			return doubleList;

		case "java.lang.Boolean":
		case "boolean":
			List<Boolean> boolList = new ArrayList<Boolean>();
			for (int i = 0; i < list.size(); ++i) {
				boolList.add((value = list.get(i)) == null ? null : toBoolean(value.toString()));
			}
			return boolList;

		case "java.util.Date":
			List<Date> dateList = new ArrayList<Date>();
			for (int i = 0; i < list.size(); ++i) {
				dateList.add((value = list.get(i)) == null ? null : DateUtils.strToDate(value.toString()));
			}
			return dateList;

		default:
			List<Object> objList = new ArrayList<>();
			try {
				Class<?> cls = Class.forName(elementTypeName);
				for (int i = 0; i < list.size(); ++i) {
					objList.add((value = list.get(i)) == null ? null : strToObject(cls, value.toString()));
				}
			} catch (Exception e) {
				return null;
			}
			return objList;
		}
	}

	/**
	 * 文字列値をtypeNameで指定されたクラスのオブジェクトに変換する。<br/>
	 * <strong>※クラスには文字列を単一のパラメータとするコンストラクタが定義されていなければならない。未定義の場合はnullが返される。</strong>
	 *
	 * @param typeName
	 *            変換後のクラス
	 * @param value
	 *            文字列値
	 * @return 変換後のオブジェクト
	 */
	public static Object strToObject(String typeName, String value) {
		try {
			return strToObject(Class.forName(typeName), value);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Byte値をtypeNameで指定されたクラスのオブジェクトに変換する。<br/>
	 * <strong>※クラスにはByte値を単一のパラメータとするコンストラクタが定義されていなければならない。未定義の場合はnullが返される。</strong>
	 *
	 * @param typeName
	 *            変換後のクラス
	 * @param value
	 *            Byte値
	 * @return 変換後のオブジェクト
	 */
	public static Object byteToObject(String typeName, Byte value) {
		try {
			return byteToObject(Class.forName(typeName), value);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Short値をtypeNameで指定されたクラスのオブジェクトに変換する。<br/>
	 * <strong>※クラスにはShort値を単一のパラメータとするコンストラクタが定義されていなければならない。未定義の場合はnullが返される。</strong>
	 *
	 * @param typeName
	 *            変換後のクラス
	 * @param value
	 *            Short値
	 * @return 変換後のオブジェクト
	 */
	public static Object shortToObject(String typeName, Short value) {
		try {
			return shortToObject(Class.forName(typeName), value);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Integer値をtypeNameで指定されたクラスのオブジェクトに変換する。<br/>
	 * <strong>※クラスにはInteger値を単一のパラメータとするコンストラクタが定義されていなければならない。未定義の場合はnullが返される。</strong>
	 *
	 * @param typeName
	 *            変換後のクラス
	 * @param value
	 *            Integer値
	 * @return 変換後のオブジェクト
	 */
	public static Object intToObject(String typeName, Integer value) {
		try {
			return intToObject(Class.forName(typeName), value);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Long値をtypeNameで指定されたクラスのオブジェクトに変換する。<br/>
	 * <strong>※クラスにはLong値を単一のパラメータとするコンストラクタが定義されていなければならない。未定義の場合はnullが返される。</strong>
	 *
	 * @param typeName
	 *            変換後のクラス
	 * @param value
	 *            Long値
	 * @return 変換後のオブジェクト
	 */
	public static Object longToObject(String typeName, Long value) {
		try {
			return longToObject(Class.forName(typeName), value);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Date値をtypeNameで指定されたクラスのオブジェクトに変換する。<br/>
	 * <strong>※クラスにはDate値を単一のパラメータとするコンストラクタが定義されていなければならない。未定義の場合はnullが返される。</strong>
	 *
	 * @param typeName
	 *            変換後のクラス
	 * @param value
	 *            Date値
	 * @return 変換後のオブジェクト
	 */
	public static Object dateToObject(String typeName, java.util.Date value) {
		try {
			return dateToObject(Class.forName(typeName), value);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Float値をtypeNameで指定されたクラスのオブジェクトに変換する。<br/>
	 * <strong>※クラスにはFloat値を単一のパラメータとするコンストラクタが定義されていなければならない。未定義の場合はnullが返される。</strong>
	 *
	 * @param typeName
	 *            変換後のクラス
	 * @param value
	 *            Float値
	 * @return 変換後のオブジェクト
	 */
	public static Object floatToObject(String typeName, Float value) {
		try {
			return floatToObject(Class.forName(typeName), value);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Double値をtypeNameで指定されたクラスのオブジェクトに変換する。<br/>
	 * <strong>※クラスにはDouble値を単一のパラメータとするコンストラクタが定義されていなければならない。未定義の場合はnullが返される。</strong>
	 *
	 * @param typeName
	 *            変換後のクラス
	 * @param value
	 *            Double値
	 * @return 変換後のオブジェクト
	 */
	public static Object doubleToObject(String typeName, Double value) {
		try {
			return doubleToObject(Class.forName(typeName), value);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 文字列値をtypeNameで指定されたクラスのオブジェクトに変換する。<br/>
	 * <strong>※クラスには文字列を単一のパラメータとするコンストラクタが定義されていなければならない。未定義の場合はnullが返される。</strong>
	 *
	 * @param cls
	 *            変換後のクラス
	 * @param value
	 *            文字列値
	 * @return 変換後のオブジェクト
	 */
	public static Object strToObject(Class<?> cls, String value) {
		try {
			Constructor<?> ctr = cls.getConstructor(java.lang.String.class);
			return ctr.newInstance(value);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Byte値をtypeNameで指定されたクラスのオブジェクトに変換する。<br/>
	 * <strong>※クラスにはByte値を単一のパラメータとするコンストラクタが定義されていなければならない。未定義の場合はnullが返される。</strong>
	 *
	 * @param cls
	 *            変換後のクラス
	 * @param value
	 *            Byte値
	 * @return 変換後のオブジェクト
	 */
	public static Object byteToObject(Class<?> cls, Byte value) {
		try {
			Constructor<?> ctr = cls.getConstructor(java.lang.Byte.class);
			return ctr.newInstance(value);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Short値をtypeNameで指定されたクラスのオブジェクトに変換する。<br/>
	 * <strong>※クラスにはShort値を単一のパラメータとするコンストラクタが定義されていなければならない。未定義の場合はnullが返される。</strong>
	 *
	 * @param cls
	 *            変換後のクラス
	 * @param value
	 *            Short値
	 * @return 変換後のオブジェクト
	 */
	public static Object shortToObject(Class<?> cls, Short value) {
		try {
			Constructor<?> ctr = cls.getConstructor(java.lang.Short.class);
			return ctr.newInstance(value);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Integer値をtypeNameで指定されたクラスのオブジェクトに変換する。<br/>
	 * <strong>※クラスにはInteger値を単一のパラメータとするコンストラクタが定義されていなければならない。未定義の場合はnullが返される。</strong>
	 *
	 * @param cls
	 *            変換後のクラス
	 * @param value
	 *            Integer値
	 * @return 変換後のオブジェクト
	 */
	public static Object intToObject(Class<?> cls, Integer value) {
		try {
			Constructor<?> ctr = cls.getConstructor(java.lang.Integer.class);
			return ctr.newInstance(value);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Long値をtypeNameで指定されたクラスのオブジェクトに変換する。<br/>
	 * <strong>※クラスにはLong値を単一のパラメータとするコンストラクタが定義されていなければならない。未定義の場合はnullが返される。</strong>
	 *
	 * @param cls
	 *            変換後のクラス
	 * @param value
	 *            Long値
	 * @return 変換後のオブジェクト
	 */
	public static Object longToObject(Class<?> cls, Long value) {
		try {
			Constructor<?> ctr = cls.getConstructor(java.lang.Long.class);
			return ctr.newInstance(value);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Date値をtypeNameで指定されたクラスのオブジェクトに変換する。<br/>
	 * <strong>※クラスにはDate値を単一のパラメータとするコンストラクタが定義されていなければならない。未定義の場合はnullが返される。</strong>
	 *
	 * @param cls
	 *            変換後のクラス
	 * @param value
	 *            Date値
	 * @return 変換後のオブジェクト
	 */
	public static Object dateToObject(Class<?> cls, java.util.Date value) {
		try {
			Constructor<?> ctr = cls.getConstructor(java.util.Date.class);
			return ctr.newInstance(value);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Float値をtypeNameで指定されたクラスのオブジェクトに変換する。<br/>
	 * <strong>※クラスにはFloat値を単一のパラメータとするコンストラクタが定義されていなければならない。未定義の場合はnullが返される。</strong>
	 *
	 * @param cls
	 *            変換後のクラス
	 * @param value
	 *            Float値
	 * @return 変換後のオブジェクト
	 */
	public static Object floatToObject(Class<?> cls, Float value) {
		try {
			Constructor<?> ctr = cls.getConstructor(java.lang.Float.class);
			return ctr.newInstance(value);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Double値をtypeNameで指定されたクラスのオブジェクトに変換する。<br/>
	 * <strong>※クラスにはDouble値を単一のパラメータとするコンストラクタが定義されていなければならない。未定義の場合はnullが返される。</strong>
	 *
	 * @param cls
	 *            変換後のクラス
	 * @param value
	 *            Double値
	 * @return 変換後のオブジェクト
	 */
	public static Object doubleToObject(Class<?> cls, Double value) {
		try {
			Constructor<?> ctr = cls.getConstructor(java.lang.Double.class);
			return ctr.newInstance(value);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ObjectをIntegerに変換して返す。 下記タイプを変換対象とする。それ以外はnullを返している。<br/>
	 * java.lang.Integer、 java.lang.String、 java.lang.Short、 java.lang.Long、 java.lang.Byte、 java.lang.Float、 java.lang.Double、 java.lang.Boolean、 java.math.BigDecimal、 java.math.BigInteger
	 * <ul>
	 * <li>Stringの場合、変換できない場合はNumberFormatExceptionが発生する。</li>
	 * <li>Booleanの場合、trueは1、falseは0に変換される。</li>
	 * <li>浮動小数、あるいはIntegerの範囲を超える数の場合、Integerに変換された値になる。（変換ロジックは各タイプのintValueメソッドのロジックを参照）</li>
	 * </ul>
	 *
	 * @param value
	 * @return
	 */
	public static Integer toInteger(Object value) {

		if (value == null) {
			return null;
		}

		if (value instanceof java.lang.Integer) {
			return (Integer) value;

		} else if (value instanceof java.lang.String) {
			return Integer.valueOf((String) value);

		} else if (value instanceof java.lang.Short) {
			return ((Short) value).intValue();

		} else if (value instanceof java.lang.Long) {
			return ((Long) value).intValue();

		} else if (value instanceof java.lang.Byte) {
			return ((Byte) value).intValue();

		} else if (value instanceof java.lang.Float) {
			return ((Float) value).intValue();

		} else if (value instanceof java.lang.Double) {
			return ((Double) value).intValue();

		} else if (value instanceof java.lang.Boolean) {
			return fromBooleanToInt((Boolean) value);

		} else if (value instanceof java.math.BigDecimal) {
			return ((BigDecimal) value).intValue();

		} else if (value instanceof java.math.BigInteger) {
			return ((BigInteger) value).intValue();

		}

		return null;
	}

	/**
	 * ObjectをLongに変換して返す。 下記タイプを変換対象とする。それ以外はnullを返している。<br/>
	 * java.lang.Integer、 java.lang.String、 java.lang.Short、 java.lang.Long、 java.lang.Byte、 java.lang.Float、 java.lang.Double、 java.lang.Boolean、 java.math.BigDecimal、 java.math.BigInteger
	 * <ul>
	 * <li>Stringの場合、変換できない場合はNumberFormatExceptionが発生する。</li>
	 * <li>Booleanの場合、trueは1、falseは0に変換される。</li>
	 * <li>浮動小数、あるいはLongの範囲を超える数の場合、Longに変換された値になる。（変換ロジックは各タイプのlongValueメソッドのロジックを参照）</li>
	 * </ul>
	 *
	 * @param value
	 * @return
	 */
	public static Long toLong(Object value) {
		if (value == null) {
			return null;
		}

		if (value instanceof java.util.Date) {
			return ((java.util.Date) value).getTime();

		} else if (value instanceof java.lang.Float) {
			return ((Float) value).longValue();

		} else if (value instanceof java.lang.Double) {
			return ((Double) value).longValue();

		} else if (value instanceof java.math.BigDecimal) {
			return ((BigDecimal) value).longValue();

		} else if (value instanceof java.math.BigInteger) {
			return ((BigInteger) value).longValue();
		}

		return toInteger(value).longValue();
	}

	/**
	 * 文字列化して返す。（文字列化は各タイプのtoStringメソッドのロジックを参照）<br/>
	 * ※valueがnullでも例外は発生しない。nullの場合はnullを返す。
	 *
	 * @param value
	 * @return
	 */
	public static String toStr(Object value) {
		if (value == null) {
			return null;
		}
		return value.toString();
	}
}
