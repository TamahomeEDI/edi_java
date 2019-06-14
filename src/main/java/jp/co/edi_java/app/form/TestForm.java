package jp.co.edi_java.app.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import jp.co.edi_java.app.util.validate.Email;
import jp.co.edi_java.app.util.validate.EmailConfirm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EmailConfirm
public class TestForm extends BaseForm {

	@Min(0)
	@Max(Integer.MAX_VALUE)
    public int testSeq;

	@Size(min = 1, max = 20)
    public String id;

    public String name;

	@Email
    public String email;

	@Email
	public String emailConfirm;

    public String documentId;

    public String mail;



}
