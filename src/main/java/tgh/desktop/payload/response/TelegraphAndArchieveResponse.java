package tgh.desktop.payload.response;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TelegraphAndArchieveResponse {


	@JsonProperty("seqNo")
	 Integer seqNo;
	
	@JsonProperty("tghCode")
	 String tghCode;
	
	@JsonProperty("userCode")
	 String userCode;
	
	
	@JsonProperty("notes")
	 String notes;
	
	
	@JsonProperty(value = "incomingSeq", defaultValue = "0",required = true)
	Integer incomingSeq;
	
	@JsonProperty("callerTelNo")
	 String callerTelNo;
	@JsonProperty("countryCode")
	 String countryCode;
	
	@JsonProperty("billTelNo")
	 String billTelNo;

	
	
	@JsonProperty("senderName")
	 String senderName;
	
	@JsonProperty("recName")
	 String recName;
	
	
	@JsonProperty("statusCode")
	 String statusCode;
	
	@JsonProperty("officeCode")
	 String officeCode;
	
	@JsonProperty("originOffice")
	 String orginOffice;
	
	
	
	@JsonProperty("outgoingSeq")
	 String outgoingSeq;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonProperty("tghDate")
	 Date tghDate;
	

	
	@JsonProperty(value = "archiveBy")
	 String archiveBy;
	
	@JsonProperty(value = "archiveDate")
	 String archiveDate;
	
	@JsonProperty("noOfWords")
	 Integer noOfWords;
	
	@JsonProperty("tghCost")
	 Integer tghCost;

	@JsonProperty(value = "accountType")
	 String accountType;
	
}
