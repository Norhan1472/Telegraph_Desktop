package tgh.desktop.repos;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tgh.desktop.models.TGH_RECEPIENT_DRAFT;

@Repository
public interface REC_DRAFT_Repo extends JpaRepository<TGH_RECEPIENT_DRAFT , Integer>{
	@Transactional
	@Modifying
	@Query(value = "update TGH_RECEPIENT_DRAFT\n"
			+ "	set  REC_NAME =:REC_NAME ,\n"
			+ "REC_TEL_NO=:REC_TEL_NO ,\n"
			+ "	ADDRESS=:ADDRESS , POSTAL_OFFICE=:POSTAL_OFFICE \n"
			+ "	, OFFICE_CODE=:OFFICE_CODE ,\n"
			+ "	REC_VIP=:REC_VIP , VIP_NO=:VIP_NO \n"
			+ ",SENDER_RESP=:SENDER_RESP,\n"
			+ "REC_NAME2=:REC_NAME2,CITY_CODE=:CITY_CODE\n"
			+ "	where  GEN_ID =:GEN_ID"
	, nativeQuery = true)
	 void updateRecDraft(Integer GEN_ID,String  REC_NAME, String REC_TEL_NO
			 , String ADDRESS, String POSTAL_OFFICE, String OFFICE_CODE, String REC_VIP
			 , String VIP_NO, String SENDER_RESP
			 , String REC_NAME2,String CITY_CODE);
	
	@Query(value = "select  count(*) from TGH_RECEPIENT_DRAFT where GEN_ID =:id "
			, nativeQuery = true)
	 Integer checkGenId(Integer id);
	
}
