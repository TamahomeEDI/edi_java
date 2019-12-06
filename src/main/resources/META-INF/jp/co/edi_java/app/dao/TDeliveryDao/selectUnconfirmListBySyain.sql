select d.* from T_DELIVERY d
left outer join M_KOUJI k on d.KOUJI_CODE = k.KOUJI_CODE
where
d.DELETE_FLG = '0'
and d.STAFF_RECEIPT_FLG = '0'
and d.MANAGER_RECEIPT_FLG = '0'
and d.REMAND_FLG = '0'
/*%if eigyousyoCode != null && eigyousyoCode != ""*/
and k.EIGYOUSYO_CODE = /*eigyousyoCode*/'0'
/*%end*/
/*%if syainCode != null && syainCode != ""*/
and k.TANTOU_SYAIN_CODE = /*syainCode*/'0'
/*%end*/
order by d.DELIVERY_NUMBER;