select * from T_ARCHIVE_FOLDER fo
where fo.DELETE_FLG = '0'
/*%if entity.folderId != null && entity.folderId != ""*/
and fo.FOLDER_ID = /*entity.folderId*/'google'
/*%end*/
/*%if entity.folderName != null && entity.folderName != ""*/
and fo.FOLDER_NAME = /*entity.folderName*/'google'
/*%end*/
/*%if entity.folderPath != null && entity.folderPath != ""*/
and fo.FOLDER_PATH = /*entity.folderPath*/'google'
/*%end*/
/*%if entity.gyousyaCode != null && entity.gyousyaCode != ""*/
and fo.GYOUSYA_CODE = /*entity.gyousyaCode*/'A000000'
/*%end*/
/*%if entity.reportType != null && entity.reportType != ""*/
and fo.REPORT_TYPE = /*entity.reportType*/'10'
/*%end*/
/*%if entity.reportYear != null && entity.reportYear != ""*/
and fo.REPORT_YEAR = /*entity.reportYear*/'2020'
/*%end*/
/*%if entity.reportMonth != null && entity.reportMonth != ""*/
and fo.REPORT_MONTH = /*entity.reportMonth*/'10'
/*%end*/
/*%if entity.reportYearMonth != null && entity.reportYearMonth != ""*/
and fo.REPORT_YEAR_MONTH = /*entity.reportYearMonth*/'202010'
/*%end*/
/*%if entity.ignoreArchiveFinished != null && entity.ignoreArchiveFinished == "1"*/
and not exists (select 1 from T_ARCHIVE_FILE fi where fi.PARENT_FOLDER_ID = fo.FOLDER_ID and fi.DELETE_FLG = '0')
/*%end*/
;