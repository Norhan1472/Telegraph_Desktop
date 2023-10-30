package tgh.desktop.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tgh.desktop.models.TGH_COUNTRY_PLAN;
import tgh.desktop.payload.response.PlanResponse;



@Repository
public interface COUNTRY_PLAN_Repo  extends JpaRepository<TGH_COUNTRY_PLAN, String> {
  
	@Query(value = "SELECT cp.country_code , cp.plan_code ,  cp.plan_name , cp.active ,"
    		+ " cp.default_plan FROM"
    		+ "(TGH_COUNTRY_PLAN  cp inner JOIN TGH_COUNTRY tc on cp.COUNTRY_CODE = tc.COUNTRY_CODE)"
    		+ " INNER JOIN TGH_TARIFF_PLAN tp ON  cp.PLAN_CODE= tp.PLAN_CODE WHERE tc.INTERNATIONAL =:inter "
    		+ "and admin =:admin and tc.COUNTRY_CODE =:code "
			, nativeQuery = true)     
    List <TGH_COUNTRY_PLAN> findPlanInter(Integer admin ,Integer inter , String code);

    @Query(value = "SELECT cp.country_code , cp.plan_code ,  cp.plan_name , cp.active ,"
    		+ " cp.default_plan FROM"
    		+ "(TGH_COUNTRY_PLAN  cp inner JOIN TGH_COUNTRY tc"
    		+ " on cp.COUNTRY_CODE = tc.COUNTRY_CODE) INNER JOIN TGH_TARIFF_PLAN tp ON "
  				+ "cp.PLAN_CODE= tp.PLAN_CODE WHERE admin =:admin and tc.COUNTRY_CODE=:code"
    				, nativeQuery = true)
    		List <TGH_COUNTRY_PLAN> findPlanWithCode(Integer admin ,String code);
    		
    		@Query(value = "SELECT p.plan_code,p.plan_name,p.default_plan, \n"
    				+ "tp.TEMPLATE_ENABLE , tp.DELIVERY_ENABLE ,tp.URGENT_ENABLE, \n"
    				+ "tp.DECORATION_ENABLE \n"
    				+ ", tp.SMS_ENABLE  \n"
    				+ "from tgh_country_plan p INNER JOIN TGH_TARIFF_PLAN tp \n"
    				+ "On p.plan_code = tp.plan_code \n"
    				+ "where p.country_code=:country_code and tp.admin=:admin ", 
    				nativeQuery = true)
    		List<PlanResponse> getPlans(String country_code,Integer admin);

		@Query(value = "SELECT p.plan_code,p.plan_name,p.default_plan, \n"
			+ "tp.TEMPLATE_ENABLE , tp.DELIVERY_ENABLE ,tp.URGENT_ENABLE, \n"
			+ "tp.DECORATION_ENABLE \n"
			+ ", tp.SMS_ENABLE  \n"
			+ "from tgh_country_plan p INNER JOIN TGH_TARIFF_PLAN tp \n"
			+ "On p.plan_code = tp.plan_code \n"
			+ "where p.country_code=:country_code and tp.admin=:admin and p.active=:active",
			nativeQuery = true)
		List<PlanResponse> getPlansByActive(String country_code,Integer admin,Integer active);
    		
}
