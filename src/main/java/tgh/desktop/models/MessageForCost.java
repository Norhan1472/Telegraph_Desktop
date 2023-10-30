package tgh.desktop.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageForCost {

	private String recName;
	private String address;
	private Integer wordCount;
	private Integer noOfWords;
	private Double postalOfficePrice; //piasters
	private Double messageCost;
	private  Double various ;
	private  Double  salesTax; // piasters
	private Integer numOfExtra ;
	private Double costOfExtraName ;

	private Integer vipPrice ;
	private Double messageCostByFees;
//	private Double sumCost;

}
