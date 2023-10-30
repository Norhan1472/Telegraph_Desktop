package tgh.desktop.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface Tarrif_Cost_Plan_Response {

	
	@JsonProperty("planCode")
	String getPLAN_CODE ();
	
	@JsonProperty("planName")
	String getPLAN_NAME ();
	
	@JsonProperty("templateEnable")
	Integer getTEMPLATE_ENABLE();
	
	@JsonProperty("deliveryEnable")
	Integer getDELIVERY_ENABLE();
	
	@JsonProperty("urgentEnable")
	Integer getURGENT_ENABLE();
	
	@JsonProperty("decorationEnable")
	Integer getDECORATION_ENABLE ();
	
	@JsonProperty("wordPrice")
	Integer getWORD_PRICE();
	
	@JsonProperty("decorationPrice")
	Integer getDECORATION_PRICE();
	
	@JsonProperty("deliveryPrice")
	Integer getDELIVERY_PRICE();
	
	@JsonProperty("poPrice")
	Integer getPO_PRICE();
	
	@JsonProperty("urgentPrice")
	Integer getURGENT_PRICE();
	
	@JsonProperty("tamplatePrice")
	Integer getTEMPLATE_PRICE();
	
	@JsonProperty("admin")
	Integer getADMIN();
	
	@JsonProperty("inquiryPrice")
	Integer getINQUIRY_PRICE();
	
	@JsonProperty("smsEnable")
	Integer getSMS_ENABLE();
	
	@JsonProperty("smsPrice")
	Integer getSMS_PRICE();
	
	@JsonProperty("charWord")
	Integer getCHAR_WORD ();
	
	
}
