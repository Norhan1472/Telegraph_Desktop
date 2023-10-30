package tgh.desktop.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name="TGH_COUNTRY_VIP")
@IdClass(ID_VIP.class)
public class TGH_VIP {
	@Id
	@Column(name = "PERSON_NO" )
	private Integer personNo;
	
	@Id
	@Column(name = "COUNTRY_CODE" )
	private String countryCode;
	
	@Column(name = "PERSON_TITLE" )
	private String personTitle;
	
	@Column(name = "PERSON_NAME" )
	private String personName;
	
	@Column(name = "OFFICIAL_ADDRESS" )
	private String officialAddress;
	
	@Column(name = "ADDED_COST" )
	private Integer addedCost;
	
	@Column(name = "VIP_ENTITY" )
	private String vipEntity;

	@Column(name = "CITY_CODE" )
	private String cityCode;
	
	@Column(name = "OFFICE_CODE" )
	private String officeCode;
	
	@Column(name = "FEES" )
	private Integer fees;

//    @ManyToOne(fetch = FetchType.LAZY, targetEntity = TGH_Country.class)
//    @JoinColumn(name="COUNTRY_CODE", referencedColumnName = "COUNTRY_CODE"
//    ,insertable = false,updatable = false)
//    private TGH_Country country;
	
//	public String getCountryCode() {
//		return countryCode;
//	}
//
//	public void setCountryCode(String countryCode) {
//		this.countryCode = countryCode;
//	}
//
//	public Integer getPersonNo() {
//		return personNo;
//	}
//
//	public void setPersonNo(Integer personNo) {
//		this.personNo = personNo;
//	}
//
//	public String getPersonTitle() {
//		return personTitle;
//	}
//
//	public void setPersonTitle(String personTitle) {
//		this.personTitle = personTitle;
//	}
//
//	public String getPersonName() {
//		return personName;
//	}
//
//	public void setPersonName(String personName) {
//		this.personName = personName;
//	}
//
//	public String getOfficialAddress() {
//		return officialAddress;
//	}
//
//	public void setOfficialAddress(String officialAddress) {
//		this.officialAddress = officialAddress;
//	}
//
//	public Integer getAddedCost() {
//		return addedCost;
//	}
//
//	public void setAddedCost(Integer addedCost) {
//		this.addedCost = addedCost;
//	}
//
//	public String getVipEntity() {
//		return vipEntity;
//	}
//
//	public void setVipEntity(String vipEntity) {
//		this.vipEntity = vipEntity;
//	}

//	public TGH_Country getCountry() {
//		return country;
//	}
//
//	public void setCountry(TGH_Country country) {
//		this.country = country;
//	}

//	@Override
//	public String toString() {
//		return "TGH_VIP [countryCode=" + countryCode + ", personNo=" + personNo + ", personTitle=" 
//				+ personTitle + ", personName=" + personName + ", officialAddress="
//				+ officialAddress + ", addedCost=" + addedCost+ ", vipEntity=" + vipEntity ;
//	}

}
