package jp.co.edi_java.app.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

import jp.co.edi_java.app.dto.SearchDeliveryDto;
import jp.co.edi_java.app.dto.SearchEstimateDto;
import jp.co.edi_java.app.dto.SearchKoujiInfoDto;
import jp.co.edi_java.app.dto.SearchOrderInfoDto;
import jp.co.edi_java.app.dto.SearchWorkReportDto;
import jp.co.edi_java.app.entity.TestEntity;
import jp.co.edi_java.app.form.SearchForm;

@Dao
@ConfigAutowireable
public interface SearchDao {

	default Config getInjectedConfig() {
        return Config.get(this);
    }

	@Select
	List<SearchEstimateDto> selectEstimate(SearchForm params, int limit, int offset);

	@Select
	int countEstimate(SearchForm params);

	@Select
	int countDelivery(SearchForm params);

	@Select
	int countWorkReport(SearchForm params);

	@Select
	int countKoujiInfo(String koujiCode, String koujiName, String eigyousyoCode, String kanseiKubun);

	@Select
	SearchOrderInfoDto selectOrderInfo(String orderNumber, SearchForm params, String orderDate);

	@Select
	SearchOrderInfoDto getOrderInfo(String orderNumber, String orderDate);

	@Select
	SearchKoujiInfoDto selectKoujiInfo(String koujiCode, String koujiName);

	@Select
    List<SearchDeliveryDto> selectDelivery(SearchForm params, int limit, int offset);

	@Select
    List<SearchWorkReportDto> selectWorkReport(SearchForm params, int limit, int offset);

	@Select
    List<TestEntity> selectInspectionReceipt();

	// チェックリスト出力用
	@Select
	List<SearchOrderInfoDto> getOrderInfoList(List<String> orderNumber);
	@Select
	List<SearchOrderInfoDto> selectOrderInfoList(List<String> orderNumber, SearchForm params);
	@Select
	List<SearchKoujiInfoDto> getKoujiInfoList(List<String> koujiCodeList, String koujiName, String eigyousyoCode, String kanseiKubun);

}
