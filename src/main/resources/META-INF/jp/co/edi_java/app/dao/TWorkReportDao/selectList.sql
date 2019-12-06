select *
from T_WORK_REPORT
where DELETE_FLG = '0'
and ORDER_NUMBER = /*orderNumber*/0
/*%if remandFlg != null && remandFlg != ""*/
and REMAND_FLG = /*remandFlg*/1
/*%end*/
order by INSERT_DATE desc;