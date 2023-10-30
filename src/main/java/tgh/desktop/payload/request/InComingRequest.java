package tgh.desktop.payload.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InComingRequest {

//	Integer idNumber;
	Integer tghId;
	Integer deliveryNotice;
	Integer admin;
	Integer incomingSeq ;
	String country ;
	String tghCode ;
	String city ;
	@JsonFormat(pattern="dd/MM/yyyy")
	String tghDate ;
	String officeCode ;
	String lang ;
	String status ;
	Integer decoration ;
	Integer template ;
	@JsonFormat(pattern="dd/MM/yyyy")
	String fromDate ;
	@JsonFormat(pattern="dd/MM/yyyy")
	String endDate ;

}
