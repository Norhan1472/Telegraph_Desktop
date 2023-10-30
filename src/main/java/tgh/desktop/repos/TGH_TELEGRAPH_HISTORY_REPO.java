package tgh.desktop.repos;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import tgh.desktop.models.TGH_TELEGRAPH_HISTORY;

@Repository
public interface TGH_TELEGRAPH_HISTORY_REPO extends JpaRepository<TGH_TELEGRAPH_HISTORY, Integer>{
	
	@Query(value = "SELECT T.TGH_ID,T.ACTION_DATE,T.ACTION_BY,S.DISPLAY_NAME AS USER_NAME,T.STATUS_CODE,M.STATUS_NAME,T.OFFICE_CODE,T.NOTES,T.ACTION,T.TGH_DATE,T.OFFICE_SEQ,T.TGH_CODE \r\n" + 
			"FROM TGH_TELEGRAPH_HISTORY T LEFT JOIN MTS_SECURITY.SC_USERS S \r\n" + 
			"ON T.ACTION_BY= NVL(S.USER_NAME,T.ACTION_BY) LEFT JOIN TGH_MSG_STATUS M ON T.STATUS_CODE=M.STATUS_CODE \r\n" + 
			"WHERE T.TGH_ID=:TGH_ID", nativeQuery = true)
	List<TGH_TELEGRAPH_HISTORY> getTghHistory(Integer TGH_ID);

}
