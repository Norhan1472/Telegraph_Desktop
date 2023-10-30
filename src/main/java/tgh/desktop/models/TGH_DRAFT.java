package tgh.desktop.models;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="TGH_GENERATOR_DRAFT")
public class TGH_DRAFT {

	@Id
	@Column(name = "GEN_ID" ) 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="GEN_ID_SEQ")
	@SequenceGenerator(name = "GEN_ID_SEQ", sequenceName="GEN_ID_SEQ", allocationSize = 1)
	private Integer   genId ;
	
	@Column(name = "USER_CODE" )
	private String  userCode; 
	
	
	@Column(name = "CALL_DATE" )
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date  callDate ; 
	
	@Column(name = "CALLER_TEL_NO" )
	private String   callerTelNo;
	
	@Column(name = "CALLER_NAME" )
	private String   callerName;
	
//	@Column(name = "BILL_TEL_NO" )
//	private String   billTelNo;
	
	@Column(name = "SEND_DATE")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date   sendDate;
	
	@Column(name = "COUNTRY_CODE")
	private String countryCode;
	
	@Column(name = "LANG_CODE" )
	private	String  langCode; 
	
	@Column(name = "PLAN_CODE" )
	private	String  planCode;
	
	
	@Column(name = "TEMPLATE" )
	private	String template;
	
	@Column(name = "TEMP_TYPE_CODE" )
	private	String   tempTypeCode;
	
	@Column(name = "TEMP_CODE" )
	private	String   tempCode;
	
	@Column(name = "DELIVERY_NOTICE")
	private	String	  deliveryNotic;
	
	
	@Column(name = "DECORATION")
	private	String	  decoration;
	
	@Column(name = "URGENT" )
	private	String	 urgent;
	
	@Column(name = "ADMIN" )
	private	String  admin;	
	
	
	@Column(name = "INTERNATIONAL" )
	private	String  international;
	
	@Column(name = "NOTES" )
	private	String  notes;
	
	@Column(name = "MESSAGE" )
	private	String  message;
	
	@Column(name = "DRAFT_DATE" )
	private	Date draftDate;
	
	@Column(name = "SMS")
	private String sms;
	
	
	
	@OneToMany(mappedBy ="tghDraft",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private  List<TGH_RECEPIENT_DRAFT> recepients;
	
	@OneToMany(mappedBy ="tghDraft",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private  List<TGH_SENDER_DRAFT> senders;
	
	
	public Integer getGenId() {
		return genId;
	}

	public void setGenId(Integer genId) {
		this.genId = genId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Date getCallDate() {
		return callDate;
	}

	public void setCallDate(Date callDate) {
		this.callDate = callDate;
	}

	public String getCallerTelNo() {
		return callerTelNo;
	}

	public void setCallerTelNo(String callerTelNo) {
		this.callerTelNo = callerTelNo;
	}

	public String getCallerName() {
		return callerName;
	}

	public void setCallerName(String callerName) {
		this.callerName = callerName;
	}

//	public String getBillTelNo() {
//		return billTelNo;
//	}
//
//	public void setBillTelNo(String billTelNo) {
//		this.billTelNo = billTelNo;
//	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getTempTypeCode() {
		return tempTypeCode;
	}

	public void setTempTypeCode(String tempTypeCode) {
		this.tempTypeCode = tempTypeCode;
	}

	public String getTempCode() {
		return tempCode;
	}

	public void setTempCode(String tempCode) {
		this.tempCode = tempCode;
	}

	public String getDeliveryNotic() {
		return deliveryNotic;
	}

	public void setDeliveryNotic(String deliveryNotic) {
		this.deliveryNotic = deliveryNotic;
	}

	public String getDecoration() {
		return decoration;
	}

	public void setDecoration(String decoration) {
		this.decoration = decoration;
	}

	public String getUrgent() {
		return urgent;
	}

	public void setUrgent(String urgent) {
		this.urgent = urgent;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getInternational() {
		return international;
	}

	public void setInternational(String international) {
		this.international = international;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDraftDate() {
		return draftDate;
	}

	public void setDraftDate(Date draftDate) {
		this.draftDate = draftDate;
	}

	public List<TGH_SENDER_DRAFT> getSenders() {
		return senders;
	}

	public void setSenders(List<TGH_SENDER_DRAFT> senders) {
		this.senders = senders;
	}

	public List<TGH_RECEPIENT_DRAFT> getRecepients() {
		return recepients;
	}

	public void setRecepients(List<TGH_RECEPIENT_DRAFT> recepients) {
		this.recepients = recepients;
	}

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	

}
