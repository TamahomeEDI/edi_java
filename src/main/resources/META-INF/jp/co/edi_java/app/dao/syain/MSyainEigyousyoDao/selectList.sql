SELECT
	MSE.SYAIN_CODE,
	MSE.EIGYOUSYO_GROUP_CODE,
	MSE.SYUMU_FLG,
	MEI.EIGYOUSYO_CODE,
	MEI.EIGYOUSYO_NAME,
	MEI.EIGYOUSYO_KANA,
	MEI.EIGYOUSYO_YUUBIN,
	MEI.EIGYOUSYO_JYUUSYO,
	MEI.EIGYOUSYO_TATEMONO_NAME,
	MEI.EIGYOUSYO_TEL,
	MEI.EIGYOUSYO_FAX
FROM
	M_SYAIN_EIGYOUSYO MSE
INNER JOIN M_EIGYOUSYO MEI ON MSE.EIGYOUSYO_CODE = MEI.EIGYOUSYO_CODE
WHERE
	MSE.SYAIN_CODE = /*syainCode*/0
ORDER BY
	MEI.EIGYOUSYO_KANA COLLATE utf8_unicode_ci ASC;