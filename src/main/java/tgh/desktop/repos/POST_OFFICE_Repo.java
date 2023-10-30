package tgh.desktop.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tgh.desktop.models.POST_OFFICE;


@Repository
public interface POST_OFFICE_Repo extends JpaRepository<POST_OFFICE , String>{
	
	
//	@Query(value = "SELECT OFFICE_CODE , OFFICE_NAME , OFF_TEL_NO , OFF_ADDRESS , INTERNATIONAL "
//			+ ",COUNTRY_CODE , DEST_IND , IN_SERVICE , NOTES ,CITY_CODE ,OUTGOING ,INCOMING "
//			+ ",ARRIVAL_COUNT FROM TGH_POST_OFFICE WHERE INTERNATIONAL=0"
//			, nativeQuery = true)
//		public List<POST_OFFICE> findLocalPostalOffice();
	
		@Query(value="SELECT OFFICE_NAME FROM TGH_POST_OFFICE",nativeQuery = true)
		List<String> getOriginOffices();
		
		@Query(value="SELECT OFFICE_CODE,OFFICE_NAME,OFF_TEL_NO,OFF_ADDRESS,INTERNATIONAL,COUNTRY_CODE,DEST_IND,IN_SERVICE\r\n" + 
				",NOTES,CITY_CODE,OUTGOING,INCOMING,ARRIVAL_COUNT FROM TGH_POST_OFFICE",nativeQuery = true)
		List<POST_OFFICE> getOffices();
		
		@Query(value = "SELECT OFFICE_CODE , OFFICE_NAME , OFF_TEL_NO , OFF_ADDRESS , INTERNATIONAL \n" +
				",COUNTRY_CODE , DEST_IND , IN_SERVICE , NOTES ,CITY_CODE ,OUTGOING ,INCOMING , \n" +
				"ARRIVAL_COUNT from TGH_POST_OFFICE \n" +
				"where INCOMING = 1 and (:cityCode IS NULL OR CITY_CODE =:cityCode ) "
				, nativeQuery = true)
			public List<POST_OFFICE> findInterPostalOffice(String cityCode);
	@Query(value = "SELECT OFFICE_CODE , OFFICE_NAME , OFF_TEL_NO , OFF_ADDRESS , INTERNATIONAL \n" +
			",COUNTRY_CODE , DEST_IND , IN_SERVICE , NOTES ,CITY_CODE ,OUTGOING ,INCOMING , \n" +
			"ARRIVAL_COUNT from TGH_POST_OFFICE \n" +
			"where INCOMING = 1 and (:cityCode IS NULL OR CITY_CODE =:cityCode ) and office_code != :officeCode"
			, nativeQuery = true)
	public List<POST_OFFICE> findInterPostalOfficewithoutCurrentOffice(String cityCode,String officeCode);
	@Query(value = "SELECT OFFICE_CODE , OFFICE_NAME , OFF_TEL_NO , OFF_ADDRESS , INTERNATIONAL \n " +
			",COUNTRY_CODE , DEST_IND , IN_SERVICE , NOTES ,CITY_CODE ,OUTGOING ,INCOMING ,\n " +
			"ARRIVAL_COUNT from TGH_POST_OFFICE \n " +
			" where OUTGOING = 1 and (:cityCode IS NULL OR CITY_CODE =:cityCode ) and IN_SERVICE = 1 "
			, nativeQuery = true)
	public List<POST_OFFICE> findOriginOffice(String cityCode);
	@Query(value="SELECT OFFICE_NAME FROM TGH_POST_OFFICE where OFFICE_CODE= :officeCode",nativeQuery = true)
	public String getOfficesByOfficeCode(String officeCode);
		
}
