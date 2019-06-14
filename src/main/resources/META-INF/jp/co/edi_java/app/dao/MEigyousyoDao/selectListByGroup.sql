select *
from M_EIGYOUSYO
where EIGYOUSYO_GROUP_CODE = /*eigyousyoGroupCode*/0
order by
	EIGYOUSYO_KANA collate utf8_unicode_ci asc;