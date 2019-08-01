select * from T_ORDER
where CONFIRMATION_REQUEST_DATE is not null
and (CANCEL_REQUEST_DATE is null or CANCEL_FLG = '0')
and DELETE_FLG = '0';