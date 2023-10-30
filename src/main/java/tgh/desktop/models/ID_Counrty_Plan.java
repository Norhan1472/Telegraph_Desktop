package tgh.desktop.models;

import java.io.Serializable;


public class ID_Counrty_Plan implements Serializable  {
	
	private String countryCode;
	
	private String planCode;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
}
