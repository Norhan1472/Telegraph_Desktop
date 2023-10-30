package tgh.desktop.services;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tgh.desktop.models.POST_OFFICE;
import tgh.desktop.models.TGH_MSG_STATUS;
import tgh.desktop.models.TGH_TELEGHRAPH_ARCHIVE;
import tgh.desktop.models.TGH_TELEGRAPH;
import tgh.desktop.models.TGH_TELEGRAPH_HISTORY;
import tgh.desktop.payload.request.ArchiveSearchRequest;
import tgh.desktop.payload.request.QueueSearchRequest;
import tgh.desktop.payload.response.PrintResponse;
import tgh.desktop.repos.POST_OFFICE_Repo;
import tgh.desktop.repos.Queue_Repo;
import tgh.desktop.repos.TGH_MSG_STATUS_Repo;
import tgh.desktop.repos.TGH_TELEGRAPH_HISTORY_REPO;
import tgh.desktop.repos.TghArchiveRepo;

@Service
public class ArchiveService {

	@Autowired
	TghArchiveRepo archiveRepo;
	@Autowired
	TGH_MSG_STATUS_Repo tgh_MSG_STATUS_Repo;
	@Autowired
	POST_OFFICE_Repo post_OFFICE_Repo;
	@Autowired
	TGH_TELEGRAPH_HISTORY_REPO historyRepo;
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	
	public List<TGH_MSG_STATUS> getMsgStatus(){
		return tgh_MSG_STATUS_Repo.getMsgStatusArchive();
	}
	
	public List<POST_OFFICE> getOffices(){
		return post_OFFICE_Repo.getOffices();
	}
	
	public List<String> getOriginOffices(){
		return post_OFFICE_Repo.getOriginOffices();
	}
	
	public List<TGH_TELEGRAPH_HISTORY> getTghHistory(Integer TGH_ID){
		System.out.println("historyRepo.getTghHistory(TGH_ID)="+historyRepo.getTghHistory(TGH_ID));
		return historyRepo.getTghHistory(TGH_ID);
	}
	

	
	public List<PrintResponse> printTGH(List<String> TGH_ID){
		return archiveRepo.printTGH(TGH_ID);
	}
	
	
	public List<TGH_TELEGHRAPH_ARCHIVE> getTGHDetails(Integer TGH_ID){
		return archiveRepo.getTGHDetails(TGH_ID); 
	}
	
//	public List<TGH_TELEGHRAPH_ARCHIVE> SearchTGH(String CurrentUser,ArchiveSearchRequest q) {
//		
//		return archiveRepo.Search(CurrentUser,q.getSeqNo(), q.getTGHCode(), q.getTGHDateFrom(), q.getTGHDateTo(), q.getCallerTelNo(),
//				q.getBillTelNo(),q.getInternational(),q.getGenId(),q.getSenderName(),q.getRecName(),q.getAddress(),
//				q.getStatusCode(),q.getOfficeCode(),q.getOriginOffice(),q.getOutgoingSeq(),q.getTGH_DATE(), q.getARCHIVE_DATE(),
//				q.getInternationalDirection(),q.getIncomingSeq());
//		
//	}
	
	
	public List<TGH_TELEGHRAPH_ARCHIVE> search(String CurrentUser,ArchiveSearchRequest q) {

		StringBuilder queryBuilder = new StringBuilder("SELECT tgh.TGH_ID,tgh.USER_CODE ,tgh.TGH_CODE ,tgh.CALLER_NAME,tgh.ARCHIVED_BY,tgh.TGH_DATE ,tgh.ARCHIVE_DATE,tgh.BILL_TEL_NO,tgh.OFFICE_SEQ \n"
				+ " , tgh.INTERNATIONAL ,tgh.SEQ_NO, tgh.GEN_ID ,tgh.SENDER_NAME , tgh.REC_NAME, tgh.ADDRESS ,m.STATUS_NAME ,p.OFFICE_NAME \n"
				+ " ,po.OFFICE_NAME ORIGIN_OFFICE , tgh.ACCOUNT_TYPE , tgh.PLAN_CODE , tgh.CALLER_TEL_NO , tgh.TEMP_CODE , tgh.LANG_CODE ,\n"
				+ " tgh.DELIVERY_NOTICE ,tgh.DECORATION , tgh.URGENT , tgh.NOTES , tgh.ADMIN , tgh.TEMPLATE , tgh.SEND_DATE ,tgh.COUNTRY_CODE \n"
				+ " ,tgh.TEMP_TYPE_CODE ,tgh.POSTAL_OFFICE , tgh.VIP_NO , tgh.TGH_COST , tgh.ACTUAL_REC_NAME,tgh.ACTUAL_REC_DATE,tgh.MESSAGE \n"
				+ " ,tgh.CALL_DATE,tgh.REDIRECTED,tgh.REDIRECT_NOTES,tgh.GENERATED_BY,tgh.SENDER_RESP,tgh.SENDER_NAME2,tgh.REC_NAME2,\n"
				+ " tgh.SENDER_ADDRESS,tgh.NO_OF_WORDS,tgh.BILL_FLAG,EMAIL,tgh.VIP_NO,tgh.REC_VIP \n "
				+ " FROM TGH_TELEGHRAPH_ARCHIVE tgh, tgh_post_office po ,tgh_post_office p, TGH_MSG_STATUS m \n"
				+ "	WHERE tgh.OFFICE_CODE = p.OFFICE_CODE  (+)\n"
				+ "	and  tgh.origin_office =  po.OFFICE_CODE (+)\n"
				+ " and tgh.STATUS_CODE = m.STATUS_CODE (+) ");
		
//		TGH_TELEGRAPH q = new TGH_TELEGRAPH();
		System.out.println("TGH_ID="+q.getSeqNo());

        List<Object> queryParams = new ArrayList<>();

        if (!(q.getIncomingSeq() == null || q.getIncomingSeq().equals(""))) {
            queryBuilder.append(" AND tgh.SEQ_NO= ?");
            queryParams.add(q.getIncomingSeq());
        }
        
        if (!(q.getSeqNo() == null || q.getSeqNo().equals(""))) {
            queryBuilder.append(" AND tgh.TGH_ID = ?");
            queryParams.add(q.getSeqNo());
        }
        if (!(q.getCallerTelNo() == null || q.getCallerTelNo().equals(""))) {
            queryBuilder.append(" AND tgh.CALLER_TEL_NO = ?");
            queryParams.add(q.getCallerTelNo());
        }
        if (!(q.getTGHCode() == null || q.getTGHCode().equals(""))) {
            queryBuilder.append(" AND tgh.TGH_CODE= ?");
            queryParams.add(q.getTGHCode());
        }
        

        if (!(q.getTGH_DATE() == null || q.getTGH_DATE().equals(""))) {
            queryBuilder.append(" AND tgh.TGH_DATE= ?");
            queryParams.add(q.getTGH_DATE());
        }
        
        
        
    	
        if (!(q.getBillTelNo() == null || q.getBillTelNo().equals(""))) {
            queryBuilder.append(" AND tgh.BILL_TEL_NO= ?");
            queryParams.add(q.getBillTelNo());
        }
    	
        if (!(q.getInternational() == null || q.getInternational().equals(""))) {
            queryBuilder.append(" AND tgh.INTERNATIONAL= ?");
            queryParams.add(q.getInternational());
        }
        
        if (!(q.getGenId() == null || q.getGenId().equals(""))) {
            queryBuilder.append(" AND tgh.GEN_ID= ?");
            queryParams.add(q.getGenId());
        }
        
        
        if (!(q.getSenderName() == null || q.getSenderName().equals(""))) {
            queryBuilder.append(" AND tgh.SENDER_NAME= ?");
            queryParams.add(q.getSenderName());
        }
        
        if (!(q.getRecName() == null || q.getRecName().equals(""))) {
            queryBuilder.append(" AND tgh.REC_NAME= ?");
            queryParams.add(q.getRecName());
        }
        
        if (!(q.getAddress() == null || q.getAddress().equals(""))) {
            queryBuilder.append(" AND tgh.ADDRESS= ?");
            queryParams.add(q.getAddress());
        }
        
        
        if (!(q.getStatusCode() == null || q.getStatusCode().equals(""))) {
            queryBuilder.append(" AND tgh.STATUS_CODE= ?");
            queryParams.add(q.getStatusCode());
        }
        
        if (!(q.getOfficeCode() == null || q.getOfficeCode().equals(""))) {
            queryBuilder.append(" AND tgh.OFFICE_CODE= ?");
            queryParams.add(q.getOfficeCode());
        }
        
        if (!(q.getOriginOffice() == null || q.getOriginOffice().equals(""))) {
            queryBuilder.append(" AND  po.OFFICE_NAME =?");
            queryParams.add(q.getOriginOffice());
        }

        
        if (!(q.getOutgoingSeq() == null || q.getOutgoingSeq().equals(""))) {
            queryBuilder.append(" AND tgh.OFFICE_SEQ= ?");
            queryParams.add(q.getOutgoingSeq());
        }
        
        if (!(q.getARCHIVE_DATE() == null || q.getARCHIVE_DATE().equals(""))) {
            queryBuilder.append(" AND tgh.ARCHIVE_DATE= ?");
            queryParams.add(q.getOutgoingSeq());
        }
        
        if (!(q.getOutgoingSeq() == null || q.getOutgoingSeq().equals(""))) {
            queryBuilder.append(" AND tgh.OFFICE_SEQ= ?");
            queryParams.add(q.getOutgoingSeq());
        }
        
//        if (!(q.getInternationalDirection() == null || q.getInternationalDirection().equals(""))) {
//            queryBuilder.append(" AND  DECODE(po.country_code ,'EG','OUT','IN') =:internationalDirection) ");
//            queryParams.add(q.getInternationalDirection());
//        }
        
       
         
        queryBuilder.append(" Order by tgh.tgh_DATE DESC");
        
        System.out.println("queryBuilder="+queryBuilder);
        
        String query = queryBuilder.toString();
        System.out.println("query="+query);
        
        Object[] params = queryParams.toArray();

        // Execute the query
        @SuppressWarnings("deprecation")
		List<TGH_TELEGHRAPH_ARCHIVE> results = jdbcTemplate.query(query, params, (rs, rowNum) -> {
            // Map the result rows to your Result class
			TGH_TELEGHRAPH_ARCHIVE result = new TGH_TELEGHRAPH_ARCHIVE();
        	
            result.setTGH_ID(rs.getInt("TGH_ID"));
            result.setTGH_CODE(rs.getString("TGH_Code"));
            
            result.setCALLER_NAME(rs.getString("CALLER_NAME"));
            result.setARCHIVED_BY(rs.getString("ARCHIVED_BY"));
            result.setTGH_DATE(rs.getDate("TGH_DATE"));
            
            result.setARCHIVE_DATE(rs.getDate("ARCHIVE_DATE"));
        
            result.setBILL_TEL_NO(rs.getString("BILL_TEL_NO"));
            
            result.setOFFICE_SEQ(rs.getString("OFFICE_SEQ"));
            result.setINTERNATIONAL(rs.getInt("INTERNATIONAL"));
          
            result.setSEQ_NO(rs.getInt("SEQ_NO"));
            
            result.setGEN_ID(rs.getInt("GEN_ID"));
            
            result.setSENDER_NAME(rs.getString("SENDER_NAME"));
            
            result.setREC_NAME(rs.getString("REC_NAME"));
            
            result.setADDRESS(rs.getString("ADDRESS"));
            
            result.setSTATUS_CODE(rs.getString("STATUS_NAME"));
            result.setOFFICE_CODE(rs.getString("OFFICE_NAME"));
            
            result.setORIGIN_OFFICE(rs.getString("ORIGIN_OFFICE"));
            
            result.setACCOUNT_TYPE(rs.getString("ACCOUNT_TYPE"));
            result.setUSER_CODE(rs.getString("USER_CODE"));

            result.setPLAN_CODE(rs.getString("PLAN_CODE"));
            result.setCALLER_TEL_NO(rs.getString("CALLER_TEL_NO"));

            result.setTEMP_CODE(rs.getString("TEMP_CODE"));

            result.setLANG_CODE(rs.getString("LANG_CODE"));

            result.setDELIVERY_NOTICE(rs.getInt("DELIVERY_NOTICE"));

            result.setDECORATION(rs.getInt("DECORATION"));
            result.setURGENT(rs.getInt("URGENT"));
            
            result.setNOTES(rs.getString("NOTES"));
            
            result.setADMIN(rs.getInt("ADMIN"));
            
            result.setTEMPLATE(rs.getInt("TEMPLATE"));
            
            result.setSEND_DATE(rs.getDate("SEND_DATE"));
            
            result.setCOUNTRY_CODE(rs.getString("COUNTRY_CODE"));
            
            result.setTEMP_TYPE_CODE(rs.getString("TEMP_TYPE_CODE"));
            
            result.setPOSTAL_OFFICE(rs.getString("POSTAL_OFFICE"));
            
            result.setREC_VIP(rs.getInt("REC_VIP"));
            
            result.setVIP_NO(rs.getInt("VIP_NO"));
            result.setTGH_COST(rs.getInt("TGH_COST"));

            result.setACTUAL_REC_NAME(rs.getString("ACTUAL_REC_NAME"));
            
            result.setACTUAL_REC_DATE(rs.getDate("ACTUAL_REC_DATE"));
            result.setMESSAGE(rs.getString("MESSAGE"));
            
            result.setCALL_DATE(rs.getDate("CALL_DATE"));
            result.setREDIRECTED(rs.getInt("REDIRECTED"));
            
            result.setREDIRECT_NOTES(rs.getString("REDIRECT_NOTES"));
            
            result.setGENERATED_BY(rs.getString("GENERATED_BY"));
            
            
            result.setSENDER_RESP(rs.getInt("SENDER_RESP"));
           
            
            result.setSENDER_NAME2(rs.getString("SENDER_NAME2"));
            result.setREC_NAME2(rs.getString("REC_NAME2"));
            result.setSENDER_ADDRESS(rs.getString("SENDER_ADDRESS"));
            result.setNO_OF_WORDS(rs.getInt("NO_OF_WORDS"));
            
            result.setBILL_FLAG(rs.getInt("BILL_FLAG"));
          
            
            result.setEMAIL(rs.getString("EMAIL"));
            
          
            if(result.getCOUNTRY_CODE().equals("EG")) 
            	result.setInternationalDirection("OUT");
            	
            	else
            		result.setInternationalDirection("IN");
            
            
            System.out.println("result="+result);
            return result;
        });
		
//		List<Queue_Entity> tghQueue = queueRepo.findByGenId(queue.getGenId());
        System.out.println("results="+results);
		return results;
	
	}



}
