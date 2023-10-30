package tgh.desktop.models;

import java.util.Date;
import java.util.Set;
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

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="TGH_GENERATOR")
public class TGH_Entity {
	
	@Id
	@Column(name = "GEN_ID" )
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="GEN_ID_SEQ")
	@SequenceGenerator(name = "GEN_ID_SEQ", sequenceName="GEN_ID_SEQ", allocationSize = 1)
	private Integer   genId ;
	
	@Column(name = "USER_CODE" )
	private String  userCode; 
	
	
	@Column(name = "CALL_DATE" )
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")// hh:mm:ss a
	private Date  callDate; 
	
	@Column(name = "CALLER_TEL_NO" )
	private String   callerTelNo;
	
	@Column(name = "CALLER_NAME" )
	private String   callerName;
	
//	@Column(name = "BILL_TEL_NO" )
//	private String   billTelNo;
	
	@Column(name = "SEND_DATE")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date   sendDate;
	
	@Column(name = "COUNTRY_CODE")
	private String countryCode;
	
	@Column(name = "LANG_CODE" )
	private	String  langCode; 
	
	@Column(name = "PLAN_CODE" )
	private	String  planCode;
	
	
	@Column(name = "TEMPLATE" )
	private	Integer template;
	
	@Column(name = "TEMP_TYPE_CODE" )
	private	String   tempTypeCode;
	
	@Column(name = "TEMP_CODE" )
	private	String   tempCode;
	
	@Column(name = "DELIVERY_NOTICE")
	private	Integer	  deliveryNotic;
	
	
	@Column(name = "DECORATION")
	private	Integer	  decoration;
	
	@Column(name = "URGENT" )
	private	Integer	 urgent;
	
	@Column(name = "ADMIN" )
	private	Integer  admin;	
	
	
	@Column(name = "INTERNATIONAL" )
	private	Integer  international;
	
	@Column(name = "NOTES" )
	private	String  notes;
	
	@Column(name = "MESSAGE" )
	private	String  message;
	
	@Column(name = "BALANCE" )
	private	Integer  balance;
	
	@Column(name = "PAIDMOD" )
	private	String  paidmod;
	
	@Column(name = "STATUS" )
	private	Integer  statues;
	
	@Column(name = "SMS")
	private Integer sms;
	
	@OneToMany(mappedBy ="tgh_Entity",fetch = FetchType.LAZY)
	private Set<TGH_RECEPIENT> recepients;
	
	@OneToMany(mappedBy="tgh_Entity",fetch = FetchType.LAZY)
	private  Set<TGH_SENDER> senders;

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

	public Integer getTemplate() {
		return template;
	}

	public void setTemplate(Integer template) {
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

	public Integer getDeliveryNotic() {
		return deliveryNotic;
	}

	public void setDeliveryNotic(Integer deliveryNotic) {
		this.deliveryNotic = deliveryNotic;
	}

	public Integer getDecoration() {
		return decoration;
	}

	public void setDecoration(Integer decoration) {
		this.decoration = decoration;
	}

	public Integer getUrgent() {
		return urgent;
	}

	public void setUrgent(Integer urgent) {
		this.urgent = urgent;
	}

	public Integer getAdmin() {
		return admin;
	}

	public void setAdmin(Integer admin) {
		this.admin = admin;
	}

	public Integer getInternational() {
		return international;
	}

	public void setInternational(Integer international) {
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

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public String getPaidmod() {
		return paidmod;
	}

	public void setPaidmod(String paidmod) {
		this.paidmod = paidmod;
	}

	public Integer getStatues() {
		return statues;
	}

	public void setStatues(Integer statues) {
		this.statues = statues;
	}

	public Set<TGH_RECEPIENT> getRecepients() {
		return recepients;
	}

	public void setRecepients(Set<TGH_RECEPIENT> recepients) {
		this.recepients = recepients;
	}

	public Set<TGH_SENDER> getSenders() {
		return senders;
	}

	public void setSenders(Set<TGH_SENDER> senders) {
		this.senders = senders;
	}

	public Integer getSms() {
		return sms;
	}

	public void setSms(Integer sms) {
		this.sms = sms;
	}
	
	
	
}
