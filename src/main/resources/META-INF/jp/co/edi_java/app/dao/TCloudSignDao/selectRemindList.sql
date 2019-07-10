select *
from T_CLOUD_SIGN
where EXECUTION_DATE is null
and DELETE_FLG = '0'
and APPLICATION_DATE >= /*applicationDateFrom*/1
and APPLICATION_DATE < /*applicationDateTo*/1;