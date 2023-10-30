package tgh.desktop.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DailyReportRequest {

	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonProperty("dateFrom")
	private String TGHDateFrom;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonProperty("dateTo")
	private String TGHDateTo;
	
	private String userName;
}
