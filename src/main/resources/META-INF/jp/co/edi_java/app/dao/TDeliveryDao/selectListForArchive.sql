select * from T_DELIVERY
where
DELETE_FLG = '0'
and GYOUSYA_CODE in (select GYOUSYA_CODE from T_GYOUSYA_ACCOUNT where AUTH_FLG = '1' and DELETE_FLG = '0')
and FILE_ID is not null
/*%if (gyousyaCode != null && gyousyaCode != "")*/
and GYOUSYA_CODE = /*gyousyaCode*/'0'
/*%end*/
/*%if (remandFlg != null && remandFlg == "0")*/
/*%if (from != null && from != "")*/
AND MANAGER_RECEIPT_DATE >= /*from*/'99990101'
/*%end*/
/*%if (to != null && to != "")*/
AND MANAGER_RECEIPT_DATE <= /*to*/'99990101'
/*%end*/
AND REMAND_FLG = /*remandFlg*/'0'
/*%end*/
/*%if (remandFlg != null && remandFlg == "1")*/
/*%if (from != null && from != "")*/
AND REMAND_DATE >= /*from*/'99990101'
/*%end*/
/*%if (to != null && to != "")*/
AND REMAND_DATE <= /*to*/'99990101'
/*%end*/
AND REMAND_FLG = /*remandFlg*/'0'
/*%end*/
order by KOUJI_CODE, ORDER_NUMBER
;
