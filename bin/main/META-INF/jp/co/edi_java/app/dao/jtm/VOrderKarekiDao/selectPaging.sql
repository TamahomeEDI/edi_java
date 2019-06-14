SELECT
	VOK2.*
FROM
	(
		SELECT
			ROWNUM rownumber,
			VOK.*
		FROM
			(
				SELECT
					*
				FROM
					V_ORDER_KAREKI
				ORDER BY
					KAREKI_BOX_CODE DESC
			) VOK
	) VOK2
WHERE
	VOK2.rownumber BETWEEN /*from*/1
AND /*to*/2;