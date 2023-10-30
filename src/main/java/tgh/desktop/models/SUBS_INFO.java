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
@Table(name="SUBS_INFO")
public class SUBS_INFO {

	@Id
	@Column(name = "TELEPHONE_NO" )
	private Integer teleNo;

	@Column(name = "CITY_CODE" )
	private String cityCode;
	
	@Column(name = "NAME" )
	private String name;

	@Column(name = "ADDRESS" )
	private String address;

	@Column(name = "CATEGORYTYPE" )
	private String categoryType;
}
