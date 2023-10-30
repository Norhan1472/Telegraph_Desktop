package tgh.desktop.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
public class OfficeUser_Responsesss {
	@JsonProperty("displayUser")
	String DISPLAY_NAME;
	@JsonProperty("office")
	String BU_CODE;
	@JsonProperty("outcoming")
	Integer OUTGOING;
	@JsonProperty("incoming")
	Integer INCOMING;
}
