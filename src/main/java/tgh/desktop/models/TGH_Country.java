package tgh.desktop.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="TGH_COUNTRY")
public class TGH_Country {
	
	@Id
	@Column(name = "COUNTRY_CODE" )
	private String countryCode;
	
	@Column(name = "COUNTRY_NAME")
		private String countryName;
	
	@Column(name = "DEST_IND" )
	private String destInd;
	
	@Column(name = "ACTIVE")
	private Integer active;
	
	@Column(name = "CAPITAL")
	private String capital;

	@Column(name = "INTERNATIONAL")
	private Integer international = 0;

	@Column(name = "ENGLISH_NAME")
	private String englishName;
	
	@Column(name = "DEST_IND_ENG")
	private String destIndEng;

	
//	public String getCountryCode() {
//		return countryCode;
//	}
//
//	public void setCountryCode(String countryCode) {
//		this.countryCode = countryCode;
//	}
//
//	public String getCountryName() {
//		return countryName;
//	}
//
//	public void setCountryName(String countryName) {
//		this.countryName = countryName;
//	}
//
//	public String getDestInd() {
//		return destInd;
//	}
//
//	public void setDestInd(String destInd) {
//		this.destInd = destInd;
//	}
//
//	public Integer getActive() {
//		return active;
//	}
//
//	public void setActive(Integer active) {
//		this.active = active;
//	}
//
//	public String getCapital() {
//		return capital;
//	}
//
//	public void setCapital(String capital) {
//		this.capital = capital;
//	}
//
//	public Integer getInternational() {
//		return international;
//	}
//
//	public void setInternational(Integer international) {
//		this.international = international;
//	}
//
//	public String getEnglishName() {
//		return englishName;
//	}
//
//	public void setEnglishName(String englishName) {
//		this.englishName = englishName;
//	}
//
//	public String getDestIndEng() {
//		return destIndEng;
//	}
//
//	public void setDestIndEng(String destIndEng) {
//		this.destIndEng = destIndEng;
//	}
//
//	@Override
//	public String toString() {
//		return "TGH_Country [countryCode=" + countryCode + ", countryName=" + countryName + ", destInd=" + destInd
//				+ ", active=" + active + ", capital=" + capital + ", international=" + international + ", englishName="
//				+ englishName + ", destIndEng=" + destIndEng + "]";
//	}


	

}
