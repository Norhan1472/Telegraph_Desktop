package tgh.desktop.payload.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class lineInfo_Response {
	
	private String paidMode;
	private String returnCode;
	private String returnDesc;
	private String subscriberStatus;
	private String Balance;
	
}
