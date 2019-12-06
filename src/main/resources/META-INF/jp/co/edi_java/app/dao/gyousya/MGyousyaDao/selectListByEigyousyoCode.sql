SELECT
	MGY.GYOUSYA_CODE,
	MGY.GYOUSYA_NAME,
	MGY.GYOUSYA_KANA,
	MGE.TORIHIKI_STATUS
FROM
	M_GYOUSYA_EIGYOUSYO MGE
LEFT OUTER JOIN M_GYOUSYA MGY ON MGY.GYOUSYA_CODE = MGE.GYOUSYA_CODE
WHERE
	MGY.GYOUSYA_CODE LIKE 'A%'
	/*%if (eigyousyoCode == null || eigyousyoCode == "")*/
	AND 1=2
	/*%end*/
	/*%if (eigyousyoCode != null && eigyousyoCode != "")*/
	AND MGE.EIGYOUSYO_CODE = /*eigyousyoCode*/'0'
	/*%end*/
	/*%if torihikiStatus == "0"*/
	AND MGE.TORIHIKI_STATUS = '0'
	/*%elseif torihikiStatus == "1"*/
	AND MGE.TORIHIKI_STATUS = '1'
	/*%elseif torihikiStatus == "2"*/
	/*%end*/
ORDER BY
	MGY.GYOUSYA_KANA COLLATE utf8_unicode_ci ASC;