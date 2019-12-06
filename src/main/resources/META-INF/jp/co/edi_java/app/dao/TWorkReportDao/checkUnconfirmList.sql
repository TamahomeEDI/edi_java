select * from T_WORK_REPORT
where
DELETE_FLG = '0'
and STAFF_RECEIPT_FLG in ('0','1')
and MANAGER_RECEIPT_FLG = '0'
and REMAND_FLG = '0'
/*%if orderNumber != null && orderNumber != ""*/
and ORDER_NUMBER = /*orderNumber*/'0'
/*%end*/
order by WORK_REPORT_NUMBER;