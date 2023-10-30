package tgh.desktop.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor 
public class OutComingResquest {
//	private Integer idNumber ;

	Integer tghId;
	Integer deliveryNotice;
	Integer admin;

	private String tghCode ;
	
	private String country ;
	private String city ;
	@JsonFormat(pattern="dd/MM/yyyy") 
	private String tghDate ;
	private String officeCode ;  
	private String userCode ;
}
