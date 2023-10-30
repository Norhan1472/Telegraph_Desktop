package tgh.desktop.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface OfficeUser_Response {
	@JsonProperty("displayUser")
	String getDISPLAY_NAME();
	@JsonProperty("office")
	String getBU_CODE();
	@JsonProperty("outcoming")
	Integer getOUTGOING();
	@JsonProperty("incoming")
	Integer getINCOMING();
}
