package tgh.desktop.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

//import lombok.val;

public interface address_Response {

	@JsonProperty("keywordName")
	 String getKEYWORD_NAME();
	@JsonProperty("officeCODE")
	String getOFFICE_CODE();
	
	@JsonIgnore
	@JsonProperty("topIndex")
	 Integer getTOPINDEX();
	@JsonIgnore
	@JsonProperty("startIndex")
	 Integer getSTARTINDEX();
}
