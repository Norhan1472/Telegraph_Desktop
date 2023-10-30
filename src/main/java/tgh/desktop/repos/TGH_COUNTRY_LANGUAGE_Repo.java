package tgh.desktop.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tgh.desktop.models.TGH_COUNTRY_LANGUAGE;

import java.util.List;

@Repository
public interface TGH_COUNTRY_LANGUAGE_Repo extends JpaRepository
        <TGH_COUNTRY_LANGUAGE, String> {

    @Query(value ="Select LANG_CODE from TGH_COUNTRY_LANGUAGE "
            + " where COUNTRY_CODE =:countryCode  ",nativeQuery=true)
    List<String> findByCountryCode(String countryCode);
}
