package tgh.desktop.payload.response;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import tgh.desktop.models.SC_USER_MODULE;
import tgh.desktop.payload.response.LoginResponse;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
	
	 private String username;
	 private Collection<? extends GrantedAuthority> authorities;
	 private List<SC_USER_MODULE> userModules;
	 private  String displayUser ;
	 private  String office ;
	 private  Integer incoming ;
	 private  Integer outcoming ;

	 public LoginResponse(String username, Collection<? extends GrantedAuthority> authorities,
			 List<SC_USER_MODULE> userModules ,String displayUser 
			 ,  String office ,Integer  incoming ,Integer outcoming) { 
		    this.username = username;
		    this.authorities = authorities; 
		    this.userModules=userModules;
		    this.displayUser=displayUser;
		    this.office=office;
		    this.incoming=incoming;
		    this.outcoming=outcoming;
	 }

}
