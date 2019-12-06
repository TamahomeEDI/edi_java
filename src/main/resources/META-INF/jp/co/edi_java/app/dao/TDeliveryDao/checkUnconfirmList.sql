select * from T_DELIVERY
where
DELETE_FLG = '0'
and STAFF_RECEIPT_FLG in ('0','1')
and MANAGER_RECEIPT_FLG = '0'
and REMAND_FLG = '0'
/*%if orderNumber != null && orderNumber != ""*/
and ORDER_NUMBER = /*orderNumber*/'0'
/*%end*/
order by DELIVERY_NUMBER;