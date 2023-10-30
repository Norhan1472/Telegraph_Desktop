package tgh.desktop.payload.response;

import lombok.Data;

@Data
public class MessageDetailsPrintResponse {

	private String المرسل_اليه;
	private Integer جهه_الورود;
	private Integer عدد_الكلمات_الراسل_و_المرسل_اليه_الاضافيان; //piasters
	private final String various = "0 PT";
	private final String  salesTax = "0 PT"; // piasters
	private String messageCost; //piasters
	private Integer numOfExtra ;
	
	private Integer costOfExtraName ;
	private String address;
	private Integer costTgh ;
}
