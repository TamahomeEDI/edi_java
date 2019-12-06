package jp.co.edi_java.app.entity.jtm;

import java.math.BigDecimal;
import java.sql.Date;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "V_ORDER")
@Getter
@Setter
public class VOrderEntity {
    @Id
    public int hattyuuSeqNo; // HATTYUU_SEQ_NO
	@Id
	public int hattyuuSyubetuFlg; // HATTYUU_SYUBETU_FLG
	@Id
	public String koujiCode; // KOUJI_CODE
	@Id
	public int kanriSeqNo; // KANRI_SEQ_NO

	public int rownumber;

//	public String koujiName; // KOUJI_NAME

	public String eigyousyoCode; // EIGYOUSYO_CODE

//	public String eigyousyoName; // EIGYOUSYO_NAME

	public String syainCode; // SYAIN_CODE

//	public String syainName; // SYAIN_NAME

	public String saimokuKousyuCode; // SAIMOKU_KOUSYU_CODE

//	public String saimokuKousyuName; // SAIMOKU_KOUSYU_NAME

	public String orderType; // ORDER_TYPE

	public String gyousyaCode; // GYOUSYA_CODE

//	public String gyousyaName; // GYOUSYA_NAME

//	public String gyousyaKana; // GYOUSYA_KANA

	public String kazeiFlg; // KAZEI_FLG

	public BigDecimal tax; // TAX 税率

	public BigDecimal hattyuuKingaku; // HATTYUU_KINGAKU

	public BigDecimal hattyuuKingakuTax; // HATTYUU_KINGAKU_TAX

	public BigDecimal hattyuuNebiki; // HATTYUU_NEBIKI

	public String hattyuuDate; // HATTYUU_DATE

	public String hattyuuSyainCode; // HATTYUU_SYAIN_CODE

	public String denpyouNo; // DENPYOU_NO

	public String sakujyoFlg; //SAKUJYO_FLG

	// 1:本体発注, 2:S発注, 3:A発注, 4:B発注, 5:C発注
	public int yosanFlg; // YOSAN_FLG

	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Date saisyuuKousinDate;  // SAISYUU_KOUSIN_DATE


}

