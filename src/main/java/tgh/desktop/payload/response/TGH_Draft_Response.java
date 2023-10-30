package tgh.desktop.payload.response;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class TGH_Draft_Response {
	
	private Integer   genId ;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date  callDate; 
	private	String  notes;
	private	String  message;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date  draftDate;
	private String   callerTelNo;
	private String  userCode; 
	private String   callerName;
	private String   billTelNo;	
}
