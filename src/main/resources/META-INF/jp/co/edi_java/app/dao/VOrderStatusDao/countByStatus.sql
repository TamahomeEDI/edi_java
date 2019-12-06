select ORDER_STATUS, count(*) as COUNT
from V_ORDER_STATUS
where
/*%if (eigyousyoCode == null || eigyousyoCode == "") && (gyousyaCode == null || gyousyaCode == "")*/
1=2
/*%end*/
/*%if eigyousyoCode != null && eigyousyoCode != ""*/
EIGYOUSYO_CODE = /*eigyousyoCode*/'0'
/*%end*/
/*%if gyousyaCode != null && gyousyaCode != ""*/
and GYOUSYA_CODE = /*gyousyaCode*/'0'
/*%end*/

group by ORDER_STATUS;