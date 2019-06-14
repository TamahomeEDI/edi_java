package jp.co.edi_java.app.form;

import java.util.List;

import jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartnerForm extends BaseForm {

    public String password;

    public String gyousyaName;

    public String gyousyaKana;

	public String eigyousyoCode;

	public String token;

	public String mailaddress;

	public List<TGyousyaMailaddressEntity> mailaddressList;

	public String saimokuKousyuCode;

	public String registKbn;

	public String torihikiStatus;

	//ページネーション
	public int page = 1;

    public int count = 20;


}
