package jp.co.edi_java.app.form;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstimateRequestForm extends BaseForm {

	public String estimateRequestNumber;

	public String koujiCode;

	public String registSyainCode;

	public String estimateRequestDate;

    public String userBikou;

    public String partnerBikou;

    public List<String> fileIdList;

	public String eigyousyoCode;


}
