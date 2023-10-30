package tgh.desktop.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tgh.desktop.models.SUBS_INFO;

@Repository
public interface SUBS_Repo extends JpaRepository<SUBS_INFO, Integer>{
	@Query(value = "SELECT NAME from SUBS_INFO where TELEPHONE_NO = :tel", nativeQuery = true)
	String findByName(Integer tel);
}
