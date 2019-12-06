select * from M_KOUJI
where
/*%if koujiCodeList == null */
1=2
/*%end*/
/*%if koujiCodeList != null */
KOUJI_CODE in /*koujiCodeList*/(0)
/*%end*/
order by KOUJI_CODE;