select
	*
from
	M_EIGYOUSYO_GROUP
where
	EIGYOUSYO_GROUP_CODE in (
		select
			EIGYOUSYO_GROUP_CODE
		from
			M_EIGYOUSYO
		where
			EIGYOUSYO_CODE in (
				select
					EIGYOUSYO_CODE
				from
					M_GYOUSYA_EIGYOUSYO
				where
					GYOUSYA_CODE = /*gyousyaCode*/0
				group by
					EIGYOUSYO_CODE
			)
		group by
			EIGYOUSYO_GROUP_CODE
	);