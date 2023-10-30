package tgh.desktop.services;

public interface CalCostInterface {
	public double calcCost(String planCode,
	 Integer template,
	 Integer delivery, 
	 Integer urgent ,
	 Integer decoration ,
	 String message, 
	 String sender,
	 String rec,
	 String postalOffice);

}
