select *
from M_EIGYOUSYO
where EIGYOUSYO_CODE in /*eigyousyoList*/(0,1)
order by
	EIGYOUSYO_KANA COLLATE utf8_unicode_ci ASC;