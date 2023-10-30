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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

//@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="TGH_RECEPIENT")
public class TGH_RECEPIENT {

	@Id
	@Column(name = "REC_ID" )
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="TGH_RECEPIENT_SEQ")
	@SequenceGenerator(name = "TGH_RECEPIENT_SEQ", sequenceName="TGH_RECEPIENT_SEQ", allocationSize = 1)
	private Integer recId;
	
	@Column(name = "GEN_ID" )
	private Integer genId;
	
	
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
	
	
	@Column(name = "REC_VIP" )
	private Integer  recVip;
	
	
	@Column(name = "VIP_NO" )
	private Integer  vipNo;

	
	@Column(name = "SENDER_RESP" )
	private Integer senderResp;
	
	
	@Column(name = "REC_NAME2" )
	private String recName2;
	
	@Column(name = "CITY_CODE" )
	private String cityCode;
	
	
	@Column(name = "ADDRESS_DETAILS") 
	@JsonProperty(value = "addressDetails") 
	private String addressDetails;
	
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = TGH_Entity.class)
    @JoinColumn(name="GEN_ID", referencedColumnName = "GEN_ID",insertable = false,updatable = false)
    private TGH_Entity tgh_Entity;


	@Column(name = "PAID_AMOUNT")
	private double paidAmount;

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Integer getRecId() {
		return recId;
	}



	public void setRecId(Integer recId) {
		this.recId = recId;
	}



	public Integer getGenId() {
		return genId;
	}



	public void setGenId(Integer genId) {
		this.genId = genId;
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



	public Integer getRecVip() {
		return recVip;
	}



	public void setRecVip(Integer recVip) {
		this.recVip = recVip;
	}



	public Integer getVipNo() {
		return vipNo;
	}



	public void setVipNo(Integer vipNo) {
		this.vipNo = vipNo;
	}



//	public String getCountry_Code() {
//		return country_Code;
//	}
//
//
//
//	public void setCountry_Code(String country_Code) {
//		this.country_Code = country_Code;
//	}
//
//
//
//	public String getNotes() {
//		return notes;
//	}
//
//
//
//	public void setNotes(String notes) {
//		this.notes = notes;
//	}



	public Integer getSenderResp() {
		return senderResp;
	}



	public void setSenderResp(Integer senderResp) {
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
//
//
//	public void setOrgOfficeCode(String orgOfficeCode) {
//		this.orgOfficeCode = orgOfficeCode;
//	}



	public TGH_Entity getTgh_Entity() {
		return tgh_Entity;
	}



	public void setTgh_Entity(TGH_Entity tgh_Entity) {
		this.tgh_Entity = tgh_Entity;
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
