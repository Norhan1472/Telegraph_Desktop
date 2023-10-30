package tgh.desktop.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QueueSearchRequest {
	
	@JsonProperty("seqNo")
	private String seqNo;
	
	@JsonProperty("TGHCode")
	private String TGHCode;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonProperty("TGHDateFrom")
	private String TGHDateFrom;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonProperty("TGHDateTo")
	private String TGHDateTo;
	
	@JsonProperty("callerTelNo")
	private String callerTelNo;
	
	@JsonProperty("billTelNo")
	private String billTelNo;
	
	@JsonProperty(value = "international")
	private String international;
	
	@JsonProperty("genId")
	private String genId;
	
	@JsonProperty("senderName")
	private String senderName;
	
	@JsonProperty("recName")
	private String recName;
	
	@JsonProperty("address")
	private String address;
	
	@JsonProperty("statusCode")
	private String statusCode;
	
	@JsonProperty("officeCode")
	private String officeCode;
	
	@JsonProperty("originOffice")
	private String orginOffice;
	
	@JsonProperty("outgoingSeq")
	private String outgoingSeq;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonProperty("TGH_DATE")
	private String TGH_DATE;
	
	@JsonProperty("message")
	private String message;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonProperty("callDate")
	private String callDate;
	
	@JsonProperty("internationalDirection")
	private String internationalDirection;
	
	@JsonProperty("accountType")
	private String accountType;
	
	@JsonProperty(value = "incomingSeq", defaultValue = "0",required = true)
	private String incomingSeq;
	
	@JsonProperty(value = "nationalId")
	private String nationalId;
	
	@JsonProperty(value = "mobileNo")
	private String mobileNo;

}
