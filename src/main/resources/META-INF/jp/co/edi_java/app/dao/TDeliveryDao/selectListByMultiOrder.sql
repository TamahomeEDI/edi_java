select *
from T_DELIVERY
where
DELETE_FLG = '0'
/*%if orderNumberList == null*/
and 1=2
/*%end*/
/*%if orderNumberList != null*/
and ORDER_NUMBER in /*orderNumberList*/('0')
/*%end*/
/*%if remandFlg != null && remandFlg != ""*/
and REMAND_FLG = /*remandFlg*/1
/*%end*/
order by ORDER_NUMBER, INSERT_DATE desc;