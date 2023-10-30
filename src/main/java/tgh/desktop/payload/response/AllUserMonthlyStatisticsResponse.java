package tgh.desktop.payload.response;



import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AllUserMonthlyStatisticsResponse {

	@JsonProperty(value = "UserMonthly")
	List<ListUserMonthly> listUserMonthly ;
	
	@JsonProperty(value = "userCode")
	String userCode;
	
	@JsonProperty(value = "firstTGH")
	String FIRST_TGH;
	
	@JsonProperty(value = "lastTGH")
	String LAST_TGH ;
	
	@JsonProperty(value = "tghCost")
	String TGHCost ;
	
	@JsonProperty(value = "month")
	String month ;
	@JsonProperty(value = "year")
	String year ;
	
	
	
	
//	UserMonthlyStatisticsResponse monthlyStatisticsResponse;
}
