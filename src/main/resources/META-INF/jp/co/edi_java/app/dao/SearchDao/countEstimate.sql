select
	COUNT(ESR.ESTIMATE_REQUEST_NUMBER)
FROM
	T_ESTIMATE_REQUEST ESR
LEFT OUTER JOIN (
	SELECT
		ESTIMATE_NUMBER,
		ESTIMATE_REQUEST_NUMBER,
		ESTIMATE_DATE,
		UNREAD_FLG
	FROM
		T_ESTIMATE EST
	WHERE
		EXISTS (
			SELECT
				*
			FROM
				T_ESTIMATE EST2
			WHERE
				EST2.ESTIMATE_REQUEST_NUMBER = EST.ESTIMATE_REQUEST_NUMBER
			GROUP BY
				EST2.ESTIMATE_REQUEST_NUMBER
			HAVING
				MAX(EST2.INSERT_DATE) = EST.INSERT_DATE
		)
) N_EST ON ESR.ESTIMATE_REQUEST_NUMBER = N_EST.ESTIMATE_REQUEST_NUMBER
LEFT OUTER JOIN M_KOUJI KOJ ON ESR.KOUJI_CODE = KOJ.KOUJI_CODE
LEFT OUTER JOIN M_EIGYOUSYO EIG ON KOJ.EIGYOUSYO_CODE = EIG.EIGYOUSYO_CODE
LEFT OUTER JOIN M_SYAIN SYA ON KOJ.TANTOU_SYAIN_CODE = SYA.SYAIN_CODE
LEFT OUTER JOIN M_GYOUSYA GYO ON ESR.GYOUSYA_CODE = GYO.GYOUSYA_CODE
LEFT OUTER JOIN M_GYOUSYA_EIGYOUSYO MGE ON EIG.EIGYOUSYO_CODE = MGE.EIGYOUSYO_CODE AND GYO.GYOUSYA_CODE = MGE.GYOUSYA_CODE
WHERE
	ESR.DELETE_FLG = '0'
/*%if (params.isAdmin != "1" && params.userId == null)*/
and 1=2
/*%end*/
/*%if (params.isAdmin != "1" && params.userFlg == "1" && params.userId != null)*/
and EIG.EIGYOUSYO_CODE in (select EIGYOUSYO_CODE from M_SYAIN_EIGYOUSYO where SYAIN_CODE = /*params.userId*/'0')
/*%end*/
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
and KOJ.KOUJI_CODE LIKE /*@prefix(params.koujiCode)*/'smith' escape '$'
/*%end*/
/*%if params.koujiName != null && params.koujiName != ""*/
and (KOJ.KOUJI_NAME LIKE /*@infix(params.koujiName)*/'smith' escape '$' or KOJ.KEIYAKUSYA_KANA LIKE /*@infix(params.koujiName)*/'smith' escape '$')
/*%end*/
/*%if params.gyousyaCode != null && params.gyousyaCode != ""*/
and GYO.GYOUSYA_CODE = /*params.gyousyaCode*/1
/*%end*/
/*%if params.estimateStatus == "1"*/
and N_EST.ESTIMATE_NUMBER IS NULL
/*%elseif params.estimateStatus == "2"*/
and N_EST.ESTIMATE_NUMBER IS NOT NULL
/*%end*/
/*%if params.estimateRequestNumber != null && params.estimateRequestNumber != ""*/
and ESR.ESTIMATE_REQUEST_NUMBER LIKE /*@prefix(params.estimateRequestNumber)*/'smith' escape '$'
/*%end*/
/*%if params.estimateNumber != null && params.estimateNumber != ""*/
and N_EST.ESTIMATE_NUMBER LIKE /*@prefix(params.estimateNumber)*/'smith' escape '$'
/*%end*/
/*%if params.estimateRequestDateFrom != null && params.estimateRequestDateFrom != ""*/
and ESR.ESTIMATE_REQUEST_DATE >= /*params.estimateRequestDateFrom*/1
/*%end*/
/*%if params.estimateRequestDateTo != null && params.estimateRequestDateTo != ""*/
and ESR.ESTIMATE_REQUEST_DATE <= /*params.estimateRequestDateTo*/1
/*%end*/
/*%if params.estimateDateFrom != null && params.estimateDateFrom != ""*/
and N_EST.ESTIMATE_DATE >= /*params.estimateDateFrom*/1
/*%end*/
/*%if params.estimateDateTo != null && params.estimateDateTo != ""*/
and N_EST.ESTIMATE_DATE <= /*params.estimateDateTo*/1
/*%end*/
;