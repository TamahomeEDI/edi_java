package jp.co.edi_java.app.form;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExclusiveForm extends BaseForm {

    public String exclusiveObjectName;

    public String exclusiveObjectKey;

    public String exclusiveUser;

    public String exclusiveSessionId;

    public Timestamp exclusiveLimitDate;

    public List<String> exclusiveObjectKeyList;

}
