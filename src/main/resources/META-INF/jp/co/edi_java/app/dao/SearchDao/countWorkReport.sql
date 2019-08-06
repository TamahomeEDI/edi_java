select
	count(1)
from
	T_WORK_REPORT WRP
left outer join M_KOUJI KOU on WRP.KOUJI_CODE = KOU.KOUJI_CODE
left outer join M_GYOUSYA GYO on WRP.GYOUSYA_CODE = GYO.GYOUSYA_CODE
left outer join M_EIGYOUSYO EIG on KOU.EIGYOUSYO_CODE = EIG.EIGYOUSYO_CODE
left outer join M_SAIMOKU_KOUSYU SAI on WRP.SAIMOKU_KOUSYU_CODE = SAI.SAIMOKU_KOUSYU_CODE
left outer join M_SYAIN SYA on KOU.TANTOU_SYAIN_CODE = SYA.SYAIN_CODE
left outer join M_GYOUSYA_EIGYOUSYO MGE on EIG.EIGYOUSYO_CODE = MGE.EIGYOUSYO_CODE AND GYO.GYOUSYA_CODE = MGE.GYOUSYA_CODE
where
	WRP.DELETE_FLG = '0'
/*%if params.eigyousyoGroupCode != null && params.eigyousyoGroupCode != ""*/
and EIG.EIGYOUSYO_GROUP_CODE = /*params.eigyousyoGroupCode*/1
/*%end*/
/*%if params.eigyousyoCode != null && params.eigyousyoCode != ""*/
and EIG.EIGYOUSYO_CODE = /*params.eigyousyoCode*/1
/*%end*/
/*%if params.syainCode != null && params.syainCode != ""*/
and SYA.SYAIN_CODE = /*params.syainCode*/1
/*%end*/
/*%if params.koujiCode != null && params.koujiCode != ""*/
and KOU.KOUJI_CODE like /*@prefix(params.koujiCode)*/'smith' escape '$'
/*%end*/
/*%if params.koujiName != null && params.koujiName != ""*/
and (KOU.KOUJI_NAME like /*@infix(params.koujiName)*/'smith' escape '$' or KOU.KEIYAKUSYA_KANA like /*@infix(params.koujiName)*/'smith' escape '$')
/*%end*/
/*%if params.gyousyaCode != null && params.gyousyaCode != ""*/
and GYO.GYOUSYA_CODE = /*params.gyousyaCode*/1
/*%end*/
/*%if params.saimokuKousyuCode != null && params.saimokuKousyuCode != ""*/
and SAI.SAIMOKU_KOUSYU_CODE = /*params.saimokuKousyuCode*/1
/*%end*/
/*%if params.orderNumber != null && params.orderNumber != ""*/
and WRP.ORDER_NUMBER like /*@prefix(params.orderNumber)*/'smith' escape '$'
/*%end*/
/*%if params.receptionStatus == "1"*/
and WRP.STAFF_RECEIPT_FLG = '0'
and WRP.CLERK_RECEIPT_FLG = '0'
and WRP.MANAGER_RECEIPT_FLG = '0'
and WRP.REMand_FLG = '0'
/*%elseif params.receptionStatus == "2"*/
and WRP.STAFF_RECEIPT_FLG = '1'
and WRP.CLERK_RECEIPT_FLG = '0'
and WRP.MANAGER_RECEIPT_FLG = '0'
and WRP.REMand_FLG = '0'
/*%elseif params.receptionStatus == "3"*/
and WRP.STAFF_RECEIPT_FLG = '1'
and WRP.CLERK_RECEIPT_FLG = '1'
and WRP.MANAGER_RECEIPT_FLG = '0'
and WRP.REMand_FLG = '0'
/*%elseif params.receptionStatus == "4"*/
and WRP.STAFF_RECEIPT_FLG = '1'
and WRP.CLERK_RECEIPT_FLG = '1'
and WRP.MANAGER_RECEIPT_FLG = '1'
and WRP.REMand_FLG = '0'
/*%elseif params.receptionStatus == "5"*/
and WRP.REMand_FLG = '1'
/*%end*/
/*%if params.deliveryDateFrom != null && params.deliveryDateFrom != ""*/
and WRP.WORK_REPORT_DATE >= /*params.deliveryDateFrom*/1
/*%end*/
/*%if params.deliveryDateTo != null && params.deliveryDateTo != ""*/
and WRP.WORK_REPORT_DATE <= /*params.deliveryDateTo*/1
/*%end*/
/*%if params.inspectionReceiptDateFrom != null && params.inspectionReceiptDateFrom != ""*/
and WRP.STAFF_RECEIPT_DATE >= /*params.inspectionReceiptDateFrom*/1
/*%end*/
/*%if params.inspectionReceiptDateTo != null && params.inspectionReceiptDateTo != ""*/
and WRP.STAFF_RECEIPT_DATE <= /*params.inspectionReceiptDateTo*/1
/*%end*/

;

