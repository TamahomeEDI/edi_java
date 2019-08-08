SELECT
	KOU.KOUJI_CODE
	,KOU.KOUJI_NAME
	,KOU.KOUJI_STATUS
	,KOU.KOUJI_STATUS_NAME
	,KOU.TYAKKOU_DATE_Y
	,KOU.TYAKKOU_DATE
	,KOU.SYUNKOU_DATE_Y
	,KOU.SYUNKOU_DATE
	,KOU.HIKIWATASI_DATE_Y
	,KOU.HIKIWATASI_DATE
	,KOU.KENTIKUTI_YUUBIN
	,KOU.KENTIKUTI_JYUUSYO
	,KOU.EIGYOUSYO_CODE
	,EIG.EIGYOUSYO_NAME
	,KOU.TANTOU_SYAIN_CODE
	,SYA.SYAIN_NAME
FROM
	M_KOUJI KOU
LEFT OUTER JOIN M_EIGYOUSYO EIG ON EIG.EIGYOUSYO_CODE = KOU.EIGYOUSYO_CODE
LEFT OUTER JOIN M_SYAIN SYA ON SYA.SYAIN_CODE = KOU.TANTOU_SYAIN_CODE
WHERE
	(/*%for koujiCode : koujiCodeList*/
		KOU.KOUJI_CODE like /*@prefix(koujiCode)*/'smith' escape '$'
		/*%if koujiCode_has_next */
			/*# "or" */
		/*%end */
	/*%end */)
	/*%if eigyousyoCode != null && eigyousyoCode != ""*/
		and KOU.EIGYOUSYO_CODE = /*eigyousyoCode*/0
	/*%end*/
	/*%if kanseiKubun != null && kanseiKubun != ""*/
		and KOU.KANSEI_KUBUN = /*kanseiKubun*/0
	/*%end*/
	/*%if koujiName != null && koujiName != ""*/
		and (KOU.KOUJI_NAME like /*@infix(koujiName)*/'smith' escape '$' or KOU.KEIYAKUSYA_KANA like /*@infix(koujiName)*/'smith' escape '$')
	/*%end*/;


