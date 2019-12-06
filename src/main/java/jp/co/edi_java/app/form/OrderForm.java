package jp.co.edi_java.app.form;

import java.util.List;

import jp.co.edi_java.app.entity.TOrderItemEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderForm extends BaseForm {

    public String orderNumber;

    public String eigyousyoCode;

    public String koujiCode;

    public String saimokuKousyuCode;

    public String userBikou;

    public String partnerBikou;

    public String fileIdOrder;

    public String fileIdCancel;

    public List<String> fileIdList;

    //請書発行
    public String mailaddress;

    public String gyousyaName;

    public String filePath;
    // 2019/6/24 クラウドサインメール件名変更対応で支店名追加(業者コード→支店名)
    public String eigyousyoName;

    public String saimokuKousyuName;

    public String koujiName;

    // 2019/7/3 一括発注
    public List<String> orderNumberList;

    // 検索改善対応
    public String gyousyaCode;

    public List<TOrderItemEntity> itemList;

    public String orderAmount;

    public String orderAmountTax;

    public String groupOrderNumber;

}
