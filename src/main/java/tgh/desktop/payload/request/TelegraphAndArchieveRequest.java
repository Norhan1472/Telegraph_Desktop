package tgh.desktop.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
public class TelegraphAndArchieveRequest {

	
	@JsonProperty("seqNo")
	private String seqNo;
	
	@JsonProperty("TGHCode")
	private String TGHCode;
	

	@JsonProperty(value = "incomingSeq", defaultValue = "0",required = true)
	private String incomingSeq;
	
	@JsonProperty("callerTelNo")
	private String callerTelNo;	
	
	@JsonProperty("billTelNo")
	private String billTelNo;
	
	@JsonProperty(value = "international")
	private String international;

	@JsonProperty(value = "countryCode")
	 String countryCode;
	
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
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonProperty("tghDateFrom")
	private String TGHDateFrom;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonProperty("tghDateTo")
	private String TGHDateTo;
	
	@JsonProperty("outgoingSeq")
	private String outgoingSeq;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonProperty("tghDate")
	private String TGH_DATE;
	
	@JsonProperty("internationalDirection")
	private String internationalDirection;
	
	@JsonProperty(value = "nationalId")
	private String nationalId;
	
	@JsonProperty(value = "mobileNo")
	private String mobileNo;
	
	@JsonProperty(value = "archived")
	private Integer archived;
	
}
