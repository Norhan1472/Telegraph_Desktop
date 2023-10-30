package tgh.desktop.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TGH_TELEGHRAPH_ARCHIVE")
public class TGH_TELEGHRAPH_ARCHIVE {
	
	@Id
	@Column(name = "TGH_ID" )
	@JsonProperty(value = "seqNo")
	private Integer TGH_ID;
	
	
	@Column(name = "TGH_CODE" )
	@JsonProperty(value = "TGHCode")
	private String TGH_CODE;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "TGH_DATE" )
	@JsonProperty(value = "TGH_DATE")
	private Date TGH_DATE;
	
	@Column(name = "USER_CODE" )
	@JsonProperty(value = "userCode")
	private String USER_CODE;
	
	@Column(name = "SEQ_NO" )
	@JsonProperty(value = "incomingSeq")
	private Integer SEQ_NO;
	
	@Column(name = "CALLER_NAME" )
	@JsonProperty(value = "callerName")
	private String CALLER_NAME;
	
	@Column(name = "CALLER_TEL_NO" )
	@JsonProperty(value = "callerTelNo")
	private String CALLER_TEL_NO;
	
	@Column(name = "BILL_TEL_NO")
	@JsonProperty(value = "billTelNo")
	private String BILL_TEL_NO;
	
	@Column(name = "PLAN_CODE")
	@JsonProperty(value = "planCode")
	private String PLAN_CODE;
	
	@Column(name = "TEMP_CODE")
	@JsonProperty(value = "tempCode")
	private String TEMP_CODE;
	
	@Column(name = "LANG_CODE")
	@JsonProperty(value = "langCode")
	private String LANG_CODE;
	
	@Column(name = "DELIVERY_NOTICE")
	@JsonProperty(value = "deliveryNotice")
	private Integer DELIVERY_NOTICE;
	
	@Column(name = "DECORATION")
	@JsonProperty(value = "decoration")
	private Integer DECORATION;
	
	@Column(name = "NOTES")
	@JsonProperty(value = "notes")
	private String NOTES;
	
	@Column(name = "URGENT")
	@JsonProperty(value = "urgent")
	private Integer URGENT;
	
	@Column(name = "ADMIN")
	@JsonProperty(value = "admin")
	private Integer ADMIN;
	
	@Column(name = "INTERNATIONAL")
	@JsonProperty(value = "international")
	private Integer INTERNATIONAL;
	
	
	
	@Column(name = "TEMPLATE")
	@JsonProperty(value = "template")
	private Integer TEMPLATE;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "SEND_DATE")
	@JsonProperty("sendDate")
	private Date SEND_DATE;
	
	@Column(name = "COUNTRY_CODE")
	@JsonProperty("countryCode")
	private String COUNTRY_CODE;
	
	@Column(name = "TEMP_TYPE_CODE")
	@JsonProperty("tempTypeCode")
	private String TEMP_TYPE_CODE;
	
	@Column(name = "GEN_ID")
	@JsonProperty(value = "genId")
	private Integer GEN_ID;
	
	@Column(name = "SENDER_NAME")
	@JsonProperty(value = "senderName")
	private String SENDER_NAME;
	
	@Column(name = "REC_NAME")
	@JsonProperty(value = "recName")
	private String REC_NAME;
	
	@Column(name = "POSTAL_OFFICE")
	@JsonProperty(value = "postalOffice")
	private String POSTAL_OFFICE;
	
	@Column(name = "ADDRESS")
	@JsonProperty(value = "address")
	private String ADDRESS;
	
	@Column(name = "REC_VIP")
	@JsonProperty(value = "recVip")
	private Integer REC_VIP;
	
	@Column(name = "VIP_NO")
	@JsonProperty(value = "vipNo")
	private Integer VIP_NO;
	
	@Column(name = "STATUS_CODE")
	@JsonProperty(value = "statusCode")
	private String STATUS_CODE;
	
	@Column(name = "OFFICE_CODE")
	@JsonProperty(value = "officeCode")
	private String OFFICE_CODE;
	
	@Column(name = "TGH_COST")
	@JsonProperty(value = "tghCost")
	private Integer TGH_COST;
	
	@Column(name = "ACTUAL_REC_NAME")
	@JsonProperty(value = "actualRecName")
	private String ACTUAL_REC_NAME;
	
	@Column(name = "ACTUAL_REC_DATE")
	@JsonProperty(value = "actualRecDate")
	private Date ACTUAL_REC_DATE;
	
	@Column(name = "MESSAGE")
	@JsonProperty(value = "message")
	private String MESSAGE;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "CALL_DATE")
	@JsonProperty(value = "callDate")
	private Date CALL_DATE;
	
	@Column(name = "ORIGIN_OFFICE")
	@JsonProperty(value = "originOffice")
	private String ORIGIN_OFFICE;
	
	@Column(name = "REDIRECTED")
	@JsonProperty(value = "redirected")
	private Integer REDIRECTED;
	
	@Column(name = "REDIRECT_NOTES")
	@JsonProperty(value = "redirectNotes")
	private String REDIRECT_NOTES;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonProperty(value = "archiveDate")
	@Column(name = "ARCHIVE_DATE")
	private Date ARCHIVE_DATE;
	
	@JsonProperty(value = "archiveBy")
	@Column(name = "ARCHIVED_BY")
	private String ARCHIVED_BY;
	
	@Column(name = "GENERATED_BY")
	@JsonProperty(value = "generatedBy")
	private String GENERATED_BY;
	
	@Column(name = "SENDER_NAME2")
	@JsonProperty(value = "senderName2")
	private String SENDER_NAME2;
	
	@Column(name = "REC_NAME2")
	@JsonProperty(value = "recName2")
	private String REC_NAME2;
	
	@Column(name = "NO_OF_WORDS")
	@JsonProperty(value = "noOfWords")
	private Integer NO_OF_WORDS;
	
	@Column(name = "SENDER_RESP")
	@JsonProperty(value = "senderResp")
	private Integer SENDER_RESP;
	
	@Column(name = "SENDER_ADDRESS")
	@JsonProperty(value = "senderAddress")
	private String SENDER_ADDRESS;
	
	@Column(name = "BILL_FLAG")
	@JsonProperty(value = "billFlag")
	private Integer BILL_FLAG;
	
	@Column(name = "OFFICE_SEQ")
	@JsonProperty(value = "outgoingSeq")
	private String OFFICE_SEQ;
	
	@Column(name = "EMAIL")
	@JsonProperty(value = "email")
	private String EMAIL;
	
	@Column(name = "MOBILE_NO")
	@JsonProperty(value = "mobileNo")
	private String MOBILE_NO;
	
	@Column(name = "NATIONAL_ID")
	@JsonProperty(value = "nationalId")
	private String NATIONAL_ID;
	
	@Column(name = "ACCOUNT_TYPE") 
	@JsonProperty(value = "accountType")
	private String ACCOUNT_TYPE;

	@Transient
	@Column(name = "internationalDirection") 
	@JsonProperty(value = "internationalDirection")
	private String internationalDirection;
}
