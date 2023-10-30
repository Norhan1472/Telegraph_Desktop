package tgh.desktop.payload.response;

import java.sql.Clob;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import tgh.desktop.configuration.ClobDeserializer;


public interface PrintResponse {
	
	
	@JsonFormat(pattern="yyyy-MM-dd")
	@JsonProperty("tghDate")
	String getTGH_DATE();
	
	@JsonProperty("keyWord")
	String getADDRESS();
	
	@JsonProperty("officeName")
	String getOFFICE_NAME();
	
	@JsonProperty("orgineOffice")
	String getORIGIN_OFFICE();
	
	@JsonProperty("senderName")
	String getSENDER_NAME();
	
	@JsonProperty("senderName2")
	String getSENDER_NAME2();
	
	@JsonProperty("recName2")
	String getREC_NAME2();
	
	@JsonProperty("recName")
	String getREC_NAME();
	
	@JsonProperty("senderAddress")
	String getSENDER_ADDRESS();
	
	@JsonProperty("tghId")
	String getTGH_ID();
	
	@JsonProperty("incomingSeq")
	String getSEQ_NO();
		
	@JsonProperty("tghCode")
	String getTGH_CODE();
	
	
	@JsonProperty("address")
	String getADDRESS_DETAILS();
	
	@JsonProperty("postalOffice")
	String getPOSTAL_OFFICE();
	
	@JsonProperty("urgent")
	Integer getURGENT();
	
	@JsonProperty("deliveryNotice")
	Integer getDELIVERY_NOTICE();
	
	@JsonProperty("sms")
	Integer getSMS();
	
	@JsonDeserialize(using = ClobDeserializer.class)
	@JsonProperty("message")
	Clob getMESSAGE();
	
	@JsonProperty("langCode")
	String getLANG_CODE();
	@JsonProperty("admin")
	Integer getADMIN();
	
	
}
