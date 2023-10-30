package tgh.desktop.repos;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tgh.desktop.models.TGH_TELEGRAPH;
import tgh.desktop.payload.response.*;


@Repository
public interface Queue_Repo extends JpaRepository<TGH_TELEGRAPH , Integer>{
	@Query(value = "UPDATE TGH_TELEGRAPH tel\n" +
			"SET tel.status_code = '10'\n" +
			"WHERE tel.tgh_id = :tghId\n" +
			"AND not EXISTS (\n" +
			"    SELECT 1\n" +
			"    FROM MTS_SECURITY.SC_USER_Permission u\n" +
			"    WHERE u.USER_NAME = :userName\n" +
			"    AND u.MODULE_ID = :moduleId \n" +
			"    AND u.PERMISSION_ID = :permissionNo \n" +
			")",nativeQuery = true)
	@Modifying
	@Transactional
	int updateStatusCode(int tghId,String userName,Integer moduleId,Integer permissionNo);
	List<TGH_TELEGRAPH> findByGenId(Integer genId );
	
	@Query(value = "SELECT count(*) count from TGH_TELEGRAPH \n"
			+ " where STATUS_CODE= 2 and ORIGIN_OFFICE IN "
			+ " ( SELECT u.BU_CODE from MTS_SECURITY.SC_USERS u where u.USER_NAME =:username )",
			nativeQuery=true)
	Integer getNumberOfTGH(String username);
	
	
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE TGH_TELEGRAPH SET status_code='9', notes =:reason ,bill_tel_no=:billTelNo WHERE tgh_id=:TGH_ID",nativeQuery=true)
	void cancelTGH(Integer TGH_ID,String billTelNo,String reason);
	
	@Query(value = "select bill_tel_no from TGH_TELEGRAPH where tgh_id=:TGH_ID",nativeQuery=true)
	String getBillTelNo(Integer TGH_ID);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE TGH_TELEGRAPH SET status_code='11' WHERE tgh_id=:TGH_ID",nativeQuery=true)
	void modifyTGH(Integer TGH_ID); 
	
	@Query(value = "SELECT tgh.TGH_ID,tgh.tgh_CODE,tgh.tgh_DATE,tgh.USER_CODE,tgh.SEQ_NO,tgh.CALLER_NAME,tgh.CALLER_TEL_NO,tgh.BILL_TEL_NO,tgh.PLAN_CODE,tgh.TEMP_CODE,tgh.LANG_CODE,tgh.DELIVERY_NOTICE\n"
			+ ",tgh.DECORATION,tgh.NOTES,tgh.URGENT,tgh.ADMIN,tgh.INTERNATIONAL,tgh.TEMPLATE,tgh.SEND_DATE,tgh.COUNTRY_CODE,tgh.TEMP_TYPE_CODE,tgh.GEN_ID,tgh.SENDER_NAME,tgh.REC_NAME,tgh.POSTAL_OFFICE\n"
			+ ",tgh.ADDRESS,tgh.REC_VIP,tgh.VIP_NO,tgh.STATUS_CODE,tgh.OFFICE_CODE,tgh.tgh_COST,tgh.ACTUAL_REC_NAME,tgh.ACTUAL_REC_DATE,tgh.MESSAGE,tgh.CALL_DATE,tgh.ORIGIN_OFFICE,tgh.REDIRECTED\n"
			+ ",tgh.REDIRECT_NOTES,tgh.GENERATED_BY,tgh.LAST_MODIFIED_BY,tgh.SENDER_RESP,tgh.RECEIPT_NO,tgh.OFFICE_SEQ,tgh.SENDER_NAME2,tgh.REC_NAME2,tgh.SENDER_ADDRESS,tgh.NO_OF_WORDS\n"
			+ ",tgh.BILL_FLAG,tgh.RECORD_STATUS,tgh.UPDATE_OFF_SEQ,tgh.EMAIL,tgh.MOBILE_NO,tgh.NATIONAL_ID,tgh.ACCOUNT_TYPE,--,SMS\n"
			+ "DECODE(po.country_code ,'EG','OUT','IN') internationalDirection,/*tgh.PAID_AMOUNT,*/tgh.SMS,tgh.CITY_CODE\n"
			+ "FROM tgh_TELEGRAPH tgh,tgh_post_office po \n"
			+ "WHERE tgh.OFFICE_CODE = po.OFFICE_CODE(+) \n"
			+ "	--and   tgh.status_code = sdef.status_code (+) \n"
			+ "and  tgh.origin_office =  po.OFFICE_CODE (+) \n"
			+ "and tgh.USER_CODE=:CurrentUser\n"
			+ "and (:TGH_ID IS NULL OR tgh.TGH_ID =:TGH_ID) \n"
			+ "and (:TGH_CODE IS NULL OR tgh.TGH_CODE = :TGH_CODE) \n"
			+ "and (:F_DATE IS NULL OR :S_DATE IS NULL OR tgh.TGH_DATE BETWEEN TO_DATE(:F_DATE,'dd/MM/yyyy') and TO_DATE(:S_DATE,'dd/MM/yyyy')+1) \n"
			+ "and (:CALLER_TEL_NO IS NULL OR tgh.CALLER_TEL_NO =:CALLER_TEL_NO) \n"
			+ "and (:BILL_TEL_NO IS NULL OR tgh.BILL_TEL_NO =:BILL_TEL_NO) \n"
			+ "and (:INTERNATIONAL IS NULL OR tgh.INTERNATIONAL =:INTERNATIONAL) \n"
			+ "and (:GEN_ID IS NULL OR tgh.GEN_ID =:GEN_ID) \n"
			+ "and (:SENDER_NAME IS NULL OR tgh.SENDER_NAME LIKE CONCAT(CONCAT('%',:SENDER_NAME),'%')) \n"
			+ "and (:REC_NAME IS NULL OR tgh.REC_NAME LIKE CONCAT(CONCAT('%',:REC_NAME),'%')) \n"
			+ "and (:ADDRESS IS NULL OR tgh.ADDRESS LIKE CONCAT(CONCAT('%',:ADDRESS),'%')) \n"
			+ "and (:STATUS_CODE IS NULL OR tgh.STATUS_CODE =:STATUS_CODE) \n"
			+ "and (:OFFICE_CODE IS NULL OR tgh.OFFICE_CODE =:OFFICE_CODE) \n"
			+ "and (:ORIGIN_OFFICE IS NULL OR po.OFFICE_NAME =:ORIGIN_OFFICE) \n"
			+ "and (:TGH_DATE IS NULL OR TO_CHAR(tgh.TGH_DATE,'dd/MM/yyyy') = :TGH_DATE) \n"
			+ "and (:MESSAGE IS NULL OR tgh.MESSAGE LIKE CONCAT(CONCAT('%',:MESSAGE),'%')) \n"
			+ "and (:CALL_DATE IS NULL OR TO_CHAR(tgh.CALL_DATE,'dd/MM/yyyy') =:CALL_DATE) \n"
			+ "and (:ACCOUNT_TYPE IS NULL OR tgh.ACCOUNT_TYPE =:ACCOUNT_TYPE) \n"
			+ "and (:SEQ_NO IS NULL OR tgh.SEQ_NO =:SEQ_NO) \n"
			+ "and (:OFFICE_SEQ IS NULL OR tgh.OFFICE_SEQ =:OFFICE_SEQ) \n"
			+ "and (:NATIONAL_ID IS NULL OR tgh.NATIONAL_ID =:NATIONAL_ID) \n"
			+ "and (:MOBILE_NO IS NULL OR tgh.MOBILE_NO =:MOBILE_NO) \n"
			+ "--      and (:CITY_CODE IS NULL OR tgh.CITY_CODE =:CITY_CODE) \n"
			+ "and (:internationalDirection IS NULL OR DECODE(po.country_code ,'EG','OUT','IN') =:internationalDirection) \n"
			+ "and tgh.STATUS_CODE IN (select rs.value from mts_security.sc_scopes sc,mts_security.sc_rolescopes rs, \n"
			+ "mts_security.sc_userrole ur ,mts_security.SC_USERS scu \n"
			+ "where sc.scope_id = rs.scope_id \n"
			+ "and ur.role_id = rs.role_id \n"
			+ "and sc.code='STATUS_CODE' \n"
			+ "and scu.USER_ID=UR.USER_ID \n"
			+ "and scu.USER_NAME=:CurrentUser) \n"
			+ "and (tgh.OFFICE_CODE is null or tgh.OFFICE_CODE  IN (select rs.value \n"
			+ "from mts_security.sc_scopes sc,mts_security.sc_rolescopes rs \n"
			+ ",mts_security.sc_userrole ur ,mts_security.SC_USERS scu \n"
			+ "where sc.scope_id = rs.scope_id \n"
			+ "and ur.role_id = rs.role_id \n"
			+ "and sc.code='OFFICE_CODE' \n"
			+ "and scu.USER_ID=UR.USER_ID \n"
			+ "and scu.USER_NAME=:CurrentUser)) \n"
			+ "Order by tgh.tgh_DATE DESC", nativeQuery = true)
	List<TGH_TELEGRAPH> Search(String CurrentUser,String TGH_ID, String TGH_CODE,String F_DATE,String S_DATE,
			String CALLER_TEL_NO,String BILL_TEL_NO,String INTERNATIONAL,String GEN_ID,
			String SENDER_NAME,String REC_NAME,String ADDRESS,String STATUS_CODE,
			String OFFICE_CODE,String ORIGIN_OFFICE,String OFFICE_SEQ,String TGH_DATE,String MESSAGE,String CALL_DATE,
			String internationalDirection,String ACCOUNT_TYPE,String SEQ_NO,String NATIONAL_ID,String MOBILE_NO);
	

	@Query(value = "SELECT T.SEQ_NO,T.TGH_CODE,T.TGH_DATE,T.TGH_ID,"
			+ "T.DELIVERY_NOTICE,T.ADDRESS_DETAILS,T.POSTAL_OFFICE,T.URGENT ,T.SMS \n"
			+ "   , T.REC_NAME, T.REC_NAME2,T.SENDER_NAME,T.SENDER_NAME2,T.NATIONAL_ID,T.SENDER_ADDRESS\n"
			+ ",T.ADDRESS,T.MOBILE_NO,T.EMAIL,T.MESSAGE,PO.OFFICE_NAME ,O.OFFICE_NAME ORIGIN_OFFICE\n"
			+ "  ,T.LANG_CODE , T.ADMIN"
			+ " FROM TGH_TELEGRAPH T,TGH_POST_OFFICE O,TGH_POST_OFFICE PO \n"
			+ " WHERE T.ORIGIN_OFFICE = O.OFFICE_CODE\n"
			+ " AND T.OFFICE_CODE = PO.OFFICE_CODE (+)\n"
			+ " AND T.TGH_ID IN (:TGH_ID)",nativeQuery=true)
	List<PrintResponse> printTGH(List<String> TGH_ID);
	 
	@Query(value = "SELECT ORA_ROWSCN,GEN_ID , TGH_DATE,STATUS_CODE,NOTES, USER_CODE ,RECEIPT_NO,SEQ_NO , TGH_ID , OFFICE_CODE , TGH_CODE , CALLER_NAME \n"
			+ "  ,SEND_DATE,TEMPLATE,DECORATION,URGENT,CALLER_TEL_NO,ADMIN ,DELIVERY_NOTICE, INTERNATIONAL , TGH_COST , REC_NAME\n"
			+ "  ,REC_NAME2 , ADDRESS, ACTUAL_REC_NAME , ACTUAL_REC_DATE ,REDIRECT_NOTES , MESSAGE , POSTAL_OFFICE ,SENDER_NAME \n"
			+ "  ,SENDER_NAME2 , SENDER_ADDRESS, SENDER_RESP,DATE_LOCK ,REC_VIP ,PAID_AMOUNT  \n"
			+ "  FROM TGH_TELEGRAPH WHERE TGH_ID=:TGH_ID " ,nativeQuery=true)
	DetailsResponse getTGHDetails(Integer TGH_ID);
	@Query(value = "SELECT NATIONAL_ID , MOBILE_NO From TGH_TELEGRAPH WHERE TGH_ID=:TGH_ID ",nativeQuery=true)
	CustomerDetails_Res getCustomerDetails(Integer TGH_ID);
//	@Query(value = "SELECT SCN_TO_TIMESTAMP(\n" +
//			"    (SELECT ORA_ROWSCN \n" +
//			"     FROM TGH_TELEGRAPH\n" +
//			"     WHERE tgh_id = :id)\n" +
//			") AS timestamp_value\n" +
//			"FROM dual",nativeQuery = true)
	@Query(value = "SELECT ORA_ROWSCN FROM TGH_TELEGRAPH WHERE tgh_id = :id",nativeQuery = true)
	Long startUpdating(String id);

//	@Query(value = "SELECT ora_rowscn  from TGH_TELEGRAPH t where t.tgh_id=:id",nativeQuery = true)
//	List<Long> findIdForLastModify(long tghId);
	// For row-level locking when retrieving the entity by TGH_ID (PK).
	// Pessimistic Locking While the lock is held, no other person may obtain the lock (and thus must wait). 
	// Once that person is done updating the record, the lock is released and the next person is eligible to obtain the lock.
	
	// Optimistic Locking records are freely given out to whoever wants them. 
	// Every record has a version field that can be represented with a unique number, timestamp, or some sort of a hash.
	// Before the save operation, however, we need to check if the version we originally got matches what’s currently in the database. If they don’t match, we know that during the time we’ve had the record, someone else has already requested and saved that same record before we could save,
	// and therefore we must take action to ensure that our update will be consistent
	@Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<TGH_TELEGRAPH> findById(Integer TGH_ID);
    
//    Example flow:
//    	1. User A gets version 1
//    	2. — — — — — — — — — — User B also gets version 1
//    	3. — — — — — — — — — — User B saves, the version is now incremented to 2
//    	4. User A attempts to save. ← Lock Error.
//
//    	So in step 4, User A still has a copy of version 1 
//    and the save fails because the current version is now 2. User A would have to get a fresh snapshot of the version 2 data 
//    and retry the save.
	
    
    @Query(value = "Select COUNT(*) from TGH_telegraph where user_code =:userCode",
			nativeQuery = true)
	Integer countTGH(  String userCode);
	
	@Query(value = "Select sum(TGH_COST) from TGH_telegraph where user_code =:userCode",
			nativeQuery = true)
	String sumCostTGH(  String userCode);
	
	
	@Query(value = "Select TGH_Code from (SELECT TGH_Code ,user_code  "
			+ "FROM TGH_telegraph ORDER BY TGH_DATE )"
			+ "where  user_code =:userCode and rownum = 1",
			nativeQuery = true)
	String getFirstTghCode(String userCode);
	
	
	@Query(value ="Select TGH_Code from (SELECT TGH_Code ,user_code  "
			+ "FROM TGH_telegraph ORDER BY TGH_DATE DESC )"
			+ "where  user_code =:userCode and rownum = 1", 
			nativeQuery = true)
	String getLastTghCode(String userCode);
	
	
	
	@Query(value = "SELECT STATUS_NAME as STATUS_NAME, COUNT(*) as COUNT FROM TGH_TELEGRAPH,TGH_MSG_STATUS \r\n"
			+ "WHERE TGH_TELEGRAPH.STATUS_CODE = TGH_MSG_STATUS.STATUS_CODE  and TGH_TELEGRAPH.USER_CODE=:userCode \r\n"
			+ "and TGH_TELEGRAPH.TGH_DATE BETWEEN to_date(:dateFrom,'dd/mm/yyyy') AND to_date(:dateTo,'dd/mm/yyyy') +1 \r\n"
			+ "GROUP BY STATUS_NAME,USER_CODE \r\n"
			+ "ORDER BY  STATUS_NAME",
			nativeQuery = true)
	List<DailyStatisticsInterface> userDailyStatistics(String userCode,String dateFrom , String dateTo );
	
	
//	@Query(value = "SELECT TRIM(TO_CHAR(TGH_DATE,'Month')) MONTH,\r\n"
//			+ "TRIM(TO_CHAR(TGH_DATE,'YYYY')) YEAR,\r\n"
//			+ "USER_CODE, TGH_MSG_STATUS.STATUS_NAME, \r\n"
//			+ "Count(tgh_id)TghCount ,SUM(TGH_COST)/100 TGH_COST ,min(tgh_code) First_Tgh,max(tgh_code) Last_Tgh \r\n"
//			+ "FROM TGH_TELEGRAPH,TGH_MSG_STATUS\r\n"
//			+ "WHERE TGH_TELEGRAPH.STATUS_CODE=TGH_MSG_STATUS.STATUS_CODE and  USER_CODE=:userCode\r\n"
//			+ "and (to_char(TGH_DATE, 'MM/YYYY')=:userDate )\r\n"
//			+ "GROUP BY TO_CHAR(TGH_DATE,'Month'), TO_CHAR(TGH_DATE,'YYYY'), USER_CODE,STATUS_NAME",
//			nativeQuery = true)
//	List<UserMonthlyStatisticsInterface> userMonthlyStatistics(String userCode,String userDate );
	
	
	
	@Query(value = "SELECT TRIM(TO_CHAR(TGH_DATE,'Month')) MONTH,\n"
			+ "	TRIM(TO_CHAR(TGH_DATE,'YYYY')) YEAR,\n"
			+ "	USER_CODE, TGH_MSG_STATUS.STATUS_NAME, \n"
			+ "	Count(tgh_id)TghCount  \n"
			+ "	FROM TGH_TELEGRAPH,TGH_MSG_STATUS\n"
			+ "	WHERE TGH_TELEGRAPH.STATUS_CODE=TGH_MSG_STATUS.STATUS_CODE and "
			+ " USER_CODE=:userCode\n"
			+ "	and (to_char(TGH_DATE, 'MM/YYYY')=:userDate )\n"
			+ "	GROUP BY TO_CHAR(TGH_DATE,'Month'), TO_CHAR(TGH_DATE,'YYYY'), USER_CODE,STATUS_NAME ",
			nativeQuery = true)
	List<UserMonthlyStatisticsInterface> ListuserMonthlyStatistics(String userCode,String userDate );
	
	
	
	@Query(value = "Select TGH_Code from (SELECT TGH_Code ,user_code  \n"
			+ "	FROM TGH_telegraph \n"
			+ "   where(to_char(TGH_DATE, 'MM/YYYY')=:userDate)\n"
			+ "   ORDER BY TGH_DATE )\n"
			+ "  where  user_code =:userCode and rownum = 1",
			nativeQuery = true)
	String getFirstTghCodeMonthly(String userDate,String userCode);
	
	
	@Query(value ="Select TGH_Code from (SELECT TGH_Code ,user_code \n"
			+ "	FROM TGH_telegraph \n"
			+ "  where(to_char(TGH_DATE, 'MM/YYYY')=:userDate)\n"
			+ "  ORDER BY TGH_DATE DESC)\n"
			+ "  where  user_code =:userCode and rownum = 1", 
			nativeQuery = true)
	String getLastTghCodeMonthly(String userDate,String userCode);
	
	
	
	@Query(value ="SELECT sum (TGH_COST)/100 TGH_COST FROM TGH_telegraph \n"
			+ "	where USER_CODE=:userCode and (to_char(TGH_DATE, 'MM/YYYY')=:userDate )", 
			nativeQuery = true)
	String getTGHCost(String userDate,String userCode);
	
	@Query(value ="SELECT TRIM(TO_CHAR(TGH_DATE,'Month')) \n"
			+ " FROM TGH_TELEGRAPH \n"
			+ "	where (to_char(TGH_DATE, 'MM/YYYY')=:userDate) and  USER_CODE=:userCode and ROWNUM =1 ", 
			nativeQuery = true)
	String getMonth(String userDate,String userCode);
	
	
	@Query(value ="SELECT TRIM(TO_CHAR(TGH_DATE,'YYYY')) FROM TGH_TELEGRAPH \n"
			+ "	where (to_char(TGH_DATE, 'MM/YYYY')=:userDate) and  USER_CODE=:userCode and ROWNUM =1 ", 
			nativeQuery = true)
	String getYear(String userDate,String userCode);
	
	
	
	@Query(value = "SELECT TRIM(TO_CHAR(TGH_DATE,'Month')) MONTH,\n"
			+ "	TRIM(TO_CHAR(TGH_DATE,'YYYY')) YEAR,\n"
			+ "	USER_CODE ,min(tgh_code) First_Tgh,max(tgh_code) Last_Tgh ,SUM(TGH_COST)/100 TGHCOST \n"
			+ "	FROM TGH_TELEGRAPH where  USER_CODE=:userCode\n"
			+ "	and (to_char(TGH_DATE, 'MM/YYYY')=:userDate )\n"
			+ "	GROUP BY TO_CHAR(TGH_DATE,'Month'), TO_CHAR(TGH_DATE,'YYYY'), USER_CODE ",
			nativeQuery = true)
	UserMonthlyStatisticsResponse UserMonthlyStatistics(String userCode,String userDate );
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO TGH_TELEGRAPH(TGH_DATE\n"
			+ ",USER_CODE\n"
			+ ",CALLER_NAME\n"
			+ ",CALLER_TEL_NO\n"
			+ ",BILL_TEL_NO\n"
			+ ",PLAN_CODE\n"
			+ ",TEMP_CODE\n"
			+ ",LANG_CODE\n"
			+ ",DELIVERY_NOTICE\n"
			+ ",DECORATION\n"
			+ ",NOTES\n"
			+ ",URGENT\n"
			+ ",ADMIN\n"
			+ ",INTERNATIONAL\n"
			+ ",TEMPLATE\n"
			+ ",SEND_DATE\n"
			+ ",COUNTRY_CODE\n"
			+ ",TEMP_TYPE_CODE\n"
			+ ",GEN_ID\n"
			+ ",SENDER_NAME\n"
			+ ",REC_NAME\n"
			+ ",POSTAL_OFFICE\n"
			+ ",ADDRESS\n"
			+ ",REC_VIP\n"
			+ ",VIP_NO\n"
			+ ",OFFICE_CODE\n"
			+ ",CALL_DATE\n"
			+ ",SENDER_RESP\n"
			+ ",SENDER_NAME2\n"
			+ ",REC_NAME2\n"
			+ ",SENDER_ADDRESS\n"
			+ ",EMAIL\n"
			+ ",MOBILE_NO\n"
			+ ",NATIONAL_ID\n"
			+ ",SMS\n"
			+ ",MESSAGE,CITY_CODE)\n"
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
, nativeQuery = true)
	void drafts(Integer GEN_ID);
	
	
	@Query(value ="SELECT t.USER_CODE ,t.TGH_ID, t.TGH_CODE,t.SEND_DATE,t.TGH_COST, p.OFFICE_NAME\n"
			+ "  from TGH_telegraph t , TGH_POST_OFFICE p\n"
			+ "	where t.TGH_DATE BETWEEN TO_DATE(:f ,'dd/MM/yyyy')\n"
			+ " AND TO_DATE(:t,'dd/MM/yyyy')+1 \n"
			+ "  AND t.OFFICE_CODE = p.OFFICE_CODE\n"
			+ "	 AND USER_CODE =:u ", 
			nativeQuery = true)
	List<DailyRepeortResponse> getDailyReport(String f,String t,String u);

	
	
	@Query(value ="SELECT sum(TGH_COST)total from TGH_telegraph\n"
			+ "  where TGH_DATE BETWEEN TO_DATE(:f ,'dd/MM/yyyy') AND TO_DATE(:t,'dd/MM/yyyy')+1 \n"
			+ "  AND USER_CODE =:u GROUP BY USER_CODE ", 
			nativeQuery = true)
	Integer getDailyReportCost(String f,String t,String u);

	@Modifying
	@Transactional
	@Query(value = "UPDATE TGH_TELEGRAPH SET date_lock = SYSDATE ,LOCK_DATA = :lockData WHERE tgh_id = :tghId",nativeQuery = true)
	int lockRow(Integer tghId,Integer lockData);

	@Query(value = "UPDATE TGH_TELEGRAPH tel\n" +
			"SET tel.ORIGIN_OFFICE = :originOffice\n" +
			"WHERE tel.gen_id = :genId \n",nativeQuery = true)
	@Modifying
	@Transactional
	int updateOffice(String originOffice,Integer genId);
}
