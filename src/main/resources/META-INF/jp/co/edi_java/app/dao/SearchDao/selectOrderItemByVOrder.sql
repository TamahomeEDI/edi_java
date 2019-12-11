select * from T_ORDER_ITEM oi
where
oi.DELETE_FLG = '0'
and exists(
select * from V_ORDER_STATUS os
where
os.ORDER_NUMBER = oi.ORDER_NUMBER
and os.DELETE_FLG = '0'
and os.GYOUSYA_CODE in (select GYOUSYA_CODE from T_GYOUSYA_ACCOUNT where AUTH_FLG = '1' and DELETE_FLG = '0')
/*%if (params.isAdmin != "1" && params.userId == null)*/
and 1=2
/*%end*/
/*%if (params.isAdmin != "1" && params.userFlg == "1" && params.userId != null)*/
and os.EIGYOUSYO_CODE in (select EIGYOUSYO_CODE from M_SYAIN_EIGYOUSYO where SYAIN_CODE = /*params.userId*/'0')
and os.GYOUSYA_CODE in (select GYOUSYA_CODE from M_GYOUSYA_EIGYOUSYO where EIGYOUSYO_CODE in (select EIGYOUSYO_CODE from M_SYAIN_EIGYOUSYO where SYAIN_CODE = /*params.userId*/'0'))
/*%end*/
/*%if (params.isAdmin != "1" && params.userFlg != "1" && params.userId != null)*/
and os.GYOUSYA_CODE = /*params.userId*/'0'
and os.ORDER_STATUS != '0'
/*%end*/
/*%if (params.orderNumber != "" && params.orderNumber != null)*/
and os.ORDER_NUMBER = /*params.orderNumber*/'0'
/*%end*/
/*%if (params.koujiCode != null && params.koujiCode != "")*/
and os.KOUJI_CODE = /*params.koujiCode*/'0'
/*%end*/
/*%if (params.eigyousyoGroupCode != null && params.eigyousyoGroupCode != "")*/
and os.EIGYOUSYO_GROUP_CODE = /*params.eigyousyoGroupCode*/'0'
/*%end*/
/*%if (params.eigyousyoCode != null && params.eigyousyoCode != "")*/
and os.EIGYOUSYO_CODE = /*params.eigyousyoCode*/'0'
/*%end*/
/*%if (params.gyousyaCode != null && params.gyousyaCode != "")*/
and os.GYOUSYA_CODE = /*params.gyousyaCode*/'0'
/*%end*/
/*%if (params.syainCode != null && params.syainCode != "")*/
and os.SYAIN_CODE = /*params.syainCode*/'0'
/*%end*/
/*%if (params.saimokuKousyuCode != null && params.saimokuKousyuCode != "")*/
and os.SAIMOKU_KOUSYU_CODE = /*params.saimokuKousyuCode*/'0'
/*%end*/
/*%if (params.koujiStatusList != null)*/
and os.KANSEI_KUBUN in /*params.koujiStatusList*/('-1')
/*%end*/
/*%if (params.processList != null)*/
and os.PROCESS in /*params.processList*/('-1')
/*%end*/
/*%if (params.orderStatusList != null)*/
and os.ORDER_STATUS in /*params.orderStatusList*/('-1')
/*%end*/
/*%if (params.keyword != null && params.keyword != "")*/
and os.ORDER_NUMBER in (select ORDER_NUMBER from T_ORDER_KEYWORD where match(ORDER_KEYWORD) AGAINST(/*params.keyword*/'smith' IN BOOLEAN MODE))
/*%end*/
/*%if (params.orderDateFrom != null && params.orderDateFrom != "")*/
and os.ORDER_DATE >= /*params.orderDateFrom*/'99990101'
/*%end*/
/*%if (params.orderDateTo != null && params.orderDateTo != "")*/
and os.ORDER_DATE <= /*params.orderDateTo*/'99990101'
/*%end*/
/*%if (params.koujiStartDateFrom != null && params.koujiStartDateFrom != "")*/
and os.TYAKKOU_DATE >= /*params.koujiStartDateFrom*/'99990101'
/*%end*/
/*%if (params.koujiStartDateTo != null && params.koujiStartDateTo != "")*/
and os.TYAKKOU_DATE <= /*params.koujiStartDateTo*/'99990101'
/*%end*/
/*%if (params.koujiCompleteDateFrom != null && params.koujiCompleteDateFrom != "")*/
and os.SYUNKOU_DATE >= /*params.koujiCompleteDateFrom*/'99990101'
/*%end*/
/*%if (params.koujiCompleteDateTo != null && params.koujiCompleteDateTo != "")*/
and os.SYUNKOU_DATE <= /*params.koujiCompleteDateTo*/'99990101'
/*%end*/
/*%if (params.koujiDeliveryDateFrom != null && params.koujiDeliveryDateFrom != "")*/
and os.HIKIWATASI_DATE >= /*params.koujiDeliveryDateFrom*/'99990101'
/*%end*/
/*%if (params.koujiDeliveryDateTo != null && params.koujiDeliveryDateTo != "")*/
and os.HIKIWATASI_DATE <= /*params.koujiDeliveryDateTo*/'99990101'
/*%end*/
/*%if (params.confirmationDateFrom != null && params.confirmationDateFrom != "")*/
and os.CONFIRMATION_AGREE_DATE >= /*params.confirmationDateFrom*/'99990101'
/*%end*/
/*%if (params.confirmationDateTo != null && params.confirmationDateTo != "")*/
and os.CONFIRMATION_AGREE_DATE <= /*params.confirmationDateTo*/'99990101'
/*%end*/
/*%if (params.deliveryDateFrom != null && params.deliveryDateFrom != "")*/
and os.DELIVERY_REQUEST_DATE >= /*params.deliveryDateFrom*/'99990101'
/*%end*/
/*%if (params.deliveryDateTo != null && params.deliveryDateTo != "")*/
and os.DELIVERY_REQUEST_DATE <= /*params.deliveryDateTo*/'99990101'
/*%end*/
/*%if (params.completionDateFrom != null && params.completionDateFrom != "")*/
and os.MANAGER_RECEIPT_DATE >= /*params.completionDateFrom*/'99990101'
/*%end*/
/*%if (params.completionDateTo != null && params.completionDateTo != "")*/
and os.MANAGER_RECEIPT_DATE <= /*params.completionDateTo*/'99990101'
/*%end*/
)
order by oi.ORDER_NUMBER, oi.ITEM_NUMBER
;
