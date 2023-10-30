package tgh.desktop.models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class intiateTGH {

	private Integer getGenId;
	private List<Template_Type> template_Type ;
	private List<TGH_Country> countries;
	private String callDate;
	
	}
