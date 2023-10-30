package tgh.desktop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tgh.desktop.payload.response.Plan_Items_Cost_Respone;
import tgh.desktop.payload.response.Tarrif_Cost_Plan_Response;
import tgh.desktop.services.CostPlan_Service;

@CrossOrigin("*")
@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("planCost")
public class planCost_Controller {

	@Autowired
	CostPlan_Service service;

	@GetMapping("planCost")
	@PreAuthorize("hasAuthority('TghPlanCostDesktop.view')")
	public List<Plan_Items_Cost_Respone> getPlanCost(@RequestParam String planCode) {
		return service.getPlanCost(planCode);
	}
	
	@GetMapping("getPlans")
	@PreAuthorize("hasAuthority('TghPlanCostDesktop.view')")
	public List<Tarrif_Cost_Plan_Response> getPlans() {
		return service.getPlans();
	}
	
	
	
}
