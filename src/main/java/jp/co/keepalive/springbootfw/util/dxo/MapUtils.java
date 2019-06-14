/**************************************************************
 * システム名		:
 * サブシステム名	:
 * モジュール名		:MapUtils.java
 *
 * 修正日			:修正者			:修正理由
 * ------------------------------------------
 * 2007/9/3			:成田　敦		:新規作成
 *
 **************************************************************/
package jp.co.keepalive.springbootfw.util.dxo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <b>[概要]</b><br>
 * <p>
 * Map を扱うためのユーティリティクラスです.
 * </p>
 *
 * @author Atsushi Narita
 */
public class MapUtils {

	private MapUtils(){
		// cannot instanciate
	}

	/**
	 * <p>
	 * MapのKeySetが同一であるかを検査します.<br>
	 * ソースとなるMapに含まれるKeyが検査対象Mapにすべて含まれていれば真を返却します.<br>
	 * 検査対象MapがソースとなるMapのキー全部＋α個別のキーを持つ場合も真とします.<br>
	 * 検査対象MapがソースとなるMapのキーをひとつでも欠いていた場合、偽を返却します.
	 * </p>
	 *
	 * @param src <code>検査するMap</code>
	 * @param dest <code>検査されるMap</code>
	 * @return 同一である場合:True, ひとつでも一致しない場合:false
	 */
	public static boolean equalsKeySet(Map src, Map dest){
		int equalsCount = 0;
		for(Iterator srcite = src.keySet().iterator(); srcite.hasNext();){
			String srckey = srcite.next().toString();
			for(Iterator destite = dest.keySet().iterator(); destite.hasNext();){
				String destkey = destite.next().toString();
				if(srckey.equals(destkey)){
					equalsCount++;
					break;
				}
			}
		}
		if(src.size() == equalsCount){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * <p>
	 * マップを複製します。複製されたマップは HashMap で返されます。
	 * </p>
	 *
	 * @param map 複製元のMap
	 * @return 複製されたMap
	 */
	@SuppressWarnings("unchecked")
	public static Map clone(Map map){
		Map ret = new HashMap();
		ret.putAll(map);
		return ret;
	}

}
