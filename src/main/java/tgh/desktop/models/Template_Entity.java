package tgh.desktop.models;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
//@Data
//@Setter
//@Getter
//@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
//@EqualsAndHashCode
@Entity
@Table(name="TGH_TEMPLATE")
public class Template_Entity {

	@Id
	@Column(name = "TEMP_CODE" )
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String tempCode;
	
	@Column(name = "TEMP_TYPE_CODE" )
	private String tempTypeCode;
	
	@Column(name = "TEMP_NAME")
	private String tempName;
	
	@Column(name = "TEMP_SUBJECT")
	private String tempSubject;

	@Column(name = "TEMP_LANG_CODE")
	private String tempLangCode;
	
	@Column(name = "LANG_DIRECTION")
	private String langDirection;
	   
	
	@ManyToOne(fetch = FetchType.LAZY , targetEntity = Template_Type.class )
	@JoinColumn(name="TEMP_TYPE_CODE", referencedColumnName = "TEMP_TYPE_CODE",
		insertable =false, updatable = false)
	
	private Template_Type template_Type;


	public String getTempCode() {
		return tempCode;
	}

	public void setTempCode(String tempCode) {
		this.tempCode = tempCode;
	}

	public String getTempTypeCode() {
		return tempTypeCode;
	}

	public void setTempTypeCode(String tempTypeCode) {
		this.tempTypeCode = tempTypeCode;
	}

	public String getTempName() {
		return tempName;
	}

	public void setTempName(String tempName) {
		this.tempName = tempName;
	}

	public String getTempSubject() {
		return tempSubject;
	}

	public void setTempSubject(String tempSubject) {
		this.tempSubject = tempSubject;
	}

	public String getTempLangCode() {
		return tempLangCode;
	}

	public void setTempLangCode(String tempLangCode) {
		this.tempLangCode = tempLangCode;
	}

	public String getLangDirection() {
		return langDirection;
	}

	public void setLangDirection(String langDirection) {
		this.langDirection = langDirection;
	}

//	@Override
//	public String toString() {
//		return "Template_Entity [tempCode=" + tempCode + ", tempTypeCode=" + tempTypeCode + ", tempName=" + tempName
//				+ ", tempSubject=" + tempSubject + ", tempLangCode=" + tempLangCode + ", langDirection=" + langDirection
//				+ ", template_Type=" + template_Type + "]";
//	}
//	
	

}
