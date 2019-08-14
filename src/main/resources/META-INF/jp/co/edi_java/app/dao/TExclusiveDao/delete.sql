delete from T_EXCLUSIVE
where
EXCLUSIVE_OBJECT_NAME = /*entity.exclusiveObjectName*/'obj'

and EXCLUSIVE_OBJECT_KEY = /*entity.exclusiveObjectKey*/'obj'

and EXCLUSIVE_SESSION_ID = /*entity.exclusiveSessionId*/'obj'

;

