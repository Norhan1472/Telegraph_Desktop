package tgh.desktop.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tgh.desktop.models.TGH_Country;


@Repository
public interface TGH_Country_Repo extends JpaRepository<TGH_Country , String>{
	
	@Query(value =
			"SELECT COUNTRY_CODE , COUNTRY_NAME , DEST_IND , ACTIVE , CAPITAL , "
			+ "INTERNATIONAL ,ENGLISH_NAME , DEST_IND_ENG from TGH_COUNTRY where INTERNATIONAL=0"
			, nativeQuery = true)
	
	public List<TGH_Country> findLocalCountry();
	
	
	@Query(value =
			"SELECT COUNTRY_CODE , COUNTRY_NAME , DEST_IND , ACTIVE , CAPITAL , "
			+ "INTERNATIONAL ,ENGLISH_NAME , DEST_IND_ENG from TGH_COUNTRY where INTERNATIONAL=:flag "
			, nativeQuery = true)
	public List<TGH_Country> findInterCountry(Integer flag);
	
	@Query(value = "SELECT COUNTRY_CODE , COUNTRY_NAME ,DEST_IND , ACTIVE, CAPITAL \n"
			+ ", INTERNATIONAL , ENGLISH_NAME , DEST_IND_ENG from tgh_country where \n"
			+ " (:international IS NULL OR international = :international)",nativeQuery = true)
	public List<TGH_Country> getCountries(Integer international);

	@Query("select t from TGH_Country t where t.active = ?1")
	List<TGH_Country> findByActive(Integer active);
}
