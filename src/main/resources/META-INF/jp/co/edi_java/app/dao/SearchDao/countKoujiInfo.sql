SELECT
	COUNT(*)
FROM
	M_KOUJI KOU
WHERE
	/*%if koujiCode != null && koujiCode != ""*/
		KOU.KOUJI_CODE like /*@prefix(koujiCode)*/'smith' escape '$'
	/*%end*/
	/*%if eigyousyoCode != null && eigyousyoCode != ""*/
		and KOU.EIGYOUSYO_CODE = /*eigyousyoCode*/0
	/*%end*/
	/*%if koujiName != null && koujiName != ""*/
		and (KOU.KOUJI_NAME like /*@infix(koujiName)*/'smith' escape '$' or KOU.KEIYAKUSYA_KANA like /*@infix(koujiName)*/'smith' escape '$')
	/*%end*/;