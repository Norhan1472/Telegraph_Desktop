package tgh.desktop.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;


@Data
public class UserDailyStatisticsRequest {

//	@JsonProperty("userCode")
	private String userCode;
	
//	@JsonProperty("dateFrom")
	@JsonFormat(pattern =  "dd/mm/yyyy")
	private String dateFrom;
	
//	@JsonProperty("dateTo")
	@JsonFormat(pattern =  "dd/mm/yyyy")
	private String dateTo;

	
}
