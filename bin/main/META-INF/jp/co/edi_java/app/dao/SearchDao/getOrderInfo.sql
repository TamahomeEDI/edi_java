SELECT
	*
FROM
	(
	SELECT
		*,
		CASE
	    WHEN INF.REMAND_FLG = '1' THEN '5'
	    WHEN INF.CONFIRMATION_FLG = '0' AND INF.CONFIRMATION_REQUEST_DATE IS NULL AND /*orderDate*/0 != '00000000' THEN '1'
	    WHEN INF.CONFIRMATION_FLG = '0' AND INF.CONFIRMATION_REQUEST_DATE IS NULL THEN '0'
	    WHEN INF.CONFIRMATION_FLG = '0' AND INF.CONFIRMATION_REQUEST_DATE IS NOT NULL THEN '1'
	    WHEN INF.CONFIRMATION_FLG = '1' AND INF.WORK_NUMBER IS NULL THEN '2'
	    WHEN INF.WORK_NUMBER IS NOT NULL AND INF.MANAGER_RECEIPT_FLG = '0' THEN '3'
	    WHEN INF.MANAGER_RECEIPT_FLG = '1' AND INF.REMAND_FLG = '0' THEN '4'
	    ELSE '0'
		END ORDER_STATUS
	FROM
		(
		SELECT
			ODR.ORDER_NUMBER,
			ODR.ORDER_TYPE,
			ODR.CONFIRMATION_FLG,
			ODR.CONFIRMATION_REQUEST_DATE,
			ODR.CONFIRMATION_AGREE_DATE,
			ODR.CANCEL_FLG,
			ODR.CANCEL_REQUEST_DATE,
			ODR.CANCEL_AGREE_DATE,
			CASE ODR.ORDER_TYPE
				WHEN '1' THEN N_DEL.DELIVERY_NUMBER
				WHEN '2' THEN N_WRE.WORK_REPORT_NUMBER
				ELSE NULL
			END WORK_NUMBER,
			CASE ODR.ORDER_TYPE
				WHEN '1' THEN N_DEL.MANAGER_RECEIPT_FLG
				WHEN '2' THEN N_WRE.MANAGER_RECEIPT_FLG
				ELSE NULL
			END MANAGER_RECEIPT_FLG,
			CASE ODR.ORDER_TYPE
				WHEN '1' THEN N_DEL.MANAGER_RECEIPT_DATE
				WHEN '2' THEN N_WRE.MANAGER_RECEIPT_DATE
				ELSE NULL
			END MANAGER_RECEIPT_DATE,
			CASE ODR.ORDER_TYPE
				WHEN '1' THEN N_DEL.REMAND_FLG
				WHEN '2' THEN N_WRE.REMAND_FLG
				ELSE NULL
			END REMAND_FLG
		FROM
			T_ORDER ODR
		LEFT OUTER JOIN (
			SELECT
				DELIVERY_NUMBER,
				ORDER_NUMBER,
				STAFF_RECEIPT_FLG,
				STAFF_RECEIPT_DATE,
				MANAGER_RECEIPT_FLG,
				MANAGER_RECEIPT_DATE,
				REMAND_FLG
			FROM
				T_DELIVERY DEL
			WHERE
				DEL.ORDER_NUMBER = /*orderNumber*/1
			AND EXISTS (
				SELECT
					*
				FROM
					T_DELIVERY DEL2
				WHERE
					DEL2.ORDER_NUMBER = DEL.ORDER_NUMBER
				AND DEL2.DELETE_FLG = '0'
				GROUP BY
					DEL2.ORDER_NUMBER
				HAVING
					MAX(DEL2.INSERT_DATE) = DEL.INSERT_DATE
			)
		) N_DEL ON N_DEL.ORDER_NUMBER = ODR.ORDER_NUMBER
		LEFT OUTER JOIN (
			SELECT
				WORK_REPORT_NUMBER,
				ORDER_NUMBER,
				STAFF_RECEIPT_FLG,
				STAFF_RECEIPT_DATE,
				MANAGER_RECEIPT_FLG,
				MANAGER_RECEIPT_DATE,
				REMAND_FLG
			FROM
				T_WORK_REPORT WRE
			WHERE
				WRE.ORDER_NUMBER = /*orderNumber*/1
			AND EXISTS (
				SELECT
					*
				FROM
					T_WORK_REPORT WRE2
				WHERE
					WRE2.ORDER_NUMBER = WRE.ORDER_NUMBER
				AND WRE2.DELETE_FLG = '0'
				GROUP BY
					WRE2.ORDER_NUMBER
				HAVING
					MAX(WRE2.INSERT_DATE) = WRE.INSERT_DATE
			)
		) N_WRE ON N_WRE.ORDER_NUMBER = ODR.ORDER_NUMBER
		WHERE
			ODR.ORDER_NUMBER = /*orderNumber*/1
			AND ODR.DELETE_FLG = '0'
		) INF
	) DTO
WHERE
	ORDER_NUMBER IS NOT NULL
;