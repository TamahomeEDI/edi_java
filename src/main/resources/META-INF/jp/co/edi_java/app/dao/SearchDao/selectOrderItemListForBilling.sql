select * from T_ORDER_ITEM oi
where
oi.DELETE_FLG = '0'
and oi.ORDER_NUMBER in (
 select vo.ORDER_NUMBER from V_ORDER_STATUS vo
 where (
   vo.ORDER_NUMBER in (
   select c.ORDER_NUMBER from T_CLOUD_SIGN c
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
 )
 or
 (
   vo.GROUP_ORDER_NUMBER is not null
   and
   vo.GROUP_ORDER_NUMBER in (
     select c.GROUP_ORDER_NUMBER from T_CLOUD_SIGN c
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
     and c.GROUP_ORDER_NUMBER is not null
   )
 ))
)
order by oi.ORDER_NUMBER, oi.ITEM_NUMBER
;
