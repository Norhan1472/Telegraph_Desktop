package tgh.desktop.payload.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InComingResponse {
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date tghDate;
	private String seqNo;
	private String incomingSeq;
	private String senderName;
	private String tghCode;
	private String recName;
	private String address;
	private String status;
	private String cost;
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date sendDate;
	private String originOffice;
	private String origin;
	private String noOfWords;
	
}
