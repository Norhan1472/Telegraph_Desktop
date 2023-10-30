package tgh.desktop.repos;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
//import tgh.desktop.models.TGH_DRAFT;

import tgh.desktop.models.TGH_DRAFT;

@Repository
public interface TGH_DRAFT_Repo extends JpaRepository<TGH_DRAFT, Integer>{
	

	@Query(value = "SELECT TghGeneratorDraft.GEN_ID, \n"
			+ "	TghGeneratorDraft.USER_CODE, \n"
			+ "	 TghGeneratorDraft.CALL_DATE, \n"
			+ "	 TghGeneratorDraft.CALLER_TEL_NO, \n"
			+ "	TghGeneratorDraft.CALLER_NAME, \n"
			+ "	TghGeneratorDraft.BILL_TEL_NO, \n"
			+ " TghGeneratorDraft.SEND_DATE, \n"
			+ "  TghGeneratorDraft.COUNTRY_CODE, \n"
			+ "	TghGeneratorDraft.LANG_CODE, \n"
			+ "	  TghGeneratorDraft.PLAN_CODE, \n"
			+ "	 TghGeneratorDraft.TEMPLATE, \n"
			+ "	  TghGeneratorDraft.TEMP_TYPE_CODE, \n"
			+ "	  TghGeneratorDraft.TEMP_CODE, \n"
			+ "	  TghGeneratorDraft.DELIVERY_NOTICE, \n"
			+ "	 TghGeneratorDraft.DECORATION, \n"
			+ "	 TghGeneratorDraft.URGENT, \n"
			+ "	 TghGeneratorDraft.ADMIN, \n"
			+ "	 TghGeneratorDraft.INTERNATIONAL, \n"
			+ "	 TghGeneratorDraft.NOTES, \n"
			+ "	 TghGeneratorDraft.MESSAGE, \n"
			+ "	 TghGeneratorDraft.DRAFT_DATE, \n"
			+ "	TghGeneratorDraft.SMS \n"
			+ "	 FROM TGH_GENERATOR_DRAFT TghGeneratorDraft\n"
			+ "	 WHERE TghGeneratorDraft.BILL_TEL_NO = :billTelNo \n"
			//+ "   and user_code=:usercode\n"
			+ "	 AND TO_DATE(TghGeneratorDraft.DRAFT_DATE) = TO_DATE(SYSDATE)\n"
			+ "	ORDER BY TghGeneratorDraft.DRAFT_DATE DESC"
			, nativeQuery = true)
	List<TGH_DRAFT> findByBillTelNo(String billTelNo);
	
	

	@Query(value = "SELECT TghGeneratorDraft.GEN_ID, \n"
			+ "	   TghGeneratorDraft.USER_CODE, \n"
			+ "	   TghGeneratorDraft.CALL_DATE, \n"
			+ "	   TghGeneratorDraft.CALLER_TEL_NO, \n"
			+ "	   TghGeneratorDraft.CALLER_NAME, \n"
			+ "	   TghGeneratorDraft.BILL_TEL_NO, \n"
			+ "	   TghGeneratorDraft.SEND_DATE, \n"
			+ "	   TghGeneratorDraft.COUNTRY_CODE, \n"
			+ "	   TghGeneratorDraft.LANG_CODE, \n"
			+ "	   TghGeneratorDraft.PLAN_CODE, \n"
			+ "	   TghGeneratorDraft.TEMPLATE, \n"
			+ "	   TghGeneratorDraft.TEMP_TYPE_CODE, \n"
			+ "	   TghGeneratorDraft.TEMP_CODE, \n"
			+ "	   TghGeneratorDraft.DELIVERY_NOTICE, \n"
			+ "	   TghGeneratorDraft.DECORATION, \n"
			+ " TghGeneratorDraft.URGENT, \n"
			+ " TghGeneratorDraft.ADMIN, \n"
			+ " TghGeneratorDraft.INTERNATIONAL, \n"
			+ " TghGeneratorDraft.NOTES, \n"
			+ " TghGeneratorDraft.MESSAGE, \n"
			+ " TghGeneratorDraft.DRAFT_DATE ,"
			+ " TghGeneratorDraft.SMS \n"
			+ "	 FROM TGH_GENERATOR_DRAFT TghGeneratorDraft\n"
			+ "	 WHERE TghGeneratorDraft.USER_CODE = :userCode \n"
			+ "	 AND TO_DATE(TghGeneratorDraft.DRAFT_DATE) = TO_DATE(SYSDATE)\n"
			+ "	 ORDER BY TghGeneratorDraft.DRAFT_DATE DESC "
			, nativeQuery = true)
	List<TGH_DRAFT> findByUserCode(String userCode);
	
	
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO TGH_GENERATOR_DRAFT(GEN_ID,USER_CODE,CALL_DATE,"
+ "CALLER_TEL_NO,CALLER_NAME,SEND_DATE,"
+ "COUNTRY_CODE,LANG_CODE,PLAN_CODE,"
+ "TEMPLATE,TEMP_TYPE_CODE,TEMP_CODE,DELIVERY_NOTICE,"
+ "DECORATION,URGENT,ADMIN,INTERNATIONAL,"
+ "NOTES,MESSAGE ,SMS)"
+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
, nativeQuery = true)
	void drafts(Integer GEN_ID,String USER_CODE,Date CALL_DATE ,String CALLER_TEL_NO,
			String CALLER_NAME,Date SEND_DATE,
			String COUNTRY_CODE,String LANG_CODE,String PLAN_CODE,
			String TEMPLATE,String TEMP_TYPE_CODE,String TEMP_CODE,String DELIVERY_NOTICE,
			String DECORATION,String URGENT,String ADMIN,String INTERNATIONAL,
			String NOTES,String MESSAGE ,String SMS);

	@Transactional
	@Modifying
	@Query(value = "update TGH_GENERATOR_DRAFT \r\n"
			+ "set  USER_CODE =:USER_CODE , CALL_DATE=:CALL_DATE , \r\n"
			+ "CALLER_TEL_NO=:CALLER_TEL_NO , CALLER_NAME=:CALLER_NAME  "
			+ ", SEND_DATE=:SEND_DATE , \r\n"
			+ "COUNTRY_CODE=:COUNTRY_CODE , LANG_CODE=:LANG_CODE , PLAN_CODE=:PLAN_CODE , \r\n"
			+ "TEMPLATE=:TEMPLATE , TEMP_TYPE_CODE=:TEMP_TYPE_CODE , TEMP_CODE=:TEMP_CODE"
			+ " , DELIVERY_NOTICE=:DELIVERY_NOTICE , \r\n"
			+ "DECORATION=:DECORATION , URGENT=:URGENT , ADMIN=:ADMIN , INTERNATIONAL=:INTERNATIONAL , \r\n"
			+ "NOTES=:NOTES , MESSAGE =:MESSAGE , SMS =:SMS \r\n"
			+ "where  GEN_ID =:GEN_ID"
			, nativeQuery = true)
				void updateDraft(Integer GEN_ID,String USER_CODE,Date CALL_DATE ,
						String CALLER_TEL_NO,
						String CALLER_NAME,Date SEND_DATE,
						String COUNTRY_CODE,String LANG_CODE,String PLAN_CODE,
						String TEMPLATE,String TEMP_TYPE_CODE,String TEMP_CODE,String DELIVERY_NOTICE,
						String DECORATION,String URGENT,String ADMIN,String INTERNATIONAL,
						String NOTES,String MESSAGE ,String SMS);
	
	@Query(value = "select  count(*) from TGH_GENERATOR_DRAFT where GEN_ID =:id "
			, nativeQuery = true)
	 Integer checkGenId(Integer id);
	   
	
}

