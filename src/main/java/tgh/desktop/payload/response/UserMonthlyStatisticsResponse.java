package tgh.desktop.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface UserMonthlyStatisticsResponse {

	@JsonProperty(value = "userCode")
	String getUSERCODE();
	@JsonProperty(value = "firstTGH")
	String getFIRST_TGH();
	
	@JsonProperty(value = "lastTGH")
	String getLAST_TGH ();
	
	@JsonProperty(value = "tghCost")
	String getTGHCost ();
	
	
	@JsonProperty(value = "year")
	String getYEAR ();

	@JsonProperty(value = "month")
	String getMONTH ();
	
}
