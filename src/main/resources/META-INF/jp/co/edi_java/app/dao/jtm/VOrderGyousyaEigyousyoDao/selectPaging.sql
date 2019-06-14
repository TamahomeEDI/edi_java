SELECT
	VOG2.*
FROM
	(
		SELECT
			ROWNUM rownumber,
			VOG.*
		FROM
			(
				SELECT
					*
				FROM
					V_ORDER_GYOUSYA_EIGYOUSYO
				ORDER BY
					GYOUSYA_CODE DESC
			) VOG
	) VOG2
WHERE
	VOG2.rownumber BETWEEN /*from*/1
AND /*to*/2;