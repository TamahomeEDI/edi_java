package jp.co.edi_java.app.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChecklistOrderDto {

    public String orderNumber;

    public String orderDate;
    //発注金額（税抜）
    public String orderAmount;
    //業者コード
    public String gyousyaCode;
    //業者名
    public String gyousyaName;
    //業者TEL
    public String gyousyaTel;
    //細目工種コード
    public String saimokuKousyuCode;
    //細目工種名
    public String saimokuKousyuName;
    //工事コード
    public String koujiCode;
    //工事名
    public String koujiName;
    //工事ステータス
    public String koujiStatus;
    //施工支店コード
    public String eigyousyoCode;
    //施工支店名
    public String eigyousyoName;
    //支店グループコード
    public String eigyousyoGroupCode;
    //支店グループ名
    public String eigyousyoGroupName;
    //担当者社員コード
    public String syainCode;
    //担当者社員名
    public String syainName;
    //担当者TEL
    public String syainKeitai;
    //担当者メールアドレス
    public String syainMail;
    //着工日
    public String tyakkouDate;
    //着工予定日
    public String tyakkouDateY;
    //竣工日
    public String syunkouDate;
    //竣工予定日
    public String syunkouDateY;
    //引渡日
    public String hikiwatasiDate;
    //引渡予定日
    public String hikiwatasiDateY;
    //建築地郵便
    public String kentikutiYuubin;
    //建築地住所
    public String kentikutiJyuusyo;
    //明細
    public List<ChecklistOrderDetailDto> itemList;

}