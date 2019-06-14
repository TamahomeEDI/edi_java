select *
from M_GYOUSYA
where
GYOUSYA_CODE is not null
/*%if eigyousyoCode != null*/
and EIGYOUSYO_CODE = /*eigyousyoCode*/1
/*%end*/
/*%if gyousyaCode != null*/
and GYOUSYA_CODE = /*gyousyaCode*/1
/*%end*/
;
