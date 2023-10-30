package tgh.desktop.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="WFM_EMPLOYEES_SHIFTS")
public class WFM_EMPLOYEES_SHIFTS {

	@Id
	@Column(name = "EMP_ID" )
	@JsonProperty("empId")
	private Integer EMP_ID;
	
	@JsonProperty("firstName")
	@Column(name = "FIRST_NAME" )
	private String FIRST_NAME;
	
	@JsonProperty("lastName")
	@Column(name = "LAST_NAME" )
	private String LAST_NAME;
	
	@JsonProperty("officeCode")
	@Column(name = "OFFICE_CODE" )
	private String OFFICE_CODE;
	
	@JsonProperty("calendarDate")
	@Column(name = "CALENDAR_DATE" )
	private Date CALENDAR_DATE;
	
	@JsonProperty("shiftStart")
	@Column(name = "SHIFT_START" )
	private Date SHIFT_START;
	
	@JsonProperty("shiftEnd")
	@Column(name = "SHIFT_END" )
	private Date SHIFT_END;
	
	@JsonProperty("duration")
	@Column(name = "DURATION" )
	private Integer DURATION;
	@JsonProperty("officeName")
	@Column(name = "OFFICE_NAME" )
	private String OFFICE_NAME;
}
