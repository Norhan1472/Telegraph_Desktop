package tgh.desktop.repos;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tgh.desktop.models.TGH_TELEGHRAPH_ARCHIVE;
import tgh.desktop.models.TGH_TELEGRAPH;
import tgh.desktop.payload.response.PrintResponse;

@Repository
public interface TghArchiveRepo extends JpaRepository<TGH_TELEGHRAPH_ARCHIVE, Integer> {

	@Query(value = "SELECT T.SEQ_NO,T.TGH_CODE,T.TGH_DATE, T.REC_NAME, T.REC_NAME2,T.SENDER_NAME,T.SENDER_NAME2,T.NATIONAL_ID,T.SENDER_ADDRESS \n"
			+ "	,T.ADDRESS,T.MOBILE_NO,T.EMAIL,T.MESSAGE,PO.OFFICE_NAME ,O.OFFICE_NAME ORIGIN_OFFICE \n"
			+ "	FROM TGH_TELEGHRAPH_ARCHIVE T,TGH_POST_OFFICE O,TGH_POST_OFFICE PO\n"
			+ "	WHERE T.ORIGIN_OFFICE = O.OFFICE_CODE \n"
			+ "	AND T.OFFICE_CODE = PO.OFFICE_CODE (+) \n"
			+ "	AND T.TGH_ID IN (:TGH_ID)",nativeQuery=true)
	List<PrintResponse> printTGH(List<String> TGH_ID);

	@Query(value = "SELECT * FROM TGH_TELEGHRAPH_ARCHIVE WHERE TGH_ID=:TGH_ID" ,nativeQuery=true)
	List<TGH_TELEGHRAPH_ARCHIVE> getTGHDetails(Integer TGH_ID);
	
	@Query(value = "SELECT * FROM TGH_TELEGHRAPH_ARCHIVE tgh,tgh_post_office po  \r\n" + 
			"WHERE tgh.OFFICE_CODE = po.OFFICE_CODE(+) \r\n" + 
			"--and   tgh.status_code = sdef.status_code (+) \r\n" + 
			"and  tgh.origin_office =  po.OFFICE_CODE (+) \r\n" + 
			"and tgh.USER_CODE=:CurrentUser \r\n" + 
			"and (:TGH_ID IS NULL OR tgh.TGH_ID =:TGH_ID) \r\n" + 
			"and (:TGH_CODE IS NULL OR tgh.TGH_CODE = :TGH_CODE) \r\n" + 
			"and (:F_DATE IS NULL OR :S_DATE IS NULL OR tgh.TGH_DATE BETWEEN TO_DATE(:F_DATE,'dd/MM/yyyy') and TO_DATE(:S_DATE,'dd/MM/yyyy')+1) \r\n" + 
			"and (:CALLER_TEL_NO IS NULL OR tgh.CALLER_TEL_NO =:CALLER_TEL_NO) \r\n" + 
			"and (:BILL_TEL_NO IS NULL OR tgh.BILL_TEL_NO =:BILL_TEL_NO) \r\n" + 
			"and (:INTERNATIONAL IS NULL OR tgh.INTERNATIONAL =:INTERNATIONAL) \r\n" + 
			"and (:GEN_ID IS NULL OR tgh.GEN_ID =:GEN_ID) \r\n" + 
			"and (:SENDER_NAME IS NULL OR tgh.SENDER_NAME LIKE CONCAT(CONCAT('%',:SENDER_NAME),'%')) \r\n" + 
			"and (:REC_NAME IS NULL OR tgh.REC_NAME LIKE CONCAT(CONCAT('%',:REC_NAME),'%')) \r\n" + 
			"and (:ADDRESS IS NULL OR tgh.ADDRESS LIKE CONCAT(CONCAT('%',:ADDRESS),'%')) \r\n" + 
			"and (:STATUS_CODE IS NULL OR tgh.STATUS_CODE =:STATUS_CODE) \r\n" + 
			"and (:OFFICE_CODE IS NULL OR tgh.OFFICE_CODE =:OFFICE_CODE) \r\n" + 
			"and (:ORIGIN_OFFICE IS NULL OR po.OFFICE_NAME =:ORIGIN_OFFICE) \r\n" + 
			"and (:TGH_DATE IS NULL OR TO_CHAR(tgh.TGH_DATE,'dd/MM/yyyy') = :TGH_DATE) \r\n" + 
			"and (:ARCHIVE_DATE IS NULL OR TO_CHAR(tgh.ARCHIVE_DATE,'dd/MM/yyyy') = :ARCHIVE_DATE)\r\n" + 
			"--and (:MESSAGE IS NULL OR tgh.MESSAGE LIKE CONCAT(CONCAT('%',:MESSAGE),'%')) \r\n" + 
			"--and (:CALL_DATE IS NULL OR TO_CHAR(tgh.CALL_DATE,'dd/MM/yyyy') =:CALL_DATE) \r\n" + 
			"--and (:ACCOUNT_TYPE IS NULL OR tgh.ACCOUNT_TYPE =:ACCOUNT_TYPE) \r\n" + 
			"and (:SEQ_NO IS NULL OR tgh.SEQ_NO =:SEQ_NO) \r\n" + 
			"and (:OFFICE_SEQ IS NULL OR tgh.OFFICE_SEQ =:OFFICE_SEQ) \r\n" + 
			"--and (:NATIONAL_ID IS NULL OR tgh.NATIONAL_ID =:NATIONAL_ID) \r\n" + 
			"--and (:MOBILE_NO IS NULL OR tgh.MOBILE_NO =:MOBILE_NO) \r\n" + 
			"and (:internationalDirection IS NULL OR DECODE(po.country_code ,'EG','OUT','IN') =:internationalDirection) \r\n" + 
			"and tgh.STATUS_CODE IN (select rs.value from mts_security.sc_scopes sc,mts_security.sc_rolescopes rs, \r\n" + 
			"mts_security.sc_userrole ur ,mts_security.SC_USERS scu \r\n" + 
			"where sc.scope_id = rs.scope_id \r\n" + 
			"and ur.role_id = rs.role_id \r\n" + 
			"and sc.code='STATUS_CODE' \r\n" + 
			"and scu.USER_ID=UR.USER_ID \r\n" + 
			"and scu.USER_NAME=:CurrentUser) \r\n" + 
			"and (tgh.OFFICE_CODE is null or tgh.OFFICE_CODE  IN (select rs.value \r\n" + 
			"from mts_security.sc_scopes sc,mts_security.sc_rolescopes rs \r\n" + 
			",mts_security.sc_userrole ur ,mts_security.SC_USERS scu \r\n" + 
			"where sc.scope_id = rs.scope_id \r\n" + 
			"and ur.role_id = rs.role_id \r\n" + 
			"and sc.code='OFFICE_CODE' \r\n" + 
			"and scu.USER_ID=UR.USER_ID \r\n" + 
			"and scu.USER_NAME=:CurrentUser)) \r\n" + 
			"Order by tgh.tgh_DATE DESC" ,nativeQuery=true)
	List<TGH_TELEGHRAPH_ARCHIVE> Search(String CurrentUser,String TGH_ID, String TGH_CODE,String F_DATE,String S_DATE,
			String CALLER_TEL_NO,String BILL_TEL_NO,String INTERNATIONAL,String GEN_ID,
			String SENDER_NAME,String REC_NAME,String ADDRESS,String STATUS_CODE,
			String OFFICE_CODE,String ORIGIN_OFFICE,String OFFICE_SEQ,String TGH_DATE,String ARCHIVE_DATE,
			String internationalDirection,String SEQ_NO);
	

}
