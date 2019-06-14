package jp.co.keepalive.springbootfw.util.consts;

import java.util.HashMap;
import java.util.Map;

public class ResponseCode {

    public static Map<String, Object> errorResponseCodeSet = new HashMap<String, Object>();

    public static final String NORMAL_CODE_200 = "200";
    public static final String ERROR_CODE_400 = "400";
    public static final String ERROR_CODE_500 = "500";

    //該当なしエラー
    public static final String ERROR_CODE_600 = "600";
    public static final String ERROR_CODE_601 = "601";
    public static final String ERROR_CODE_610 = "610";
    public static final String ERROR_CODE_620 = "620";
    public static final String ERROR_CODE_621 = "621";
    public static final String ERROR_CODE_630 = "630";
    public static final String ERROR_CODE_640 = "640";
    public static final String ERROR_CODE_650 = "650";
    public static final String ERROR_CODE_651 = "651";
    public static final String ERROR_CODE_652 = "652";
    public static final String ERROR_CODE_660 = "660";
    public static final String ERROR_CODE_661 = "661";
    public static final String ERROR_CODE_662 = "662";
    public static final String ERROR_CODE_670 = "670";
    public static final String ERROR_CODE_680 = "680";
    public static final String ERROR_CODE_690 = "690";

    public static final String ERROR_CODE_700 = "700";
    public static final String ERROR_CODE_701 = "701";
    public static final String ERROR_CODE_702 = "702";
    public static final String ERROR_CODE_703 = "703";
    public static final String ERROR_CODE_704 = "704";
    public static final String ERROR_CODE_705 = "705";
    public static final String ERROR_CODE_790 = "790";

    public static final String ERROR_CODE_800 = "800";
    public static final String ERROR_CODE_801 = "801";
    public static final String ERROR_CODE_802 = "802";

    //認証エラー
    public static final String ERROR_CODE_900 = "900";

    public static final String ERROR_CODE_999 = "999";

    //SAP
    public static final String ERROR_CODE_1000 = "1000";


    static {
        errorResponseCodeSet.put(ERROR_CODE_400, "不正なパラメータです");
        errorResponseCodeSet.put(ERROR_CODE_500, "システムエラーです管理者にお問合せください");

        //データなし
        errorResponseCodeSet.put(ERROR_CODE_600, "該当する社員が存在しません");
        errorResponseCodeSet.put(ERROR_CODE_601, "該当する業者が存在しません");
        errorResponseCodeSet.put(ERROR_CODE_610, "該当する工事情報が存在しません");
        errorResponseCodeSet.put(ERROR_CODE_620, "該当する支店が存在しません");
        errorResponseCodeSet.put(ERROR_CODE_621, "該当する地区本部が存在しません");
        errorResponseCodeSet.put(ERROR_CODE_630, "該当する見積依頼書が存在しません");
        errorResponseCodeSet.put(ERROR_CODE_640, "該当する見積書が存在しません");
        errorResponseCodeSet.put(ERROR_CODE_650, "該当する納品書が存在しません");
        errorResponseCodeSet.put(ERROR_CODE_651, "納品書の明細が存在しません");
        errorResponseCodeSet.put(ERROR_CODE_652, "既に納品書が登録されています");
        errorResponseCodeSet.put(ERROR_CODE_660, "該当する出来高報告書が存在しません");
        errorResponseCodeSet.put(ERROR_CODE_661, "出来高報告書の明細が存在しません");
        errorResponseCodeSet.put(ERROR_CODE_662, "既に出来高報告書が登録されています");
        errorResponseCodeSet.put(ERROR_CODE_670, "該当する発注が存在しません");
        errorResponseCodeSet.put(ERROR_CODE_680, "該当する検収明細が存在しません");
        errorResponseCodeSet.put(ERROR_CODE_690, "該当する細目工種が存在しません");
        errorResponseCodeSet.put(ERROR_CODE_700, "該当するメールアドレスが登録されている業者が存在しません");
        errorResponseCodeSet.put(ERROR_CODE_790, "該当する会計基準が存在しません");

        //アカウント
        errorResponseCodeSet.put(ERROR_CODE_800, "既に登録されている業者です");
        errorResponseCodeSet.put(ERROR_CODE_801, "既に使用されているメールアドレスです");

        //認証系
        errorResponseCodeSet.put(ERROR_CODE_900, "認証失敗");

        errorResponseCodeSet.put(ERROR_CODE_999, "マスタデータが存在しません");

        errorResponseCodeSet.put(ERROR_CODE_1000, "発注が存在しません");

    }

}