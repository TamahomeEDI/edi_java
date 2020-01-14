package jp.co.edi_java.app.util.consts;

public class CommonConsts {

	//ユーザ区分
	public static String USER_KBN_STAFF = "1";
	public static String USER_KBN_CLERK = "2";
	public static String USER_KBN_MANAGER = "3";

	//受入判定区分
	public static String JUDGMENT_KBN_HOLD = "0";
	public static String JUDGMENT_KBN_APPROVAL = "1";
	public static String JUDGMENT_KBN_REMAND = "2";

	public static String RECEIPT_FLG_ON = "1";
	public static String RECEIPT_FLG_OFF = "0";

	public static String REMAND_FLG_ON = "1";
	public static String REMAND_FLG_OFF = "0";

	// 定数マスタ用キー 前回のJTM発注情報連携日
	public static String V_ORDER_LAST_GET_TIME = "V_ORDER_LAST_GET_TIME";
	// 定数マスタ用キー 排他制御により更新できなかった発注番号
	public static String V_ORDER_LOCK_FAIL_ORDER_NUMBERS = "V_ORDER_LOCK_FAIL_ORDER_NUMBERS";

	/*EDI登録図書コード*/
	public static String FILE_TOSHO_CODE = "09";

	/*EDI登録ファイルコード*/
	//見積・設計依頼
	public static String FILE_TYPE_ESTIMATE = "01";
	//図面・その他
	public static String FILE_TYPE_PLAN = "02";
	//発注・納品
	public static String FILE_TYPE_DELIVERY = "03";
	//検収
	public static String FILE_TYPE_INSPECTIONRECEIPT = "04";

	/*EDI登録番号*/
	/* 01:見積・設計依頼 */
	//見積・設計依頼書
	public static String FILE_NO_ESTIMATE_REQUEST = "01";
	//見積書
	public static String FILE_NO_ESTIMATE = "02";

	/* 02:図面・その他 */
	//現場案内図
	public static String FILE_NO_GENBA_ANNAIZU = "01";
	//配置図
	public static String FILE_NO_HAITIZU = "02";
	//平面図
	public static String FILE_NO_HEIMENZU = "03";
	//立面図
	public static String FILE_NO_RITUMENZU = "04";
	//矩計図
	public static String FILE_NO_KUKEIZU = "05";
	//基礎図（伏図）
	public static String FILE_NO_KISOZU = "06";
	//屋根伏図
	public static String FILE_NO_YANE_HUSEZU = "07";
	//軸組計算図
	public static String FILE_NO_JIKUGUMI_KEISANZU = "08";
	//プレカット図面
	public static String FILE_NO_PRECUT_ZUMEN = "09";
	//金物積算表
	public static String FILE_NO_KAMAMONO_SEKISANHYOU = "10";
	//住宅仕様確認書
	public static String FILE_NO_SIYOU_KAKUNINSYO = "11";
	//一部変更合意見積書
	public static String FILE_NO_ITIBU_HENKOU_GOUI_ESTIMATE = "12";

	/* 03:発注・納品 */
	//発注書
	public static String FILE_NO_ORDER = "01";
	//発注請書
	public static String FILE_NO_CONFIRMATION = "02";
	//納品書
	public static String FILE_NO_DELIVERY = "03";
	//出来高報告書
	public static String FILE_NO_WORK_REPORT = "04";
	//注取消合意書
	public static String FILE_NO_ORDER_CANCEL = "05";

	/* 04:検収 */
	//検収明細書
	public static String FILE_NO_INSPECTION_RECEIPT = "01";

	public static String OUTPUT_FILE_DIR = "/home/web/edi_php/tmp/downloadFile/";

}