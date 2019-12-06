select * from V_ORDER_ITEM
where DENPYOU_NO in (select DENPYOU_NO from V_ORDER);