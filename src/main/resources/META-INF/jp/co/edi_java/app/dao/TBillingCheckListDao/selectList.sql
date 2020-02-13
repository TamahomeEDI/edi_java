select * from T_BILLING_CHECK_LIST bt
where bt.DELETE_FLG = '0'
/*%if entity.fileName != null && entity.fileName != ""*/
and bt.FILE_NAME = /*entity.fileName*/'google'
/*%end*/
/*%if entity.filePath != null && entity.filePath != ""*/
and bt.FILE_PATH = /*entity.filePath*/'google'
/*%end*/
/*%if entity.gyousyaCode != null && entity.gyousyaCode != ""*/
and bt.GYOUSYA_CODE = /*entity.gyousyaCode*/'A000000'
/*%end*/
/*%if entity.reportYear != null && entity.reportYear != ""*/
and bt.REPORT_YEAR = /*entity.reportYear*/'2020'
/*%end*/
/*%if entity.reportMonth != null && entity.reportMonth != ""*/
and bt.REPORT_MONTH = /*entity.reportMonth*/'10'
/*%end*/
/*%if entity.reportYearMonth != null && entity.reportYearMonth != ""*/
and bt.REPORT_YEAR_MONTH = /*entity.reportYearMonth*/'202010'
/*%end*/
/*%if entity.completeFlg != null && entity.completeFlg != ""*/
and bt.COMPLETE_FLG = /*entity.completeFlg*/'0'
/*%end*/
;