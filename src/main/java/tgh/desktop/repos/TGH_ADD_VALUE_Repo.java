package tgh.desktop.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tgh.desktop.models.TGH_ADD_VALUE;
import tgh.desktop.payload.response.CalculateCostResponse;
import tgh.desktop.payload.response.Plan_Items_Cost_Respone;


@Repository
public interface TGH_ADD_VALUE_Repo extends JpaRepository<TGH_ADD_VALUE,Integer> {
	
	@Query(value = "SELECT  av.ITEM_NAME, av.ITEM_NO ,   av.ADD_PRICE  \r\n"
			+ "FROM ( TGH_ADD_VALUE  av inner join TGH_TARRIF_ADD TghTarrifAdd  on  av.ITEM_NO = TghTarrifAdd.ITEM_NO )\r\n"
			+ "WHERE  TghTarrifAdd.VALID = 1 and TghTarrifAdd.PLAN_CODE=:code "
			, nativeQuery = true) 
	List <TGH_ADD_VALUE> getallItems(String code);
	
	
	  
	  @Query(value = "SELECT tp.plan_name,tp.word_price,tp.URGENT_PRICE,TP.SMS_PRICE,"
	  		+ "tp.DECORATION_PRICE,tp.DELIVERY_PRICE,\n"
	  		+ "	  tp.INQUIRY_PRICE,tp.TEMPLATE_PRICE,tp.PO_PRICE,  a.item_name,a.add_price,tp.SALES_TAX,ta.TAX_ENABLED,ta.TEMP_ENABLED,\n"
	  		+ "	  TP.CHAR_WORD from TGH_TARIFF_PLAN TP, tgh_tarrif_add ta,tgh_add_value a \n"
	  		+ "	  where ta.item_no=a.item_no and ta.plan_code=Tp.plan_code \n"
	  		+ "	  and ta.plan_code=:code and ta.valid=1"
				, nativeQuery = true) 
		List <CalculateCostResponse> getCalItems(String code);
	  
	  
	  
	  @Query(value = "\n"
	  		+ "	  SELECT v.ITEM_NAME , v.ADD_PRICE ,a.TAX_ENABLED , a.TEMP_ENABLED , a.VALID\n"
	  		+ "	  from TGH_TARRIF_ADD a , TGH_ADD_VALUE v\n"
	  		+ "	  where v.ITEM_NO = a.ITEM_NO \n"
	  		+ "	  and a.PLAN_CODE = :code"
					, nativeQuery = true) 
	  List <Plan_Items_Cost_Respone> getPlanCost(String code);
}
