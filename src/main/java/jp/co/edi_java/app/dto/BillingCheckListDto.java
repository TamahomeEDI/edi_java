package jp.co.edi_java.app.dto;

import java.util.List;
import java.util.Map;

import jp.co.edi_java.app.entity.TCloudSignEntity;
import jp.co.edi_java.app.entity.TOrderItemEntity;
import jp.co.edi_java.app.entity.VOrderStatusEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillingCheckListDto {
	//クラウドサイン送付グループキー
    public String summaryKey;
    //支店名
    public String eigyousyoName;
    //発生日
    public String applyDate;
    //請求年月
    public String billingDate;
    //課金対象フラグ（有:課金, 無:非課金）
    public String chargeFlg;
    //クラウドサイン
    public TCloudSignEntity cloudSign;
    //発注情報
    public List<VOrderStatusEntity> orderList;
    //品目明細
    public Map<String, List<TOrderItemEntity>> itemListMap;
}