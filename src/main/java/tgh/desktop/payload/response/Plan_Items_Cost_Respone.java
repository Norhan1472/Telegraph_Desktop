package tgh.desktop.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface Plan_Items_Cost_Respone {

	@JsonProperty("itemName")
	 String getITEM_NAME ();
	
	@JsonProperty("add_price")
	 String getADD_PRICE ();
	
	@JsonProperty("taxEnable")
	 String getTAX_ENABLED ();
	
	@JsonProperty("tempEnable")
	 String getTEMP_ENABLED ();
	
	@JsonProperty("valid")
	 String getVALID ();
	
}
