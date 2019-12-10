SELECT * FROM V_ORDER
WHERE
/*%if prevDate != null*/
SAISYUU_KOUSIN_DATE >= /*prevDate*/'2019-08-01'
/*%end*/
;
