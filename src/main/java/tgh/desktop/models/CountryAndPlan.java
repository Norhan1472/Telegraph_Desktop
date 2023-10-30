package tgh.desktop.models;

import java.util.List;


import lombok.Data;
import tgh.desktop.payload.response.tarrifplan_response;

@Data
public class CountryAndPlan {
	
	private List<TGH_COUNTRY_PLAN>countryPlan ;
	private List<TGH_Country>countries;
	private String defaultCountry;
	tarrifplan_response tarrifPlan;
	
//	
//	public List<TGH_COUNTRY_PLAN> getCountryPlan() {
//		return countryPlan;
//	}
//	public void setCountryPlan(List<TGH_COUNTRY_PLAN> countryPlan) {
//		this.countryPlan = countryPlan;
//	}
//	public List<TGH_Country> getCountries() {
//		return countries;
//	}
//	public void setCountries(List<TGH_Country> countries) {
//		this.countries = countries;
//	}
//	
	
}
