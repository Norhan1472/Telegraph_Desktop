package tgh.desktop.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="ADD_DICTIONARY_DEF")
public class ADD_DICTIONARY_DEF {
	@Id
	@Column(name = "DIC_ID" )
	private Integer DIC_ID; 
	
	@Column(name = "GROUP_sTAG" )
	private String  GROUP_TAG;
	
	@Column(name = "GROUP_NAME" )
	private String  GROUP_NAME;
	
}
