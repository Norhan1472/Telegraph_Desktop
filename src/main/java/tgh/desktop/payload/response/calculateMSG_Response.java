package tgh.desktop.payload.response;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import tgh.desktop.models.MessageForCost;

@Data
public class calculateMSG_Response {


	private String planName;
	private double wordPrice;
	private String addedPrice;

	private String officeName ;

	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date date= new Date();

	@JsonFormat(pattern = "hh:mm")
	private String time ;
	private double urgentPrice;
	private double templatePrice;
	private double deliveryPrice;
	private double decorationPrice;
	private double SMSPrice;
	private List< MessageForCost> messages;
	private String Totalcost;
}
