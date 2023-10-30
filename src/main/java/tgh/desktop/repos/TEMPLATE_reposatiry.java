package tgh.desktop.repos;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tgh.desktop.models.Template_Entity;
import tgh.desktop.payload.response.TempResponse;

@Repository
public interface TEMPLATE_reposatiry extends JpaRepository<Template_Entity, String> {
	
	
	@Query(value = "select temp_code,t.temp_name,t.temp_lang_code,t.temp_subject,t.temp_type_code,t.lang_direction,tt.temp_type_name \r\n" + 
			"from tgh_template t INNER JOIN tgh_template_type tt\r\n" + 
			"on t.temp_type_code = tt.temp_type_code", nativeQuery = true)
	List<TempResponse> getTemplates();

//	@Query(value = "select count(*) from tgh_template where TEMP_TYPE_CODE =:tempTypeCode", nativeQuery = true)
//	Integer findBytempTypeCode(String tempTypeCode);
//	
//	@Query(value = 
//		"SELECT TEMP_CODE , TEMP_NAME,TEMP_LANG_CODE , TEMP_SUBJECT , TEMP_TYPE_CODE , LANG_DIRECTION from tgh_template"
//		+ " where TEMP_TYPE_CODE = :tempTypeCode "
//			, nativeQuery = true)
//	List<Template_Entity> findtempTypeCode(String tempTypeCode);
	
}


