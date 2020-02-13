select * from V_ORDER_STATUS vo
where exists (
select 1 from T_CLOUD_SIGN c
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
and c.ORDER_NUMBER = vo.ORDER_NUMBER
)
or (
vo.GROUP_ORDER_NUMBER is not null
and
exists (
select 1 from T_CLOUD_SIGN c
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
and c.GROUP_ORDER_NUMBER = vo.GROUP_ORDER_NUMBER
and c.GROUP_ORDER_NUMBER is not null
))
order by vo.KOUJI_CODE, vo.EIGYOUSYO_CODE, vo.ORDER_NUMBER
;
