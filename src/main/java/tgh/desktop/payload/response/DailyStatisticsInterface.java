package tgh.desktop.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface DailyStatisticsInterface {

	
	@JsonProperty(value = "count")
	Integer getCOUNT();
	@JsonProperty(value = "statusName")
	String getSTATUS_NAME();
	
	
	
}