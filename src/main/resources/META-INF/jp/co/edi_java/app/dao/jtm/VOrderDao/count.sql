select COUNT(1) from V_ORDER
where
/*%if prevDate != null*/
SAISYUU_KOUSIN_DATE >= /*prevDate*/'2019-08-01'
/*%end*/

;