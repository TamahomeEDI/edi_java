INSERT INTO T_ORDER_KEYWORD(ORDER_NUMBER,ORDER_KEYWORD)
SELECT o.ORDER_NUMBER,
CONCAT(REPLACE(COALESCE(k.KOUJI_CODE,""),"-","")," ",COALESCE(k.KOUJI_CODE,"")," ",
COALESCE(k.KOUJI_NAME,"")," ",COALESCE(o.ORDER_NUMBER,"")," ",COALESCE(e.EIGYOUSYO_NAME,"")," ",
COALESCE(g.GYOUSYA_NAME,"")," ",COALESCE(g.GYOUSYA_KANA,"")," ",COALESCE(s.SYAIN_NAME,"")," ",
COALESCE(sai.SAIMOKU_KOUSYU_NAME,""))
FROM T_ORDER o
LEFT JOIN M_KOUJI k ON k.KOUJI_CODE = o.KOUJI_CODE
LEFT JOIN M_EIGYOUSYO e ON e.EIGYOUSYO_CODE = k.EIGYOUSYO_CODE
LEFT JOIN M_GYOUSYA g ON g.GYOUSYA_CODE = o.GYOUSYA_CODE
LEFT JOIN M_SYAIN s ON s.SYAIN_CODE = k.TANTOU_SYAIN_CODE
LEFT JOIN M_SAIMOKU_KOUSYU sai ON sai.SAIMOKU_KOUSYU_CODE = o.SAIMOKU_KOUSYU_CODE
WHERE o.ORDER_NUMBER = /*orderNumber*/'0'
;