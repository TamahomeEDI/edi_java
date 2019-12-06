package jp.co.edi_java.app.dto;

import java.util.List;
import java.util.Map;

import jp.co.edi_java.app.entity.TOrderItemEntity;
import jp.co.edi_java.app.entity.VOrderStatusEntity;
import jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchVOrderDto {

	public VOrderStatusEntity order;

    public List<TOrderItemEntity> itemList;

    public List<Map<String, Object>> fileList;

    public List<TGyousyaMailaddressEntity> mailaddressList;

}