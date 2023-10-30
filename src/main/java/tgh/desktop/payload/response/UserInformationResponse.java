package tgh.desktop.payload.response;

import lombok.Data;

@Data
public class UserInformationResponse {

	private Integer tghCount;
	private Double tghCost;
	private String firstTghCode;
	private String lastTghCode;
}
