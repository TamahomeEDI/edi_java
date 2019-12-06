select * from T_WORK_REPORT
where DELETE_FLG = '0'
and ORDER_NUMBER = /*orderNumber*/0
and WORK_REPORT_COUNT = /*workReportCount*/0
and REMAND_FLG != '1';