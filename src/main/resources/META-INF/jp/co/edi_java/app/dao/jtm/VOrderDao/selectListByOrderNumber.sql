select * from V_ORDER
where
/*%if orderNumberList == null*/
1=2
/*%end*/
/*%if orderNumberList != null*/
DENPYOU_NO in /*orderNumberList*/('0')
/*%end*/
;