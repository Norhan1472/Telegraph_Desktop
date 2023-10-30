package tgh.desktop.payload.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface DailyRepeortResponse {

	@JsonProperty("userCode")
	String getUSER_CODE();
	
	@JsonProperty("tghId")
	Integer getTGH_ID();
	
	@JsonFormat(pattern="dd/MM/yyyy hh:mm:ss")
	@JsonProperty("sendDate")
	Date getSEND_DATE();
	
	@JsonProperty("tghCost")
	Integer getTGH_COST(); 
	
	@JsonProperty("tghCode")
	String getTGH_CODE();
	
	@JsonProperty("office")
	String getOFFICE_NAME();
}
