package tgh.desktop.payload.response;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
public class printTGHCostResponse {

	private String planName;	
	
	
	private  double wordPrice;
 
	private String officeName = "فونجرام مصر";
	
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date date = new Date();
	
	
	
	@JsonFormat(pattern = "hh:mm")
	private Date time = new Date();
	 @JsonProperty("totalCost")
	private String Totalcost;
	private List< PrintMessageForCost_Response> messages;


	
	
}
