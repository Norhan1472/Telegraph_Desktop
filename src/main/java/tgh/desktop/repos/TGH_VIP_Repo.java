package tgh.desktop.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tgh.desktop.models.TGH_CITY;
import tgh.desktop.models.TGH_VIP;

@Repository
public interface TGH_VIP_Repo extends JpaRepository<TGH_VIP , Integer>{ 
			
	List<TGH_VIP> findByCountryCode(String cityCode);
	
	
	@Query(value = " SELECT FEES from TGH_COUNTRY_VIP where PERSON_NO =:vipNo"
			, nativeQuery = true)
	Integer fees (Integer vipNo);
	
	
	
}
