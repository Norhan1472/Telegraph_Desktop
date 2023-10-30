package tgh.desktop.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import tgh.desktop.models.TGH_TELEGRAPH;
import tgh.desktop.payload.request.OutComingResquest;
import tgh.desktop.payload.request.QueueSearchRequest;
import tgh.desktop.payload.response.OutComingResponse;

@Service
public class OutComingService {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<OutComingResponse> search(String user,OutComingResquest q,String currentOffice) {

		StringBuilder queryBuilder = new StringBuilder("SELECT tgh.TGH_DATE ,  tgh.TGH_CODE,tgh.NO_OF_WORDS , \n" +
                "tgh.SEQ_NO,tgh.TGH_ID,tgh.SENDER_NAME,tgh.REC_NAME,tgh.ADDRESS , m.STATUS_NAME \n" +
                ",tgh.TGH_COST , tgh.SEND_DATE,p.OFFICE_NAME,tgh.OFFICE_SEQ,tgh.CALLER_TEL_NO \n" +
                " FROM TGH_TELEGRAPH tgh, TGH_POST_OFFICE p , TGH_MSG_STATUS m \n" +
                "where tgh.ORIGIN_OFFICE IN (\n" +
                "SELECT BU_CODE from USERS where USER_NAME =? )\n" +
                "     and  tgh.status_code IN (\n" +
                " SELECT s.VALUE status_code\n" +
                "  from MTS_SECURITY.SC_USERS u, MTS_SECURITY.SC_USERROLE  r,\n" +
                " MTS_SECURITY.SC_ROLESCOPES s\n" +
                " where u.USER_ID=r.USER_ID and r.ROLE_ID=s.ROLE_ID and s.SCOPE_ID=1 \n" +
                " and u.USER_NAME=? )\n" +
                " and tgh.STATUS_CODE = m.STATUS_CODE \n" +
                "                     and tgh.ORIGIN_OFFICE = ?\n" +
                "and p.OFFICE_CODE =  ?\n" +
                " and p.OUTGOING=1");

        List<Object> queryParams = new ArrayList<>();

        queryParams.add(user);
        queryParams.add(user);
        queryParams.add(currentOffice);
        queryParams.add(currentOffice);
        
     
        
        if (!(q.getTghCode() == null || q.getTghCode().equals(""))) {
            queryBuilder.append(" AND tgh.TGH_CODE= ?"); 
            queryParams.add(q.getTghCode());
        }
        if (!(q.getCountry() == null || q.getCountry().equals(""))) {
            queryBuilder.append(" AND tgh.COUNTRY_CODE= ?"); 
            queryParams.add(q.getCountry());
        }
        if (!(q.getTghDate() == null || q.getTghDate().equals(""))) {
        	queryBuilder.append(" AND (to_char(SEND_DATE, 'DD/MM/YYYY')=?)");
            queryParams.add(q.getTghDate());
        }
        if (!(q.getCity() == null || q.getCity().equals(""))) {
            queryBuilder.append(" AND tgh.CITY_CODE= ?");
            queryParams.add(q.getCity());
        }
        if (!(q.getTghId()==null || q.getTghId()==0 ||q.getTghId().equals(""))) {
            queryBuilder.append(" AND tgh.TGH_ID= ?");
            queryParams.add(q.getTghId());
        }
        if (!(q.getDeliveryNotice()==null || q.getDeliveryNotice().equals(""))) {
            queryBuilder.append(" AND tgh.DELIVERY_NOTICE= ?");
            queryParams.add(q.getDeliveryNotice());
        }
        if (!(q.getAdmin()==null || q.getAdmin().equals(""))) {
            queryBuilder.append(" AND tgh.ADMIN= ?");
            queryParams.add(q.getAdmin());
        }

        if (!(q.getOfficeCode() == null || q.getOfficeCode().equals(""))) {
            queryBuilder.append(" AND tgh.OFFICE_CODE = ?");
            queryParams.add(q.getOfficeCode());
        }
        if (!(q.getUserCode() == null || q.getUserCode().equals(""))) {
            queryBuilder.append(" AND tgh.USER_CODE= ?");
            queryParams.add(q.getUserCode());
        }
                
        queryBuilder.append(" Order by tgh.tgh_DATE DESC");
        System.out.println("queryBuilder="+queryBuilder);
        
        String query = queryBuilder.toString();
        System.out.println("query="+query);
        
        Object[] params = queryParams.toArray();

        // Execute the query
        @SuppressWarnings("deprecation")
		List<OutComingResponse> results = jdbcTemplate.query(query, params, (rs, rowNum) -> {
            // Map the result rows to your Result class
			OutComingResponse result = new OutComingResponse(); 

            result.setIncomingSeq(rs.getInt("SEQ_NO"));
            
            result.setSeqNo(rs.getInt("TGH_ID"));
            
            result.setTghDate(rs.getDate("TGH_DATE"));
            
            result.setSenderName(rs.getString("SENDER_NAME"));
                            	
            result.setTghCode(rs.getString("TGH_CODE"));
            
            result.setRecName(rs.getString("REC_NAME"));
        
            result.setAddress(rs.getString("ADDRESS"));
            
            result.setStatus(rs.getString("STATUS_NAME"));
             
            result.setCost(rs.getInt("PAID_AMOUNT"));
            
            result.setSendDate(rs.getDate("SEND_DATE"));
            result.setNoOfWords(rs.getString("NO_OF_WORDS"));
            result.setOfficeName(rs.getString("OFFICE_NAME"));
            result.setOutcomingSeq(rs.getString("OFFICE_SEQ"));
            
            result.setTelNo(rs.getString("CALLER_TEL_NO"));
                        
            System.out.println("result="+result);
            return result;
        });
		
//		List<Queue_Entity> tghQueue = queueRepo.findByGenId(queue.getGenId());
        System.out.println("results="+results);
		return results;
	
	}

	
}
