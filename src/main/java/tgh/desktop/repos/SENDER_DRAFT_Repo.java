package tgh.desktop.repos;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tgh.desktop.models.TGH_SENDER_DRAFT;

@Repository
public interface SENDER_DRAFT_Repo extends JpaRepository<TGH_SENDER_DRAFT , Integer>{
	@Transactional
	@Modifying
	@Query(value = "update TGH_SENDER_DRAFT\n"
			+ "	set  SENDER_NAME =:SENDER_NAME ,\n"
			+ "	 ADDRESS=:ADDRESS \n"
			+ "	, SENDER_NAME2=:SENDER_NAME2 ,\n"
			+ "	MOBILE_NO=:MOBILE_NO , NATIONAL_ID=:NATIONAL_ID ,EMAIL =:EMAIL\n"
			+ "	where  GEN_ID =:GEN_ID "
			+ " "
			, nativeQuery = true)
	void updateSenderDraft(Integer GEN_ID,String SENDER_NAME,
			String ADDRESS,String SENDER_NAME2,String EMAIL,
			String MOBILE_NO,String NATIONAL_ID);	

		
	@Query(value = "select  count(*) from TGH_SENDER_DRAFT where GEN_ID =:id "
			, nativeQuery = true)
	 Integer checkGenId(Integer id);	

}
