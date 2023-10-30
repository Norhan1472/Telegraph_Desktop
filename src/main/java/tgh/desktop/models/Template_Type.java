package tgh.desktop.models;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@Entity
@Table(name="TGH_TEMPLATE_TYPE")
public class Template_Type {

	@Id
	@Column(name = "TEMP_TYPE_CODE" )
	private String tempTypeCode;
	
	@Column(name = "TEMP_TYPE_NAME")
		private String tempTypeName;

	
	@OneToMany (fetch = FetchType.EAGER, mappedBy ="template_Type", cascade = CascadeType.ALL)
	//@JsonIgnore
	private List<Template_Entity> template_Entity;

	public String getTempTypeCode() {
		return tempTypeCode;
	}

	public void setTempTypeCode(String tempTypeCode) {
		this.tempTypeCode = tempTypeCode;
	}

	public String getTempTypeName() {
		return tempTypeName;
	}

	public void setTempTypeName(String tempTypeName) {
		this.tempTypeName = tempTypeName;
	}

	

	public List<Template_Entity> getTemplate_Entity() {
		return template_Entity;
	}

	public void setTemplate_Entity(List<Template_Entity> template_Entity) {
		this.template_Entity = template_Entity;
	}

//	@Override
//	public String toString() {
//		return "Template_Type [tempTypeCode=" + tempTypeCode + ", tempTypeName=" + tempTypeName + ", template_Entity="
//				;//+ template_Entity + "]";
//	}
//
//
//
//	
	
}
