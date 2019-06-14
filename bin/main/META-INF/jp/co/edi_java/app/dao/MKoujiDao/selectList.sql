select *
from M_KOUJI
where EIGYOUSYO_CODE = /*eigyousyoCode*/0
/*%if kanseiKbn == "1"*/
and KANSEI_KUBUN = '1'
/*%elseif kanseiKbn == "2"*/
and KANSEI_KUBUN = '2'
/*%elseif kanseiKbn == "3"*/
and KANSEI_KUBUN = '3'
/*%elseif kanseiKbn == "4"*/
and KANSEI_KUBUN != '3'
/*%end*/
/*%if syainCode != null && syainCode != ""*/
and TANTOU_SYAIN_CODE = /*syainCode*/1
/*%end*/
/*%if projectTypeCode != null && projectTypeCode != ""*/
and PROJECT_TYPE = /*projectTypeCode*/1
/*%end*/
order by KEIYAKUSYA_KANA collate utf8_unicode_ci asc;