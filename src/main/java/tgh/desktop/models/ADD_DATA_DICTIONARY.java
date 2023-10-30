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
@Table(name="ADD_DATA_DICTIONARY")
public class ADD_DATA_DICTIONARY {
	@Id
	@Column(name = "INDEX_ID" )
	private Integer INDEX_ID;
	
	@Column(name = "DIC_ID" )
	private Integer DIC_ID;
	
	@Column(name = "WORD" )
	private String WORD;
	
	@Column(name = "GROUP_TAG" )
	private String GROUP_TAG;
}
