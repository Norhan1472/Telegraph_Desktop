package tgh.desktop.payload.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface TGH_TELEGRAPH_DTO {
	
	@JsonProperty("SeqNo")
	String getTGH_ID(); 
	
	@JsonProperty(value = "TGHCode")
	String getTGH_CODE(); 

	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonProperty(value = "TGH_DATE")
	Date getTGH_DATE();
	
	@JsonProperty(value = "USER_CODE")
	String getUSER_CODE();
	
	@JsonProperty(value = "incomingSeq")
	String getSEQ_NO();
	
	@JsonProperty(value = "callerName")
	String getCALLER_NAME();
	
	@JsonProperty(value = "callerTelNo")
	String getCALLER_TEL_NO();
	
	@JsonProperty(value = "billTelNo")
	String getBILL_TEL_NO();
	
	@JsonProperty(value = "planCode")
	String getPLAN_CODE();
	
	@JsonProperty(value = "tempCode")
	String getTEMP_CODE();
	
	@JsonProperty(value = "langCode")
	String getLANG_CODE();
	
	@JsonProperty(value = "deliveryNotice")
	String getDELIVERY_NOTICE();
	
	@JsonProperty(value = "decoration")
	String getDECORATION();
	
	@JsonProperty(value = "notes")
	String getNOTES();
	
	@JsonProperty(value = "urgent")
	String getURGENT();
	
	@JsonProperty(value = "admin")
	String getADMIN();
	
	@JsonProperty(value = "international")
	String getINTERNATIONAL();
	
	@JsonProperty(value = "template")
	String getTEMPLATE();
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonProperty(value = "sendDate")
	Date getSEND_DATE();
	
	@JsonProperty(value = "countryCode")
	String getCOUNTRY_CODE();
	
	@JsonProperty(value = "tempTypeCode")
	String getTEMP_TYPE_CODE();
	
	@JsonProperty(value = "genId")
	String getGEN_ID();
	
	@JsonProperty(value = "senderName")
	String getSENDER_NAME();
	
	@JsonProperty(value = "recName")
	String getREC_NAME();
	
	@JsonProperty(value = "postalOffice")
	String getPOSTAL_OFFICE();
	
	@JsonProperty(value = "address")
	String getADDRESS();
	
	@JsonProperty(value = "recVip")
	String getREC_VIP();
	
	@JsonProperty(value = "vipNo")
	String getVIP_NO();
	
	@JsonProperty(value = "statusCode")
	String getSTATUS_CODE();
	
	@JsonProperty(value = "officeCode")
	String getOFFICE_CODE();
	
	@JsonProperty(value = "tghCost")
	String getTGH_COST();
	
	@JsonProperty(value = "actualRecName")
	String getACTUAL_REC_NAME();
	
	@JsonProperty(value = "actualRecDate")
	String getACTUAL_REC_DATE();
	
//	@Lob
//	@JsonIgnsore
	@JsonProperty(value = "message")
	String getMESSAGE();
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonProperty(value = "callDate")
	Date getCALL_DATE();
	
	@JsonProperty(value = "orgineOffice")
	String getORIGIN_OFFICE();
	
	@JsonProperty(value = "redirected")
	String getREDIRECTED();	
	
	@JsonProperty(value = "redirectNotes")
	String getREDIRECT_NOTES();	
	
	@JsonProperty(value = "generatedBy")
	String getGENERATED_BY();
	
	@JsonProperty(value = "lastModeifiedBy")
	String getLAST_MODIFIED_BY();
	
	@JsonProperty(value = "senderResp")
	String getSENDER_RESP();
	
	@JsonProperty(value = "receiptNo")
	String getRECEIPT_NO();
	
	@JsonProperty(value = "outgoingSeq")
	String getOFFICE_SEQ();
	
	@JsonProperty(value = "senderName2")
	String getSENDER_NAME2();
	
	@JsonProperty(value = "recName2")
	String getREC_NAME2();
	
	@JsonProperty(value = "senderAddress")
	String getSENDER_ADDRESS();
	
	@JsonProperty(value = "noOfWords")
	String getNO_OF_WORDS();
	
	@JsonProperty(value = "billFlag")
	String getBILL_FLAG();
	
	@JsonProperty(value = "recordStatus")
	String getRECORD_STATUS();
	
	@JsonProperty(value = "updateOffSeq")
	String getUPDATE_OFF_SEQ();
	
	@JsonProperty(value = "email")
	String getEMAIL();
	
	@JsonProperty(value = "mobileNo")
	String getMOBILE_NO();
	
	@JsonProperty(value = "nationalId")
	String getNATIONAL_ID();
	
	@JsonProperty(value = "accountType")
	String getACCOUNT_TYPE();
	
	@JsonProperty(value = "internationalDirection")
	String getINTERNATIONALDIRECTION();
	
	@JsonProperty(value = "sms")
	String getSMS();
}
