package tgh.desktop.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface  WFM_EMPLOYEES_SHIFTS_Response {

	@JsonProperty("userName")
	 String getFirst_Name();
	@JsonProperty("userCode")
	 String getEMP_ID();
}

