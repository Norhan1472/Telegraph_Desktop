package tgh.desktop.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface PlanResponse  {

	@JsonProperty(value = "PLAN_CODE")
	String getPLAN_CODE();
	
	@JsonProperty(value = "PLAN_NAME")
	String getPLAN_NAME();
	
	@JsonProperty(value = "DEFAULT_PLAN")
	String getDEFAULT_PLAN();
	
	@JsonProperty(value = "tempTypeCode")
	 Integer getTEMPLATE_ENABLE();
	
	@JsonProperty(value = "deliveryNotic")
	 Integer getDELIVERY_ENABLE();
	
	@JsonProperty(value = "urgent")
	 Integer getURGENT_ENABLE();
	
	@JsonProperty(value = "decoration")
	 Integer getDECORATION_ENABLE();
	
	@JsonProperty(value = "sms")
	 Integer getSMS_ENABLE();

}
