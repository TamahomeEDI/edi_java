select * from T_LOCAL_DOCUMENT_LIST
where DELETE_FLG = '0'
/*%if entity.fileName != null && entity.fileName != ""*/
and FILE_NAME = /*entity.fileName*/'google'
/*%end*/
/*%if entity.filePath != null && entity.filePath != ""*/
and FILE_PATH = /*entity.filePath*/'google'
/*%end*/
/*%if entity.gyousyaCode != null && entity.gyousyaCode != ""*/
and GYOUSYA_CODE = /*entity.gyousyaCode*/'A000000'
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
/*%if entity.documentType != null && entity.documentType != ""*/
and DOCUMENT_TYPE = /*entity.documentType*/'0'
/*%end*/
/*%if entity.completeFlg != null && entity.completeFlg != ""*/
and COMPLETE_FLG = /*entity.completeFlg*/'0'
/*%end*/
;