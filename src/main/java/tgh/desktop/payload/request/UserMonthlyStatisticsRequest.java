package tgh.desktop.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UserMonthlyStatisticsRequest {

	String userCode;
	@JsonFormat(pattern =  "mm/yyyy")
	String  dateFrom ;
}
