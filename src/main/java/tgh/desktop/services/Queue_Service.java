package tgh.desktop.services;

import java.io.IOException;

import java.io.Reader;
import java.io.Writer;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tgh.desktop.models.POST_OFFICE;
import tgh.desktop.models.TGH_Country;
import tgh.desktop.models.TGH_TELEGRAPH;
import tgh.desktop.models.TGH_MSG_STATUS;
import tgh.desktop.models.TGH_TELEGRAPH_HISTORY;
import tgh.desktop.payload.request.QueueSearchRequest;
import tgh.desktop.payload.request.updateStatusRequest;
import tgh.desktop.payload.response.*;
import tgh.desktop.repos.COUNTRY_PLAN_Repo;
import tgh.desktop.repos.POST_OFFICE_Repo;
import tgh.desktop.repos.Queue_Repo;
import tgh.desktop.repos.TEMPLATE_reposatiry;
import tgh.desktop.repos.TGH_Country_Repo;
import tgh.desktop.repos.TGH_MSG_STATUS_Repo;
import tgh.desktop.repos.TGH_TELEGRAPH_HISTORY_REPO;


@Service
public class Queue_Service {

	@Autowired
	Queue_Repo queueRepo;
	@Autowired
	TGH_MSG_STATUS_Repo tgh_MSG_STATUS_Repo;
	@Autowired
	POST_OFFICE_Repo post_OFFICE_Repo;
	@Autowired
	TGH_TELEGRAPH_HISTORY_REPO historyRepo;
	@Autowired
	TEMPLATE_reposatiry tempRepo;
	@Autowired
	TGH_Country_Repo countryRepo;
	@Autowired
	COUNTRY_PLAN_Repo planRepo;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	LockService lockService = new LockService();

	
	private final LockManager lockManager = new LockManager();
	
	
	public List<TGH_TELEGRAPH> showTgh (Integer genId) {
		List<TGH_TELEGRAPH>result = queueRepo.findByGenId(genId);
		return result;
	}

//	
//	public List<TGH_MSG_STATUS> getMsgStatus(){
//		return tgh_MSG_STATUS_Repo.getMsgStatus();
//	}
//	
//	public List<POST_OFFICE> getOffices(){
//		return post_OFFICE_Repo.getOffices();
//	}
//	
//	public List<String> getOriginOffices(){
//		return post_OFFICE_Repo.getOriginOffices();
//	}
//	
//	public List<TGH_TELEGRAPH_HISTORY> getTghHistory(Integer TGH_ID){
//		System.out.println("historyRepo.getTghHistory(TGH_ID)="+historyRepo.getTghHistory(TGH_ID));
//		return historyRepo.getTghHistory(TGH_ID);
//	}
//	
//	public Optional<TGH_TELEGRAPH> cancelTGH(Integer tgh_ID, String billTelNo, String reason){
//		queueRepo.cancelTGH(tgh_ID,billTelNo,reason);
//		return queueRepo.findById(tgh_ID);
//	}
//	
//	@Transactional 
//	public void modifyTGH(Integer tgh_ID){
////        lockManager.acquireLock(tgh_ID, 5); // Lock for 5 minutes (300,000 milliseconds)
//		Optional<TGH_TELEGRAPH> tgh_entity = queueRepo.findById(tgh_ID);
//		System.out.println("tgh_entity="+tgh_entity);
//		queueRepo.modifyTGH(tgh_ID);
////		acquireLock(tgh_ID);
//	}
//	
	public List<PrintResponse> printTGH(List<String> TGH_ID){
		return queueRepo.printTGH(TGH_ID);
	}

	public DetailsWithFlagResponse getTGHDetails(Integer TGH_ID,String currentOffice){
		//if(Objects.nonNull(response)){
//		int updatedRow = queueRepo.lockRow(TGH_ID,1);
		DetailsWithFlagResponse responseFlag = new DetailsWithFlagResponse();
		DetailsResponse response = queueRepo.getTGHDetails(TGH_ID);
		System.out.println(response.getOFFICE_CODE());
		System.out.println("currentOffice "+currentOffice);
		responseFlag.setDetailsResponse(response);
		if(!(response.getOFFICE_CODE()==null)||(response.getOFFICE_CODE()=="")){
			if((response.getOFFICE_CODE().equals(currentOffice))){

				responseFlag.setFlag("in");
			}else{
				//responseFlag.setDetailsResponse(response);
				responseFlag.setFlag("out");
			}
		}

		List<String>ids = new ArrayList<>();
//		ids.add(String.valueOf(TGH_ID));
		lockService.startUpdatingRow(TGH_ID,response.getORA_ROWSCN(),response.getDATE_LOCK());
		return responseFlag;
	}

	public CustomerDetails_Res getCustomerDetails(Integer TGH_ID){
		return queueRepo.getCustomerDetails(TGH_ID);
	}

	
//	public List<TGH_TELEGRAPH> SearchTGH(String CurrentUser,QueueSearchRequest q) {
//		
//		return queueRepo.Search(CurrentUser,q.getSeqNo(), q.getTGHCode(), q.getTGHDateFrom(), q.getTGHDateTo(), q.getCallerTelNo(),
//				q.getBillTelNo(),q.getInternational(),q.getGenId(),q.getSenderName(),q.getRecName(),q.getAddress(),
//				q.getStatusCode(),q.getOfficeCode(),q.getOrginOffice(),q.getOutgoingSeq(),q.getTGH_DATE(),q.getMessage(),
//				q.getCallDate(),q.getInternationalDirection(),q.getAccountType(),q.getIncomingSeq(),q.getNationalId(),q.getMobileNo());
//		
//	}

	
	// this annotation means the transaction will be committed automatically if no exception occurs. 
	// if an exception is thrown, the transaction will be rolled back.
//	@Transactional
//	public String editTGH(TGH_TELEGRAPH tgh_TELEGRAPH) {
//	    try {
//	        // Access the CLOB value
//	        Clob clobValue = tgh_TELEGRAPH.getMessage();
//	        // Create a temporary variable to hold the CLOB content
//	        String clobContent = null;
//	        // Check if the CLOB value is not null
//	        if (clobValue != null) {
//	            // Read the content from the CLOB
//	            try (Reader reader = clobValue.getCharacterStream()) {
//	                char[] buffer = new char[(int) clobValue.length()];
//	                reader.read(buffer);
//	                clobContent = new String(buffer);
//	                System.out.println("clobContent="+clobContent);
//	            } catch (IOException e) {
//	                e.printStackTrace();
//	                return "Error reading CLOB content";
//	            }
//	        }
//	        // Update the CLOB content if needed
//	        if (clobContent != null) {
//	            // Write the modified content back to the CLOB
//	            try (Writer writer = clobValue.setCharacterStream(1)) {
//	                writer.write(clobContent);
//	            } catch (IOException | SQLException e) {
//	                e.printStackTrace();
//	                return "Error updating CLOB content";
//	            }
//	        }
//	        // Save the modified entity
//	        queueRepo.save(tgh_TELEGRAPH);
//	        System.out.println("tgh_TELEGRAPH after save=" + tgh_TELEGRAPH);
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return "Error saving TGH_TELEGRAPH";
//	    }
//
//	    return "Telegraph updated successfully";
//	}
//		
//	@Transactional
//    public void acquireLock(Integer TGH_ID) {
//        TGH_TELEGRAPH entity = queueRepo.findById(TGH_ID)
//                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
//
////        entity.setLocked(true);
//        System.out.println("entity="+entity);
//        
////        entityManager.createNativeQuery("SELECT TGH_ID,TGH_CODE,TGH_DATE,USER_CODE,SEQ_NO,CALLER_NAME,CALLER_TEL_NO,BILL_TEL_NO,PLAN_CODE,TEMP_CODE,LANG_CODE,DELIVERY_NOTICE\r\n" + 
////        		",DECORATION,NOTES,URGENT,ADMIN,INTERNATIONAL,TEMPLATE,SEND_DATE,COUNTRY_CODE,TEMP_TYPE_CODE,GEN_ID,SENDER_NAME,REC_NAME,POSTAL_OFFICE\r\n" + 
////        		",ADDRESS,REC_VIP,VIP_NO,STATUS_CODE,OFFICE_CODE,TGH_COST,ACTUAL_REC_NAME,ACTUAL_REC_DATE,MESSAGE,CALL_DATE,ORIGIN_OFFICE,REDIRECTED\r\n" + 
////        		",REDIRECT_NOTES,GENERATED_BY,LAST_MODIFIED_BY,SENDER_RESP,RECEIPT_NO,OFFICE_SEQ,SENDER_NAME2,REC_NAME2,SENDER_ADDRESS,NO_OF_WORDS\r\n" + 
////        		",BILL_FLAG,RECORD_STATUS,UPDATE_OFF_SEQ,EMAIL,MOBILE_NO,NATIONAL_ID,ACCOUNT_TYPE,SMS FROM TGH_TELEGRAPH WHERE TGH_ID = :TGH_ID FOR UPDATE")
////                .setParameter("TGH_ID", TGH_ID)
////                .getSingleResult();
//    }
//	
//	@Transactional
//    public void releaseLock(Integer id) {
//		TGH_TELEGRAPH entity = queueRepo.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
//
////        entity.setLocked(false);
//        System.out.println("entityLocked="+entity);
//        queueRepo.save(entity);
//    }
//
////    public boolean isLocked(Integer id) {
////    	TGH_TELEGRAPH entity = queueRepo.findById(id)
////                .orElseThrow(() -> new EntityNotFoundException("Entity is locked"));
////
//////        return entity.isLocked();
////    }
//
//	public void lockRowForUpdate(Integer rowId) {
//        try {
////            lockManager.acquireLock(rowId, 5); // Lock for 5 minutes (300,000 milliseconds)
//
//            // Retrieve and update the row
////            TGH_TELEGRAPH row = queueRepo.findById(rowId).orElseThrow(() -> new RuntimeException("Row not found"));
////
////            // Perform updates to the row
////
////            queueRepo.save(row);
//        } finally {
//            lockManager.releaseLock(rowId);
//        }
//    }

	
	
//	
//	public List<TGH_TELEGRAPH> search(String CurrentUser,QueueSearchRequest q) {
//
//		StringBuilder queryBuilder = new StringBuilder("SELECT tgh.TGH_ID,tgh.tgh_CODE,tgh.tgh_DATE,tgh.USER_CODE,tgh.SEQ_NO,tgh.CALLER_NAME,tgh.CALLER_TEL_NO,tgh.BILL_TEL_NO,tgh.PLAN_CODE,tgh.TEMP_CODE,tgh.LANG_CODE,tgh.DELIVERY_NOTICE\n"
//				+ "	,tgh.DECORATION,tgh.NOTES,tgh.URGENT,tgh.ADMIN,tgh.INTERNATIONAL,tgh.TEMPLATE,tgh.SEND_DATE,tgh.COUNTRY_CODE,tgh.TEMP_TYPE_CODE,tgh.GEN_ID,tgh.SENDER_NAME,tgh.REC_NAME,tgh.POSTAL_OFFICE\n"
//				+ "	,tgh.ADDRESS,tgh.REC_VIP,tgh.VIP_NO,m.STATUS_NAME ,po.OFFICE_NAME office_code,\n"
//				+ "	tgh.tgh_COST,tgh.ACTUAL_REC_NAME,tgh.ACTUAL_REC_DATE,tgh.MESSAGE,tgh.CALL_DATE, p.OFFICE_NAME  ORIGIN_OFFICE,tgh.REDIRECTED\n"
//				+ "	,tgh.REDIRECT_NOTES,tgh.GENERATED_BY,tgh.LAST_MODIFIED_BY,tgh.SENDER_RESP,tgh.RECEIPT_NO,tgh.OFFICE_SEQ,tgh.SENDER_NAME2,tgh.REC_NAME2,tgh.SENDER_ADDRESS,tgh.NO_OF_WORDS\n"
//				+ "	,tgh.BILL_FLAG,tgh.RECORD_STATUS,tgh.UPDATE_OFF_SEQ,tgh.EMAIL,tgh.MOBILE_NO,tgh.NATIONAL_ID,tgh.ACCOUNT_TYPE,\n"
//				+ "	 tgh.SMS,tgh.CITY_CODE \n"
//				+ "	FROM tgh_TELEGRAPH tgh,tgh_post_office po ,tgh_post_office p, TGH_MSG_STATUS m"
//				+ " WHERE tgh.OFFICE_CODE = po.OFFICE_CODE(+) \n"
//				+ " and  tgh.status_code = m.status_code (+) \n"
//				+ " and  tgh.origin_office =  p.OFFICE_CODE (+) ");
//		
////		TGH_TELEGRAPH q = new TGH_TELEGRAPH();
//		System.out.println("TGH_ID="+q.getSeqNo());
//
//        List<Object> queryParams = new ArrayList<>();
//
//        if (!(q.getIncomingSeq() == null || q.getIncomingSeq().equals(""))) {
//            queryBuilder.append(" AND tgh.SEQ_NO= ?"); 
//            queryParams.add(q.getIncomingSeq());
//        }
//        if (!(q.getSeqNo() == null || q.getSeqNo().equals(""))) {
//            queryBuilder.append(" AND tgh.TGH_ID = ?");
//            queryParams.add(q.getSeqNo());
//        }
//        if (!(q.getInternational() == null || q.getInternational().equals(""))) {
//            queryBuilder.append(" AND tgh.INTERNATIONAL= ?");
//            queryParams.add(q.getInternational());
//        }
//        
//        if (!(q.getCallerTelNo() == null || q.getCallerTelNo().equals(""))) {
//            queryBuilder.append(" AND tgh.CALLER_TEL_NO = ?");
//            queryParams.add(q.getCallerTelNo());
//        }
//        if (!(q.getTGHCode() == null || q.getTGHCode().equals(""))) {
//            queryBuilder.append(" AND tgh.TGH_CODE= ?");
//            queryParams.add(q.getTGHCode());
//        }
//        
//        if (!(q.getTGHDateFrom() == null && q.getTGHDateTo() == null || 
//        		q.getTGHDateFrom().equals("") && q.getTGHDateTo().equals(""))) {
//            queryBuilder.append(" AND tgh.TGH_DATE BETWEEN TO_DATE(?,'dd/MM/yyyy') AND TO_DATE(?,'dd/MM/yyyy')+1");
//            queryParams.add(q.getTGHDateFrom());
//            queryParams.add(q.getTGHDateTo());
//        }
//        if (!(q.getTGH_DATE() == null || q.getTGH_DATE().equals(""))) {
//            queryBuilder.append(" AND tgh.TGH_DATE= ?");
//            queryParams.add(q.getTGH_DATE());
//        }
//        
//    	
//        if (!(q.getBillTelNo() == null || q.getBillTelNo().equals(""))) {
//            queryBuilder.append(" AND tgh.BILL_TEL_NO= ?");
//            queryParams.add(q.getBillTelNo());
//        }
//    	
//        
//        
//        if (!(q.getGenId() == null || q.getGenId().equals(""))) {
//            queryBuilder.append(" AND tgh.GEN_ID= ?");
//            queryParams.add(q.getGenId());
//        }
//        
//        if (!(q.getSenderName() == null || q.getSenderName().equals(""))) {
//            queryBuilder.append(" AND tgh.SENDER_NAME= ?");
//            queryParams.add(q.getSenderName());
//        }
//        
//        if (!(q.getRecName() == null || q.getRecName().equals(""))) {
//            queryBuilder.append(" AND tgh.REC_NAME= ?");
//            queryParams.add(q.getRecName());
//        }
//        
//        if (!(q.getAddress() == null || q.getAddress().equals(""))) {
//            queryBuilder.append(" AND tgh.ADDRESS= ?");
//            queryParams.add(q.getAddress());
//        }
//        
//        
//        if (!(q.getStatusCode() == null || q.getStatusCode().equals(""))) {
//            queryBuilder.append(" AND tgh.STATUS_CODE= ?");
//            queryParams.add(q.getStatusCode());
//        }
//        
//        if (!(q.getOfficeCode() == null || q.getOfficeCode().equals(""))) {
//            queryBuilder.append(" AND tgh.OFFICE_CODE= ?");
//            queryParams.add(q.getOfficeCode());
//        }
//        
//        if (!(q.getOrginOffice() == null || q.getOrginOffice().equals(""))) {
//            queryBuilder.append(" AND p.OFFICE_NAME= ?");
//            queryParams.add(q.getOrginOffice());
//        }
// 
//        
//        if (!(q.getOutgoingSeq() == null || q.getOutgoingSeq().equals(""))) {
//            queryBuilder.append(" AND tgh.OFFICE_SEQ= ?");
//            queryParams.add(q.getOutgoingSeq());
//        }
//        
//        if (!(q.getAccountType() == null || q.getAccountType().equals(""))) {
//            queryBuilder.append(" AND tgh.ACCOUNT_TYPE= ?");
//            queryParams.add(q.getAccountType());
//        }
//        
//        
//        if (!(q.getNationalId() == null || q.getNationalId().equals(""))) {
//            queryBuilder.append(" AND tgh.NATIONAL_ID= ?");
//            queryParams.add(q.getNationalId());
//        }
//        
//        if (!(q.getMobileNo() == null || q.getMobileNo().equals(""))) {
//            queryBuilder.append(" AND tgh.MOBILE_NO= ?");
//            queryParams.add(q.getMobileNo());
//        }
//    	
//        
//        if (!(q.getInternationalDirection() == null || q.getInternationalDirection().equals(""))) {
//            queryBuilder.append(" AND DECODE(po.country_code ,'EG','OUT','IN') =? ");
//            queryParams.add(q.getInternationalDirection());
//        }
//        
//        
//        queryBuilder.append(" Order by tgh.tgh_DATE DESC");
//        System.out.println("queryBuilder="+queryBuilder);
//        
//        String query = queryBuilder.toString();
//        System.out.println("query="+query);
//        
//        Object[] params = queryParams.toArray();
//
//        // Execute the query
//        @SuppressWarnings("deprecation")
//		List<TGH_TELEGRAPH> results = jdbcTemplate.query(query, params, (rs, rowNum) -> {
//            // Map the result rows to your Result class
//			TGH_TELEGRAPH result = new TGH_TELEGRAPH(); 
//        	
//            result.setSeqNo(rs.getInt("TGH_ID"));
//            result.setTGHCode(rs.getString("TGH_Code"));
//            
//            result.setTGHDate(rs.getDate("TGH_DATE"));
//            
//            result.setIncomingSeq(rs.getInt("SEQ_NO"));
//            
//                	
//            result.setMobileNo(rs.getString("MOBILE_NO"));
//        
//            result.setBillTelNo(rs.getString("BILL_TEL_NO"));
//            
//            result.setInternational(rs.getInt("INTERNATIONAL"));
//            
//            result.setGenId(rs.getInt("GEN_ID"));
//            
//            result.setSenderName(rs.getString("SENDER_NAME"));
//            
//            result.setRecName(rs.getString("REC_NAME"));
//            
//            result.setAddress(rs.getString("ADDRESS"));
//            result.setStatus(rs.getString("STATUS_NAME"));
//  
//            result.setOffice(rs.getString("OFFICE_CODE"));
//             
//            result.setOrgineOffice(rs.getString("ORIGIN_OFFICE"));
//            
//            result.setOutgoingSeq(rs.getString("OFFICE_SEQ"));
//            
//            result.setAccountType(rs.getString("ACCOUNT_TYPE"));
//////////////////////////////////////////////////////////////////////////
//            result.setUserCode(rs.getString("USER_CODE"));
//
//            result.setPlanCode(rs.getString("PLAN_CODE"));
//            result.setCallerTelNo(rs.getString("CALLER_TEL_NO"));
//
//            result.setTempCode(rs.getString("TEMP_CODE"));
//
//            result.setLangCode(rs.getString("LANG_CODE"));
//
//            result.setDeliveryNotice(rs.getInt("DELIVERY_NOTICE"));
//
//            result.setDecoration(rs.getInt("DECORATION"));
//            result.setUrgent(rs.getInt("URGENT"));
//            
//            result.setNotes(rs.getString("NOTES"));
//            
//            result.setAdmin(rs.getInt("ADMIN"));
//            
//            result.setTemplate(rs.getInt("TEMPLATE"));
//            
//            result.setSendDate(rs.getDate("SEND_DATE"));
//            
//            result.setCountryCode(rs.getString("COUNTRY_CODE"));
//            
//            result.setTempTypeCode(rs.getString("TEMP_TYPE_CODE"));
//            
//            result.setPostalOffice(rs.getString("POSTAL_OFFICE"));
//            
//            result.setRecVip(rs.getInt("REC_VIP"));
//            
//            result.setVipNo(rs.getInt("VIP_NO"));
//            result.setTghCost(rs.getInt("TGH_COST"));
//
//            result.setActualRecName(rs.getString("ACTUAL_REC_NAME"));
//            
//            result.setActualRecDate(rs.getDate("ACTUAL_REC_DATE"));
//            result.setMessage(rs.getClob("MESSAGE"));
//            
//            result.setCallDate(rs.getDate("CALL_DATE"));
//            result.setRedirected(rs.getInt("REDIRECTED"));
//            
//            result.setRedirectNotes(rs.getString("REDIRECT_NOTES"));
//            
//            result.setGeneratedBy(rs.getString("GENERATED_BY"));
//            
//            result.setLastModeifiedBy(rs.getString("LAST_MODIFIED_BY"));
//            result.setSenderResp(rs.getInt("SENDER_RESP"));
//            result.setReceiptNo(rs.getString("RECEIPT_NO"));
//            
//            result.setSenderName(rs.getString("SENDER_NAME2"));
//            result.setRecName2(rs.getString("REC_NAME2"));
//            result.setSenderAddress(rs.getString("SENDER_ADDRESS"));
//            result.setNoOfWords(rs.getInt("NO_OF_WORDS"));
//            
//            result.setBillFlag(rs.getInt("BILL_FLAG"));
//            result.setRecordStatus(rs.getString("RECORD_STATUS"));
//            
//            result.setUpdateOffSeq(rs.getInt("UPDATE_OFF_SEQ"));
//            
//            result.setEmail(rs.getString("EMAIL"));
//            
//            result.setSms(rs.getString("SMS"));
//            
//            result.setCityCode(rs.getString("CITY_CODE"));
//           
//            if(result.getCountryCode().equals("EG")) 
//            	result.setInternationalDirection("OUT");
//            	
//            	else
//            		result.setInternationalDirection("IN");
//            
//            
//            System.out.println("result="+result);
//            return result;
//        });
//		
////		List<Queue_Entity> tghQueue = queueRepo.findByGenId(queue.getGenId());
//        System.out.println("results="+results);
//		return results;
//	
//	}
//	

//	public List<TempResponse> getTemplates() {
//		 
//		return tempRepo.getTemplates();
//	}
//
//	public List<TGH_Country> getCountries(Integer international) {
//		
//		return countryRepo.getCountries(international);
//	}
	
//	
	public List<PlanResponse> getPlans(String country_code, Integer admin) {
		
		return planRepo.getPlans(country_code,admin);
		
	}

	public List<PlanResponse> getPlansByActive(String country_code, Integer admin,Integer active) {
		return planRepo.getPlansByActive(country_code,admin,active);
	}
	
	
}