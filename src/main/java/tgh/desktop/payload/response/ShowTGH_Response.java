package tgh.desktop.payload.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ShowTGH_Response {
	private Integer seqNo;
	private String tghCode;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date sendDate;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date tghDate;
	private String recName;
	private String senderName;
	private String status;
	private Integer noOfWords;
	private Integer tghCost;
	private  String office ;
	private String userCode;

//	private Integer genId ;
//	@JsonFormat(pattern="yyyy-MM-dd")
//	private Date sendDate;
//	@JsonFormat(pattern="yyyy-MM-dd")
//	private Date tghDate;
//	private String callerName;
//	private String senderName;
//	private String recName;
//	private Integer tghCost;
//	private Integer seqNo;
//	private String status;
//	private String tghCode;
//	private String userCode;
//	private  String office ;
//	private Integer noOfWords;
}
