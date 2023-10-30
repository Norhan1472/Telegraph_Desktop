package tgh.desktop.controllers;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tgh.desktop.globalControllers.AuthController;
import tgh.desktop.models.POST_OFFICE;
import tgh.desktop.models.TGH_Country;
import tgh.desktop.models.TGH_TELEGRAPH;
import tgh.desktop.models.TGH_MSG_STATUS;
import tgh.desktop.models.TGH_TELEGRAPH_HISTORY;
import tgh.desktop.payload.request.CancelTGH;
import tgh.desktop.payload.request.QueueSearchRequest;
import tgh.desktop.payload.response.*;
import tgh.desktop.repos.Queue_Repo;
import tgh.desktop.services.AuthServiceImpl;
import tgh.desktop.services.Queue_Service;
import tgh.desktop.services.inComingService;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin("*")
@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("Queue")
public class Queue_Controller {
	
	@Autowired
	private  Queue_Service queueService;
	@Autowired
	Queue_Repo queueRepo;
	@Autowired
	inComingService incomingService;

	private final AuthController authService;
	@Autowired
	HttpServletRequest request;
	@PostMapping("printTGH") //(@RequestParam(value = "TGH_ID", collectionFormat = "multi") List<String> tghIds)
	@PreAuthorize("hasAnyAuthority('TghIncoming.view','TghOutcoming.view')")
	public ResponseEntity<APIResponse> printTGH(@RequestBody List<String> TGH_ID){
		APIResponse apiResponse = new APIResponse();
		try {
			List<PrintResponse> queue_Entity=queueService.printTGH(TGH_ID);
			
			if(queue_Entity.isEmpty()) {
				apiResponse.setStatus(HttpStatus.OK);
				apiResponse.setStatusCode(HttpStatus.OK.value());
				apiResponse.setClientMessage("No data found");
				return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);
			}
			else {
				apiResponse.setStatus(HttpStatus.OK);
				apiResponse.setStatusCode(HttpStatus.OK.value());
				apiResponse.setBody(queue_Entity);
				return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);	
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			apiResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			apiResponse.setClientMessage(e.getMessage());
			apiResponse.setDeveloperMessage(e.getCause().toString());
			return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@GetMapping("getDetails")
	@PreAuthorize("hasAnyAuthority('TghIncoming.view','TghOutcoming.view')")
	public ResponseEntity<APIResponse> getDetails(@RequestParam Integer tghId) {
		
		APIResponse apiResponse = new APIResponse();
		
	try {
//		officeName

		String currentOffice = authService.getOfficeName();
		DetailsWithFlagResponse response = queueService.getTGHDetails(tghId,currentOffice);

		
		if(response == null)
		{
			apiResponse.setStatus(HttpStatus.OK);
			apiResponse.setStatusCode(HttpStatus.OK.value());
			apiResponse.setClientMessage("No data found");
			return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);
		}
		else
		{
			apiResponse.setStatus(HttpStatus.OK);
			apiResponse.setStatusCode(HttpStatus.OK.value());
			apiResponse.setBody(response);
			return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);
		}
	}
	catch(Exception e) {
		e.printStackTrace();
		apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		apiResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		apiResponse.setClientMessage(e.getMessage());
		
		return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	}

	@GetMapping("getCustomerDetails")
	@PreAuthorize("hasAuthority('TghCustomerDetailsDesktop.view')")
	public ResponseEntity<APIResponse> getCustomerDetails(@RequestParam Integer tghId) {
		//System.out.println("1234");

		APIResponse apiResponse = new APIResponse();

		try {

			CustomerDetails_Res response = queueService.getCustomerDetails(tghId);
			System.out.println("RESPONSE ==> "+response);

			if(response == null)
			{
				apiResponse.setStatus(HttpStatus.OK);
				apiResponse.setStatusCode(HttpStatus.OK.value());
				apiResponse.setClientMessage("No data found");
				return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);
			}
			else
			{
				apiResponse.setStatus(HttpStatus.OK);
				apiResponse.setStatusCode(HttpStatus.OK.value());
				apiResponse.setBody(response);
				return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			apiResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			apiResponse.setClientMessage(e.getMessage());

			return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

		@GetMapping("getPlans")
		@PreAuthorize("hasAnyAuthority('TghIncoming.view','TghOutcoming.view')")
	public ResponseEntity<APIResponse> getPlans(@RequestParam String countryCode, Integer admin){
		APIResponse apiResponse = new APIResponse();
		try {
			//List<PlanResponse> queue_Entity=queueService.getPlans(countryCode,admin);
			List<PlanResponse> queue_Entity=queueService.getPlansByActive(countryCode,admin,1);
//			System.out.println("queue_Entity="+queue_Entity);
			if(queue_Entity == null || queue_Entity.isEmpty()) {
				apiResponse.setStatus(HttpStatus.OK);
				apiResponse.setStatusCode(HttpStatus.OK.value());
				apiResponse.setClientMessage("No data found");
				return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);
			}
			else {
				
				apiResponse.setStatus(HttpStatus.OK);
				apiResponse.setStatusCode(HttpStatus.OK.value());
				apiResponse.setBody(queue_Entity);
				return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);	
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			apiResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			apiResponse.setClientMessage(e.getMessage());
			apiResponse.setDeveloperMessage(e.getCause().toString());
			return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
		
}
