package tgh.desktop.payload.request;

import java.util.Date;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelTGH {
	
	@JsonProperty("tghID")
	private Integer TGH_ID;
	
	@JsonProperty("billTelNo")
	@Required
	private String billTelNo;
	
	@JsonProperty("reason")
	private String reason;

}
