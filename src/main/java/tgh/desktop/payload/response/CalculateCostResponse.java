package tgh.desktop.payload.response;

public interface CalculateCostResponse {

	String getPLAN_NAME();
	Integer getWORD_PRICE();
	Integer getSMS_PRICE();
	Integer getURGENT_PRICE();
	Integer getDECORATION_PRICE ();
	Integer getDELIVERY_PRICE ();
	Integer getINQUIRY_PRICE ();
	Integer getTEMPLATE_PRICE ();
	Integer getPO_PRICE ();
	String getITEM_NAME();
	Integer  getADD_PRICE();
	Integer getSALES_TAX ();
	Integer getTAX_ENABLED ();
	Integer getTEMP_ENABLED ();
	Integer getCHAR_WORD (); 
	
}
