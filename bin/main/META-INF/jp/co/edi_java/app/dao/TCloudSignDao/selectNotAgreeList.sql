SELECT *
FROM T_CLOUD_SIGN
WHERE FORM_TYPE = /*formType*/0
AND EXECUTION_DATE IS NULL
AND DELETE_FLG = '0';