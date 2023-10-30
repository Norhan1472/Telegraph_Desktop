package tgh.desktop.controllers;


import java.awt.Image;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
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
import tgh.desktop.models.*;

import tgh.desktop.payload.request.DailyReportRequest;
import tgh.desktop.payload.request.TGH_Draft_Request;
import tgh.desktop.payload.request.calculateMSG_Request;
import tgh.desktop.payload.response.APIResponse;
import tgh.desktop.payload.response.ShowTGH_Response;
import tgh.desktop.payload.response.calculateMSG_Response;
import tgh.desktop.payload.response.lineInfo_Response;
import tgh.desktop.payload.response.tarrifplanMSGResponse;
import tgh.desktop.payload.response.tarrifplan_response;
import tgh.desktop.repos.*;
import tgh.desktop.services.AuthServiceImpl;
import tgh.desktop.services.Queue_Service;
import tgh.desktop.services.SOAPConnector;
import tgh.desktop.services.TGH_Service;
import tgh.desktop.services.getTGHcost;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin("*")
@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("Composer")
public class TGH_Controller {

//	@Autowired
//	private SoapClient soapClient;
	
	@Autowired
	TGH_Country_Repo country_Repo ;
	
//	@Autowired
//	getLineConfiguration lineConfiguration ;
	
	@Autowired
	private final AuthController authServiceImpl;
	
	@Autowired
	TARRIF_PLAN_Repo plan_Repo;
	@Autowired
	COUNTRY_PLAN_Repo country_PLAN_Repo;
	
	@Autowired
	private TGH_Service tgh_Service;

	@Autowired
	private Queue_Service queue_Service;
	
	@Autowired
	TGH_CITY_Repo city_Repo;
	@Autowired
	TGH_MSG_STATUS_Repo status_Repo;
	
	@Autowired
	getTGHcost getCost;
	@Autowired
	TGH_ADD_VALUE_Repo add_VALUE_Repo;
	
	@Autowired
	POST_OFFICE_Repo office_Repo ;

	@Autowired
	HttpServletRequest request2;
	@Autowired
	Queue_Repo queueRepo;
	
	@GetMapping("IntiateTGH")
//	@PreAuthorize("hasAnyAuthority('TghGenerator.create','TghGenerator.view')")
//	@PreAuthorize("hasAuthority('TghGenerator.create') and hasAuthority('TghGenerator.view')")
	public ResponseEntity<APIResponse> intiateTGH() {
		

		APIResponse	apiResponse = new APIResponse();  
		
			intiateTGH intiateTGH = new intiateTGH();
			intiateTGH.setGetGenId(tgh_Service.getGenId());
			intiateTGH.setTemplate_Type(tgh_Service.get_types());
			intiateTGH.setCountries(country_Repo.findByActive(1));
		    intiateTGH.setCallDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a")));
			apiResponse.setStatusCode(HttpStatus.OK.value());
			apiResponse.setStatus(HttpStatus.OK);
			apiResponse.setBody(intiateTGH);

			return  ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	
	}
	
	@GetMapping("getCountries")
	@PreAuthorize("hasAuthority('TghGeneratorDesktop.create')")
	public ResponseEntity<APIResponse> getCountries() {
		APIResponse	apiResponse = new APIResponse();
	
		List<TGH_Country> countries = country_Repo.findAll();
	
			apiResponse.setStatusCode(HttpStatus.OK.value());
			 apiResponse.setStatus(HttpStatus.OK);
			 apiResponse.setBody(countries);
			 return  ResponseEntity.status(HttpStatus.OK).body(apiResponse);	
		
	}
	

	@GetMapping("getPostalOffices")
	@PreAuthorize("hasAuthority('TghGeneratorDesktop.create')")
	public ResponseEntity<APIResponse> getInterPostalOffice(@RequestParam String cityCode) {
		APIResponse	apiResponse = new APIResponse();
	
		List<POST_OFFICE> offices = tgh_Service.getPostalOffice(cityCode);
	
			apiResponse.setStatusCode(HttpStatus.OK.value());
			 apiResponse.setStatus(HttpStatus.OK);
			 apiResponse.setBody(offices);
			 return  ResponseEntity.status(HttpStatus.OK).body(apiResponse);	
		
	}
	@GetMapping("getPostalOfficesWithoutCurrentOffice")
	@PreAuthorize("hasAuthority('TghGeneratorDesktop.create')")
	public ResponseEntity<APIResponse> getInterPostalOfficeWithoutCurrentOffice(@RequestParam String cityCode) {
		APIResponse	apiResponse = new APIResponse();

		List<POST_OFFICE> offices = tgh_Service.getPostalOfficeWithoutCurrentOffice(cityCode);

		apiResponse.setStatusCode(HttpStatus.OK.value());
		apiResponse.setStatus(HttpStatus.OK);
		apiResponse.setBody(offices);
		return  ResponseEntity.status(HttpStatus.OK).body(apiResponse);

	}
	@GetMapping("getOriginOffice")
	public ResponseEntity<APIResponse>findOriginOffice(@RequestParam String cityCode){
		APIResponse	apiResponse = new APIResponse();

		List<POST_OFFICE> offices = tgh_Service.getPostalOffice(cityCode);

		apiResponse.setStatusCode(HttpStatus.OK.value());
		apiResponse.setStatus(HttpStatus.OK);
		apiResponse.setBody(offices);
		return  ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}
	

	@GetMapping("VIP")
	@PreAuthorize("hasAuthority('TghGeneratorDesktop.create')")
	public ResponseEntity<APIResponse> getVip(@RequestParam String code ) {
		APIResponse	apiResponse = new APIResponse();
	
		 apiResponse.setStatusCode(HttpStatus.OK.value());
		 apiResponse.setStatus(HttpStatus.OK);
		 apiResponse.setBody(tgh_Service.getVip(code)); 
		  return ResponseEntity.ok(apiResponse);
	
	}
	
	@GetMapping("InterCities")
	@PreAuthorize("hasAuthority('TghGeneratorDesktop.create')")
	public ResponseEntity<APIResponse> getIntercity(@RequestParam String code ) {
		APIResponse	apiResponse = new APIResponse();

		List<TGH_CITY> cities = city_Repo.findInterCities(code);
	
		 apiResponse.setStatusCode(HttpStatus.OK.value());
		 apiResponse.setStatus(HttpStatus.OK);
		 apiResponse.setBody(cities); 
		 return ResponseEntity.ok(apiResponse);
		

	}
	
	@GetMapping("GetlocalCountry")
	@PreAuthorize("hasAuthority('TghGeneratorDesktop.create')")
	public ResponseEntity<APIResponse> getLocalCountries () {
		APIResponse	apiResponse = new APIResponse();
		
		List<TGH_Country > countries = tgh_Service.	gelocalCountry();
	
		 apiResponse.setStatusCode(HttpStatus.OK.value());
		 apiResponse.setStatus(HttpStatus.OK);
		 apiResponse.setBody(countries); 
		  return ResponseEntity.ok(apiResponse); 
		

	}
 
	
	 @PostMapping("calculateMsgCost")	  
	 @PreAuthorize("hasAuthority('TghGeneratorDesktop.create')")
	  public ResponseEntity<APIResponse> calculateMsgCost(@RequestBody  calculateMSG_Request calmst ) {
		 APIResponse apiResponse = new APIResponse();


		  if(calmst.getMessage().equals(null)||calmst.getMessage().isEmpty()
				  ||calmst.getMessage()==null ||calmst.getMessage().equals(""))
		  {
//			  apiResponse.setTimestamp(LocalDateTime.now());
			  apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
			  apiResponse.setStatus(HttpStatus.BAD_REQUEST);
			  apiResponse.setBody("You must Write message");
			  return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
		  }
//		  if(calmst.getBillTelNo().equals(null)||calmst.getBillTelNo().isEmpty()
//				  ||calmst.getBillTelNo()==null ||calmst.getBillTelNo().equals(""))
//		  {
////			  apiResponse.setTimestamp(LocalDateTime.now());
//			  apiResponse.setStatusCode(HttpStatus.OK.value());
//			  apiResponse.setStatus(HttpStatus.OK);
//			  apiResponse.setBody("You must Enter Bill Tel No");
//			  return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
//		  }


//		  System.out.println("cost" + cost);
		  apiResponse.setStatusCode(HttpStatus.OK.value());
		  apiResponse.setStatus(HttpStatus.OK);
		  apiResponse.setBody( tgh_Service.calculateCost(calmst));

		  return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

	}
	
	  @PostMapping("TGHGenerator")
	  @PreAuthorize("hasAuthority('TghGeneratorDesktop.create')")//368
	  public ResponseEntity<APIResponse> CreateTGH(@RequestBody TGH_Entity tghReq) { 
	  
		  APIResponse  apiResponse = new APIResponse();
	  	  
		  
	  Set<TGH_RECEPIENT> lst_rec = tghReq.getRecepients();
	  Set<TGH_SENDER> lst_senders=tghReq.getSenders();

	 // SOAPConnector connector = new SOAPConnector();
	  
	//  StringBuffer buffer = new StringBuffer(tghReq.getBillTelNo());
	//  buffer.deleteCharAt(0);
//	  billTelNo =  buffer.toString();
	// lineInfo_Response infoRespone = connector.getGeneralLineInfo( buffer.toString());
	
//	  if(infoRespone.getPaidMode()!=null) {
//		  if(infoRespone.getPaidMode().equals("non")){
//
////		  apiResponse.setTimestamp(LocalDateTime.now())
//			  apiResponse.setStatusCode(HttpStatus.FAILED_DEPENDENCY.value());
//			  apiResponse.setStatus(HttpStatus.FAILED_DEPENDENCY);
//			  apiResponse.setClientMessage("BSS IS DOWN");
//			  return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.FAILED_DEPENDENCY);
//		  };
//
//		  }
	  
	  List <TGH_RECEPIENT> lis_rec = new ArrayList<>(lst_rec);
	  List <TGH_SENDER> senders = new ArrayList<>(lst_senders);
	  calculateMSG_Request request = new calculateMSG_Request
			  (tghReq.getCallerName(),tghReq.getPlanCode(), tghReq.getTemplate(),
					  tghReq.getDeliveryNotic(),tghReq.getAdmin(), 
					  tghReq.getUrgent(), tghReq.getDecoration(),
					  tghReq.getSms(), tghReq.getMessage(),
					  senders, lis_rec);
	  calculateMSG_Response cal = tgh_Service.calculateCost(request);
	  System.out.println("cost => "+cal.getTotalcost());
	  
//	  System.out.println("infoRespone.getPaidMode()" +infoRespone.getPaidMode());
//	  if(!(infoRespone.getPaidMode().equals("non"))) {
//		  Double balance ;
//		  if(infoRespone.getBalance()==null)
//			  balance = 0.0;
//
//		  else
//			  balance=Double.parseDouble(infoRespone.getBalance()) ;
//
		  
		  
		  Double totalCost = Double.parseDouble(cal.getTotalcost());
		  System.out.println("totalCost" +totalCost);
		  
//			Integer	status= Integer.parseInt(infoRespone.getSubscriberStatus());
//
//			if(status == 4) {
//				apiResponse.setStatusCode(HttpStatus.FAILED_DEPENDENCY.value());
//				  apiResponse.setStatus(HttpStatus.FAILED_DEPENDENCY);
//				  apiResponse.setClientMessage("line is Inactive");
//				  return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.FAILED_DEPENDENCY);
//			}
//
//			System.out.println("BALANCE => "+balance);
//
//		  if (balance < totalCost) {
//			  apiResponse.setStatusCode(HttpStatus.FAILED_DEPENDENCY.value());
//			  apiResponse.setStatus(HttpStatus.FAILED_DEPENDENCY);
//			  apiResponse.setClientMessage("Your Balance Not Enough");
//			  return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.FAILED_DEPENDENCY);
//		  }
//
//	  }
	  
	 int i = 0;
System.out.println("outtt");
	  for (TGH_SENDER sender :lst_senders) { 
		
	  
	  if(sender.getSenderName()== null ||sender.getSenderName().equals("")) {
		  
//	  apiResponse.setTimestamp(LocalDateTime.now());
	  apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
	  apiResponse.setStatus(HttpStatus.BAD_REQUEST);
	  apiResponse.setClientMessage("You Must Enter SENDER Name");
	  return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	  }
		  sender.setPaidAmount(cal.getMessages().get(i++).getMessageCostByFees());
	  }
	  i=0;
	  
	  for (TGH_RECEPIENT rec :lst_rec) {
	  
	  if( rec.getAddress()== null ||rec.getAddress().equals("")) {
	  
//	  apiResponse.setTimestamp(LocalDateTime.now());
	  apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
	  apiResponse.setStatus(HttpStatus.BAD_REQUEST);
	  apiResponse.setClientMessage("You Must Enter RECEPIENT Address"); 
	  return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	  }
	  
	  else if(rec.getRecName().isEmpty() ||rec.getRecName().equals(null) ||
			  rec.getRecName()== null) {
	  
//	  apiResponse.setTimestamp(LocalDateTime.now());
	  apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
	  apiResponse.setStatus(HttpStatus.BAD_REQUEST);
	  apiResponse.setClientMessage("You Must Enter RECEPIENT Name"); 
	  return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	  }
		  rec.setPaidAmount(cal.getMessages().get(i++).getMessageCostByFees());
	  }
	  
//	  if(tghReq.getBillTelNo()== null || tghReq.getBillTelNo().equals("")) {
//
////	      apiResponse.setTimestamp(LocalDateTime.now());
//	   	  apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
//	   	  apiResponse.setStatus(HttpStatus.NOT_FOUND);
//	   	  apiResponse.setBody("You Must Enter BILL Tel Number");
//	   	return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.NOT_FOUND);
//	  }

	   if( tghReq.getCallerName()==null ||tghReq.getCallerName().equals(""))
			  {

//	      apiResponse.setTimestamp(LocalDateTime.now());
	   	  apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
	   	  apiResponse.setStatus(HttpStatus.BAD_REQUEST);
	   	  apiResponse.setBody("You Must Enter CAllER Name");
	   	return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.BAD_REQUEST);
			  }
	  else {
		  System.out.println("TGHREQ ==>" +tghReq.toString() );
			System.out.println("tghReq.getUserCode() in controller==> " + tghReq.getUserCode() );

			TGHCreate_respose response = tgh_Service.CreateTelegraoh(tghReq);


		  System.out.println("GENID == >" + response.getGenId() );
		   request2= ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				   .getRequest();
		   String currentOffice = request2.getSession().getAttribute("officeName").toString();
		   System.out.println(currentOffice);
		   queueRepo.updateOffice(currentOffice,response.getGenId());

//		  apiResponse.setTimestamp(LocalDateTime.now());
		  apiResponse.setStatusCode(HttpStatus.OK.value());
		  apiResponse.setStatus(HttpStatus.OK);
//		  apiResponse.setClientMessage("-*- Telegraph Added Successfully -*-");
		  apiResponse.setBody(response);
		  return ResponseEntity.ok(apiResponse);
	  }

	  }

	  @GetMapping("GetCallerName")
	  @PreAuthorize("hasAuthority('TghGeneratorDesktop.create')")
	  public ResponseEntity<APIResponse> getCallerName(@RequestParam Integer telNo) {
		
		  APIResponse apiResponse = new APIResponse();
		
		  
		 	 String name = tgh_Service.getCallerName(telNo);
			  apiResponse.setStatusCode(HttpStatus.OK.value());
			  apiResponse.setStatus(HttpStatus.OK);
			  apiResponse.setBody(name);
			  return ResponseEntity.ok(apiResponse);
		

	}

	  @PostMapping("TGHDraft")
	  @PreAuthorize("hasAuthority('TghGeneratorDesktop.create')")
	  public ResponseEntity<APIResponse> TGHDraft(@RequestBody TGH_DRAFT tghReq) { 
	  
		  APIResponse  apiResponse = new APIResponse();
		
				 tgh_Service.saveTghDraft(tghReq);
				  apiResponse.setStatusCode(HttpStatus.OK.value());
				  apiResponse.setStatus(HttpStatus.OK);
				  apiResponse.setClientMessage("*-* Save Telegraph Successfully *-*");
				  return ResponseEntity.status(HttpStatus.OK).body(apiResponse);		 

	  }

	  
	  @GetMapping("ShowTGH")
	  @PreAuthorize("hasAuthority('TghGeneratorDesktop.create')")
	  public ResponseEntity<APIResponse> showTGH(@RequestParam Integer genId) { 
	  
	  APIResponse  apiResponse = new APIResponse();
		  
	  
	  List<ShowTGH_Response> showresponse = new ArrayList<>();
	  
	  List<TGH_TELEGRAPH> queue  = queue_Service.showTgh(genId);
	  
	  System.out.println("queue == >"+ queue);
		
		  for (TGH_TELEGRAPH i : queue) {
	
		  ShowTGH_Response Response= new ShowTGH_Response();
		  
		  
			BeanUtils.copyProperties(i,Response);
			
			System.out.println("STat" + Response);
			
			TGH_MSG_STATUS status = status_Repo.findById(Response.getStatus()).get();
			
			
			System.out.println("STat" + status_Repo.findById(Response.getStatus()).get());
			
			if(Response.getOffice()== null)
				Response.setOffice(null);
			else {
				POST_OFFICE office = office_Repo.findById(Response.getOffice()).get();	
				Response.setOffice(office.getOfficeName());
				}
			
			
				Response.setStatus(status.getStatusName());
			
				
			showresponse.add(Response);
			
		}
	  

	  apiResponse.setStatusCode(HttpStatus.OK.value());
	  apiResponse.setStatus(HttpStatus.OK);
	  apiResponse.setBody(showresponse);
	  return ResponseEntity.status(HttpStatus.OK).body(apiResponse); 
	  
	
	  
	  }
	    
	  @GetMapping("GetLineInfo")
	  @PreAuthorize("hasAuthority('TghGeneratorDesktop.create')")
	  public ResponseEntity<APIResponse> GetLineInfo(@RequestParam  String   serviceNumber) { 
		  
		  APIResponse  apiResponse = new APIResponse();

          SOAPConnector connector = new SOAPConnector();

          StringBuffer buffer = new StringBuffer(serviceNumber);
    	  buffer.deleteCharAt(0); 
		  apiResponse.setStatusCode(HttpStatus.OK.value());
		  apiResponse.setStatus(HttpStatus.OK);
		  apiResponse.setBody(connector.getGeneralLineInfo(buffer.toString())); 
		  return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

	}

	  @GetMapping("getTghDraft")
	  @PreAuthorize("hasAuthority('TghGeneratorDesktop.create')")
	  public ResponseEntity<APIResponse> getTghDraft (@RequestParam(required = false) String user) {
		  
	  APIResponse  apiResponse = new APIResponse();

	  if((user==null || user==""  )) {
		  
		  apiResponse.setStatusCode(HttpStatus.OK.value());
		  apiResponse.setStatus(HttpStatus.OK);
		  apiResponse.setClientMessage("Enter a valid data.");
		  return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);
	  }

		  apiResponse.setStatusCode(HttpStatus.OK.value());
		  apiResponse.setStatus(HttpStatus.OK);		  
		  apiResponse.setBody(tgh_Service.getTghDraft(user));
		  return ResponseEntity.status(HttpStatus.OK).body(apiResponse); 
	 
	  
	  }
	  @PostMapping("DraftByGenId")
	  @PreAuthorize("hasAuthority('TghGeneratorDesktop.create')")
	  public ResponseEntity<APIResponse> DraftByGenId (@RequestBody TGH_Draft_Request req  ){ 
		
		  APIResponse  apiResponse = new APIResponse();
		  apiResponse.setStatusCode(HttpStatus.OK.value());
		  apiResponse.setStatus(HttpStatus.OK);
		  apiResponse.setBody(tgh_Service.DraftByGenId(req).orElse(null));
		  return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	  }
	  
	
	  @GetMapping("getOfficeByKeywords")
	  @PreAuthorize("hasAuthority('TghGeneratorDesktop.create')")
	  public ResponseEntity<APIResponse> getOfficeByKeywords 
	  	(@RequestParam String word ,@RequestParam String code ){
		
	  APIResponse  apiResponse = new APIResponse();

	  apiResponse.setStatusCode(HttpStatus.OK.value());
	  apiResponse.setStatus(HttpStatus.OK);
	  apiResponse.setBody(tgh_Service.replaceAddress(word,code));
	  return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
		  
	  }

	  @GetMapping("getUsersName")
	  @PreAuthorize("hasAuthority('TghGeneratorDesktop.create')")
		public ResponseEntity<APIResponse> getUsersName(@RequestParam String office){
			APIResponse apiResponse = new APIResponse();
			try {
				List<String> users = tgh_Service.getUsersName(office);

				if(users.isEmpty()) {
					apiResponse.setStatus(HttpStatus.OK);
					apiResponse.setStatusCode(HttpStatus.OK.value());
					apiResponse.setClientMessage("No data found");
					return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);
				}
				else {
					apiResponse.setStatus(HttpStatus.OK);
					apiResponse.setStatusCode(HttpStatus.OK.value());
					apiResponse.setBody(users);
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
	  
	  @PostMapping("printCalculateCost")
	  @PreAuthorize("hasAuthority('TghGeneratorDesktop.create')")
		public ResponseEntity<APIResponse> printCalculateCost(@RequestBody calculateMSG_Request calmst){
			APIResponse apiResponse = new APIResponse();
			apiResponse.setStatus(HttpStatus.OK);
			apiResponse.setStatusCode(HttpStatus.OK.value());
			apiResponse.setBody( tgh_Service.printCalculateCost(calmst));
			return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);	
			
		}

	  @PostMapping("DailyReport")
	  @PreAuthorize("hasAnyAuthority('TghGeneratorDesktop.create'"
	  		+ ",'TghDailyReportDesktop.view'"
	  		+ " ,'TghDailyReportDesktop.ViewAll')")
		public ResponseEntity<APIResponse> printCalculateCost
			(@RequestBody DailyReportRequest req){
			APIResponse apiResponse = new APIResponse();
			apiResponse.setStatus(HttpStatus.OK);
			apiResponse.setStatusCode(HttpStatus.OK.value());
			req.setUserName(authServiceImpl.getUserNameData());
			apiResponse.setBody( tgh_Service.DailyReport(req));
			return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);	
			
		}
	@GetMapping("getLanguage")
//	  @PreAuthorize("hasAnyAuthority('TghGenerator.create','TghGenerator.view'"
//	  		+ ",'TghDailyReport.view','TghDailyReport.ViewAll')")
	public ResponseEntity<APIResponse> getLanguage
			(@RequestParam String countryCode){


		APIResponse apiResponse = new APIResponse();

		try {

			if(tgh_Service.getLanguage(countryCode).isEmpty()) {
				apiResponse.setStatus(HttpStatus.OK);
				apiResponse.setStatusCode(HttpStatus.OK.value());
				apiResponse.setClientMessage("No data found");
				return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);
			}
			else {
				apiResponse.setStatus(HttpStatus.OK);
				apiResponse.setStatusCode(HttpStatus.OK.value());
//
				apiResponse.setBody( tgh_Service.getLanguage(countryCode));
				return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);

			}}
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

