package tgh.desktop.controllers;

import java.util.List;


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
import tgh.desktop.payload.request.OutComingResquest;
import tgh.desktop.payload.response.APIResponse;
import tgh.desktop.payload.response.DetailsResponse;
import tgh.desktop.payload.response.OutComingResponse;
import tgh.desktop.services.OutComingService;
import tgh.desktop.services.Queue_Service;
import tgh.desktop.services.AuthServiceImpl;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin("*")
@Controller
@RequiredArgsConstructor
@RestController 
@RequestMapping("outComing")
public class outComingController {
	private final AuthServiceImpl authServiceImpl;
	@Autowired
	Queue_Service queue_Service;
	

	@Autowired
	OutComingService outComingService ;
	@Autowired
	HttpServletRequest request;

	private final AuthController authService;
	
	@PostMapping("search")
	@PreAuthorize("hasAuthority('TghOutcoming.view')")
	public ResponseEntity<APIResponse> search(@RequestBody OutComingResquest SearchRequest) {
		
		APIResponse apiResponse = new APIResponse();
		System.out.println(authService.getOfficeName());
		String currentOffice = authService.getOfficeName();
		System.out.println("hhell");
		System.out.println(currentOffice);
		System.out.println(authService.getUserNameData());
		System.out.println(currentOffice);
		List<OutComingResponse> response=//authServiceImpl.retUserName()
				outComingService.search(authService.getUserNameData(),SearchRequest,currentOffice);
		
		System.out.println("queue_Entity =>"+SearchRequest);
		
		System.out.println("queue_Entity =>"+response);
		try {
		if(response.isEmpty()) {
			apiResponse.setStatus(HttpStatus.OK);
			apiResponse.setStatusCode(HttpStatus.OK.value());
			apiResponse.setClientMessage("No data found");
			return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);
		}
		else {
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
	
	
}
