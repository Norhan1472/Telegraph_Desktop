package tgh.desktop.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface TempResponse {
	
	@JsonProperty(value = "temp_code")
	String getTEMP_CODE();
	
	@JsonProperty(value = "temp_name")
	String getTEMP_Name();
	
	@JsonProperty(value = "temp_lang_code")
	String getTEMP_LANG_CODE();
	
	@JsonProperty(value = "temp_subject")
	String getTEMP_SUBJECT();
	
	@JsonProperty(value = "temp_type_code")
	String getTEMP_TYPE_CODE();
	
	@JsonProperty(value = "lang_direction")
	String getLANG_DIRECTION();
	
	@JsonProperty(value = "temp_type_name")
	String getTEMP_TYPE_NAME();
}
