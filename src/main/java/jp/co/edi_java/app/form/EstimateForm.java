package jp.co.edi_java.app.form;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstimateForm extends BaseForm {

	public String estimateNumber;

	public String koujiCode;

	public String estimateRequestNumber;

	public String estimateDate;

    public String filePath;

    public String unreadFlg;

    public String userBikou;

    public String partnerBikou;

    public List<String> fileIdList;


}
