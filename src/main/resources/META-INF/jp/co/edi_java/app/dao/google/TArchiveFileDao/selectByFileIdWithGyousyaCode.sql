select * from T_ARCHIVE_FILE
where DELETE_FLG = '0'
and FILE_ID = /*fileId*/'google'
and PARENT_FOLDER_ID in (
select FOLDER_ID from T_ARCHIVE_FOLDER
where DELETE_FLG = '0'
and GYOUSYA_CODE = /*gyousyaCode*/'A000000'
)
;
