package tgh.desktop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tgh.desktop.payload.response.Plan_Items_Cost_Respone;
import tgh.desktop.payload.response.Tarrif_Cost_Plan_Response;
import tgh.desktop.repos.TARRIF_PLAN_Repo;
import tgh.desktop.repos.TGH_ADD_VALUE_Repo;



@Service
public class CostPlan_Service {
	
	@Autowired
	TGH_ADD_VALUE_Repo repo ;
	@Autowired
	TARRIF_PLAN_Repo  plan_Repo;
	
	public List<Plan_Items_Cost_Respone> getPlanCost(String planCode) {
		
		
		return repo.getPlanCost(planCode);
	}
	
public List<Tarrif_Cost_Plan_Response> getPlans() {
		
		
		return plan_Repo.getTarrif_Cost_Plan();
	}
}
