select ORDER_STATUS, count(*) as COUNT
from V_ORDER_STATUS
where
DELETE_FLG = '0'
and GYOUSYA_CODE in (select GYOUSYA_CODE from T_GYOUSYA_ACCOUNT where AUTH_FLG = '1' and DELETE_FLG = '0')
/*%if (params.isAdmin != "1" && params.userId == null)*/
1=2
/*%end*/
/*%if (params.isAdmin != "1" && params.userFlg == "1" && params.userId != null)*/
and EIGYOUSYO_CODE in (select EIGYOUSYO_CODE from M_SYAIN_EIGYOUSYO where SYAIN_CODE = /*params.userId*/'0')
and GYOUSYA_CODE in (select GYOUSYA_CODE from M_GYOUSYA_EIGYOUSYO where EIGYOUSYO_CODE in (select EIGYOUSYO_CODE from M_SYAIN_EIGYOUSYO where SYAIN_CODE = /*params.userId*/'0'))
/*%end*/
/*%if (params.isAdmin != "1" && params.userFlg != "1" && params.userId != null)*/
and GYOUSYA_CODE = /*params.userId*/'0'
/*%end*/
/*%if (params.eigyousyoGroupCode != null && params.eigyousyoGroupCode != "")*/
and EIGYOUSYO_GROUP_CODE = /*params.eigyousyoGroupCode*/'0'
/*%end*/
/*%if (params.eigyousyoCode != null && params.eigyousyoCode != "")*/
and EIGYOUSYO_CODE = /*params.eigyousyoCode*/'0'
/*%end*/
/*%if (params.gyousyaCode != null && params.gyousyaCode != "")*/
and GYOUSYA_CODE = /*params.gyousyaCode*/'0'
/*%end*/
/*%if (params.syainCode != null && params.syainCode != "")*/
and SYAIN_CODE = /*params.syainCode*/'0'
/*%end*/
group by ORDER_STATUS;