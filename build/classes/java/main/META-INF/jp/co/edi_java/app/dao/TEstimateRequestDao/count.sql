select LPAD(COUNT(ESTIMATE_REQUEST_NUMBER),4,'0')
from T_ESTIMATE_REQUEST ESR
left outer join M_KOUJI KOU on ESR.KOUJI_CODE = KOU.KOUJI_CODE
where KOU.EIGYOUSYO_CODE = /* eigyousyoCode */0
and ESR.ESTIMATE_REQUEST_DATE >= /* from */0
and ESR.ESTIMATE_REQUEST_DATE < /* to */0;