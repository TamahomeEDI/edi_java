select * from T_ORDER
where
DELETE_FLG = '0'
and GYOUSYA_CODE in (select GYOUSYA_CODE from T_GYOUSYA_ACCOUNT where AUTH_FLG = '1' and DELETE_FLG = '0')
and FILE_ID_ORDER is not null
/*%if (gyousyaCode != null && gyousyaCode != "")*/
and GYOUSYA_CODE = /*gyousyaCode*/'0'
/*%end*/
/*%if (from != null && from != "")*/
AND CONFIRMATION_AGREE_DATE >= /*from*/'99990101'
/*%end*/
/*%if (to != null && to != "")*/
AND CONFIRMATION_AGREE_DATE <= /*to*/'99990101'
/*%end*/


order by KOUJI_CODE, ORDER_NUMBER
;
