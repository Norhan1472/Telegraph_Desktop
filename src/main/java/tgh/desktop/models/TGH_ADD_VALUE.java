package tgh.desktop.models;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="TGH_ADD_VALUE")
public class TGH_ADD_VALUE {

	@Id
	@Column(name = "ITEM_NO" )
	private Integer itemNo;
	@Column(name = "ITEM_NAME" )
	private String itemName;

	@Column(name = "ADD_PRICE" )
	private Integer addPrice;
	
	
//	
//	@OneToMany(mappedBy ="tghAddValue",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//	private List<TGH_TARRIF_ADD> tghTarrifAdd;
//
//	public List<TGH_TARRIF_ADD> getTghTarrifAdd() {
//		return tghTarrifAdd;
//	}
//
//
//
//	public void setTghTarrifAdd(List<TGH_TARRIF_ADD> tghTarrifAdd) {
//		this.tghTarrifAdd = tghTarrifAdd;
//	}

//
//
//	public Integer getItemNo() {
//		return itemNo;
//	}
//
//
//
//	public void setItemNo(Integer itemNo) {
//		this.itemNo = itemNo;
//	}
//
//
//
//	public String getItemName() {
//		return itemName;
//	}
//
//
//
//	public void setItemName(String itemName) {
//		this.itemName = itemName;
//	}
//
//
//
//	public Integer getAddPrice() {
//		return addPrice;
//	}
//
//
//
//	public void setAddPrice(Integer addPrice) {
//		this.addPrice = addPrice;
//	}
//	
}
