package jp.co.edi_java.app.form;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForm extends BaseForm {

	public String eigyousyoCode;

	public List<String> eigyousyoCodeList;

	public String encryptSyainCode;

}
