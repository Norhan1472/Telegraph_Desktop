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
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="TGH_RECEPIENT_DRAFT")
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class TGH_RECEPIENT_DRAFT {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="TGH_RECEPIENT_DRAFT_SEQ")
	@SequenceGenerator(name = "TGH_RECEPIENT_DRAFT_SEQ", sequenceName="TGH_RECEPIENT_DRAFT_SEQ", allocationSize = 1)
	@Column(name = "ID" )
	private Integer Id;
	 
	@Column(name = "GEN_ID" )
	private Integer genId;
	
	
	@Column(name = "REC_ID" )
	private String recId;
	
	@Column(name = "REC_NAME" )
	private String	recName;
	
	
	@Column(name = "REC_TEL_NO" )
	private String telNo;
	
	
	@Column(name = "ADDRESS" )
	private String  address;
	
	
	@Column(name = "POSTAL_OFFICE" )
	private String  postalOffice;
	
	
	@Column(name = "OFFICE_CODE" )
	private String  officeCode;
	
	@Column(name = "REC_NAME2" )
	private String recName2;
	
	@Column(name = "REC_VIP" )
	private String  recVip;
	
	

	@Column(name = "VIP_NO" )
	private String  vipNo;
	
//	@Column(name = "NOTES" )
//	private String notes;
	
	
	@Column(name = "SENDER_RESP" )
	private String senderResp;
	

	@Column(name = "CITY_CODE" )
	private String cityCode;
	
	@Column(name = "ADDRESS_DETAILS") 
	@JsonProperty(value = "addressDetails") 
	private String addressDetails;
	
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = TGH_DRAFT.class)
    @JoinColumn(name="GEN_ID", referencedColumnName = "GEN_ID",
    insertable = false,updatable = false)
	@JsonIgnore
	 private TGH_DRAFT tghDraft;


	
	public Integer getGenId() {
		return genId;
	}

	public void setGenId(Integer genId) {
		this.genId = genId;
	}

	public String getRecId() {
		return recId;
	}

	public void setRecId(String recId) {
		this.recId = recId;
	}

	public String getRecName() {
		return recName;
	}

	public void setRecName(String recName) {
		this.recName = recName;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalOffice() {
		return postalOffice;
	}

	public void setPostalOffice(String postalOffice) {
		this.postalOffice = postalOffice;
	}

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public String getRecVip() {
		return recVip;
	}

	public void setRecVip(String recVip) {
		this.recVip = recVip;
	}

	public String getVipNo() {
		return vipNo;
	}

	public void setVipNo(String vipNo) {
		this.vipNo = vipNo;
	}
//
//	public String getCountry_Code() {
//		return country_Code;
//	}
//
//	public void setCountry_Code(String country_Code) {
//		this.country_Code = country_Code;
//	}

//	public String getNotes() {
//		return notes;
//	}
//
//	public void setNotes(String notes) {
//		this.notes = notes;
//	}

	public String getSenderResp() {
		return senderResp;
	}

	public void setSenderResp(String senderResp) {
		this.senderResp = senderResp;
	}

	public String getRecName2() {
		return recName2;
	}

	public void setRecName2(String recName2) {
		this.recName2 = recName2;
	}

//	public String getOrgOfficeCode() {
//		return orgOfficeCode;
//	}
//
//	public void setOrgOfficeCode(String orgOfficeCode) {
//		this.orgOfficeCode = orgOfficeCode;
//	}

	public TGH_DRAFT getTghDraft() {
		return tghDraft;
	}

	public void setTghDraft(TGH_DRAFT tghDraft) {
		this.tghDraft = tghDraft;
	}

	@Override
	public String toString() {
		return "TGH_RECEPIENT_DRAFT [genId=" + genId + ", recId=" + recId + ", recName=" + recName + ", telNo=" + telNo
				+ ", address=" + address + ", postalOffice=" + postalOffice + ", officeCode=" + officeCode + ", recVip="
				+ recVip + ", vipNo=" + vipNo   + ", senderResp="
				+ senderResp + ", recName2=" + recName2 + ", tghDraft=" + tghDraft
				+ "]";
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(String addressDetails) {
		this.addressDetails = addressDetails;
	}

	
}
