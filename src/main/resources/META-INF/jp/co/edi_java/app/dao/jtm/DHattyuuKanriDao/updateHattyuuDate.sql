update D_HATTYUU_KANRI set HATTYUU_DATE = /*orderDate*/'99990101', HATTYUU_SYAIN_CODE = /*syainCode*/'0', SAISYUU_KOUSIN_DATE = SYSDATE, SYSTEM_USER_ID = /*syainCode*/'0'
where
GROUP_CODE = 'tama'
and KAISYA_CODE = '1000'
and KOUJI_CODE = /*koujiCode*/'0'
and HATTYUU_SYUBETU_FLG = /*jtmHattyuuSyubetuFlg*/0
and HATTYUU_SEQ_NO = /*jtmHattyuuSeqNo*/0
and KANRI_SEQ_NO = /*jtmKanriSeqNo*/0
and DENPYOU_NO = /*orderNumber*/'0'
;