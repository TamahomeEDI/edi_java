package jp.co.edi_java.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApprovalDto {

    public String workNumber;

    public String judgmentKbn;

    public String userBikou;
    //JCO 明細番号
    public String jcoEbelp;
}