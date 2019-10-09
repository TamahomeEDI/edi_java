/**************************************************************
 * システム名		:
 * サブシステム名	:
 * モジュール名		:DtoAssembler.java
 *
 * 修正日			:修正者			:修正理由
 * ------------------------------------------
 * 2007/6/14		:成田　敦		:新規作成
 *
 **************************************************************/
package jp.co.keepalive.springbootfw.util.dxo;

import java.lang.reflect.Field;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;

import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;

/**
 * <b>[概要]</b><br>
 * <p>
 * Map 型 DTO（Data Transfer Object） と ValueObject（主にJavaBeans） の相互変換を行うクラスです。
 * </p>
 * <p>
 * Webサービスとデータの送受信を行う場合、送信側はデータを Map 型の DTO で送信する必要があります。
 * サービスでは、受け取った Map 型 DTO を ValueObject に復元して使用します。
 * このクラスは、その相互変換の機能を提供します。
 * </p>
 * <p>
 * アプリケーションで DTO を作成する場合、DtoAssembler を直接は使用しません。
 * DTO は {@link DtoCreator} を使用して作成を行います。
 * このクラスは DtoCreator を介して使用されます。
 * </p>
 *
 * @author Atsushi Narita
 */
public class DtoAssembler {

	private DtoAssembler() {
		super();
	}

	/**
     * <p>
     * 指定された ValueObject から Map型の DTO を作成します。<br/>
     * 指定された ValueObject が JavaBeans 形式でない場合、空の Map が返されます。
     * JavaBeans の transient または final フィールドは DTO に転記されません。
     * </p>
     *
     * @param vo ValueObject(JavaBeans形式)
     * @return Map 型 DTO
     */
	public static Map<String, Object> toDTO(Object vo) {
		if (!BeanUtils.isJavaBeans(vo)) {
			return new HashMap<String, Object>();
		}
		Map<String, Object> retmap = new HashMap<String, Object>();
		Field[] fields = BeanUtils.getBeanFieldsExcludingTransientOrFinal(vo.getClass());
		for (int i = 0, s = fields.length; i < s; i++) {
			copyDTOProperty(fields[i].getName(), vo, retmap);
		}
		return retmap;
	}

	/**
     * <p>
     * DTO に、指定された ValueObject のプロパティ名と値のセットを格納します。
     * DTO に転記される値は {@link #convertProperty} によって変換された値になります。
     * </p>
     *
     * @param propertyName
     *            プロパティ名
     * @param vo
     *            ValueObject
     * @param dto
     *            Dto
     */
	public static void copyDTOProperty(String propertyName, Object vo, Map<String, Object> dto) {
		Object val = null;
		val = BeanUtils.getPropertyValueIgnoreException(vo, propertyName);
		val = convertProperty(val);
		if (BeanUtils.isJavaBeans(val)) {
			val = new HashMap<String, Object>(toDTO(val));
			dto.put(propertyName, val);
		} else {
			dto.put(propertyName, val);
		}
	}

	/**
     * <p>
     * プロパティの値を DTO に設定可能な型に変換します。<br/>
     * 変換は以下のルールに基づき行われます。
     * </p>
     * <ul>
     * <li>List 型の場合、Object の配列に変換します。</li>
     * <li>java.util.Date 型の場合、ミリ秒（long 値）に変換します。</li>
     * </ul>
     *
     * @param value
     *            プロパティの値
     * @return 変換後のプロパティの値
     */
	public static Object convertProperty(Object value) {
		return convertDtoType(value);
	}

	/**
	 * <p>
	 * {@link #toValueObject} で復元された ValueObject が DTO から正確に復元されたかどうかを検査します。<br/>
	 * DTO のアイテムのキーと一致するプロパティ名を ValueObject が保持している場合は、復元されたと判断し、
	 * これを DTO の全てのアイテムに対して行います。
	 * その結果、DTO の全てのアイテムで一致した場合は true を返し、そうでない場合は false を返します。
	 * </p>
	 *
	 * @param vo ValueObject
	 * @param dto DTO
	 * @return 正確に復元されている:true, 情報が欠落している:false
	 */
	public static boolean checkRestoredObject(Object vo, Map dto){
		Map stored = toDTO(vo);
		return MapUtils.equalsKeySet(dto, stored);
	}

	/**
     * <p>
     * 指定された Map型 DTO に格納されているアイテムの値を ValueObject に転記します。<br/>
     * DTO に存在していても、ValueObject にプロパティとして定義されていない項目は転記されません。
     * ValueObject に定義があり、DTO に存在しない項目は転記されません。<br/>
     * DTO から ValueObject の日付型の項目に転記を行う場合は以下のルールに基づき転記が行われます。
     * </p>
     * <ul>
     * <li>DTO の値が yyyy/MM/dd/hh/mm/ss/SSS 形式の文字列の場合、日付型に変換して転記します。</li>
     * <li>DTO の値がミリ秒( long 値)の場合、日付型に変換して転記します。</li>
     * <li>上記ルールに反する値を ValueObject の日付型の項目に転記しようとした場合、
     * {@link UnsupportedFormatException} がスローされます。</li>
     * </ul>
     * <p>
     * なお、ValueObject の項目が java.util.Date, java.sql.Date, java.sql.Time, java.sql.Timestamp 型の場合のみ日付型の転記が可能です。
     * </p>
     *
     * @param map
     *            DTO
     * @param obj
     *            転記元ValueObject
     * @return 転記後のValueObject
     */
	@SuppressWarnings("unchecked")
	public static Object toValueObject(Map dto, Object vo) {
		if (dto == null || vo == null) {
			return null;
		}
		Iterator ite = dto.keySet().iterator();
		while (ite.hasNext()) {
			String key = ite.next().toString();
			Object val = dto.get(key);
			try {
				Class destType = BeanUtils.getPropertyType(vo, key);
				if (destType == null) {
					continue;
				}
				val = convertDestType(destType, val);
				if (val == null && destType.isPrimitive()) {
					continue;
				}
				BeanUtils.setProperty(vo, key, val);
			} catch (CoreRuntimeException e) {
				if(e.getCause() instanceof NoSuchMethodException){
					continue;
				}
			}
		}
		return vo;
	}

	/**
	 * <p>
	 * 指定された値を DTO に設定可能な形式に変換します。
	 * </p>
	 *
	 * @param value 変換対象の値
	 * @return 変換後の値
	 */
	@SuppressWarnings("unchecked")
	private static Object convertDtoType(Object value) {
		if (value instanceof List) {
			List list = (List) value;
			return list.toArray(new Object[list.size()]);
		} else if (value instanceof Date) {
			Date date = (Date) value;
			return Long.valueOf(date.getTime());
		}
		return value;
	}

	/**
     * <p>
     * DTO の値を転記先の ValueObject の型に合わせて変換します。
     * <ol>
     * <li>type:BinaryData val:BinaryData = 無変換</li>
     * <li>type:byte[] val:BinaryData = byte[]に変換</li>
     * <li>type:Date val:String = Dateに変換</li>
     * <li>type:Map val:Map = 無変換</li>
     * <li>type:Object[] val:Object[] = 無変換</li>
     * <li>type:Map以外 val:Map = JavaBeansとみなしtypeの型に変換後、toValueObjectにかける</li>
     * <li>type:List val:Object[] = Listに変換</li>
     * <li>type:Object[] val:primitive[] = Object[]に変換</li>
     * <li>type:primitive[] val:Object[] = primitive[]に変換</li>
     * <li>type:primitive[] val:primitive[] = 無変換</li>
     * <li>type:String val:* = commons-beanutilsのConvertUtils#convert()定義に従う</li>
     * </ol>
     * </p>
     *
     * @param type
     *            転記先（ValueObject）の項目の型
     * @param value
     *            転記対象の DTO の値
     * @return 変換後の値
     */
	private static Object convertDestType(Class type, Object value) {
		Object retval = null;
		if (type == null || value == null) {
			return null;
		} else if (BinaryData.class.isAssignableFrom(type) && value instanceof BinaryData) {
			retval = value;
		} else if (byte[].class.isAssignableFrom(type) && value instanceof BinaryData) {
			retval = ((BinaryData) value).getBytes();
		} else if (Date.class.isAssignableFrom(type)) {
			retval = convertDate(type, value);
		} else if (Map.class.isAssignableFrom(type) && value instanceof Map) {
			retval = value;
		} else if (List.class.isAssignableFrom(type) && value instanceof List) {
			retval = value;
		} else if (Object[].class.isAssignableFrom(type) && value instanceof Object[]) {
			retval = value;
		} else if (!Map.class.isAssignableFrom(type) && value instanceof Map) {
			try {
				retval = toValueObject((Map) value, type.newInstance());
			} catch (Exception e) {
				throw new CoreRuntimeException(e);
			}
		} else if (List.class.isAssignableFrom(type) && value instanceof Object[]) {
			retval = Arrays.asList((Object[]) value);
		} else if (type.isArray() && value instanceof Object[]) {
			retval = ConvertUtils.convert(value);
		} else if (Object[].class.isAssignableFrom(type) && value.getClass().isArray()) {
			retval = ConvertUtils.convert(value);
		} else if (type.isArray() && value.getClass().isArray()) {
			retval = value;
		} else {
			retval = ConvertUtils.convert(value.toString(), type);
		}
		return retval;
	}

	/**
	 * <p>
	 * DTO の値を転記先の ValueObject の日付型に合わせて変換します。
	 * </p>
	 *
	 * @param type 転記先（ValueObject）の項目の日付型
	 * @param value 転記対象の DTO の値
	 * @return 変換後の値
	 */
	private static Object convertDate(Class<?> type, Object value) {
		long longval = 0;
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd/hh/mm/ss/SSS");
		try {
			if (value.toString().equals("")) {
				return null;
			}
			try {
				Long tmplong = new Long(value.toString());
				longval = tmplong.longValue();
			} catch (NumberFormatException e) {
				// ignore
			}
			if (longval == 0 && !value.toString().equals("0")) {
				longval = format.parse(value.toString()).getTime();
			}
		} catch (ParseException e) {
			throw new CoreRuntimeException(value.toString() + "を日付型に転記できませんでした", e);
		}
		if (type == Timestamp.class) {
			return new Timestamp(longval);
		} else if (type == Date.class) {
			return new Date(longval);
		} else if (type == Time.class) {
			return new Time(longval);
		} else if (type == java.sql.Date.class) {
			return new java.sql.Date(longval);
		}
		return null;
	}

}