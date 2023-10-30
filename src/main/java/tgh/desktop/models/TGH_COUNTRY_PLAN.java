package tgh.desktop.models;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="TGH_COUNTRY_PLAN")
@IdClass(ID_Counrty_Plan.class)
public class TGH_COUNTRY_PLAN {

	@Id
	@Column(name = "COUNTRY_CODE" )
	private String countryCode;
	
	@Id
	@Column(name = "PLAN_CODE")
		private String planCode;
	
	@Column(name = "PLAN_NAME" )
	private String planName;

	@Column(name = "ACTIVE" )
	private String active;

	@Column(name = "DEFAULT_PLAN" )
	private Integer DEFAULT_PLAN ;
	
	@Transient
//	@Column(name = "TEMPLATE_ENABLE" )
	private Integer TEMPLATE_ENABLE;
	}
