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
@Table(name="TGH_TARIFF_PLAN")
public class TGH_TARRIF_PLAN {
	

	@Id
	@Column(name = "PLAN_CODE" )
	private String planCode;
	
	
	@Column(name = "PLAN_NAME" )
	private String	planName;
	
	@Column(name = "TEMPLATE_ENABLE" )
	private Integer templateEnable;
	
	@Column(name = "DELIVERY_ENABLE" )
	private Integer deliveryEnable;
	
	@Column(name = "URGENT_ENABLE" )
	private Integer urgentEnable;
	
	@Column(name = "DECORATION_ENABLE" )
	private Integer decorationEnable;
	
	@Column(name = "WORD_PRICE" )
	private Integer wordPrice;
	
	@Column(name = "DECORATION_PRICE" )
	private Integer decorationPrice;
	
	@Column(name = "PO_PRICE" )
	private Integer poPrice;
	
	@Column(name = "URGENT_PRICE" )
	private Integer urgentPrice;
	
	@Column(name = "TEMPLATE_PRICE" )
	private Integer templatePrice;
	
	@Column(name = "DELIVERY_PRICE" )
	private Integer deliveryPrice;
	
	@Column(name = "ADMIN" )
	private Integer admin = 0;
	
	@Column(name = "CHAR_WORD" )
	private Integer charWord;
	
	@Column(name = "INQUIRY_PRICE" )
	private Integer inquiryPrice;
	
	@Column(name = "SALES_TAX" )
	private Integer salesTax;
	
	@Column(name = "SMS_ENABLE" )
	private Integer SMSEnable;
	
	@Column(name = "SMS_PRICE" )
	private Integer SMSPrice; 
	
	
	@Column(name = "WORDS_COUNT" )
	private Integer wordsCount; 
	
//	@OneToMany(mappedBy ="tarrifPlan",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//	private List<TGH_TARRIF_ADD> tghTarrifAdd;
//
//	public List<TGH_TARRIF_ADD> getTghTarrifAdd() {
//		return tghTarrifAdd;
//	}
//
//	public void setTghTarrifAdd(List<TGH_TARRIF_ADD> tghTarrifAdd) {
//		this.tghTarrifAdd = tghTarrifAdd;
//	}
//
		
	
}
