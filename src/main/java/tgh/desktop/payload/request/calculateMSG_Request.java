package tgh.desktop.payload.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tgh.desktop.models.TGH_RECEPIENT;
import tgh.desktop.models.TGH_SENDER;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class calculateMSG_Request {
	private String callerName;
	private String planCode;
	private Integer template;
	private Integer delivery; 
	private Integer admin; 
	private Integer urgent ;
	private Integer decoration ;
	private Integer sms ;
	private String message; 

	private List <TGH_SENDER> senders;
	private List <TGH_RECEPIENT> recepients;

}
