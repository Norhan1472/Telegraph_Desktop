package tgh.desktop.globalControllers;

import javax.servlet.http.Cookie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.RequiredArgsConstructor;
import  tgh.desktop.payload.request.CHG_Password_Request;
import tgh.desktop.payload.request.LoginRequest;
import tgh.desktop.payload.response.APIResponse;
import tgh.desktop.payload.response.OfficeUser_Response;
import tgh.desktop.repos.SC_USERS_Repo;
import tgh.desktop.repos.SysConfigDAO;
import tgh.desktop.security.WLSJmxInterface;
import tgh.desktop.services.AuthService;
import tgh.desktop.services.AuthServiceImpl;
import tgh.desktop.services.GlobalService;
import tgh.desktop.services.SysConfigService;

import java.net.SocketException;
import java.net.URI;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;
@CrossOrigin("*")
@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController { 
	private final AuthService authService;
	private final AuthServiceImpl authServiceImpl;
	private static final Logger logger = LogManager.getLogger(AuthController.class);
	
	@Autowired 
	private SysConfigService service;
	@Autowired
	SysConfigDAO dao;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse httpResponse;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private SC_USERS_Repo sc_USERS_Repo;
	
	@Value("${referer.name}")
    private String refererName;
	
	WLSJmxInterface wlsAuth = new WLSJmxInterface();
	
	private String J_S_ID;
	
	@SuppressWarnings("static-access")
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) throws SocketException {
		logger.info("Login Request for user: {} ", loginRequest.getUsername());
		try { 
			
//			  String decryptedPassword1= GlobalService.decrypt(loginRequest.getPassword());
//				System.out.println("Engy1 = " + loginRequest.getPassword());

			APIResponse response = new APIResponse();
			ResponseEntity<String> WLResponse;
			
		    
			if(loginRequest.getUsername() == null || loginRequest.getUsername() == "" 
					|| loginRequest.getPassword() == null || loginRequest.getPassword() == "") 
			{
				String userNAME=null;
				String username = "";
				request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
			            .getRequest();
				Cookie[] cookies = request.getCookies();
			    if(cookies !=null){
			    for(Cookie cookie : cookies){
			    	System.out.println("cookie= " + cookie);
			    	System.out.println("cookie getName=" + cookie.getName());
			    	System.out.println("cookie getValue=" + cookie.getValue());
			    	System.out.println("cookie JSESSIONID check=" + cookie.getName().equals("JSESSIONID"));
			    	if(cookie.getName().equals("JSESSIONID")){
			    		J_S_ID=cookie.getValue();
						System.out.println("JSESSIONID in signin= "+ J_S_ID);
						username = request.getSession().getAttribute("userName").toString();
						break;
					 }
			      }
				    MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
					headers.set("Host",request.getServerName()+ ":" +request.getServerPort());
					headers.set("Cookie","JSESSIONID="+ J_S_ID);
					headers.set("Content-Type", "application/json");
					//userNAME=authServiceImpl.retUserName();
					System.out.println("userNAME= " + userNAME);
			    }
				if(username==null || username.equals(null))
				{
					response.setStatus(HttpStatus.UNAUTHORIZED);
					response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
					response.setClientMessage("Missing username or password");
					return new ResponseEntity<APIResponse>(response, HttpStatus.UNAUTHORIZED);
				}
					loginRequest.setUsername(username);
					response.setStatus(HttpStatus.OK);
					response.setStatusCode(HttpStatus.OK.value());
					response.setClientMessage(userNAME + " is already logged in");
					return ResponseEntity.ok(authService.authenticateUser(loginRequest));
			}
			else 
     		{

				if(wlsAuth.isUserLocked(loginRequest.getUsername())==true) {
					  System.out.println("GWA EL LOCK ");
						response.setStatus(HttpStatus.UNAUTHORIZED);
						response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
						response.setClientMessage("User is locked ");
						return new ResponseEntity<APIResponse>(response, HttpStatus.UNAUTHORIZED);
				}
				else {
				  wlsAuth.postConstruct(service.getWLSConfig());
				  String WL_IP=dao.getWLSConfig().getMAINSCREEN_IP();
				  RestTemplate restTemplate=new RestTemplate();
				  String decryptedPassword= GlobalService.decrypt(loginRequest.getPassword());
				  loginRequest.setPassword(decryptedPassword);
				  String WLURL= WL_IP+ refererName +"/j_security_check?j_username=" + loginRequest.getUsername() + "&j_password=" + URLEncoder.encode(loginRequest.getPassword().trim(), "UTF-8");
				  System.out.println("WLURL = " + WLURL);
				  URI uri = new URI(WLURL);

				  HttpEntity<String> s = null;
				  WLResponse=restTemplate.postForEntity(uri,s, String.class);
				  if(WLResponse.getStatusCodeValue()==303) {
					  System.out.println("GWA EL IF, REDIRECT= " + WLResponse.getStatusCode());
					  String jsessionid = WLResponse.getHeaders().getFirst("Set-Cookie");
                      jsessionid = jsessionid.split(";")[0].split("=")[1];
                      System.out.println("jsessionid="+jsessionid);
                      Cookie jsessionidCookie = new Cookie("JSESSIONID", jsessionid);
                 	  jsessionidCookie.setPath("/");
                      httpResponse.addCookie(jsessionidCookie);
                      
                      System.out.println("authService.authenticateUser(loginRequest)"+
                    		  authService.authenticateUser(loginRequest));
					  return ResponseEntity.ok(authService.authenticateUser(loginRequest));
				  }
				else {
					    System.out.println("BARA EL IF= " + WLResponse.getStatusCode());
						response.setStatus(HttpStatus.UNAUTHORIZED);
						response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
						response.setClientMessage("Invalid username or password");
						return new ResponseEntity<APIResponse>(response, HttpStatus.UNAUTHORIZED);
				  }
				}
     		}
		}
		catch(Exception e)
		{
			if(wlsAuth.isUserLocked(loginRequest.getUsername())==true) {
				  System.out.println("GWA EL LOCK MN Catch " );
				    APIResponse response = new APIResponse();
					response.setStatus(HttpStatus.UNAUTHORIZED);
					response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
					response.setClientMessage("User is locked ");
					return new ResponseEntity<APIResponse>(response, HttpStatus.UNAUTHORIZED);
			  }
			
			
			request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
		            .getRequest();
			Cookie[] cookies = request.getCookies();
			
		    if(cookies !=null){
		    for(Cookie cookie : cookies){
		    	System.out.println("cookie 2= " + cookie);
		    	System.out.println("cookie getName=" + cookie.getName());
		    	System.out.println("cookie getValue=" + cookie.getValue());
		    	System.out.println("cookie JSESSIONID check=" + cookie.getName().equals("JSESSIONID"));
		    	if(cookie.getName().equals("JSESSIONID")){
		    		J_S_ID=cookie.getValue();
					System.out.println("JSESSIONID 2 in signin= "+ J_S_ID);
					break;
				 }
		      }
		    }		
			
			e.printStackTrace();
			
			APIResponse response = new APIResponse();
			response.setStatus(HttpStatus.UNAUTHORIZED);
			response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
			response.setClientMessage("Invalid username or password !");
			return new ResponseEntity<APIResponse>(response, HttpStatus.UNAUTHORIZED);
			
		}
	 }  
	public HttpServletRequest getRequest() {
		return request;
	}


	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	@GetMapping("/Logout")
	public ResponseEntity<APIResponse> Logout()
	{
		APIResponse response = new APIResponse();
		request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
	            .getRequest();
		System.out.println("JSESSIONID in Logout=  "+request.getRequestedSessionId());	
		System.out.println("JSESSIONID Valid in Logout=  "+request.isRequestedSessionIdValid());
		request.getSession().removeAttribute("userName");
		request.getSession().removeAttribute("authorities");
		request.getSession(false);
		request.getSession().invalidate();
		System.out.println("JSESSIONID Valid after Logout=  "+request.isRequestedSessionIdValid());
		Cookie JSESSIONIDcookie = new Cookie("JSESSIONID", null);
		JSESSIONIDcookie.setMaxAge(0);
		JSESSIONIDcookie.setPath("/");
		httpResponse.addCookie(JSESSIONIDcookie);

		System.out.println("Cookies Deleted");
		
	    response.setStatus(HttpStatus.OK);
	    response.setStatusCode(HttpStatus.OK.value());
	    response.setClientMessage("Logged out Successfully");
	    return new ResponseEntity<APIResponse>(response, HttpStatus.OK);
	}
	
	@PostMapping("/setParam")
	ResponseEntity<APIResponse> setSessionParam(@RequestBody LoginRequest loginRequest){
		request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
	            .getRequest();
		System.out.println("JSESSIONID in Session=  "+request.getRequestedSessionId());	
		System.out.println("JSESSIONID Valid in Session=  "+request.isRequestedSessionIdValid());	
		APIResponse response = new APIResponse();
		
		try {
			if(loginRequest.getUsername() == null || loginRequest.getUsername() == "") {
				String userNAME=null;
				Cookie[] cookies = request.getCookies();
			    if(cookies !=null){
			    for(Cookie cookie : cookies){
			    	System.out.println("cookie= " + cookie);
			    	System.out.println("cookie getName=" + cookie.getName());
			    	System.out.println("cookie getValue=" + cookie.getValue());
			    	System.out.println("cookie JSESSIONID check=" + cookie.getName().equals("JSESSIONID"));
			    	if(cookie.getName().equals("JSESSIONID")){
			    		J_S_ID=cookie.getValue();
						System.out.println("JSESSIONID in Session= "+ J_S_ID);
						break;
					 }
			      }
				    MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
					headers.set("Host",request.getServerName()+ ":" +request.getServerPort());
					headers.set("Cookie","JSESSIONID="+ J_S_ID);
					headers.set("Content-Type", "application/json");
					//userNAME=authServiceImpl.retUserName();
					//System.out.println("userNAME= " + userNAME);
					if(userNAME==null || userNAME.equals(null))
					{
						response.setStatus(HttpStatus.UNAUTHORIZED);
						response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
						response.setClientMessage("Unauthorized");
						return new ResponseEntity<APIResponse>(response, HttpStatus.UNAUTHORIZED);
					}
					loginRequest.setUsername(userNAME);					
			    }
			}
			
			String decryptedPassword= GlobalService.decrypt(loginRequest.getPassword());
			loginRequest.setPassword(decryptedPassword);
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword().trim()));
			
			UserDetails user = (UserDetails) authentication.getPrincipal();

			String username=user.getUsername();
			request.getSession().setAttribute("userName", username);
			
			request.getSession().setAttribute("authorities", user.getAuthorities());
			OfficeUser_Response scUser =  sc_USERS_Repo.findByUserName(username);
			String office = scUser.getBU_CODE();
			request.getSession().setAttribute("officeName",office);
			System.out.println(username);
			System.out.println(office);
			System.out.println(request.getSession().getAttribute("officeName"));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			response.setStatus(HttpStatus.OK);
		    response.setStatusCode(HttpStatus.OK.value());
		    response.setClientMessage("Success");
		    return new ResponseEntity<APIResponse>(response, HttpStatus.OK);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.setStatus(HttpStatus.UNAUTHORIZED);
			response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
			response.setClientMessage("Unauthorized!");
			return new ResponseEntity<APIResponse>(response, HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PostMapping("/changePassword")
	ResponseEntity<APIResponse> changePassword(@RequestBody CHG_Password_Request chg_Password_Request){ 
		APIResponse response = new APIResponse();
		try {
			String decOldPassword=GlobalService.decrypt(chg_Password_Request.getOldPassword());
			String decNewPassword=GlobalService.decrypt(chg_Password_Request.getNewPassword());
	
			chg_Password_Request.setOldPassword(decOldPassword);
			chg_Password_Request.setNewPassword(decNewPassword);
			request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
		            .getRequest();
			String username=request.getSession().getAttribute("userName").toString();
			OfficeUser_Response scUser =  sc_USERS_Repo.findByUserName(username);
			String office = scUser.getBU_CODE();
			request.getSession().setAttribute("officeName",office);
			System.out.println("username= " + username);
			
			if(username==null || username=="") {
				response.setStatus(HttpStatus.UNAUTHORIZED);
				response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
				response.setClientMessage("Unauthorized");
				return new ResponseEntity<APIResponse>(response, HttpStatus.UNAUTHORIZED);
			}
			else if(wlsAuth.changeUserPassword(username, chg_Password_Request.getOldPassword().trim(), chg_Password_Request.getNewPassword().trim())) {
				response.setStatus(HttpStatus.OK);
			    response.setStatusCode(HttpStatus.OK.value());
			    response.setClientMessage("Success");
			    return new ResponseEntity<APIResponse>(response, HttpStatus.OK);
			}
			else {
				response.setStatus(HttpStatus.OK);
				response.setStatusCode(HttpStatus.OK.value());
				response.setClientMessage("Wrong password");
				return new ResponseEntity<APIResponse>(response, HttpStatus.OK);
			}
		}
			catch(Exception e) {
				response.setStatus(HttpStatus.UNAUTHORIZED);
				response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
				response.setClientMessage("Unauthorized !");
				return new ResponseEntity<APIResponse>(response, HttpStatus.UNAUTHORIZED);
			}
	}
	public  String getOfficeName(){
		request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
				.getRequest();
		String office = request.getSession().getAttribute("officeName").toString();
		return office;
	}
	public  String getUserNameData(){
		request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
				.getRequest();
		String userName = request.getSession().getAttribute("userName").toString();
		return userName;
	}

}