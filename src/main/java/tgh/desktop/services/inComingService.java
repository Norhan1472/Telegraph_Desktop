package tgh.desktop.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import tgh.desktop.models.TGH_TELEGRAPH;
import tgh.desktop.models.WFM_EMP;
import tgh.desktop.payload.request.InComingRequest;
import tgh.desktop.payload.request.OutComingResquest;
import tgh.desktop.payload.request.QueueSearchRequest;
import tgh.desktop.payload.request.updateStatusRequest;
import tgh.desktop.payload.response.*;
import tgh.desktop.repos.Queue_Repo;
import tgh.desktop.repos.WFM_EMPLOYEES_SHIFTS_reposatiry;
import tgh.desktop.repos.WFM_EMP_REPO;

@Service
public class inComingService {

	@Autowired
	Queue_Repo queue_Repo ;
	
	@Autowired
	WFM_EMPLOYEES_SHIFTS_reposatiry empShifts_repo;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	private WFM_EMP_REPO emp_repo;
	@Autowired
	LockService lockService = new LockService();
	
	public List<InComingResponse> search(String user,InComingRequest q,String currentOffice) {

		StringBuilder queryBuilder = new StringBuilder("SELECT tgh.TGH_ID,tgh.TGH_CODE,tgh.tgh_DATE  ,tgh.SEQ_NO,tgh.SENDER_NAME,tgh.REC_NAME,tgh.ADDRESS,tgh.OFFICE_SEQ ,m.STATUS_NAME,tgh.TGH_COST,tgh.NO_OF_WORDS,\n" +
				"  tgh.SEND_DATE,p.OFFICE_NAME ORIGIN ,tgh.office_code\n" +
				" FROM tgh_TELEGRAPH tgh ,tgh_post_office p,  TGH_MSG_STATUS m \n" +
				"  WHERE tgh.OFFICE_CODE IN (\n" +
				"  SELECT s.VALUE \n" +
				"   from MTS_SECURITY.SC_USERS u, MTS_SECURITY.SC_USERROLE  r,MTS_SECURITY.SC_ROLESCOPES s \n" +
				"  where u.USER_ID=r.USER_ID and r.ROLE_ID=s.ROLE_ID and s.SCOPE_ID=2 and u.USER_NAME=?) \n" +
				" and  tgh.status_code IN (\n" +
				" SELECT s.VALUE status_code\n" +
				"   from MTS_SECURITY.SC_USERS u, MTS_SECURITY.SC_USERROLE  r, MTS_SECURITY.SC_ROLESCOPES s\n" +
				"   where u.USER_ID=r.USER_ID and r.ROLE_ID=s.ROLE_ID and s.SCOPE_ID=1  and u.USER_NAME=? )\n" +
				"                   and tgh.STATUS_CODE = m.STATUS_CODE \n" +
				"                 and tgh.office_code = ?\n" +
				"                 and p.office_code = ?\n" +
				"  AND p.INCOMING = 1 ");
		
        List<Object> queryParams = new ArrayList<>();

        queryParams.add(user);
        queryParams.add(user);
		queryParams.add(currentOffice);
		queryParams.add(currentOffice);
        
        
        if (!(q.getTghCode()==null || q.getTghCode().isEmpty())) {
            queryBuilder.append(" AND tgh.TGH_CODE= ?"); 
            queryParams.add(q.getTghCode());
        }

        if (!(q.getIncomingSeq()==null || q.getIncomingSeq()==0||q.getIncomingSeq().equals(""))) {
            queryBuilder.append(" AND tgh.SEQ_NO= ?");
            queryParams.add(q.getIncomingSeq());
        }
        if (!(q.getCountry()==null  || q.getCountry().isEmpty() ||q.getCountry()=="")) {
            queryBuilder.append(" AND tgh.COUNTRY_CODE= ?");
            queryParams.add(q.getCountry());
        }
        if (!(q.getTghDate()==null  || q.getTghDate().isEmpty()||q.getTghDate()=="")) {
        	queryBuilder.append(" AND (to_char(TGH_DATE, 'DD/MM/YYYY')=?)");
            queryParams.add(q.getTghDate());
        }
        if (!(q.getCity()==null || q.getCity().isEmpty() ||q.getCity().equals(""))) {
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

        if (!(q.getOfficeCode()==null || q.getOfficeCode().isEmpty() ||q.getOfficeCode().equals(""))) {
            queryBuilder.append(" AND tgh.ORIGIN_OFFICE = ?");
            queryParams.add(q.getOfficeCode());
        }
        if (!(q.getLang()==null || q.getLang().isEmpty()|| q.getLang().equals(""))) {
            queryBuilder.append(" AND tgh.LANG_CODE= ?");
            queryParams.add(q.getLang());
        }
        if (!(q.getStatus()==null || q.getStatus().isEmpty()|| q.getStatus().equals(""))) {
            queryBuilder.append(" AND tgh.STATUS_CODE= ?");
            queryParams.add(q.getStatus());
        }

        if (!(q.getDecoration()==null ||  q.getDecoration().equals(""))) {
            queryBuilder.append(" AND tgh.DECORATION= ?");
            queryParams.add(q.getDecoration());
        }

        if (!(q.getTemplate()==null  ||  q.getTemplate().equals(""))) {
            queryBuilder.append(" AND tgh.TEMP_CODE= ?");
			System.out.println("q.getTemplate() "+q.getTemplate());
            queryParams.add(q.getTemplate());
        }

        if (!(q.getFromDate() == null && q.getEndDate() == null ||
        		q.getFromDate().equals("") && q.getEndDate().equals(""))) {
            queryBuilder.append(" AND tgh.TGH_DATE BETWEEN TO_DATE(?,'dd/MM/yyyy') AND TO_DATE(?,'dd/MM/yyyy')+1");
            queryParams.add(q.getFromDate());
            queryParams.add(q.getEndDate());
        }

        queryBuilder.append(" Order by tgh.tgh_DATE DESC");
        System.out.println("queryBuilder="+queryBuilder);

        String query = queryBuilder.toString();
        System.out.println("query="+query);

        Object[] params = queryParams.toArray();

        // Execute the query
        @SuppressWarnings("deprecation")
		List<InComingResponse> results = jdbcTemplate.query(query, params, (rs, rowNum) -> {
            // Map the result rows to your Result class
			InComingResponse result = new InComingResponse();


            result.setTghDate(rs.getDate("tgh_DATE"));
            result.setSeqNo(rs.getString("TGH_ID"));
            result.setTghCode(rs.getString("TGH_CODE"));
            result.setIncomingSeq(rs.getString("SEQ_NO"));

            result.setSenderName(rs.getString("SENDER_NAME"));
            result.setRecName(rs.getString("REC_NAME"));

            result.setAddress(rs.getString("ADDRESS"));
			result.setNoOfWords(rs.getString("NO_OF_WORDS"));

            result.setStatus(rs.getString("STATUS_NAME"));

            result.setCost(rs.getString("PAID_AMOUNT"));

            result.setSendDate(rs.getDate("SEND_DATE"));

            result.setOriginOffice(rs.getString("ORIGIN"));
            result.setOrigin(rs.getString("OFFICE_SEQ"));


            System.out.println("result="+result);
            return result;
        });

//		List<Queue_Entity> tghQueue = queueRepo.findByGenId(queue.getGenId());
        System.out.println("results="+results);
		return results;

	}


	public String updateStatus(String user,updateStatusRequest q ) {
		// lock row
		//lockService.startUpdating(q.getTghId());

		String msg = "";
		String exception="";

		if(q.getCriteria()!=null){
			System.out.println("kkk");
			msg = updateStatusWithCases(user,q);
		}
		else{
			if(q.getTghId()!=null||q.getTghId().size()!=0){
				for (int i = 0; i < q.getTghId().size(); i++) {
//					if(lockService.checkLock(q.getTghId().get(i))){
//						exception+=q.getTghId().get(i)+" ";
//						continue;
//					}
					msg = updateStatusWithCases(user,q);

//					int updatedRow = queue_Repo.lockRow(Integer.parseInt(q.getTghId().get(0)),0);
				}
			}
		}


        System.out.println("MSG"+msg);
//		if(exception.length()!=0){
//			return msg+"But these Telegraphs not updated as need to refresh ";
//		}
		return msg;

	}
	public String updateStatusWithCases(String user,updateStatusRequest q){
		String msg = "";
		StringBuilder queryBuilder = new StringBuilder("");

		List<Object> queryParams = new ArrayList<>();

		System.out.println("STat => "+q.getOldStatus());
		System.out.println("STat => "+q.getNewStatus());

		if(q.getOldStatus().equals("4")) {
			if(q.getNewStatus().equals("5")){

				queryBuilder.append(" Update TGH_TELEGRAPH Set Status_code=5,NOTES=? "
						+ " where Status_code=4 ");
				queryParams.add(q.getNotes());
				if(q.getTghId()!=null&& !q.getTghId().isEmpty()){
					queryBuilder.append("and  tgh_id IN (?) ");
					queryParams.add(q.getTghId().get(0));
				}
				else{
					queryBuilder.append("AND tgh_id IN (\n" +
							"    SELECT tgh.TGH_ID\n" +
							"    FROM tgh_TELEGRAPH tgh, tgh_post_office p, TGH_MSG_STATUS m\n" +
							"    WHERE tgh.OFFICE_CODE IN (\n" +
							"        SELECT s.VALUE\n" +
							"        FROM MTS_SECURITY.SC_USERS u, MTS_SECURITY.SC_USERROLE r, MTS_SECURITY.SC_ROLESCOPES s\n" +
							"        WHERE u.USER_ID = r.USER_ID AND r.ROLE_ID = s.ROLE_ID AND s.SCOPE_ID = 2 AND u.USER_NAME = ? \n" +
							"    )\n" +
							"    AND tgh.status_code IN (\n" +
							"        SELECT s.VALUE AS status_code\n" +
							"        FROM MTS_SECURITY.SC_USERS u, MTS_SECURITY.SC_USERROLE r, MTS_SECURITY.SC_ROLESCOPES s\n" +
							"        WHERE u.USER_ID = r.USER_ID AND r.ROLE_ID = s.ROLE_ID AND s.SCOPE_ID = 1 AND u.USER_NAME = ? \n" +
							"    )\n" +
							"    AND tgh.STATUS_CODE = m.STATUS_CODE\n" +
							"    AND tgh.ORIGIN_OFFICE = p.OFFICE_CODE\n" +
							"    AND p.INCOMING = 1\n" );
					queryParams.add(user);
					queryParams.add(user);


					if (!(q.getCriteria().getTghCode() == null || q.getCriteria().getTghCode().equals(""))) {
						queryBuilder.append(" AND tgh.TGH_CODE= ?");
						queryParams.add(q.getCriteria().getTghCode());
					}

					if (!(q.getCriteria().getIncomingSeq() == null || q.getCriteria().getIncomingSeq().equals(""))) {
						queryBuilder.append(" AND tgh.SEQ_NO= ?");
						queryParams.add(q.getCriteria().getIncomingSeq());
					}
					if (!(q.getCriteria().getCountry() == null || q.getCriteria().getCountry().equals(""))) {
						queryBuilder.append(" AND tgh.COUNTRY_CODE= ?");
						queryParams.add(q.getCriteria().getCountry());
					}
					if (!(q.getCriteria().getTghDate() == null || q.getCriteria().getTghDate().equals(""))) {
						queryBuilder.append(" AND (to_char(TGH_DATE, 'DD/MM/YYYY')=?)");
						queryParams.add(q.getCriteria().getTghDate());
					}
					if (!(q.getCriteria().getCity() == null || q.getCriteria().getCity().equals(""))) {
						queryBuilder.append(" AND tgh.CITY_CODE= ?");
						queryParams.add(q.getCriteria().getCity());
					}

					if (!(q.getOfficeCode() == null || q.getOfficeCode().equals(""))) {
						queryBuilder.append(" AND tgh.OFFICE_CODE = ?");
						queryParams.add(q.getOfficeCode());
					}
					if (!(q.getCriteria().getLang() == null || q.getCriteria().getLang().equals(""))) {
						queryBuilder.append(" AND tgh.LANG_CODE= ?");
						queryParams.add(q.getCriteria().getLang());
					}
					if (!(q.getCriteria().getStatus() == null || q.getCriteria().getStatus().equals(""))) {
						queryBuilder.append(" AND tgh.STATUS_CODE= ?");
						queryParams.add(q.getCriteria().getStatus());
					}

					if (!(q.getCriteria().getDecoration() == null || q.getCriteria().getDecoration().equals(""))) {
						queryBuilder.append(" AND tgh.DECORATION= ?");
						queryParams.add(q.getCriteria().getDecoration());
					}

					if (!(q.getCriteria().getTemplate() == null || q.getCriteria().getTemplate().equals(""))) {
						queryBuilder.append(" AND tgh.TEMPLATE= ?");
						queryParams.add(q.getCriteria().getTemplate());
					}



					if (!(q.getCriteria().getFromDate() == null && q.getCriteria().getEndDate() == null ||
							q.getCriteria().getFromDate().equals("") && q.getCriteria().getEndDate().equals(""))) {
						queryBuilder.append(" AND tgh.TGH_DATE BETWEEN TO_DATE(?,'dd/MM/yyyy') AND TO_DATE(?,'dd/MM/yyyy')+1");
						queryParams.add(q.getCriteria().getFromDate());
						queryParams.add(q.getCriteria().getEndDate());
					}
					queryBuilder.append(")");
				}



				String query = queryBuilder.toString();
				Object[] params = queryParams.toArray();

				jdbcTemplate.update(query, params);
				msg=("تمت الاضافة الي قائمة التسليم");
			}
			else if(q.getNewStatus().equals("10")){

				queryBuilder.append(" Update TGH_TELEGRAPH Set Status_code=10 ,REDIRECT_NOTES=?,OFFICE_CODE=?,NOTES=?,"
						+ "	last_modified_by=(SELECT u.BU_CODE from MTS_SECURITY.SC_USERS u where u.USER_NAME=?) ");

				queryParams.add(q.getRedirctNotes());
				queryParams.add(q.getOfficeCode());
				queryParams.add(q.getNotes());
				queryParams.add(user);
				if(q.getTghId().size()!=0||q.getTghId()!=null){
					queryBuilder.append("where tgh_id IN (?) ");
					queryParams.add(q.getTghId().get(0));
				}
				else {
					queryBuilder.append("where tgh_id IN (\n" +
							"    SELECT tgh.TGH_ID\n" +
							"    FROM tgh_TELEGRAPH tgh, tgh_post_office p, TGH_MSG_STATUS m\n" +
							"    WHERE tgh.OFFICE_CODE IN (\n" +
							"        SELECT s.VALUE\n" +
							"        FROM MTS_SECURITY.SC_USERS u, MTS_SECURITY.SC_USERROLE r, MTS_SECURITY.SC_ROLESCOPES s\n" +
							"        WHERE u.USER_ID = r.USER_ID AND r.ROLE_ID = s.ROLE_ID AND s.SCOPE_ID = 2 AND u.USER_NAME = ? \n" +
							"    )\n" +
							"    AND tgh.status_code IN (\n" +
							"        SELECT s.VALUE AS status_code\n" +
							"        FROM MTS_SECURITY.SC_USERS u, MTS_SECURITY.SC_USERROLE r, MTS_SECURITY.SC_ROLESCOPES s\n" +
							"        WHERE u.USER_ID = r.USER_ID AND r.ROLE_ID = s.ROLE_ID AND s.SCOPE_ID = 1 AND u.USER_NAME = ? \n" +
							"    )\n" +
							"    AND tgh.STATUS_CODE = m.STATUS_CODE\n" +
							"    AND tgh.ORIGIN_OFFICE = p.OFFICE_CODE\n" +
							"    AND p.INCOMING = 1\n" );
					queryParams.add(user);
					queryParams.add(user);


					if (!(q.getCriteria().getTghCode() == null || q.getCriteria().getTghCode().equals(""))) {
						queryBuilder.append(" AND tgh.TGH_CODE= ?");
						queryParams.add(q.getCriteria().getTghCode());
					}

					if (!(q.getCriteria().getIncomingSeq() == null || q.getCriteria().getIncomingSeq().equals(""))) {
						queryBuilder.append(" AND tgh.SEQ_NO= ?");
						queryParams.add(q.getCriteria().getIncomingSeq());
					}
					if (!(q.getCriteria().getCountry() == null || q.getCriteria().getCountry().equals(""))) {
						queryBuilder.append(" AND tgh.COUNTRY_CODE= ?");
						queryParams.add(q.getCriteria().getCountry());
					}
					if (!(q.getCriteria().getTghDate() == null || q.getCriteria().getTghDate().equals(""))) {
						queryBuilder.append(" AND (to_char(TGH_DATE, 'DD/MM/YYYY')=?)");
						queryParams.add(q.getCriteria().getTghDate());
					}
					if (!(q.getCriteria().getCity() == null || q.getCriteria().getCity().equals(""))) {
						queryBuilder.append(" AND tgh.CITY_CODE= ?");
						queryParams.add(q.getCriteria().getCity());
					}

					if (!(q.getOfficeCode() == null || q.getOfficeCode().equals(""))) {
						queryBuilder.append(" AND tgh.OFFICE_CODE = ?");
						queryParams.add(q.getOfficeCode());
					}
					if (!(q.getCriteria().getLang() == null || q.getCriteria().getLang().equals(""))) {
						queryBuilder.append(" AND tgh.LANG_CODE= ?");
						queryParams.add(q.getCriteria().getLang());
					}
					if (!(q.getCriteria().getStatus() == null || q.getCriteria().getStatus().equals(""))) {
						queryBuilder.append(" AND tgh.STATUS_CODE= ?");
						queryParams.add(q.getCriteria().getStatus());
					}

					if (!(q.getCriteria().getDecoration() == null || q.getCriteria().getDecoration().equals(""))) {
						queryBuilder.append(" AND tgh.DECORATION= ?");
						queryParams.add(q.getCriteria().getDecoration());
					}

					if (!(q.getCriteria().getTemplate() == null || q.getCriteria().getTemplate().equals(""))) {
						queryBuilder.append(" AND tgh.TEMPLATE= ?");
						queryParams.add(q.getCriteria().getTemplate());
					}



					if (!(q.getCriteria().getFromDate() == null && q.getCriteria().getEndDate() == null ||
							q.getCriteria().getFromDate().equals("") && q.getCriteria().getEndDate().equals(""))) {
						queryBuilder.append(" AND tgh.TGH_DATE BETWEEN TO_DATE(?,'dd/MM/yyyy') AND TO_DATE(?,'dd/MM/yyyy')+1");
						queryParams.add(q.getCriteria().getFromDate());
						queryParams.add(q.getCriteria().getEndDate());
					}
					queryBuilder.append(")");
				}
				String query = queryBuilder.toString();
				Object[] params = queryParams.toArray();

				jdbcTemplate.update(query, params);
				msg=("تم اعادة التوجيه  ");

			}
		}

		if(q.getOldStatus().equals("3")) {

			if(q.getNewStatus().equals("4")) {

				queryBuilder.append("Update TGH_TELEGRAPH Set Status_code=4,NOTES=? ,"
						+ "last_modified_by= (SELECT u.BU_CODE from MTS_SECURITY.SC_USERS u where u.USER_NAME=?) where Status_code=3 ");//and  tgh_id IN (?)
				queryParams.add(q.getNotes());
				queryParams.add(user);
				if(q.getTghId().size()!=0||q.getTghId()!=null){
					queryBuilder.append("and  tgh_id IN (?)");
					queryParams.add(q.getTghId().get(0));
				}
				else{
					queryBuilder.append("AND tgh_id IN (\n" +
							"    SELECT tgh.TGH_ID\n" +
							"    FROM tgh_TELEGRAPH tgh, tgh_post_office p, TGH_MSG_STATUS m\n" +
							"    WHERE tgh.OFFICE_CODE IN (\n" +
							"        SELECT s.VALUE\n" +
							"        FROM MTS_SECURITY.SC_USERS u, MTS_SECURITY.SC_USERROLE r, MTS_SECURITY.SC_ROLESCOPES s\n" +
							"        WHERE u.USER_ID = r.USER_ID AND r.ROLE_ID = s.ROLE_ID AND s.SCOPE_ID = 2 AND u.USER_NAME = ? \n" +
							"    )\n" +
							"    AND tgh.status_code IN (\n" +
							"        SELECT s.VALUE AS status_code\n" +
							"        FROM MTS_SECURITY.SC_USERS u, MTS_SECURITY.SC_USERROLE r, MTS_SECURITY.SC_ROLESCOPES s\n" +
							"        WHERE u.USER_ID = r.USER_ID AND r.ROLE_ID = s.ROLE_ID AND s.SCOPE_ID = 1 AND u.USER_NAME = ? \n" +
							"    )\n" +
							"    AND tgh.STATUS_CODE = m.STATUS_CODE\n" +
							"    AND tgh.ORIGIN_OFFICE = p.OFFICE_CODE\n" +
							"    AND p.INCOMING = 1\n" );
					queryParams.add(user);
					queryParams.add(user);


					if (!(q.getCriteria().getTghCode() == null || q.getCriteria().getTghCode().equals(""))) {
						queryBuilder.append(" AND tgh.TGH_CODE= ?");
						queryParams.add(q.getCriteria().getTghCode());
					}

					if (!(q.getCriteria().getIncomingSeq() == null || q.getCriteria().getIncomingSeq().equals(""))) {
						queryBuilder.append(" AND tgh.SEQ_NO= ?");
						queryParams.add(q.getCriteria().getIncomingSeq());
					}
					if (!(q.getCriteria().getCountry() == null || q.getCriteria().getCountry().equals(""))) {
						queryBuilder.append(" AND tgh.COUNTRY_CODE= ?");
						queryParams.add(q.getCriteria().getCountry());
					}
					if (!(q.getCriteria().getTghDate() == null || q.getCriteria().getTghDate().equals(""))) {
						queryBuilder.append(" AND (to_char(TGH_DATE, 'DD/MM/YYYY')=?)");
						queryParams.add(q.getCriteria().getTghDate());
					}
					if (!(q.getCriteria().getCity() == null || q.getCriteria().getCity().equals(""))) {
						queryBuilder.append(" AND tgh.CITY_CODE= ?");
						queryParams.add(q.getCriteria().getCity());
					}

					if (!(q.getOfficeCode() == null || q.getOfficeCode().equals(""))) {
						queryBuilder.append(" AND tgh.OFFICE_CODE = ?");
						queryParams.add(q.getOfficeCode());
					}
					if (!(q.getCriteria().getLang() == null || q.getCriteria().getLang().equals(""))) {
						queryBuilder.append(" AND tgh.LANG_CODE= ?");
						queryParams.add(q.getCriteria().getLang());
					}
					if (!(q.getCriteria().getStatus() == null || q.getCriteria().getStatus().equals(""))) {
						queryBuilder.append(" AND tgh.STATUS_CODE= ?");
						queryParams.add(q.getCriteria().getStatus());
					}

					if (!(q.getCriteria().getDecoration() == null || q.getCriteria().getDecoration().equals(""))) {
						queryBuilder.append(" AND tgh.DECORATION= ?");
						queryParams.add(q.getCriteria().getDecoration());
					}

					if (!(q.getCriteria().getTemplate() == null || q.getCriteria().getTemplate().equals(""))) {
						queryBuilder.append(" AND tgh.TEMPLATE= ?");
						queryParams.add(q.getCriteria().getTemplate());
					}



					if (!(q.getCriteria().getFromDate() == null && q.getCriteria().getEndDate() == null ||
							q.getCriteria().getFromDate().equals("") && q.getCriteria().getEndDate().equals(""))) {
						queryBuilder.append(" AND tgh.TGH_DATE BETWEEN TO_DATE(?,'dd/MM/yyyy') AND TO_DATE(?,'dd/MM/yyyy')+1");
						queryParams.add(q.getCriteria().getFromDate());
						queryParams.add(q.getCriteria().getEndDate());
					}
					queryBuilder.append(")");
				}


				String query = queryBuilder.toString();
				Object[] params = queryParams.toArray();
				jdbcTemplate.update(query, params);

				msg=("تمت الاضافة الي الطباعة");

			}
			else if(q.getNewStatus().equals("10")){

				queryBuilder.append(" Update TGH_TELEGRAPH Set Status_code=10 ,REDIRECT_NOTES=?,OFFICE_CODE=?,NOTES=?,"
						+ "	last_modified_by=(SELECT u.BU_CODE from MTS_SECURITY.SC_USERS u where u.USER_NAME=?) "
						);//+ "where tgh_id IN (?)"
				queryParams.add(q.getRedirctNotes());
				queryParams.add(q.getOfficeCode());
				queryParams.add(q.getNotes());
				queryParams.add(user);
				if(q.getTghId().size()!=0||q.getTghId()!=null){
					queryBuilder.append("where tgh_id IN (?) ");
					queryParams.add(q.getTghId().get(0));
				}
				else{
					queryBuilder.append("where tgh_id IN (\n" +
							"    SELECT tgh.TGH_ID\n" +
							"    FROM tgh_TELEGRAPH tgh, tgh_post_office p, TGH_MSG_STATUS m\n" +
							"    WHERE tgh.OFFICE_CODE IN (\n" +
							"        SELECT s.VALUE\n" +
							"        FROM MTS_SECURITY.SC_USERS u, MTS_SECURITY.SC_USERROLE r, MTS_SECURITY.SC_ROLESCOPES s\n" +
							"        WHERE u.USER_ID = r.USER_ID AND r.ROLE_ID = s.ROLE_ID AND s.SCOPE_ID = 2 AND u.USER_NAME = ? \n" +
							"    )\n" +
							"    AND tgh.status_code IN (\n" +
							"        SELECT s.VALUE AS status_code\n" +
							"        FROM MTS_SECURITY.SC_USERS u, MTS_SECURITY.SC_USERROLE r, MTS_SECURITY.SC_ROLESCOPES s\n" +
							"        WHERE u.USER_ID = r.USER_ID AND r.ROLE_ID = s.ROLE_ID AND s.SCOPE_ID = 1 AND u.USER_NAME = ? \n" +
							"    )\n" +
							"    AND tgh.STATUS_CODE = m.STATUS_CODE\n" +
							"    AND tgh.ORIGIN_OFFICE = p.OFFICE_CODE\n" +
							"    AND p.INCOMING = 1\n" );
					queryParams.add(user);
					queryParams.add(user);


					if (!(q.getCriteria().getTghCode() == null || q.getCriteria().getTghCode().equals(""))) {
						queryBuilder.append(" AND tgh.TGH_CODE= ?");
						queryParams.add(q.getCriteria().getTghCode());
					}

					if (!(q.getCriteria().getIncomingSeq() == null || q.getCriteria().getIncomingSeq().equals(""))) {
						queryBuilder.append(" AND tgh.SEQ_NO= ?");
						queryParams.add(q.getCriteria().getIncomingSeq());
					}
					if (!(q.getCriteria().getCountry() == null || q.getCriteria().getCountry().equals(""))) {
						queryBuilder.append(" AND tgh.COUNTRY_CODE= ?");
						queryParams.add(q.getCriteria().getCountry());
					}
					if (!(q.getCriteria().getTghDate() == null || q.getCriteria().getTghDate().equals(""))) {
						queryBuilder.append(" AND (to_char(TGH_DATE, 'DD/MM/YYYY')=?)");
						queryParams.add(q.getCriteria().getTghDate());
					}
					if (!(q.getCriteria().getCity() == null || q.getCriteria().getCity().equals(""))) {
						queryBuilder.append(" AND tgh.CITY_CODE= ?");
						queryParams.add(q.getCriteria().getCity());
					}

					if (!(q.getOfficeCode() == null || q.getOfficeCode().equals(""))) {
						queryBuilder.append(" AND tgh.OFFICE_CODE = ?");
						queryParams.add(q.getOfficeCode());
					}
					if (!(q.getCriteria().getLang() == null || q.getCriteria().getLang().equals(""))) {
						queryBuilder.append(" AND tgh.LANG_CODE= ?");
						queryParams.add(q.getCriteria().getLang());
					}
					if (!(q.getCriteria().getStatus() == null || q.getCriteria().getStatus().equals(""))) {
						queryBuilder.append(" AND tgh.STATUS_CODE= ?");
						queryParams.add(q.getCriteria().getStatus());
					}

					if (!(q.getCriteria().getDecoration() == null || q.getCriteria().getDecoration().equals(""))) {
						queryBuilder.append(" AND tgh.DECORATION= ?");
						queryParams.add(q.getCriteria().getDecoration());
					}

					if (!(q.getCriteria().getTemplate() == null || q.getCriteria().getTemplate().equals(""))) {
						queryBuilder.append(" AND tgh.TEMPLATE= ?");
						queryParams.add(q.getCriteria().getTemplate());
					}



					if (!(q.getCriteria().getFromDate() == null && q.getCriteria().getEndDate() == null ||
							q.getCriteria().getFromDate().equals("") && q.getCriteria().getEndDate().equals(""))) {
						queryBuilder.append(" AND tgh.TGH_DATE BETWEEN TO_DATE(?,'dd/MM/yyyy') AND TO_DATE(?,'dd/MM/yyyy')+1");
						queryParams.add(q.getCriteria().getFromDate());
						queryParams.add(q.getCriteria().getEndDate());
					}
					queryBuilder.append(")");
				}


				String query = queryBuilder.toString();
				Object[] params = queryParams.toArray();
				jdbcTemplate.update(query, params);
				msg=("تم اعادة التوجيه  ");


			}
		}

		if(q.getOldStatus().equals("2"))
		{

			if(q.getNewStatus().equals("4")) {

				System.out.println("BEFOOR");
				queryBuilder.append("Update TGH_TELEGRAPH Set Status_code=4,NOTES=?,"
						+ "	 last_modified_by=(SELECT u.BU_CODE from MTS_SECURITY.SC_USERS u "
						+ "	where u.USER_NAME=?) where Status_code=2 ");

				queryParams.add(q.getNotes());
				queryParams.add(user);
				if(q.getTghId()!=null&& !q.getTghId().isEmpty()){
					queryBuilder.append("and  tgh_id IN (?)");
					queryParams.add(q.getTghId().get(0));
				}
				else{
					queryBuilder.append("AND tgh_id IN (\n" +
							"    SELECT tgh.TGH_ID\n" +
							"    FROM tgh_TELEGRAPH tgh, tgh_post_office p, TGH_MSG_STATUS m\n" +
							"    WHERE tgh.OFFICE_CODE IN (\n" +
							"        SELECT s.VALUE\n" +
							"        FROM MTS_SECURITY.SC_USERS u, MTS_SECURITY.SC_USERROLE r, MTS_SECURITY.SC_ROLESCOPES s\n" +
							"        WHERE u.USER_ID = r.USER_ID AND r.ROLE_ID = s.ROLE_ID AND s.SCOPE_ID = 2 AND u.USER_NAME = ? \n" +
							"    )\n" +
							"    AND tgh.status_code IN (\n" +
							"        SELECT s.VALUE AS status_code\n" +
							"        FROM MTS_SECURITY.SC_USERS u, MTS_SECURITY.SC_USERROLE r, MTS_SECURITY.SC_ROLESCOPES s\n" +
							"        WHERE u.USER_ID = r.USER_ID AND r.ROLE_ID = s.ROLE_ID AND s.SCOPE_ID = 1 AND u.USER_NAME = ? \n" +
							"    )\n" +
							"    AND tgh.STATUS_CODE = m.STATUS_CODE\n" +
							"    AND tgh.ORIGIN_OFFICE = p.OFFICE_CODE\n" +
							"    AND p.INCOMING = 1\n" +
							")");
					queryParams.add(user);
					queryParams.add(user);


					if (!(q.getCriteria().getTghCode() == null || q.getCriteria().getTghCode().equals(""))) {
						queryBuilder.append(" AND tgh.TGH_CODE= ?");
						queryParams.add(q.getCriteria().getTghCode());
					}

					if (!(q.getCriteria().getIncomingSeq() == null || q.getCriteria().getIncomingSeq().equals(""))) {
						queryBuilder.append(" AND tgh.SEQ_NO= ?");
						queryParams.add(q.getCriteria().getIncomingSeq());
					}
					if (!(q.getCriteria().getCountry() == null || q.getCriteria().getCountry().equals(""))) {
						queryBuilder.append(" AND tgh.COUNTRY_CODE= ?");
						queryParams.add(q.getCriteria().getCountry());
					}
					if (!(q.getCriteria().getTghDate() == null || q.getCriteria().getTghDate().equals(""))) {
						queryBuilder.append(" AND (to_char(TGH_DATE, 'DD/MM/YYYY')=?)");
						queryParams.add(q.getCriteria().getTghDate());
					}
					if (!(q.getCriteria().getCity() == null || q.getCriteria().getCity().equals(""))) {
						queryBuilder.append(" AND tgh.CITY_CODE= ?");
						queryParams.add(q.getCriteria().getCity());
					}

					if (!(q.getOfficeCode() == null || q.getOfficeCode().equals(""))) {
						queryBuilder.append(" AND tgh.OFFICE_CODE = ?");
						queryParams.add(q.getOfficeCode());
					}
					if (!(q.getCriteria().getLang() == null || q.getCriteria().getLang().equals(""))) {
						queryBuilder.append(" AND tgh.LANG_CODE= ?");
						queryParams.add(q.getCriteria().getLang());
					}
					if (!(q.getCriteria().getStatus() == null || q.getCriteria().getStatus().equals(""))) {
						queryBuilder.append(" AND tgh.STATUS_CODE= ?");
						queryParams.add(q.getCriteria().getStatus());
					}

					if (!(q.getCriteria().getDecoration() == null || q.getCriteria().getDecoration().equals(""))) {
						queryBuilder.append(" AND tgh.DECORATION= ?");
						queryParams.add(q.getCriteria().getDecoration());
					}

					if (!(q.getCriteria().getTemplate() == null || q.getCriteria().getTemplate().equals(""))) {
						queryBuilder.append(" AND tgh.TEMPLATE= ?");
						queryParams.add(q.getCriteria().getTemplate());
					}



					if (!(q.getCriteria().getFromDate() == null && q.getCriteria().getEndDate() == null ||
							q.getCriteria().getFromDate().equals("") && q.getCriteria().getEndDate().equals(""))) {
						queryBuilder.append(" AND tgh.TGH_DATE BETWEEN TO_DATE(?,'dd/MM/yyyy') AND TO_DATE(?,'dd/MM/yyyy')+1");
						queryParams.add(q.getCriteria().getFromDate());
						queryParams.add(q.getCriteria().getEndDate());
					}
				}
				String query = queryBuilder.toString();
				System.out.println("Query = > "+ query);
				Object[] params = queryParams.toArray();

				jdbcTemplate.update(query, params);

				msg = ("تم اعتماد البرقية ");


			}

			else if(q.getNewStatus().equals("10")){

				if(q.getRedirctNotes()==null ||q.getRedirctNotes()=="" ||q.getRedirctNotes().isEmpty())
					msg = "يجب ادخال اسباب اعادة التوجية";
				else {
					queryBuilder.append(" Update TGH_TELEGRAPH Set Status_code=10 ,REDIRECT_NOTES=?,OFFICE_CODE=?,NOTES=?,\r\n"
							+ "	last_modified_by=(SELECT u.BU_CODE from MTS_SECURITY.SC_USERS u where u.USER_NAME=?) ");//where tgh_id IN (?)

					queryParams.add(q.getRedirctNotes());
					queryParams.add(q.getOfficeCode());
					queryParams.add(q.getNotes());
					queryParams.add(user);

					if(q.getTghId().size()!=0||q.getTghId()!=null){
						queryBuilder.append("where tgh_id IN (?) ");
						queryParams.add(q.getTghId().get(0));
					}
					else{
						queryBuilder.append("where tgh_id IN (\n" +
								"    SELECT tgh.TGH_ID\n" +
								"    FROM tgh_TELEGRAPH tgh, tgh_post_office p, TGH_MSG_STATUS m\n" +
								"    WHERE tgh.OFFICE_CODE IN (\n" +
								"        SELECT s.VALUE\n" +
								"        FROM MTS_SECURITY.SC_USERS u, MTS_SECURITY.SC_USERROLE r, MTS_SECURITY.SC_ROLESCOPES s\n" +
								"        WHERE u.USER_ID = r.USER_ID AND r.ROLE_ID = s.ROLE_ID AND s.SCOPE_ID = 2 AND u.USER_NAME = ? \n" +
								"    )\n" +
								"    AND tgh.status_code IN (\n" +
								"        SELECT s.VALUE AS status_code\n" +
								"        FROM MTS_SECURITY.SC_USERS u, MTS_SECURITY.SC_USERROLE r, MTS_SECURITY.SC_ROLESCOPES s\n" +
								"        WHERE u.USER_ID = r.USER_ID AND r.ROLE_ID = s.ROLE_ID AND s.SCOPE_ID = 1 AND u.USER_NAME = ? \n" +
								"    )\n" +
								"    AND tgh.STATUS_CODE = m.STATUS_CODE\n" +
								"    AND tgh.ORIGIN_OFFICE = p.OFFICE_CODE\n" +
								"    AND p.INCOMING = 1\n" );
						queryParams.add(user);
						queryParams.add(user);


						if (!(q.getCriteria().getTghCode() == null || q.getCriteria().getTghCode().equals(""))) {
							queryBuilder.append(" AND tgh.TGH_CODE= ?");
							queryParams.add(q.getCriteria().getTghCode());
						}

						if (!(q.getCriteria().getIncomingSeq() == null || q.getCriteria().getIncomingSeq().equals(""))) {
							queryBuilder.append(" AND tgh.SEQ_NO= ?");
							queryParams.add(q.getCriteria().getIncomingSeq());
						}
						if (!(q.getCriteria().getCountry() == null || q.getCriteria().getCountry().equals(""))) {
							queryBuilder.append(" AND tgh.COUNTRY_CODE= ?");
							queryParams.add(q.getCriteria().getCountry());
						}
						if (!(q.getCriteria().getTghDate() == null || q.getCriteria().getTghDate().equals(""))) {
							queryBuilder.append(" AND (to_char(TGH_DATE, 'DD/MM/YYYY')=?)");
							queryParams.add(q.getCriteria().getTghDate());
						}
						if (!(q.getCriteria().getCity() == null || q.getCriteria().getCity().equals(""))) {
							queryBuilder.append(" AND tgh.CITY_CODE= ?");
							queryParams.add(q.getCriteria().getCity());
						}

						if (!(q.getOfficeCode() == null || q.getOfficeCode().equals(""))) {
							queryBuilder.append(" AND tgh.OFFICE_CODE = ?");
							queryParams.add(q.getOfficeCode());
						}
						if (!(q.getCriteria().getLang() == null || q.getCriteria().getLang().equals(""))) {
							queryBuilder.append(" AND tgh.LANG_CODE= ?");
							queryParams.add(q.getCriteria().getLang());
						}
						if (!(q.getCriteria().getStatus() == null || q.getCriteria().getStatus().equals(""))) {
							queryBuilder.append(" AND tgh.STATUS_CODE= ?");
							queryParams.add(q.getCriteria().getStatus());
						}

						if (!(q.getCriteria().getDecoration() == null || q.getCriteria().getDecoration().equals(""))) {
							queryBuilder.append(" AND tgh.DECORATION= ?");
							queryParams.add(q.getCriteria().getDecoration());
						}

						if (!(q.getCriteria().getTemplate() == null || q.getCriteria().getTemplate().equals(""))) {
							queryBuilder.append(" AND tgh.TEMPLATE= ?");
							queryParams.add(q.getCriteria().getTemplate());
						}



						if (!(q.getCriteria().getFromDate() == null && q.getCriteria().getEndDate() == null ||
								q.getCriteria().getFromDate().equals("") && q.getCriteria().getEndDate().equals(""))) {
							queryBuilder.append(" AND tgh.TGH_DATE BETWEEN TO_DATE(?,'dd/MM/yyyy') AND TO_DATE(?,'dd/MM/yyyy')+1");
							queryParams.add(q.getCriteria().getFromDate());
							queryParams.add(q.getCriteria().getEndDate());
						}
						queryBuilder.append(")");
					}

					String query = queryBuilder.toString();
					Object[] params = queryParams.toArray();
					jdbcTemplate.update(query, params);

					msg=("تم اعادة التوجيه  ");

					System.out.println("msg" + msg);
				}


			}
		}

		if(q.getOldStatus().equals("5"))
		{

			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			format.setTimeZone(java.util.TimeZone.getTimeZone("GMT+3:00"));
			String time =(format.format(new Date()));
			queryBuilder.append("Update TGH_TELEGRAPH Set Status_code=6,NOTES=?,DISPATCHED_TO=?,DISPATCHED_TIME=?, "
					+ " last_modified_by=(SELECT u.BU_CODE from MTS_SECURITY.SC_USERS u where u.USER_NAME=?) ");//where tgh_id IN (?)

			System.out.println("queryBuilder = > "+queryBuilder.toString());
			queryParams.add(q.getNotes());
			queryParams.add(q.getDispatchedTo());
			queryParams.add(q.getDispatchedTime());
			queryParams.add(user);

			if(q.getTghId()!=null&&q.getTghId().size()!=0){
				queryBuilder.append("where tgh_id IN (?) ");
				queryParams.add(q.getTghId().get(0));
			}
			else{
				queryBuilder.append("where tgh_id IN (\n" +
						"    SELECT tgh.TGH_ID\n" +
						"    FROM tgh_TELEGRAPH tgh, tgh_post_office p, TGH_MSG_STATUS m\n" +
						"    WHERE tgh.OFFICE_CODE IN (\n" +
						"        SELECT s.VALUE\n" +
						"        FROM MTS_SECURITY.SC_USERS u, MTS_SECURITY.SC_USERROLE r, MTS_SECURITY.SC_ROLESCOPES s\n" +
						"        WHERE u.USER_ID = r.USER_ID AND r.ROLE_ID = s.ROLE_ID AND s.SCOPE_ID = 2 AND u.USER_NAME = ? \n" +
						"    )\n" +
						"    AND tgh.status_code IN (\n" +
						"        SELECT s.VALUE AS status_code\n" +
						"        FROM MTS_SECURITY.SC_USERS u, MTS_SECURITY.SC_USERROLE r, MTS_SECURITY.SC_ROLESCOPES s\n" +
						"        WHERE u.USER_ID = r.USER_ID AND r.ROLE_ID = s.ROLE_ID AND s.SCOPE_ID = 1 AND u.USER_NAME = ? \n" +
						"    )\n" +
						"    AND tgh.STATUS_CODE = m.STATUS_CODE\n" +
						"    AND tgh.ORIGIN_OFFICE = p.OFFICE_CODE\n" +
						"    AND p.INCOMING = 1\n"
						);
				queryParams.add(user);
				queryParams.add(user);


				if (!(q.getCriteria().getTghCode() == null || q.getCriteria().getTghCode().equals(""))) {
					queryBuilder.append(" AND tgh.TGH_CODE= ?");
					queryParams.add(q.getCriteria().getTghCode());
				}

				if (!(q.getCriteria().getIncomingSeq() == null || q.getCriteria().getIncomingSeq().equals(""))) {
					queryBuilder.append(" AND tgh.SEQ_NO= ?");
					queryParams.add(q.getCriteria().getIncomingSeq());
				}
				if (!(q.getCriteria().getCountry() == null || q.getCriteria().getCountry().equals(""))) {
					queryBuilder.append(" AND tgh.COUNTRY_CODE= ?");
					queryParams.add(q.getCriteria().getCountry());
				}
				if (!(q.getCriteria().getTghDate() == null || q.getCriteria().getTghDate().equals(""))) {
					queryBuilder.append(" AND (to_char(TGH_DATE, 'DD/MM/YYYY')=?)");
					queryParams.add(q.getCriteria().getTghDate());
				}
				if (!(q.getCriteria().getCity() == null || q.getCriteria().getCity().equals(""))) {
					queryBuilder.append(" AND tgh.CITY_CODE= ?");
					queryParams.add(q.getCriteria().getCity());
				}

				if (!(q.getOfficeCode() == null || q.getOfficeCode().equals(""))) {
					queryBuilder.append(" AND tgh.OFFICE_CODE = ?");
					queryParams.add(q.getOfficeCode());
				}
				if (!(q.getCriteria().getLang() == null || q.getCriteria().getLang().equals(""))) {
					queryBuilder.append(" AND tgh.LANG_CODE= ?");
					queryParams.add(q.getCriteria().getLang());
				}
				if (!(q.getCriteria().getStatus() == null || q.getCriteria().getStatus().equals(""))) {
					queryBuilder.append(" AND tgh.STATUS_CODE= ?");
					queryParams.add(q.getCriteria().getStatus());
				}

				if (!(q.getCriteria().getDecoration() == null || q.getCriteria().getDecoration().equals(""))) {
					queryBuilder.append(" AND tgh.DECORATION= ?");
					queryParams.add(q.getCriteria().getDecoration());
				}

				if (!(q.getCriteria().getTemplate() == null || q.getCriteria().getTemplate().equals(""))) {
					queryBuilder.append(" AND tgh.TEMPLATE= ?");
					queryParams.add(q.getCriteria().getTemplate());
				}



				if (!(q.getCriteria().getFromDate() == null && q.getCriteria().getEndDate() == null ||
						q.getCriteria().getFromDate().equals("") && q.getCriteria().getEndDate().equals(""))) {
					queryBuilder.append(" AND tgh.TGH_DATE BETWEEN TO_DATE(?,'dd/MM/yyyy') AND TO_DATE(?,'dd/MM/yyyy')+1");
					queryParams.add(q.getCriteria().getFromDate());
					queryParams.add(q.getCriteria().getEndDate());
				}
				queryBuilder.append(")");
			}

//	            System.out.println("queryBuilder = > "+queryBuilder.toString());

			String query = queryBuilder.toString();
			System.out.println("query = " + query);
			Object[] params = queryParams.toArray();

			jdbcTemplate.update(query, params);

			msg=("تم نقل الرسالة الي مهام التسليم");
		}


		if(q.getOldStatus().equals("6") &&q.getNewStatus().equals("7"))
		{

			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
			format.setTimeZone(java.util.TimeZone.getTimeZone("GMT+3:00"));
			String time =(format.format(new Date()));

			System.out.println("time" + time);
			if (q.getActualRecName()==""||q.getActualRecName()== null
					||q.getActualRecDate().equals(null)||q.getActualRecDate()==null
					||q.getReceiptNo()==""||q.getReceiptNo()==null)
			{


				msg = ("الرجاء ادخال البيانات الناقصة ");
			}


			else if (q.getReceiptNo().length() > 20)
			{
				msg = ("الرجاء إدخال رقم الفاتورة اقل من 20 حرف ");

			}
			else {


				queryBuilder.append(" Update TGH_TELEGRAPH Set Status_code=7,NOTES=?,ACTUAL_REC_NAME=?,ACTUAL_REC_DATE=?,"
						+ "RECEIPT_NO =? , REC_DATE=?,REC_NAME=?,LAST_MODIFIED_BY= (SELECT u.BU_CODE from MTS_SECURITY.SC_USERS u where u.USER_NAME=?) ");//where TGH_ID =?
				queryParams.add(q.getNotes());
				queryParams.add(q.getActualRecName());
				queryParams.add(q.getActualRecDate());
				queryParams.add(q.getReceiptNo());
				queryParams.add(q.getRecDate());
				queryParams.add(q.getRecName());
				queryParams.add(user);

				if(q.getTghId().size()!=0||q.getTghId()!=null){
					queryBuilder.append("where tgh_id IN (?) ");
					queryParams.add(q.getTghId().get(0));
				}
				else{
					queryBuilder.append("where tgh_id IN (\n" +
							"    SELECT tgh.TGH_ID\n" +
							"    FROM tgh_TELEGRAPH tgh, tgh_post_office p, TGH_MSG_STATUS m\n" +
							"    WHERE tgh.OFFICE_CODE IN (\n" +
							"        SELECT s.VALUE\n" +
							"        FROM MTS_SECURITY.SC_USERS u, MTS_SECURITY.SC_USERROLE r, MTS_SECURITY.SC_ROLESCOPES s\n" +
							"        WHERE u.USER_ID = r.USER_ID AND r.ROLE_ID = s.ROLE_ID AND s.SCOPE_ID = 2 AND u.USER_NAME = ? \n" +
							"    )\n" +
							"    AND tgh.status_code IN (\n" +
							"        SELECT s.VALUE AS status_code\n" +
							"        FROM MTS_SECURITY.SC_USERS u, MTS_SECURITY.SC_USERROLE r, MTS_SECURITY.SC_ROLESCOPES s\n" +
							"        WHERE u.USER_ID = r.USER_ID AND r.ROLE_ID = s.ROLE_ID AND s.SCOPE_ID = 1 AND u.USER_NAME = ? \n" +
							"    )\n" +
							"    AND tgh.STATUS_CODE = m.STATUS_CODE\n" +
							"    AND tgh.ORIGIN_OFFICE = p.OFFICE_CODE\n" +
							"    AND p.INCOMING = 1\n" );
					queryParams.add(user);
					queryParams.add(user);


					if (!(q.getCriteria().getTghCode() == null || q.getCriteria().getTghCode().equals(""))) {
						queryBuilder.append(" AND tgh.TGH_CODE= ?");
						queryParams.add(q.getCriteria().getTghCode());
					}

					if (!(q.getCriteria().getIncomingSeq() == null || q.getCriteria().getIncomingSeq().equals(""))) {
						queryBuilder.append(" AND tgh.SEQ_NO= ?");
						queryParams.add(q.getCriteria().getIncomingSeq());
					}
					if (!(q.getCriteria().getCountry() == null || q.getCriteria().getCountry().equals(""))) {
						queryBuilder.append(" AND tgh.COUNTRY_CODE= ?");
						queryParams.add(q.getCriteria().getCountry());
					}
					if (!(q.getCriteria().getTghDate() == null || q.getCriteria().getTghDate().equals(""))) {
						queryBuilder.append(" AND (to_char(TGH_DATE, 'DD/MM/YYYY')=?)");
						queryParams.add(q.getCriteria().getTghDate());
					}
					if (!(q.getCriteria().getCity() == null || q.getCriteria().getCity().equals(""))) {
						queryBuilder.append(" AND tgh.CITY_CODE= ?");
						queryParams.add(q.getCriteria().getCity());
					}

					if (!(q.getOfficeCode() == null || q.getOfficeCode().equals(""))) {
						queryBuilder.append(" AND tgh.OFFICE_CODE = ?");
						queryParams.add(q.getOfficeCode());
					}
					if (!(q.getCriteria().getLang() == null || q.getCriteria().getLang().equals(""))) {
						queryBuilder.append(" AND tgh.LANG_CODE= ?");
						queryParams.add(q.getCriteria().getLang());
					}
					if (!(q.getCriteria().getStatus() == null || q.getCriteria().getStatus().equals(""))) {
						queryBuilder.append(" AND tgh.STATUS_CODE= ?");
						queryParams.add(q.getCriteria().getStatus());
					}

					if (!(q.getCriteria().getDecoration() == null || q.getCriteria().getDecoration().equals(""))) {
						queryBuilder.append(" AND tgh.DECORATION= ?");
						queryParams.add(q.getCriteria().getDecoration());
					}

					if (!(q.getCriteria().getTemplate() == null || q.getCriteria().getTemplate().equals(""))) {
						queryBuilder.append(" AND tgh.TEMPLATE= ?");
						queryParams.add(q.getCriteria().getTemplate());
					}



					if (!(q.getCriteria().getFromDate() == null && q.getCriteria().getEndDate() == null ||
							q.getCriteria().getFromDate().equals("") && q.getCriteria().getEndDate().equals(""))) {
						queryBuilder.append(" AND tgh.TGH_DATE BETWEEN TO_DATE(?,'dd/MM/yyyy') AND TO_DATE(?,'dd/MM/yyyy')+1");
						queryParams.add(q.getCriteria().getFromDate());
						queryParams.add(q.getCriteria().getEndDate());
					}
					queryBuilder.append(")");
				}

				String query = queryBuilder.toString();
				System.out.println("query = "+query);

				Object[] params = queryParams.toArray();

				jdbcTemplate.update(query, params);

				msg=("تم تأكيد استلام الرسالة");
			}

		}



		if(q.getOldStatus().equals("6") &&q.getNewStatus().equals("8"))
		{

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
			format.setTimeZone(java.util.TimeZone.getTimeZone("GMT+3:00"));
			String time =(format.format(new Date()));

			if (q.getNotes()==""||q.getNotes()==null||q.getNotes().isEmpty())
			{

				msg = ("أدخل اسباب فشل الاستلام");
			}

			else {

				queryBuilder.append("Update TGH_TELEGRAPH Set Status_code=8,Notes=? ,last_modified_by= "
						+ "(SELECT u.BU_CODE from MTS_SECURITY.SC_USERS u where u.USER_NAME=?) where Status_code=6 ");//and  tgh_id IN (?)
				queryParams.add(q.getNotes());
				queryParams.add(user);

//				queryParams.add(q.getTghId().get(i));
				if(q.getTghId().size()!=0||q.getTghId()!=null){
					queryBuilder.append("and  tgh_id IN (?)");
					queryParams.add(q.getTghId().get(0));
				}
				else{
					queryBuilder.append("AND tgh_id IN (\n" +
							"    SELECT tgh.TGH_ID\n" +
							"    FROM tgh_TELEGRAPH tgh, tgh_post_office p, TGH_MSG_STATUS m\n" +
							"    WHERE tgh.OFFICE_CODE IN (\n" +
							"        SELECT s.VALUE\n" +
							"        FROM MTS_SECURITY.SC_USERS u, MTS_SECURITY.SC_USERROLE r, MTS_SECURITY.SC_ROLESCOPES s\n" +
							"        WHERE u.USER_ID = r.USER_ID AND r.ROLE_ID = s.ROLE_ID AND s.SCOPE_ID = 2 AND u.USER_NAME = ? \n" +
							"    )\n" +
							"    AND tgh.status_code IN (\n" +
							"        SELECT s.VALUE AS status_code\n" +
							"        FROM MTS_SECURITY.SC_USERS u, MTS_SECURITY.SC_USERROLE r, MTS_SECURITY.SC_ROLESCOPES s\n" +
							"        WHERE u.USER_ID = r.USER_ID AND r.ROLE_ID = s.ROLE_ID AND s.SCOPE_ID = 1 AND u.USER_NAME = ? \n" +
							"    )\n" +
							"    AND tgh.STATUS_CODE = m.STATUS_CODE\n" +
							"    AND tgh.ORIGIN_OFFICE = p.OFFICE_CODE\n" +
							"    AND p.INCOMING = 1\n" );
					queryParams.add(user);
					queryParams.add(user);


					if (!(q.getCriteria().getTghCode() == null || q.getCriteria().getTghCode().equals(""))) {
						queryBuilder.append(" AND tgh.TGH_CODE= ?");
						queryParams.add(q.getCriteria().getTghCode());
					}

					if (!(q.getCriteria().getIncomingSeq() == null || q.getCriteria().getIncomingSeq().equals(""))) {
						queryBuilder.append(" AND tgh.SEQ_NO= ?");
						queryParams.add(q.getCriteria().getIncomingSeq());
					}
					if (!(q.getCriteria().getCountry() == null || q.getCriteria().getCountry().equals(""))) {
						queryBuilder.append(" AND tgh.COUNTRY_CODE= ?");
						queryParams.add(q.getCriteria().getCountry());
					}
					if (!(q.getCriteria().getTghDate() == null || q.getCriteria().getTghDate().equals(""))) {
						queryBuilder.append(" AND (to_char(TGH_DATE, 'DD/MM/YYYY')=?)");
						queryParams.add(q.getCriteria().getTghDate());
					}
					if (!(q.getCriteria().getCity() == null || q.getCriteria().getCity().equals(""))) {
						queryBuilder.append(" AND tgh.CITY_CODE= ?");
						queryParams.add(q.getCriteria().getCity());
					}

					if (!(q.getOfficeCode() == null || q.getOfficeCode().equals(""))) {
						queryBuilder.append(" AND tgh.OFFICE_CODE = ?");
						queryParams.add(q.getOfficeCode());
					}
					if (!(q.getCriteria().getLang() == null || q.getCriteria().getLang().equals(""))) {
						queryBuilder.append(" AND tgh.LANG_CODE= ?");
						queryParams.add(q.getCriteria().getLang());
					}
					if (!(q.getCriteria().getStatus() == null || q.getCriteria().getStatus().equals(""))) {
						queryBuilder.append(" AND tgh.STATUS_CODE= ?");
						queryParams.add(q.getCriteria().getStatus());
					}

					if (!(q.getCriteria().getDecoration() == null || q.getCriteria().getDecoration().equals(""))) {
						queryBuilder.append(" AND tgh.DECORATION= ?");
						queryParams.add(q.getCriteria().getDecoration());
					}

					if (!(q.getCriteria().getTemplate() == null || q.getCriteria().getTemplate().equals(""))) {
						queryBuilder.append(" AND tgh.TEMPLATE= ?");
						queryParams.add(q.getCriteria().getTemplate());
					}



					if (!(q.getCriteria().getFromDate() == null && q.getCriteria().getEndDate() == null ||
							q.getCriteria().getFromDate().equals("") && q.getCriteria().getEndDate().equals(""))) {
						queryBuilder.append(" AND tgh.TGH_DATE BETWEEN TO_DATE(?,'dd/MM/yyyy') AND TO_DATE(?,'dd/MM/yyyy')+1");
						queryParams.add(q.getCriteria().getFromDate());
						queryParams.add(q.getCriteria().getEndDate());
					}
					queryBuilder.append(")");
				}

				String query = queryBuilder.toString();
				Object[] params = queryParams.toArray();

				jdbcTemplate.update(query, params);

				msg=("تم تعديل الحاله الي فشل الاستلام");
			}

		}

		return msg;
	}

	
	public  List<WFM_EMP> getEmployee_Shifts() {
//		String date2 = getNextDate(date);
//		int size = empShifts_repo.getEmployee(date,date2).size();
//
//		System.out.println("size "+size);
		 return emp_repo.findAll();
	}

	public  Integer getNumberOfTGH(String username) {
		return queue_Repo.getNumberOfTGH(username);
	}
	public String getNextDate(String date1){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			// Parse the string into a java.util.Date object
			Date currentDate = dateFormat.parse(date1);

			// Create a Calendar instance and set it to the parsed date
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);

			// Add 1 day to the current date
			calendar.add(Calendar.DATE, 1);

			// Get the next date
			Date nextDate = calendar.getTime();

			// Print the next date
			System.out.println("Next date: " + dateFormat.format(nextDate));
			String date2 = dateFormat.format(nextDate);
			System.out.println("date2 "+date2);
			return date2;
		} catch (ParseException e) {
			System.err.println("Invalid date format: " + e.getMessage());
		}
        return null;
    }

}
