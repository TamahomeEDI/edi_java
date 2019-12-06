select * from T_DELIVERY
where DELETE_FLG = '0'
and ORDER_NUMBER = /*orderNumber*/0
and DELIVERY_COUNT = /*deliveryCount*/0
and REMAND_FLG != '1';