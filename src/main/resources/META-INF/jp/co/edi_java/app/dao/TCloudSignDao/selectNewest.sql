select *
from T_CLOUD_SIGN
where DELETE_FLG = '0'
and FORM_TYPE = /*formType*/1
and ORDER_NUMBER = /*orderNumber*/1
order by
	INSERT_DATE desc
limit 1;