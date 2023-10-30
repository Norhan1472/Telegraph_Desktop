package tgh.desktop.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tgh.desktop.models.TGH_TARRIF_PLAN;
import tgh.desktop.payload.response.Plan_Items_Cost_Respone;
import tgh.desktop.payload.response.Tarrif_Cost_Plan_Response;

@Repository
public interface TARRIF_PLAN_Repo  extends JpaRepository<TGH_TARRIF_PLAN, String> {

	@Query(value = "SELECT tp.PLAN_CODE , tp.PLAN_NAME ,tp.TEMPLATE_ENABLE ,tp.DELIVERY_ENABLE"
			+ " ,tp.URGENT_ENABLE ,tp.DECORATION_ENABLE ,tp.WORD_PRICE ,tp.DECORATION_PRICE,"
			+ "tp.PO_PRICE,URGENT_PRICE , tp.TEMPLATE_PRICE ,tp.DELIVERY_PRICE ,tp.ADMIN , "
			+ "tp.CHAR_WORD , tp.INQUIRY_PRICE , tp.SALES_TAX ,tp.SMS_ENABLE,tp.SMS_PRICE "
			+ "FROM TGH_TARIFF_PLAN tp , TGH_COUNTRY_PLAN cp"
			+ " WHERE tp.PLAN_CODE=cp.PLAN_CODE AND tp.admin = 0 AND cp.Country_code = 'EG'"
			+ " ORDER BY cp.default_plan DESC"
			, nativeQuery = true) 
	List <TGH_TARRIF_PLAN> findPlanNoAdmin();

	@Query(value = "SELECT tp.PLAN_CODE ,tp.PLAN_NAME ,tp.TEMPLATE_ENABLE ,tp.DELIVERY_ENABLE,"
			+ "tp.URGENT_ENABLE ,tp.DECORATION_ENABLE ,tp.WORD_PRICE ,tp.DECORATION_PRICE ,"
			+ "tp.PO_PRICE,URGENT_PRICE , tp.TEMPLATE_PRICE ,tp.DELIVERY_PRICE ,tp.ADMIN ,"
			+ "tp.CHAR_WORD , tp.INQUIRY_PRICE , tp.SALES_TAX ,tp.SMS_ENABLE,tp.SMS_PRICE "
			+ "FROM TGH_TARIFF_PLAN tp ,"
			+ "TGH_COUNTRY_PLAN cp WHERE tp.PLAN_CODE=cp.PLAN_CODE AND tp.PLAN_CODE =:code"
			, nativeQuery = true)
	TGH_TARRIF_PLAN findEnablePlan(String code);
	
	@Query(value = "SELECT tp.PLAN_CODE ,tp.PLAN_NAME ,tp.TEMPLATE_ENABLE ,tp.DELIVERY_ENABLE,\r\n"
			+ "tp.URGENT_ENABLE ,tp.DECORATION_ENABLE,tp.WORDS_COUNT ,tp.WORD_PRICE ,tp.DECORATION_PRICE ,\r\n"
			+ "tp.PO_PRICE,URGENT_PRICE , tp.TEMPLATE_PRICE ,tp.DELIVERY_PRICE ,tp.ADMIN ,\r\n"
			+ "tp.CHAR_WORD , tp.INQUIRY_PRICE , tp.SALES_TAX,tp.SMS_ENABLE,tp.SMS_PRICE FROM TGH_TARIFF_PLAN tp \r\n"
			+ " WHERE  tp.PLAN_CODE=:planCode"
			, nativeQuery = true)
	TGH_TARRIF_PLAN findByPlanCodeQ(String planCode);
	
	@Query(value = "Select tp.PLAN_CODE ,tp.PLAN_NAME , tp.TEMPLATE_ENABLE\n"
			+ ",tp.DELIVERY_ENABLE,tp.URGENT_ENABLE,tp.DECORATION_ENABLE ,\n"
			+ "tp.WORD_PRICE,tp.DECORATION_PRICE,tp.DELIVERY_PRICE\n"
			+ ",tp.PO_PRICE,tp.URGENT_PRICE,tp.TEMPLATE_PRICE\n"
			+ ",tp.ADMIN,tp.INQUIRY_PRICE,tp.SMS_ENABLE,tp.SMS_PRICE,tp.CHAR_WORD\n"			
			+ "from TGH_TARIFF_PLAN tp , TGH_COUNTRY_PLAN cp , TGH_COUNTRY c \n"
			+ "  where tp.PLAN_CODE=cp.PLAN_CODE \n"
			+ "  (+) and cp.COUNTRY_CODE = c.COUNTRY_CODE (+)\n"
			+ "  and c.INTERNATIONAL = 0 "
			, nativeQuery = true)
	List <Tarrif_Cost_Plan_Response> getTarrif_Cost_Plan();
	
	
	

	@Query(value = "SELECT v.ITEM_NAME , v.ADD_PRICE ,a.TAX_ENABLED , a.TEMP_ENABLED , a.VALID\n"
			+ "	from TGH_TARRIF_ADD a , TGH_ADD_VALUE v\n"
			+ "	where v.ITEM_NO = a.ITEM_NO \n"
			+ "	and a.PLAN_CODE =:planCode"
			, nativeQuery = true)
	Plan_Items_Cost_Respone getCost_Plan(String planCode);
	
}


