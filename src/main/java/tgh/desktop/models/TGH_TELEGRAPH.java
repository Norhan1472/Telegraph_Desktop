package tgh.desktop.models;

import java.sql.Clob;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tgh.desktop.configuration.ClobDeserializer;

@Data
@AllArgsConstructor
@NoArgsConstructor 
@Entity
@Table(name="TGH_TELEGRAPH")
public class TGH_TELEGRAPH {
	 
	@Id
	@Column(name = "TGH_ID" )
	@JsonProperty(value = "seqNo")
	private Integer seqNo;  
	//
	@Column(name = "TGH_CODE" )
	@JsonProperty(value = "TGHCode")
	private String tghCode;
	//
	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "TGH_DATE" )
	@JsonProperty(value = "TGH_DATE")
	private Date tghDate;
	
	@Column(name = "USER_CODE" )
	private String userCode;
	
	@Column(name = "SEQ_NO" )
	@JsonProperty(value = "incomingSeq")
	private Integer incomingSeq; 
	
	@Column(name = "CALLER_NAME" )
	@JsonProperty(value = "callerName")
	private String callerName;
	//
	@Column(name = "CALLER_TEL_NO" )
	@JsonProperty(value = "callerTelNo")
	private String callerTelNo; 
	//
	@Column(name = "BILL_TEL_NO")
	@JsonProperty(value = "billTelNo")
	private String billTelNo; 
	
	@Column(name = "PLAN_CODE")
	private String planCode; 
	
	@Column(name = "TEMP_CODE")
	private String tempCode;
	
	@Column(name = "LANG_CODE")
	private String langCode;
	
	@Column(name = "DELIVERY_NOTICE")
	private Integer deliveryNotice;
	
	@Column(name = "DECORATION")
	private Integer decoration;
	//
	@Column(name = "NOTES")
	private String notes;
	
	@Column(name = "URGENT")
	private Integer urgent;
	
	@Column(name = "ADMIN")
	@JsonProperty(value = "admin")
	private Integer admin;
	
	@Column(name = "INTERNATIONAL")
	@JsonProperty(value = "international")
	private Integer international; 
	
	@Column(name = "TEMPLATE")
	private Integer template;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "SEND_DATE")
	private Date sendDate;
	
	@Column(name = "COUNTRY_CODE")
	private String countryCode;
	
	@Column(name = "TEMP_TYPE_CODE")
	private String tempTypeCode;
	
	@Column(name = "GEN_ID")
	@JsonProperty(value = "genId")
	private Integer genId; 
	//
	@Column(name = "SENDER_NAME")
//	@JsonProperty(value = "senderName")
	private String senderName; 
	//
	@Column(name = "REC_NAME")
	@JsonProperty(value = "recName")
	private String recName; 
	//
	@Column(name = "POSTAL_OFFICE")
	private String postalOffice;
	
	@Column(name = "ADDRESS")
	@JsonProperty(value = "address")
	private String address;
	
	@Column(name = "ADDRESS_DETAILS")
	@JsonProperty(value = "addressDetails") 
	private String addressDetails;
	
	@Column(name = "REC_VIP")
	private Integer recVip;
	
	@Column(name = "VIP_NO")
	private Integer vipNo;
	//
	@Column(name = "STATUS_CODE")
	@JsonProperty(value = "statusCode")
	private String status; 
	
	@Column(name = "OFFICE_CODE")
	@JsonProperty(value = "officeCode")
	private String office; 
	//
	@Column(name = "TGH_COST")
	private Integer tghCost;
	
	@Column(name = "ACTUAL_REC_NAME")
	private String actualRecName;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "ACTUAL_REC_DATE")
	private Date actualRecDate;
	
//	@Lob
	@JsonDeserialize(using = ClobDeserializer.class)
//	@JacksonFeatures(serializationDisable = {SerializationFeature.FAIL_ON_EMPTY_BEANS})
	@Column(name = "MESSAGE")
	@JsonProperty(value = "message")
	private Clob message;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "CALL_DATE")
	@JsonProperty(value = "callDate")
	private Date callDate;
	//
	@Column(name = "ORIGIN_OFFICE")
	@JsonProperty(value = "originOffice")
	private String orgineOffice; 
	
	@Column(name = "REDIRECTED")
	private Integer redirected;
	
	@Column(name = "REDIRECT_NOTES")
	private String redirectNotes;
	
	@Column(name = "GENERATED_BY")
	private String generatedBy;
	
	@Column(name = "LAST_MODIFIED_BY")
	private String lastModeifiedBy;
	
	@Column(name = "SENDER_RESP")
	private Integer senderResp;
	
	@Column(name = "RECEIPT_NO")
	private String receiptNo;
	
	@Column(name = "OFFICE_SEQ")
	@JsonProperty(value = "outgoingSeq")
	private String outgoingSeq;
	
	@Column(name = "SENDER_NAME2")
	private String senderName2;
	
	@Column(name = "REC_NAME2")
	private String recName2;
	
	@Column(name = "SENDER_ADDRESS")
	private String senderAddress;
	//
	@Column(name = "NO_OF_WORDS")
	private Integer noOfWords;
	
	@Column(name = "BILL_FLAG")
	private Integer billFlag;
	
	@Column(name = "RECORD_STATUS")
	private String recordStatus;
	
	@Column(name = "UPDATE_OFF_SEQ")
	private Integer updateOffSeq;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "MOBILE_NO")
	@JsonProperty(value = "mobileNo")
	private String mobileNo;
	
	@Column(name = "NATIONAL_ID")
	@JsonProperty(value = "nationalId")
	private String nationalId;
	
	@Column(name = "ACCOUNT_TYPE") 
	@JsonProperty(value = "accountType")
	private String accountType;

	@Column(name = "SMS")
	private String sms;
	
	@Column(name = "CITY_CODE")
	private String cityCode;
	
	@Column(name = "PAID_AMOUNT")
	private Integer paidAmount;

	@Transient
	@Column(name = "internationalDirection") 
	@JsonProperty(value = "internationalDirection")
	private String internationalDirection;
	
	
	@Column(name = "DISPATCHED_TO")
	@JsonProperty(value = "dispatchedTo")
	private String DISPATCHED_TO ;
	
	@Column(name = "DISPATCHED_TIME")
	@JsonProperty(value = "dispatchedTime")
	private Date DISPATCHED_TIME;
	

}
