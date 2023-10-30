package tgh.desktop.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tgh.desktop.models.SC_USERS;
import tgh.desktop.payload.response.OfficeUser_Response;
import tgh.desktop.payload.response.OfficeUser_Responsesss;
 
@Repository
public interface SC_USERS_Repo  extends JpaRepository<SC_USERS, Integer> {

	@Query(value="SELECT u.USER_NAME from MTS_SECURITY.SC_USERS u  "
			+ "where u.BU_CODE = :office ",nativeQuery = true)
	List<String> userName(String office);
	

//	@Query(value="SELECT USER_NAME,"
//			+ "EMAIL_ADDRESS,"
//			+ "USER_ID,"
//			+ "DISPLAY_NAME,"
//			+ "USER_IMG,"
//			+ "OWNERSHIP_ID,"
//			+ "LAST_MODIFIED_BY,"
//			+ "BU_CODE,"
//			+ "TGH_COUNT,"
//			+ "PASSWORD"
//			+ " from MTS_SECURITY.SC_USERS where USER_NAME=:userName",nativeQuery = true)
//	SC_USERS findByUserName(String userName);
	
	

     
    			@Query(value="SELECT sc.DISPLAY_NAME,sc.BU_CODE,\r\n"
    					+ "   p.OUTGOING, p.INCOMING\r\n"
    					+ "	 from MTS_SECURITY.SC_USERS sc ,TGH_POST_OFFICE p\r\n"
    					+ "   where USER_NAME=:userName\r\n"
    					+ "    and sc.BU_CODE = p.OFFICE_CODE",nativeQuery = true)
    			OfficeUser_Response findByUserName(String userName);
	
}
