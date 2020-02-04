select * from T_ARCHIVE_FOLDER
where DELETE_FLG = '0'
/*%if entity.folderId != null && entity.folderId != ""*/
and FOLDER_ID = /*entity.folderId*/'google'
/*%end*/
/*%if entity.folderName != null && entity.folderName != ""*/
and FOLDER_NAME = /*entity.folderName*/'google'
/*%end*/
/*%if entity.folderPath != null && entity.folderPath != ""*/
and FOLDER_PATH = /*entity.folderPath*/'google'
/*%end*/
/*%if entity.gyousyaCode != null && entity.gyousyaCode != ""*/
and GYOUSYA_CODE = /*entity.gyousyaCode*/'A000000'
/*%end*/
/*%if entity.reportType != null && entity.reportType != ""*/
and REPORT_TYPE = /*entity.reportType*/'10'
/*%end*/
/*%if entity.reportYear != null && entity.reportYear != ""*/
and REPORT_YEAR = /*entity.reportYear*/'2020'
/*%end*/
/*%if entity.reportMonth != null && entity.reportMonth != ""*/
and REPORT_MONTH = /*entity.reportMonth*/'10'
/*%end*/
/*%if entity.reportYearMonth != null && entity.reportYearMonth != ""*/
and REPORT_YEAR_MONTH = /*entity.reportYearMonth*/'202010'
/*%end*/
;