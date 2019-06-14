package jp.co.edi_java.app.form;

import java.util.List;

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


}
