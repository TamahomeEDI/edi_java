select * from T_DELIVERY
where
DELETE_FLG = '0'
and STAFF_RECEIPT_FLG = '0'
and MANAGER_RECEIPT_FLG = '0'
and REMAND_FLG = '0';