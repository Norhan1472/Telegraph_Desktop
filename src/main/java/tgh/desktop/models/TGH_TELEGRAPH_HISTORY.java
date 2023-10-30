package tgh.desktop.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="TGH_TELEGRAPH_HISTORY")
@IdClass(TGH_HISTORY_COMP_KEY.class)
public class TGH_TELEGRAPH_HISTORY {
	
	@Id
	@Column(name = "TGH_ID" )
	private Integer TGH_ID;
	
	@Id
	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "ACTION_DATE" )
	private Date ACTION_DATE;
	
	@Column(name = "ACTION_BY" )
	private String ACTION_BY;
	
	@Column(name = "STATUS_CODE" )
	private String STATUS_CODE;

	@Column(name = "OFFICE_CODE" )
	private String OFFICE_CODE;
	
	@Column(name = "NOTES" )
	private String NOTES;
	
	@Column(name = "ACTION" )
	private String ACTION;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "TGH_DATE" )
	private Date TGH_DATE;
	
	@Column(name = "OFFICE_SEQ" )
	private String OFFICE_SEQ;
	
	@Column(name = "TGH_CODE" )
	private String TGH_CODE;
	
//	@Transient
	@Column(name = "USER_NAME" )
	private String USER_NAME;

	@Column(name = "STATUS_NAME" )
	private String STATUS_NAME;

}
