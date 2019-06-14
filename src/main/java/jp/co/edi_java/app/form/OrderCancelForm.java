package jp.co.edi_java.app.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCancelForm extends BaseForm {

	public String emailConfirm;

    public String fileIdOrder;

    public String fileIdCancel;


}
