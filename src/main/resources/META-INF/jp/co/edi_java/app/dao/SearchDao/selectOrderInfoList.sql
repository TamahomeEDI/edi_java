SELECT
	*
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
			WHEN '1' THEN N_DEL.STAFF_RECEIPT_FLG
			WHEN '2' THEN N_WRE.STAFF_RECEIPT_FLG
			ELSE NULL
		END STAFF_RECEIPT_FLG,
		CASE ODR.ORDER_TYPE
			WHEN '1' THEN N_DEL.STAFF_RECEIPT_DATE
			WHEN '2' THEN N_WRE.STAFF_RECEIPT_DATE
			ELSE NULL
		END STAFF_RECEIPT_DATE,
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
			REMAND_FLG
		FROM
			T_DELIVERY DEL
		WHERE
			DEL.ORDER_NUMBER in /*orderNumber*/(1)
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
			REMAND_FLG
		FROM
			T_WORK_REPORT WRE
		WHERE
			WRE.ORDER_NUMBER in /*orderNumber*/(1)
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
		ODR.ORDER_NUMBER in /*orderNumber*/(1)
		AND ODR.DELETE_FLG = '0'
	) DTO
WHERE
	ORDER_NUMBER IS NOT NULL
/*%if params.confirmationDateFrom != null && params.confirmationDateFrom != ""*/
AND CONFIRMATION_REQUEST_DATE >= /*params.confirmationDateFrom*/1
/*%end*/
/*%if params.confirmationDateTo != null && params.confirmationDateTo != ""*/
AND CONFIRMATION_REQUEST_DATE <= /*params.confirmationDateTo*/1
/*%end*/
/*%if params.completionDateFrom != null && params.completionDateFrom != ""*/
AND STAFF_RECEIPT_DATE >= /*params.completionDateFrom*/1
/*%end*/
/*%if params.completionDateTo != null && params.completionDateTo != ""*/
AND STAFF_RECEIPT_DATE <= /*params.completionDateTo*/1
/*%end*/
/*%if params.orderCancelRequestDateFrom != null && params.orderCancelRequestDateFrom != ""*/
AND CANCEL_REQUEST_DATE >= /*params.orderCancelRequestDateFrom*/1
/*%end*/
/*%if params.orderCancelRequestDateTo != null && params.orderCancelRequestDateTo != ""*/
AND CANCEL_REQUEST_DATE <= /*params.orderCancelRequestDateTo*/1
/*%end*/
/*%if params.orderCancelAgreeDateFrom != null && params.orderCancelAgreeDateFrom != ""*/
AND CANCEL_AGREE_DATE >= /*params.orderCancelAgreeDateFrom*/1
/*%end*/
/*%if params.orderCancelAgreeDateTo != null && params.orderCancelAgreeDateTo != ""*/
AND CANCEL_AGREE_DATE <= /*params.orderCancelAgreeDateTo*/1
/*%end*/
;