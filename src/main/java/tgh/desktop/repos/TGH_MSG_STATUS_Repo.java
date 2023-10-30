package tgh.desktop.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tgh.desktop.models.TGH_MSG_STATUS;

@Repository
public interface TGH_MSG_STATUS_Repo extends JpaRepository<TGH_MSG_STATUS, String> {
	
	@Query(value="SELECT STATUS_CODE,STATUS_NAME,STATUS_ORDER,DESCRIPTION,SHOW_ARCHIVE FROM TGH_MSG_STATUS",nativeQuery = true)
	List<TGH_MSG_STATUS> getMsgStatus();
	
	@Query(value="SELECT STATUS_CODE,STATUS_NAME,STATUS_ORDER,DESCRIPTION,SHOW_ARCHIVE"
			+ " FROM TGH_MSG_STATUS where STATUS_CODE IN(7 ,9 ,13)",nativeQuery = true)
	List<TGH_MSG_STATUS> getMsgStatusArchive();
	
	
	@Query(value="SELECT STATUS_CODE,STATUS_NAME,STATUS_ORDER,DESCRIPTION,SHOW_ARCHIVE\n"
			+ "	 FROM TGH_MSG_STATUS where STATUS_CODE not in 1"
			,nativeQuery = true)
	List<TGH_MSG_STATUS> statusForInComing();
	
	


}
