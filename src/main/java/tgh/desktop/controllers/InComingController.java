package tgh.desktop.controllers;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import tgh.desktop.models.TGH_MSG_STATUS;
import tgh.desktop.models.Template_Entity;
import tgh.desktop.models.Template_Type;
import tgh.desktop.models.WFM_EMP;
import tgh.desktop.payload.request.InComingRequest;
import tgh.desktop.payload.request.OutComingResquest;
import tgh.desktop.payload.request.updateStatusRequest;
import tgh.desktop.payload.response.*;
import tgh.desktop.repos.IncomingRepo;
import tgh.desktop.repos.Queue_Repo;
import tgh.desktop.repos.TEMPLATE_reposatiry;
import tgh.desktop.repos.TGH_MSG_STATUS_Repo;
import tgh.desktop.services.*;

import javax.servlet.http.HttpServletRequest;


@CrossOrigin("*")
@Controller
@RequiredArgsConstructor
@RestController 
@RequestMapping("inComing")
public class InComingController {
	@Autowired
	Queue_Service queue_Service;
	@Autowired
	private TEMPLATE_reposatiry templateType_reposatiry ;
	
	private final AuthServiceImpl authServiceImpl;
	
	@Autowired
	inComingService inComingService ;
	
	@Autowired
	TGH_MSG_STATUS_Repo status_Repo;
	@Autowired
	Queue_Repo queueRepo;
	@Autowired
	LockService lockService;
	@Autowired
	HttpServletRequest request;
	@Autowired
	IncomingRepo  incomingRepo;

	private final AuthController authService;
	//search/TghIncoming.view
	@PostMapping("search")
	@PreAuthorize("hasAuthority('TghIncoming.view')")
	public ResponseEntity<APIResponse> search(@RequestBody InComingRequest SearchRequest) {
		IncomingSearchCustom searchCustom = new IncomingSearchCustom();
		APIResponse apiResponse = new APIResponse();

		String currentOffice = authService.getOfficeName();
		Integer countStatusWait = incomingRepo.getCountStatusWaiting(currentOffice);



		List<InComingResponse> response=
				inComingService.search(authService.getUserNameData(),SearchRequest,currentOffice);//authServiceImpl.retUserName()
		System.out.println("herere");
		searchCustom.setCount(countStatusWait);
		searchCustom.setInComingResponseList(response);

		System.out.println("queue_Entity =>"+SearchRequest);
		try {
		if(response.isEmpty()) {
			apiResponse.setStatus(HttpStatus.OK);
			apiResponse.setStatusCode(HttpStatus.OK.value());
			apiResponse.setBody(searchCustom);
			return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);
		}
		else {
			apiResponse.setStatus(HttpStatus.OK);
			apiResponse.setStatusCode(HttpStatus.OK.value());
			apiResponse.setBody(searchCustom);
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
	
	@GetMapping("getTemplates")
	public List<Template_Entity> getTemplate() {
		return templateType_reposatiry.findAll() ;	

}

	@GetMapping("getStauts")
	@PreAuthorize("hasAuthority('TghIncoming.view')")
	public List<TGH_MSG_STATUS> getStauts() {
		return status_Repo.statusForInComing();	

}

	@GetMapping("/startCheckLocking")
	public Map<String,Long> startCheckLocking(@RequestParam List<String> tghIds) {
		return lockService.startUpdating(tghIds);
	}
	@GetMapping("/getCurrentDate")
	public ResponseEntity<APIResponse> getCurrentDate(){
		APIResponse apiResponse = new APIResponse();
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		// Format the current date
		String formattedDate = dateFormat.format(currentDate);
		apiResponse.setStatus(HttpStatus.OK);
		apiResponse.setStatusCode(HttpStatus.OK.value());
		apiResponse.setBody(formattedDate);
		return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);
		//return formattedDate;
	}
	//inComing/updateStatus
	@PostMapping("updateStatus")
	public ResponseEntity<APIResponse> updateStatus(@RequestBody updateStatusRequest q) {
		APIResponse apiResponse = new APIResponse();
		
		
		try {
//			request= ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
//					.getRequest();
//			String username=request.getSession().getAttribute("userName").toString();
			String response = inComingService.updateStatus(authService.getUserNameData(),q);

			if(q.getOldStatus().equals("4")){
				if(q.getNewStatus().equals("10")){
					if(q.getOfficeCode()==null){

						apiResponse.setStatus(HttpStatus.BAD_REQUEST);
						apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
						apiResponse.setClientMessage("YOU MUST ENTER OFFICE CODE");
						return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.BAD_REQUEST);
					}
				}
			}
			if(q.getOldStatus().equals("3")){
				if(q.getNewStatus().equals("4")){
					if(q.getDispatchedTo()==null || q.getDispatchedTo()==""||q.getDispatchedTo().equals("")||q.getDispatchedTo().isEmpty()){
						apiResponse.setStatus(HttpStatus.BAD_REQUEST);
						apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
						apiResponse.setClientMessage("يجب ادخال تسليم الي");
						return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.BAD_REQUEST);
					}
				}
			}
			if(q.getOldStatus().equals("6") &&q.getNewStatus().equals("8")){
				if (q.getNotes()==""||q.getNotes()==null||q.getNotes().isEmpty())
				{
					apiResponse.setStatus(HttpStatus.BAD_REQUEST);
					apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
					apiResponse.setClientMessage("أدخل اسباب فشل الاستلام");
					return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.BAD_REQUEST);
				}

			}
			if(q.getOldStatus().equals("2") &&q.getNewStatus().equals("10")){
				if (q.getRedirctNotes()==""||q.getRedirctNotes()==null||q.getRedirctNotes().isEmpty())
				{
					apiResponse.setStatus(HttpStatus.BAD_REQUEST);
					apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
					apiResponse.setClientMessage("يجب ادخال اسباب اعادة التوجية");
					return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.BAD_REQUEST);
				}

			}
			if(q.getOldStatus().equals("5") &&q.getNewStatus().equals("6")){
				if (q.getDispatchedTo()==""||q.getDispatchedTo()==null||q.getDispatchedTo().isEmpty()||q.getDispatchedTime()==null){
						apiResponse.setStatus(HttpStatus.BAD_REQUEST);
						apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
						apiResponse.setClientMessage("يجب ادخال البيانات الناقصه");
						return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.BAD_REQUEST);
				}

			}
			if(q.getOldStatus().equals("6") &&q.getNewStatus().equals("7")){
				if(q.getActualRecName()==""||q.getActualRecName()== null
						||q.getActualRecDate().equals(null)||q.getActualRecDate()==null
						||q.getReceiptNo()==""||q.getReceiptNo()==null){
					apiResponse.setStatus(HttpStatus.BAD_REQUEST);
					apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
					apiResponse.setClientMessage("الرجاء ادخال البيانات الناقصة ");
					return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.BAD_REQUEST);
				}
			}
			if(response.equals(null)||response == null ||response.isEmpty())
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
				apiResponse.setClientMessage(response);
				return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);
			}
		}
		
		catch(Exception e) {
			e.printStackTrace();
			apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			apiResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			apiResponse.setClientMessage(e.getMessage());
			//apiResponse.setDeveloperMessage(e.getCause().toString());
			return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		

}

	@GetMapping("getEmployee_Shifts")
	@PreAuthorize("hasAuthority('TghIncoming.view')")
	public ResponseEntity<APIResponse> getEmployee_Shifts() {
		
	APIResponse apiResponse = new APIResponse();
		
		
		try {
			
			List<WFM_EMP> response =
					inComingService.getEmployee_Shifts();
			
			if(response.isEmpty()) {
			
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
			apiResponse.setDeveloperMessage(e.getCause().toString());
			return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

}
	
	@GetMapping("getNumberOfTGH")
	@PreAuthorize("hasAuthority('TghIncoming.view')")
	public  ResponseEntity<APIResponse> getNumberOfTGH(@RequestParam String username) {

		
		APIResponse apiResponse = new APIResponse();
	try{
		if(inComingService.getNumberOfTGH(username)==null) {
			
			apiResponse.setStatus(HttpStatus.OK);
			apiResponse.setStatusCode(HttpStatus.OK.value());
			apiResponse.setClientMessage("No data found");
			return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);}
		else {
			apiResponse.setStatus(HttpStatus.OK);
			apiResponse.setStatusCode(HttpStatus.OK.value());
			apiResponse.setBody(inComingService.getNumberOfTGH(username));
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
