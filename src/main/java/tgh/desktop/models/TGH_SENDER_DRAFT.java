package tgh.desktop.models;


import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="TGH_SENDER_DRAFT")
public class TGH_SENDER_DRAFT {
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="TGH_SENDER_DRAFT_SEQ")
	@SequenceGenerator(name = "TGH_SENDER_DRAFT_SEQ", sequenceName="TGH_SENDER_DRAFT_SEQ", allocationSize = 1)
	@Column(name = "ID" )
	private Integer Id;

	
	@Column(name = "GEN_ID" )
	private Integer genId;
	
	@Column(name = "SENDER_ID" )
	private String senderId;
	
	 	
	@Column(name = "SENDER_NAME" )
	private String senderName;

	@Column(name = "ADDRESS" )
	private String address;
	
	@Column(name = "SENDER_NAME2" )
	private String senderName2;
	@Column(name = "EMAIL" )
	private String email;
	
	@Column(name = "MOBILE_NO" )
	private String mobileNo;
	
	@Column(name = "NATIONAL_ID" )
	private String nationalId;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = TGH_DRAFT.class)
    @JoinColumn(name="GEN_ID", referencedColumnName = "GEN_ID",
    insertable = false,updatable = false)
	@JsonIgnore
	 private TGH_DRAFT tghDraft;
	
	public String getSenderId() {
		return senderId; 
	}


	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}


	public Integer getGenId() {
		return genId;
	}


	public void setGenId(Integer genId) {
		this.genId = genId;
	}


	public String getSenderName() {
		return senderName;
	}


	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}


//	public String getBillTelNo() {
//		return billTelNo;
//	}
//
//
//	public void setBillTelNo(String billTelNo) {
//		this.billTelNo = billTelNo;
//	}


//	public String getNotes() {
//		return notes;
//	}
//
//
//	public void setNotes(String notes) {
//		this.notes = notes;
//	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getSenderName2() {
		return senderName2;
	}


	public void setSenderName2(String senderName2) {
		this.senderName2 = senderName2;
	}


	public TGH_DRAFT getTghDraft() {
		return tghDraft;
	}


	public void setTghDraft(TGH_DRAFT tghDraft) {
		this.tghDraft = tghDraft;
	}


	@Override
	public String toString() {
		return "TGH_SENDER_DRAFT [genId=" + genId + ", senderId=" + senderId + ", senderName=" + senderName
				+  ", address=" + address + ", senderName2="
				+ senderName2 + ", tghDraft=" + tghDraft + "]";
	}


	public Integer getId() {
		return Id;
	}


	public void setId(Integer id) {
		Id = id;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getMobileNo() {
		return mobileNo;
	}


	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}


	public String getNationalId() {
		return nationalId;
	}


	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}


}
