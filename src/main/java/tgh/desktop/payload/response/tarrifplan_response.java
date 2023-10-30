package tgh.desktop.payload.response;

import lombok.Data;

@Data
public class tarrifplan_response {
	
	
private String planCode;

	private String	planName;
	

	private Integer wordPrice;
	

	private Integer decorationPrice;
	

	private Integer poPrice;
	

	private Integer urgentPrice;
	

	private Integer templatePrice;
	

	private Integer deliveryPrice;
	

	private Integer admin ;
	
	private Integer charWord;
	
	
	private Integer inquiryPrice;
	
	
	private Integer salesTax;
	
	
	private Integer SMSPrice; 
	private tarrifplanMSGResponse message;
	
}
