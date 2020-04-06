select
fi.FILE_ID,
fi.FILE_NAME,
fi.FILE_PATH,
fi.PARENT_FOLDER_ID,
fo.GYOUSYA_CODE,
fo.REPORT_TYPE,
fo.REPORT_YEAR,
fo.REPORT_MONTH,
fo.REPORT_YEAR_MONTH
from T_ARCHIVE_FILE fi
inner join T_ARCHIVE_FOLDER fo on fi.PARENT_FOLDER_ID = fo.FOLDER_ID
where fi.DELETE_FLG = '0'
and fo.DELETE_FLG = '0'
/*%if gyousyaCode == null || gyousyaCode == ""*/
and 1=2
/*%end*/
/*%if gyousyaCode != null && gyousyaCode != ""*/
and fo.GYOUSYA_CODE = /*gyousyaCode*/'A000000'
/*%end*/
/*%if reportType != null && reportType != ""*/
and fo.REPORT_TYPE = /*reportType*/'10'
/*%end*/
/*%if fromYearMonth != null && fromYearMonth != ""*/
and fo.REPORT_YEAR_MONTH >= /*fromYearMonth*/'202001'
/*%end*/
/*%if toYearMonth != null && toYearMonth != ""*/
and fo.REPORT_YEAR_MONTH <= /*toYearMonth*/'202003'
/*%end*/
;