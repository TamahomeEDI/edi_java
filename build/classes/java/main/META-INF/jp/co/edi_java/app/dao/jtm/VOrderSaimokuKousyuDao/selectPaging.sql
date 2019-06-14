SELECT
	VOS2.*
FROM
	(
		SELECT
			ROWNUM rownumber,
			VOS.*
		FROM
			(
				SELECT
					*
				FROM
					V_ORDER_SAIMOKU_KOUSYU
				ORDER BY
					SAIMOKU_KOUSYU_CODE DESC
			) VOS
	) VOS2
WHERE
	VOS2.rownumber BETWEEN /*from*/1
AND /*to*/2;