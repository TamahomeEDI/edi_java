select * from M_SYAIN
where
/*%if syainCodeList == null */
1=2
/*%end*/
/*%if syainCodeList != null */
SYAIN_CODE in /*syainCodeList*/(0)
/*%end*/
;