package tgh.desktop.models;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;

import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="TGH_CITY")
public class TGH_CITY {
	@Id
	@Column(name = "CITY_CODE")
	private String cityCode;
	
	@Column(name = "COUNTRY_CODE" )
	private String countryCode;
	
	
	@Column(name = "CITY_NAME" )
	private String cityName;

	
//	 @ManyToOne(fetch = FetchType.LAZY, targetEntity = TGH_Country.class)
//	 @JoinColumn(name="COUNTRY_CODE", referencedColumnName = "COUNTRY_CODE",insertable = false,updatable = false)
//	    private TGH_Country country;


//	public String getCityCode() {
//		return cityCode;
//	}
//
//
//	public void setCityCode(String cityCode) {
//		this.cityCode = cityCode;
//	}
//
//
//	public String getCountryCode() {
//		return countryCode;
//	}
//
//
//	public void setCountryCode(String countryCode) {
//		this.countryCode = countryCode;
//	}
//
//
//	public String getCityName() {
//		return cityName;
//	}
//
//
//	public void setCityName(String cityName) {
//		this.cityName = cityName;
//	}
//
//
//	public TGH_Country getCountry() {
//		return country;
//	}
//
//
//	public void setCountry(TGH_Country country) {
//		this.country = country;
//	}
//
//
//	@Override
//	public String toString() {
//		return "TGH_CITY [cityCode=" + cityCode + ", countryCode=" + countryCode + ", cityName=" + cityName;
////				+ ", country=" + country + "]";
//	}

	

		
}
