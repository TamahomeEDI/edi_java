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
@Table(name = "V_ORDER_ITEM")
@Getter
@Setter
public class VOrderItemEntity {

    @Id
    public int hattyuuSeqNo; // HATTYUU_SEQ_NO
	@Id
	public int hattyuuSyubetuFlg; // HATTYUU_SYUBETU_FLG
	@Id
	public String koujiCode; // KOUJI_CODE
	@Id
	public int kanriSeqNo; // KANRI_SEQ_NO
	@Id
	public int meisaiSeqNo; // MEISAI_SEQ_NO

	public String buzaiCode; // BUZAI_CODE

	public String buzaiName; // BUZAI_NAME

	public String siyou; // SIYOU

	public BigDecimal suuryou; // SUURYOU

	public String taniCode; // TANI_CODE

	public String taniName; // TANI_NAME

	public BigDecimal tanka; // TANKA

	public BigDecimal kingaku; // KINGAKU

	public BigDecimal kingakuTax; // KINGAKU_TAX

	public String makerName; // MAKER_NAME

	public String seihinNo; // SEIHIN_NO

	public String denpyouNo; // DENPYOU_NO

	public String sakujyoFlg; //SAKUJYO_FLG

	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Date saisyuuKousinDate;  // SAISYUU_KOUSIN_DATE

}

