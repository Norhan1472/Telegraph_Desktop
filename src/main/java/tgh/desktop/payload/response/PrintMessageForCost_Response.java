package tgh.desktop.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value =Include.NON_NULL)
public class PrintMessageForCost_Response {

	@JsonProperty("المرسل_اليه")
	private String recName;
	@JsonProperty("جهه_الورود")
	private String address;
	@JsonProperty("عدد_الكلمات ")
	private Integer noOfBulk;
	@JsonProperty("عدد_الكلمات_الشريحة ")
	private Integer wordCount;
	@JsonProperty("عدد_الكلمات_الفعلي ")
	private Integer noOfWords;
	@JsonProperty("عدد_كلمات_الراسل_و_المرسل_اليه_الاضافيان")
	private Integer numOfExtra;
	@JsonProperty("سعر_صندوق_البريد")
	private Double postalOfficePrice;

	@JsonProperty("سعر_الرد_الخالص")
	private Double urgentPrice;

	@JsonProperty("سعر النموذج")
	private Double templatePrice;

	@JsonProperty("سعر_علم_الوصول")
	private Double deliveryPrice;

	@JsonProperty("سعر_الزركشه")
	private Double decorationPrice;

	@JsonProperty("سعر_الرساله")
	private Double SMSPrice;
	@JsonProperty("رسوم الشخصيه الهامه")
	private Integer vipPrice ;

	@JsonProperty("تكلفه_البرقيه")
	private Double messageCost;

	@JsonProperty("عناصر إضافيه")
	private String addedPrice ;

	@JsonProperty("تكلفه_الراسل_والمرسل_اليه_الاضافي")
	private Double costOfExtraName ;
	@JsonProperty("متنوعات")
	private  Double various ;

	@JsonProperty("قيمه_ضريبه_المبيعات")
	private  Double  salesTax ; // piasters


	@JsonProperty("اجمالي_التكلفه")
	private Double messageCostByFees;

//	@JsonProperty("اجمالي_التكلفه_التراكميه")
//	private Double sumCost;

}

