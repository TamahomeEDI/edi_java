package jp.co.edi_java.app.util.sap;

/*
 * SAPパラメータID定義クラス
 */
public class SapApiConsts {

	//パラメータ名
	public static String PARAMS_KEY_T_I_01001 = "T_I_01001";
	public static String PARAMS_KEY_T_I_03001 = "T_I_03001";
	public static String PARAMS_KEY_T_I_03002 = "T_I_03002";
	public static String PARAMS_KEY_T_I_03003 = "T_I_03003";
	public static String PARAMS_KEY_T_E_01002 = "T_E_01002";
	public static String PARAMS_KEY_T_E_01003 = "T_E_01003";
	public static String PARAMS_KEY_T_E_01004 = "T_E_01004";
	public static String PARAMS_KEY_T_IE_03001 = "T_IE_03001";
	public static String PARAMS_KEY_T_IE_03002 = "T_IE_03002";
	public static String PARAMS_KEY_T_IE_03003 = "T_IE_03003";

	//発注検索（工事一覧）
	//public static String PARAMS_KEY_T_I_01001 = "T_I_01001";
	//public static String PARAMS_KEY_T_E_01002 = "T_E_01002";
	public static String PARAMS_KEY_T_IE_01003 = "T_IE_01003";
	//発注検索（工事単位）
	public static String PARAMS_KEY_T_IE_02001 = "T_IE_02001";
	public static String PARAMS_KEY_T_E_02002 = "T_E_02002";
	public static String PARAMS_KEY_T_E_02003 = "T_E_02003";
	public static String PARAMS_KEY_T_IE_02004 = "T_IE_02004";
	//発注検索（POSID単位）
	//public static String PARAMS_KEY_T_IE_03001 = "T_IE_03001";
	public static String PARAMS_KEY_T_E_03002 = "T_E_03002";
	public static String PARAMS_KEY_T_E_03003 = "T_E_03003";
	public static String PARAMS_KEY_T_E_03004 = "T_E_03004";

	//請書未受領検索
	public static String PARAMS_KEY_T_I_04001 = "T_I_04001";
	public static String PARAMS_KEY_T_E_04002 = "T_E_04002";
	public static String PARAMS_KEY_T_IE_04003 = "T_IE_04003";
	//請書受領更新
	public static String PARAMS_KEY_T_I_09001 = "T_I_09001";
	public static String PARAMS_KEY_T_IE_09002 = "T_IE_09002";


	/**
	 * INPUT
	 */
	//項目ID
	public static String PARAMS_ID_ZJUSNM = "ZJUSNM";		//実ユーザ（社員コード）
	public static String PARAMS_ID_ZWKFNO = "ZWKFNO";		//ダミーコード？

	public static String PARAMS_ID_ZSCRID = "ZSCRID";		//画面ID
	public static String PARAMS_ID_ZAPLID = "ZAPLID";		//アプリケーションID
	public static String PARAMS_ID_ZPRCTR = "ZPRCTR";		//支店コード
	public static String PARAMS_ID_EBELN = "EBELN";			//購買伝票番号

	public static String PARAMS_ID_BUKRS = "BUKRS";			//会社コード
	public static String PARAMS_ID_ZBSDKY = "ZBSDKY";		//支店コード
	public static String PARAMS_ID_ZBSDK1 = "ZBSDK1";		//購買伝票番号
	public static String PARAMS_ID_ZBSDK2 = "ZBSDK2";		//レコード登録日
	public static String PARAMS_ID_ZBSDK3 = "ZBSDK3";		//連番
	public static String PARAMS_ID_ZBSDK4 = "ZBSDK4";		//受入日付
	public static String PARAMS_ID_AEDAT = "AEDAT";			//対象の最終変更日
	public static String PARAMS_ID_AEZEIT = "AEZEIT";		//変更時刻

	public static String PARAMS_ID_ZDELFLG = "ZDELFLG";		//削除フラグ
	public static String PARAMS_ID_ZTRKMD = "ZTRKMD";		//取込日付
	public static String PARAMS_ID_ZSKNNO = "ZSKNNO";		//検収管理番号

	public static String PARAMS_ID_ZSTFLG = "ZSTFLG";		//選択フラグ
	public static String PARAMS_ID_LIFNR = "LIFNR";			//勘定コード
	public static String PARAMS_ID_RFPNT = "RFPNT";			//参照ポイント
	public static String PARAMS_ID_ZHTKGK = "ZHTKGK";		//発注金額
	public static String PARAMS_ID_ZHTZDK = "ZHTZDK";		//発注残高
	public static String PARAMS_ID_SEBELN = "SEBELN";		//購買伝票番号
	public static String PARAMS_ID_ZHZZDK = "ZHZZDK";		//発注税額残高
	public static String PARAMS_ID_ZPSPNR = "ZPSPNR";		//作業詳細項目要素

	//検索
	public static String PARAMS_ID_ZJTHCD = "ZJTHCD";		//地区本部コード
	public static String PARAMS_ID_ZSKSCD = "ZSKSCD";		//支店コード
	public static String PARAMS_ID_ZWRKCD = "ZWRKCD";		//工事コード
	public static String PARAMS_ID_ZWRKST = "ZWRKST";		//工事状況
	public static String PARAMS_ID_ZGYSCD = "ZGYSCD";		//業者コード
	public static String PARAMS_ID_ZPSMST = "ZPSMST";		//進捗
	public static String PARAMS_ID_ZSMKNO = "ZSMKNO";		//細目工種コード
	public static String PARAMS_ID_ZHTSTA = "ZHTSTA";		//納入完了フラグ
	public static String PARAMS_ID_ZTKSKBN = "ZWRKST";		//取消区分
	public static String PARAMS_ID_ZKMTNM = "ZWRKST";		//工務担当者コード
	public static String PARAMS_ID_ZCKJSD_F = "ZCKJSD_F";	//着工実績日from
	public static String PARAMS_ID_ZCKJSD_T = "ZCKJSD_T";	//着工実績日to
	public static String PARAMS_ID_ZSKJSD_F = "ZSKJSD_F";	//竣工実績日from
	public static String PARAMS_ID_ZSKJSD_T = "ZSKJSD_T";	//俊子実績日to
	public static String PARAMS_ID_ZHWJSD_F = "ZHWJSD_F";	//引渡実績日from
	public static String PARAMS_ID_ZHWJSD_T = "ZHWJSD_T";	//引渡実績日to
	public static String PARAMS_ID_ZORDDT_F = "ZORDDT_F";	//発注日from
	public static String PARAMS_ID_ZORDDT_T = "ZORDDT_T";	//発注日to
	public static String PARAMS_ID_ZSKHKD_F = "ZSKHKD_F";	//請書発行日from
	public static String PARAMS_ID_ZSKHKD_T = "ZSKHKD_T";	//請書発行日to
	public static String PARAMS_ID_ZNOUHD_F = "ZNOUHD_F";	//納品出来高報告日from
	public static String PARAMS_ID_ZNOUHD_T = "ZNOUHD_T";	//納品出来高報告日to
	public static String PARAMS_ID_ZSNKRD_F = "ZSNKRD_F";	//承認完了日from
	public static String PARAMS_ID_ZSNKRD_T = "ZSNKRD_T";	//承認完了日to
	public static String PARAMS_ID_ZTRKID_F = "ZTRKID_F";	//取消依頼日from
	public static String PARAMS_ID_ZTRKID_T = "ZTRKID_T";	//取消依頼日to
	public static String PARAMS_ID_ZTRKGD_F = "ZTRKGD_F";	//取消合意日from
	public static String PARAMS_ID_ZTRKGD_T = "ZTRKGD_T";	//取消合意日to

	public static String PARAMS_ID_ZNKFLG = "ZNKFLG";		//受入状況
	public static String PARAMS_ID_ZGYSNM = "ZGYSNM";		//業者名
	public static String PARAMS_ID_ZSMKSY = "ZSMKSY";		//細目工種名
	public static String PARAMS_ID_ZHTKZG = "ZHTKZG";		//発注金額税額
	public static String PARAMS_ID_ZORDDT = "ZORDDT";		//発注日
	public static String PARAMS_ID_ZUKKGK = "ZUKKGK";		//受入金額
	public static String PARAMS_ID_ZUKKZG = "ZUKKZG";		//受入金額税額

	public static String PARAMS_ID_MAKTX = "MAKTX";			//品目
	public static String PARAMS_ID_ZHTTNK = "ZHTTNK";		//単価
	public static String PARAMS_ID_ZMENGE = "ZMENGE";		//数量
	public static String PARAMS_ID_ZTANIN = "ZTANIN";		//単位
	public static String PARAMS_ID_ZHTKZTG = "ZHTKZTG";		//消費税額（合計）
	public static String PARAMS_ID_ZUKEMG = "ZUKEMG";		//受入数量
	public static String PARAMS_ID_ZUKEKN = "ZUKEKN";		//受入金額
	public static String PARAMS_ID_ZZANMG = "ZZANMG";		//残数量
	public static String PARAMS_ID_ZZANKN = "ZZANKN";		//残金額

	//発注日連携
	public static String PARAMS_ID_ZHTYDT = "ZHTYDT";		//残金額


	/**
	 * OUTPUT
	 */
	public static String PARAMS_ID_ZRESULT = "ZRESULT";		//処理結果
	public static String PARAMS_ID_ZMESSAGE = "ZMESSAGE";	//メッセージ
	public static String PARAMS_ID_ZCSETF1 = "ZCSETF1";		//エラー項目ID１
	public static String PARAMS_ID_ZCSETF2 = "ZCSETF2";		//エラー項目ID２
	public static String PARAMS_ID_ZCSETF3 = "ZCSETF3";		//エラー項目ID３
	public static String PARAMS_ID_ZCSETF4 = "ZCSETF4";		//エラー項目ID４
	public static String PARAMS_ID_ZCSETF5 = "ZCSETF5";		//エラー項目ID５
	public static String PARAMS_ID_ZLOWNO = "ZLOWNO";		//行数
	public static String PARAMS_ID_ZMAXCNT = "ZMAXCNT";		//取得制限件数
	public static String PARAMS_ID_ZKSFLG = "ZKSFLG";		//件数取得フラグ
	public static String PARAMS_ID_ZDTCNT = "ZDTCNT";		//取得データ件数
	public static String PARAMS_ID_ZSKFLG = "ZSKFLG";		//精算完了フラグ
	public static String PARAMS_ID_ZHCSSF = "ZHCSSF";		//発注精算フラグ


	/**
	 * OUTPUT_NODE名
	 */
	//共通ノード名
	public static String NODE_NAME_XML = "xml";				//親階層
	public static String NODE_NAME_T_ERROR = "T_ERROR";		//処理結果


	/**
	 * 実値
	 */
	public static String ZRESULT_VALUE_SUCCESS = "S";		//正常終了

	/** 2019/07/12 追加 */
	// ■■■■■■ 発注検索（工事一覧）Z_JRI15P203_0101_GET_CNSTCT_CL
	//T_I_01001
	public static String PARAMS_ID_ZSTATS = "ZSTATS";		//	状態
	//public static String PARAMS_ID_ZCMNMK;				//	お客様名（カナ）
	//public static String PARAMS_ID_ZCMNMJ;				//	お客様名（漢字）
	//public static String PARAMS_ID_ZWRKCD;				//	工事コード（＝WBS番号）
	//public static String PARAMS_ID_ZPRJNM;				//	工事名称
	public static String PARAMS_ID_ZSKSTN = "ZSKSTN";		//	施工支店
	public static String PARAMS_ID_ZJTSTN = "ZJTSTN";		//	受注支店
	//public static String PARAMS_ID_ZKMTCD;				//	工務担当
	//public static String PARAMS_ID_ZSLCCD;				//	営業担当
	public static String PARAMS_ID_ZWBSNO = "ZWBSNO";		//	WBS 要素
	//public static String PARAMS_ID_BUKRS;					//	会社コード
	//T_E_01002
	//public static String PARAMS_ID_BUKRS;					//	会社コード
	//public static String PARAMS_ID_ZWRKCD;				//	工事コード（＝WBS番号）
	//public static String PARAMS_ID_ZPRJNM;				//	工事名称
	public static String PARAMS_ID_ZCMNMJ = "ZCMNMJ";		//	契約者
	public static String PARAMS_ID_ZCMNMK = "ZCMNMK";		//	契約者フリガナ
	public static String PARAMS_ID_ZLNDJH = "ZLNDJH";		//	建築地住所
	public static String PARAMS_ID_ZSLCCD = "ZSLCCD";		//	営業担当
	public static String PARAMS_ID_ZKMTCD = "ZKMTCD";		//	工務担当
	public static String PARAMS_ID_ZCNSST = "ZCNSST";		//	工事ステータス
	//T_IE_01003
	//public static String PARAMS_ID_ZRESULT = "ZRESULT";	//	処理結果
	//public static String PARAMS_ID_ZMESSAGE = "ZMESSAGE";	//	メッセージ
	//public static String PARAMS_ID_ZCSETF1 = "ZCSETF1";	//	エラー項目ID
	//public static String PARAMS_ID_ZCSETF2 = "ZCSETF2";	//	エラー項目ID
	//public static String PARAMS_ID_ZCSETF3 = "ZCSETF3";	//	エラー項目ID
	//public static String PARAMS_ID_ZCSETF4 = "ZCSETF4";	//	エラー項目ID
	//public static String PARAMS_ID_ZCSETF5 = "ZCSETF5";	//	エラー項目ID
	//public static String PARAMS_ID_ZLOWNO = "ZLOWNO";		//	行数
	//public static String PARAMS_ID_ZMAXCNT = "ZMAXCNT";	//	取得制限件数
	//public static String PARAMS_ID_ZJUSNM = "ZJUSNM";		//	実ユーザ(=社員コード)	必須
	//public static String PARAMS_ID_ZKSFLG = "ZKSFLG";		//	件数取得フラグ
	//public static String PARAMS_ID_ZDTCNT = "ZDTCNT";		//	取得データ件数

	// ■■■■■■ 発注検索（工事単位）Z_JRI15P203_0102_GET_CNSTRT_VW

	//T_IE_02001
	//public static String PARAMS_ID_BUKRS;					//	会社コード	必須
	//public static String PARAMS_ID_ZWRKCD;				//	工事コード（＝WBS番号）	必須
	public static String PARAMS_ID_ZPRJNM = "ZPRJNM";		//	工事名称
	//public static String PARAMS_ID_ZCMNMJ;				//	契約者
	//public static String PARAMS_ID_ZCMNMK;				//	契約者フリガナ
	//public static String PARAMS_ID_ZLNDJH;				//	建築地住所
	//public static String PARAMS_ID_ZSLCCD;				//	営業担当
	//public static String PARAMS_ID_ZKMTCD;				//	工務担当
	//public static String PARAMS_ID_ZCNSST;				//	工事ステータス
	//T_E_02002
	//public static String PARAMS_ID_ZWRKCD;				//	工事コード（＝WBS番号）
	//public static String PARAMS_ID_ZCMNMJ;				//	契約者
	//public static String PARAMS_ID_ZLNDJH;				//	住所
	public static String PARAMS_ID_ZWBSNM = "ZWBSNM";		//	工事名称
	//public static String PARAMS_ID_ZSLCCD;				//	営業担当
	//public static String PARAMS_ID_ZKMTCD;				//	工務担当
	public static String PARAMS_ID_ZUOIGK = "ZUOIGK";		//	請負合計
	public static String PARAMS_ID_ZHTYGK = "ZHTYGK";		//	発注合計
	//public static String PARAMS_ID_ZRIEKI;				//	利益
	public static String PARAMS_ID_ZRIEKR = "ZRIEKR";		//	利益率
	//T_E_02003
	//public static String PARAMS_ID_BUKRS;					//	会社コード
	public static String PARAMS_ID_ZHTYKB = "ZHTYKB";		//	発注区分
	public static String PARAMS_ID_ZHTYNO = "ZHTYNO";		//	発注No
	public static String PARAMS_ID_ZUOIKG = "ZUOIKG";		//	請負金額
	public static String PARAMS_ID_ZHTYKG = "ZHTYKG";		//	発注金額(本体)
	public static String PARAMS_ID_ZRIEKI = "ZRIEKI";		//	利益額
	public static String PARAMS_ID_ZRIERT = "ZRIERT";		//	利益率
	public static String PARAMS_ID_ZHTYLD = "ZHTYLD";		//	最終発注日
	public static String PARAMS_ID_ZKSIDT = "ZKSIDT";		//	決裁日
	public static String PARAMS_ID_ZYOSDT = "ZYOSDT";		//	予算作成日
	public static String PARAMS_ID_ZYOSPS = "ZYOSPS";		//	予算作成者
	public static String PARAMS_ID_POSID = "POSID";			//	WBS要素
	public static String PARAMS_ID_ZHTYKG2 = "ZHTYKG2";		//	発注金額
	//public static String PARAMS_ID_ZWRKCD;				//	工事コード(=WBS番号)
	//public static String PARAMS_ID_ZPRJNM;				//	工事名
	//T_IE_02004
	//public static String PARAMS_ID_ZRESULT;				//	処理結果
	//public static String PARAMS_ID_ZMESSAGE;				//	メッセージ
	//public static String PARAMS_ID_ZCSETF1;				//	エラー項目ID
	//public static String PARAMS_ID_ZCSETF2;				//	エラー項目ID
	//public static String PARAMS_ID_ZCSETF3;				//	エラー項目ID
	//public static String PARAMS_ID_ZCSETF4;				//	エラー項目ID
	//public static String PARAMS_ID_ZCSETF5;				//	エラー項目ID
	//public static String PARAMS_ID_ZLOWNO;				//	行数
	//public static String PARAMS_ID_ZMAXCNT;				//	取得制限件数
	//public static String PARAMS_ID_ZJUSNM;				//	実ユーザ(=社員コード)
	//public static String PARAMS_ID_ZKSFLG;				//	件数取得フラグ
	//public static String PARAMS_ID_ZDTCNT;				//	取得データ件数

	// ■■■■■■ 請書未受領検索 Z_JRI15P203_1004_SEL_UKESHO

	// T_I_04001
	//public static String PARAMS_ID_ZFAXJU;				//	請書FAX未受領選択フラグ
	//public static String PARAMS_ID_ZGENJU;				//	請書原本未受領選択フラグ
	//public static String PARAMS_ID_POSID;					//	工事コード(=WBS番号)
	//public static String PARAMS_ID_ZPRJNM;				//	工事名
	//public static String PARAMS_ID_VORNR;					//	細目工種コード
	//public static String PARAMS_ID_LIFNR;					//	業者コード
	//public static String PARAMS_ID_ZSKSCD;				//	施工支店コード
	//public static String PARAMS_ID_ZKMTCD;				//	工務担当者コード
	public static String PARAMS_ID_BEDAT_FROM = "BEDAT_FROM";//	発注日_FROM
	public static String PARAMS_ID_BEDAT_TO = "BEDAT_TO";	//	発注日_TO
	public static String PARAMS_ID_ZSKKCD = "ZSKKCD";		//	利益センタ責任者
	// T_E_04002
	//public static String PARAMS_ID_BUKRS;					//	会社コード
	public static String PARAMS_ID_AUFNR = "AUFNR";			//	指図番号
	public static String PARAMS_ID_ZVORNR = "ZVORNR";		//	作業/活動番号
	public static String PARAMS_ID_ZFAXJU = "ZFAXJU";		//	請書FAX受領済みフラグ
	public static String PARAMS_ID_ZGENJU = "ZGENJU";		//	請書原本受領済みフラグ
	//public static String PARAMS_ID_POSID;					//	工事コード(=WBS番号)
	//public static String PARAMS_ID_ZPRJNM;				//	工事名
	public static String PARAMS_ID_VORNR = "VORNR";			//	細目工種コード
	public static String PARAMS_ID_KTEXT = "KTEXT";			//	細目工種名称
	//public static String PARAMS_ID_LIFNR;					//	業者コード
	public static String PARAMS_ID_NAME1 = "NAME1";			//	業者名
	public static String PARAMS_ID_TELF1 = "TELF1";			//	電話番号 1
	public static String PARAMS_ID_J_1KFREPRE = "J_1KFREPRE";//	代表者氏名
	public static String PARAMS_ID_BEDAT = "BEDAT";			//	発注日
	//public static String PARAMS_ID_EBELN;					//	購買伝票番号
	//public static String PARAMS_ID_ZPSPNR;				//	WBS要素コード（内部）
	public static String PARAMS_ID_ZSEQNO = "ZSEQNO";		//	枝番号
	//public static String PARAMS_ID_ZSKSCD;				//	施工支店コード
	public static String PARAMS_ID_ZSKSNM = "ZSKSNM";		//	施工支店名称
	//public static String PARAMS_ID_AEDAT;					//	最終変更日付
	//public static String PARAMS_ID_AEZEIT;				//	最終変更時間
	// T_IE_04003
	//public static String PARAMS_ID_ZRESULT;				//	処理結果
	//public static String PARAMS_ID_ZMESSAGE;				//	メッセージ
	//public static String PARAMS_ID_ZCSETF1;				//	エラー項目ID
	//public static String PARAMS_ID_ZCSETF2;				//	エラー項目ID
	//public static String PARAMS_ID_ZCSETF3;				//	エラー項目ID
	//public static String PARAMS_ID_ZCSETF4;				//	エラー項目ID
	//public static String PARAMS_ID_ZCSETF5;				//	エラー項目ID
	//public static String PARAMS_ID_ZLOWNO;				//	行数
	//public static String PARAMS_ID_ZMAXCNT;				//	取得制限件数
	//public static String PARAMS_ID_ZJUSNM;				//	実ユーザ(=社員コード)
	//public static String PARAMS_ID_ZKSFLG;				//	エラー項目ID
	//public static String PARAMS_ID_ZDTCNT;				//	取得データ件数

	// ■■■■■■ 請書受領更新 Z_JRI15P203_1009_UPD_JYURYOU

	// T_I_09001
//	public static String PARAMS_ID_BUKRS;					//	会社コード
//	public static String PARAMS_ID_AUFNR;					//	指図番号
//	public static String PARAMS_ID_ZVORNR;					//	作業/活動番号
//	public static String PARAMS_ID_ZFAXJU;					//	請書FAX受領済みフラグ
//	public static String PARAMS_ID_ZGENJU;					//	請書原本受領済みフラグ
//	public static String PARAMS_ID_POSID;					//	工事コード(=WBS番号)
//	public static String PARAMS_ID_ZPRJNM;					//	工事名
//	public static String PARAMS_ID_VORNR;					//	細目工種コード
//	public static String PARAMS_ID_KTEXT;					//	細目工種名称
//	public static String PARAMS_ID_LIFNR;					//	業者コード
//	public static String PARAMS_ID_NAME1;					//	業者名
//	public static String PARAMS_ID_TELF1;					//	電話番号 1
//	public static String PARAMS_ID_J_1KFREPRE;				//	代表者氏名
//	public static String PARAMS_ID_BEDAT;					//	発注日
//	public static String PARAMS_ID_EBELN;					//	購買伝票番号	必須
//	public static String PARAMS_ID_ZPSPNR;					//	WBS要素コード（内部）
//	public static String PARAMS_ID_ZSEQNO;					//	枝番号
//	public static String PARAMS_ID_ZSKSCD;					//	施工支店コード
//	public static String PARAMS_ID_ZSKSNM;					//	施工支店名称
//	public static String PARAMS_ID_AEDAT;					//	最終変更日付	必須
//	public static String PARAMS_ID_AEZEIT;					//	最終変更時間	必須
	// T_IE_09002
//	public static String PARAMS_ID_ZRESULT;					//	処理結果
//	public static String PARAMS_ID_ZMESSAGE;				//	メッセージ
//	public static String PARAMS_ID_ZCSETF1;					//	エラー項目ID
//	public static String PARAMS_ID_ZCSETF2;					//	エラー項目ID
//	public static String PARAMS_ID_ZCSETF3;					//	エラー項目ID
//	public static String PARAMS_ID_ZCSETF4;					//	エラー項目ID
//	public static String PARAMS_ID_ZCSETF5;					//	エラー項目ID
//	public static String PARAMS_ID_ZLOWNO;					//	行数
//	public static String PARAMS_ID_ZMAXCNT;					//	取得制限件数
//	public static String PARAMS_ID_ZJUSNM;					//	実ユーザ(=社員コード)
//	public static String PARAMS_ID_ZKSFLG;					//	件数取得フラグ
//	public static String PARAMS_ID_ZDTCNT;					//	取得データ件数


}