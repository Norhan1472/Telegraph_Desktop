package tgh.desktop.repos;
import java.util.Date;

import javax.persistence.QueryHint;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import tgh.desktop.models.TGH_Entity;

@Repository
public interface TGH_Repo extends JpaRepository<TGH_Entity, Integer>{
	
	@Query(value = "SELECT GEN_ID_SEQ.NEXTVAL From Dual", nativeQuery = true)
	@QueryHints(value = { @QueryHint(name = "org.hibernate.readOnly", value = "true") })
	Integer getgenId();
	

	@Transactional
	@Modifying
	@Query(value = "INSERT INTO TGH_GENERATOR(GEN_ID,USER_CODE,CALL_DATE,"
			+ "CALLER_TEL_NO,CALLER_NAME,SEND_DATE,"
			+ "COUNTRY_CODE ,LANG_CODE,PLAN_CODE,"
			+ "TEMPLATE,TEMP_TYPE_CODE,TEMP_CODE,DELIVERY_NOTICE,"
			+ "DECORATION,URGENT,ADMIN,INTERNATIONAL,"
			+ "NOTES,MESSAGE ,SMS,BILL_TEL_NO)"
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
			, nativeQuery = true)
	void setGenId(Integer GEN_ID,String USER_CODE , Date CALL_DATE, String CALLER_TEL_NO,
			String CALLER_NAME,Date SEND_DATE,
			 String COUNTRY_CODE,String LANG_CODE,String PLAN_CODE,
			 Integer TEMPLATE,String TEMP_TYPE_CODE,String TEMP_CODE,Integer DELIVERY_NOTICE,
			Integer DECORATION,Integer URGENT,Integer ADMIN,Integer INTERNATIONAL,
			String NOTES,String MESSAGE ,Integer SMS,Integer BILL_TEL_NO);

	}


