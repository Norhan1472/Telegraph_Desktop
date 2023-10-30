package tgh.desktop.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tgh.desktop.models.Template_Type;

@Repository
public interface TEMPLATE_TYPE_reposatiry extends JpaRepository<Template_Type, String> {

	
//	@Query(value = "SELECT TEMP_TYPE_CODE,TEMP_TYPE_NAME from TGH_TEMPLATE_TYPE "
//			+ "order by TEMP_TYPE_CODE", nativeQuery = true)
//	@QueryHints(value = { @QueryHint(name = "org.hibernate.readOnly", value = "true") })
//	List<Template_Type> findByTempTypeCode();
//	
//	@Query(value = "SELECT TEMP_TYPE_CODE,TEMP_TYPE_NAME from TGH_TEMPLATE_TYPE "
//			+ "where TEMP_TYPE_CODE =:code", nativeQuery = true)
//	List<Template_Type> findTempType(String code);
	
}
