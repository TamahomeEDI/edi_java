SELECT
	VOE2.*
FROM
	(
		SELECT
			ROWNUM rownumber,
			VOE.*
		FROM
			(
				SELECT
					*
				FROM
					V_ORDER_EIGYOUSYO_GROUP
				ORDER BY
					EIGYOUSYO_GROUP_CODE DESC
			) VOE
	) VOE2
WHERE
	VOE2.rownumber BETWEEN /*from*/1
AND /*to*/2;