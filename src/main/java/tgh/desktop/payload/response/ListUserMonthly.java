package tgh.desktop.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ListUserMonthly {
	@JsonProperty(value = "statusName")
	String STATUS_NAME ;
	
	@JsonProperty(value = "count")
	String TGHCOUNT ;
}
