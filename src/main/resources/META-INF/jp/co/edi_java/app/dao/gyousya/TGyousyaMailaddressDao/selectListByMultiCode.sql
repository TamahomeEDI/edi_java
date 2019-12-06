select * from T_GYOUSYA_MAILADDRESS
where
/*%if (gyousyaCodeList == null)*/
1=2
/*%end*/
/*%if (gyousyaCodeList != null)*/
GYOUSYA_CODE in /*gyousyaCodeList*/(0)
/*%end*/

;