package tgh.desktop.models;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name="TGH_POST_OFFICE")
public class POST_OFFICE {
	@Id
	@Column(name = "OFFICE_CODE" )
	private String officeCode;
	
	@Column(name = "OFFICE_NAME")
		private String officeName;
	
	@Column(name = "OFF_TEL_NO" )
	private String offTelNo;

	@Column(name = "OFF_ADDRESS" )
	private String offAddress;

	@Column(name = "INTERNATIONAL" )
	private Integer international ;
	
	@Column(name = "COUNTRY_CODE" )
	private String countryCode;

	@Column(name = "DEST_IND" )
	private String destInd;
	
	@Column(name = "IN_SERVICE" )
	private Integer inService;
	
	@Column(name = "NOTES" )
	private String notes;
	
	@Column(name = "CITY_CODE" )
	private String cityCode;
	
	@Column(name = "OUTGOING" )
	private String outgoing;
	
	@Column(name = "INCOMING" )
	private Integer incoming;
	
	@Column(name = "ARRIVAL_COUNT" )
	private Integer arrivalCount;
	
//	 @ManyToOne(fetch = FetchType.LAZY, targetEntity = TGH_CITY.class)
//	    @JoinColumn(name="CITY_CODE", referencedColumnName = "CITY_CODE",insertable = false,updatable = false)
//	    private TGH_CITY city;
//	 
//	 @ManyToOne(fetch = FetchType.LAZY, targetEntity = TGH_Country.class)
//	    @JoinColumn(name="COUNTRY_CODE", referencedColumnName = "COUNTRY_CODE",insertable = false,updatable = false)
//	    private TGH_Country country;
//
	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getOffTelNo() {
		return offTelNo;
	}

	public void setOffTelNo(String offTelNo) {
		this.offTelNo = offTelNo;
	}

	public String getOffAddress() {
		return offAddress;
	}

	public void setOffAddress(String offAddress) {
		this.offAddress = offAddress;
	}

	public Integer getInternational() {
		return international;
	}

	public void setInternational(Integer international) {
		this.international = international;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getDestInd() {
		return destInd;
	}

	public void setDestInd(String destInd) {
		this.destInd = destInd;
	}

	public Integer getInService() {
		return inService;
	}

	public void setInService(Integer inService) {
		this.inService = inService;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getOutgoing() {
		return outgoing;
	}

	public void setOutgoing(String outgoing) {
		this.outgoing = outgoing;
	}

	public Integer getIncoming() {
		return incoming;
	}

	public void setIncoming(Integer incoming) {
		this.incoming = incoming;
	}

	public Integer getArrivalCount() {
		return arrivalCount;
	}

	public void setArrivalCount(Integer arrivalCount) {
		this.arrivalCount = arrivalCount;
	}

//	public TGH_CITY getCity() {
//		return city;
//	}
//
//	public void setCity(TGH_CITY city) {
//		this.city = city;
//	}
//
//	public TGH_Country getCountry() {
//		return country;
//	}
//
//	public void setCountry(TGH_Country country) {
//		this.country = country;
//	}

	@Override
	public String toString() {
		return "POST_OFFICE [officeCode=" + officeCode + ", officeName=" 
				+ officeName + ", offTelNo=" + offTelNo
				+ ", offAddress=" + offAddress + ", international=" 
				+international + ", countryCode=" + countryCode
				+ ", destInd=" + destInd + ", inService=" + inService
				+ ", notes=" + notes + ", cityCode=" + cityCode
				+ ", outgoing=" + outgoing + ", incoming=" + incoming 
				+ ", arrivalCount=" + arrivalCount  ;
	}	

}
