package tgh.desktop.payload.request;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class updateStatusRequest {
	private List <String> tghId ;
	private String oldStatus;//4
	private String newStatus;
	private String actualRecName;
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date actualRecDate;
	private String receiptNo;
	private String redirctNotes;
	private String notes;
	private String officeCode;
	private String dispatchedTo;
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dispatchedTime;
	@JsonFormat(pattern="dd-MM-yyyy HH:mm",timezone = "Africa/Cairo")
	private Date recDate;
	private String recName;
	private InComingRequest criteria;
}
