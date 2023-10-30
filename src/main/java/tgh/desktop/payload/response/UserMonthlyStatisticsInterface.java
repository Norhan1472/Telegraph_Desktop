package tgh.desktop.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface UserMonthlyStatisticsInterface {
	
	@JsonProperty(value = "statusName")
	String getSTATUS_NAME ();
	
	@JsonProperty(value = "count")
	String getTGHCOUNT ();
	
}
