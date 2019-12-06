SELECT
VO2.*
FROM (
	SELECT
		ROW_NUMBER() OVER(ORDER BY VO.KOUJI_CODE, VO.DENPYOU_NO) rownumber,
		VO.*
	FROM
		V_ORDER VO
	WHERE
	/*%if prevDate != null*/
	VO.SAISYUU_KOUSIN_DATE >= /*prevDate*/'2019-08-01'
	/*%end*/
) VO2
WHERE
VO2.rownumber BETWEEN /*from*/1 AND /*to*/2
;
