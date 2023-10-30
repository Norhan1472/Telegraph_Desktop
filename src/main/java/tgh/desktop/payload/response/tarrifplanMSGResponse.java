package tgh.desktop.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class tarrifplanMSGResponse {

	
	@JsonProperty(value = "tempTypeCode")
	private Integer TEMPLATE_ENABLE;
	
	@JsonProperty(value = "deliveryNotice")
	private Integer DELIVERY_ENABLE;
	
	@JsonProperty(value = "urgent")
	private Integer URGENT_ENABLE;
	
	@JsonProperty(value = "decoration")
	private Integer DECORATION_ENABLE;
	
	@JsonProperty(value = "sms")
	private Integer SMS_ENABLE;
	
	
}
