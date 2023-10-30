package tgh.desktop.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name="TGH_MSG_STATUS")
public class TGH_MSG_STATUS {

	@Id
	@Column(name = "STATUS_CODE" )
	private String statusCode;
	
	@Column(name = "STATUS_NAME" )
	private String statusName;
	
	@Column(name = "STATUS_ORDER" )
	private Integer statusOrder;
	
	@Column(name = "DESCRIPTION" )
	private String descriptuin;
	
	@Column(name = "SHOW_ARCHIVE" )
	private Integer showArchive;
	
}
