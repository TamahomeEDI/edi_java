select c.* from T_CLOUD_SIGN c
left outer join T_ORDER o on o.ORDER_NUMBER = c.ORDER_NUMBER
where
/*%if (params.gyousyaCode == null || params.gyousyaCode == "")*/
1=2
/*%end*/
/*%if (params.gyousyaCode != null && params.gyousyaCode != "")*/
o.GYOUSYA_CODE = /*params.gyousyaCode*/'A999999'
/*%end*/
/*%if (params.applicationDateFrom != null && params.applicationDateFrom != "")*/
and c.APPLICATION_DATE >= /*params.applicationDateFrom*/'99990101'
/*%end*/
/*%if (params.applicationDateTo != null && params.applicationDateTo != "")*/
and c.APPLICATION_DATE <= /*params.applicationDateTo*/'99990101'
/*%end*/
order by c.APPLICATION_DATE, c.ORDER_NUMBER
;
