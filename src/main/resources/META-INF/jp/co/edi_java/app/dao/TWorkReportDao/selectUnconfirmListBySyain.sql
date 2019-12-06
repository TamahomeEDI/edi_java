select w.* from T_WORK_REPORT w
left outer join M_KOUJI k on w.KOUJI_CODE = k.KOUJI_CODE
where
w.DELETE_FLG = '0'
and w.STAFF_RECEIPT_FLG = '0'
and w.MANAGER_RECEIPT_FLG = '0'
and w.REMAND_FLG = '0'
/*%if eigyousyoCode != null && eigyousyoCode != ""*/
and k.EIGYOUSYO_CODE = /*eigyousyoCode*/'0'
/*%end*/
/*%if syainCode != null && syainCode != ""*/
and k.TANTOU_SYAIN_CODE = /*syainCode*/'0'
/*%end*/
order by w.WORK_REPORT_NUMBER;