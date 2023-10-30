package tgh.desktop.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tgh.desktop.models.TGH_CITY;

@Repository
public interface TGH_CITY_Repo  extends JpaRepository<TGH_CITY, String>{

	@Query(value = " SELECT tc.COUNTRY_CODE , tc.CITY_CODE , tc.CITY_NAME "
			+ " FROM (TGH_CITY tc inner join TGH_COUNTRY  tcu On tc.COUNTRY_CODE = tcu.COUNTRY_CODE)"
			+ " where international = 0"
			, nativeQuery = true)
		public List<TGH_CITY> findlocalCities();
		
		@Query(value = "  SELECT tc.COUNTRY_CODE , tc.CITY_CODE , tc.CITY_NAME \n"
				+ "	FROM (TGH_CITY tc inner join TGH_COUNTRY  tcu On tc.COUNTRY_CODE = tcu.COUNTRY_CODE)\n"
				+ " where (:code IS NULL OR tcu.COUNTRY_CODE = :code)"
				, nativeQuery = true)
			public List<TGH_CITY> findInterCities( String code);
		
}

