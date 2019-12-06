select
	*
from
	T_EXCLUSIVE
where
/*%if entity.exclusiveObjectName != null && entity.exclusiveObjectName != ""*/
EXCLUSIVE_OBJECT_NAME = /*entity.exclusiveObjectName*/'obj'
/*%end*/
/*%if entity.exclusiveObjectKey != null && entity.exclusiveObjectKey != ""*/
and EXCLUSIVE_OBJECT_KEY = /*entity.exclusiveObjectKey*/'obj'
/*%end*/
/*%if entity.exclusiveSessionId != null && entity.exclusiveSessionId != ""*/
and EXCLUSIVE_SESSION_ID != /*entity.exclusiveSessionId*/'obj'
/*%end*/
/*%if entity.exclusiveLimitDate != null */
and EXCLUSIVE_LIMIT_DATE >= /*entity.exclusiveLimitDate*/1
/*%end*/
order by EXCLUSIVE_OBJECT_NAME, EXCLUSIVE_OBJECT_KEY
for update
;

