select *
from M_EIGYOUSYO
where
/*%if eigyousyoList == null */
1=2
/*%end*/
/*%if eigyousyoList != null */
EIGYOUSYO_CODE in /*eigyousyoList*/(0,1)
/*%end*/
order by
	EIGYOUSYO_KANA COLLATE utf8_unicode_ci ASC;