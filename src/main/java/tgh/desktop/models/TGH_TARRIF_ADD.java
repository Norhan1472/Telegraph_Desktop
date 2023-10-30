package tgh.desktop.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="TGH_TARRIF_ADD")
@IdClass(Tarrif_ADD_ID.class)
public class TGH_TARRIF_ADD {
	
	@Id
	@Column(name = "PLAN_CODE" )
	private String planCode;

	@Id
	@Column(name = "ITEM_NO" )
	private Integer itemNo;
	
	@Column(name = "VALID" )
	private Integer Valid;

	@Column(name = "TAX_ENABLED" )
	private Integer taxEnabled;
	

	@Column(name = "TEMP_ENABLED" )
	private Integer tempEnabled;
	
//	@ManyToOne(fetch = FetchType.LAZY, targetEntity = TGH_ADD_VALUE.class,cascade = CascadeType.ALL)
//    @JoinColumn(name="ITEM_NO", referencedColumnName = "ITEM_NO",
//    insertable = false,updatable = false)
//    private TGH_ADD_VALUE tghAddValue;
//
//
//	@ManyToOne(fetch = FetchType.LAZY, targetEntity = TGH_TARRIF_PLAN.class)
//    @JoinColumn(name="PLAN_CODE", referencedColumnName = "PLAN_CODE",
//    insertable = false,updatable = false)
//    private TGH_TARRIF_PLAN tarrifPlan;
//
//
//	public TGH_ADD_VALUE getTghAddValue() {
//		return tghAddValue;
//	}
//
//
//	public void setTghAddValue(TGH_ADD_VALUE tghAddValue) {
//		this.tghAddValue = tghAddValue;
//	}


//	public TGH_TARRIF_PLAN getTarrifPlan() {
//		return tarrifPlan;
//	}
//
//
//	public void setTarrifPlan(TGH_TARRIF_PLAN tarrifPlan) {
//		this.tarrifPlan = tarrifPlan;
//	}


//	public String getPlanCode() {
//		return planCode;
//	}
//
//
//	public void setPlanCode(String planCode) {
//		this.planCode = planCode;
//	}
//
//
//	public Integer getItemNo() {
//		return itemNo;
//	}
//
//
//	public void setItemNo(Integer itemNo) {
//		this.itemNo = itemNo;
//	}
//
//
//	public Integer getValid() {
//		return Valid;
//	}
//
//
//	public void setValid(Integer valid) {
//		Valid = valid;
//	}
//
//
//	public Integer getTaxEnabled() {
//		return taxEnabled;
//	}
//
//
//	public void setTaxEnabled(Integer taxEnabled) {
//		this.taxEnabled = taxEnabled;
//	}
//
//
//	public Integer getTempEnabled() {
//		return tempEnabled;
//	}
//
//
//	public void setTempEnabled(Integer tempEnabled) {
//		this.tempEnabled = tempEnabled;
//	}
//
//
//	
	
}
