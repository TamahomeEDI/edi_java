package jp.co.keepalive.springbootfw.util.lang;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * コピー対象となるフィールドに付加するアノテーション
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface CopyField {
	/** コピー先フィールド名 */
	String targetFieldName() default "";

	/** コピー先フィールドのタイプ */
	Class<?> targetClass() default java.lang.Void.class;

	/** コピー先フィールドがList<?>タイプの場合の要素のクラス */
	Class<?> listElementClass() default java.lang.Object.class;

	/** コピー対象外とするかどうか */
	boolean outOfTarget() default false;
}
