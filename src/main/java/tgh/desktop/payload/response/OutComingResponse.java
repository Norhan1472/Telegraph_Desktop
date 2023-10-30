package tgh.desktop.payload.response;

import java.util.Date;


import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutComingResponse {

	@JsonFormat(pattern="dd/MM/yyyy")	
	private Date tghDate;
	
	private String tghCode ;
	private Integer incomingSeq;

	 
	private Integer seqNo;
	 
	 
	 private String senderName;
	 
	 
	 private String recName;
	 
	 
	 private String address;
	 
	 
	 private String status;
	 
	 
	 private Integer cost;
	 
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date sendDate;
	 
	 
	 private String officeName;
	 
	 
	 private String outcomingSeq ;
	 
	 
	 private String telNo;
	private String noOfWords;
}
