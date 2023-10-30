package tgh.desktop.services;

import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import tgh.desktop.payload.request.OfficialTelegraphSearchSeq;
import tgh.desktop.payload.response.*;
import tgh.desktop.repos.OFFICIAL_TELEGRAPH_Repo;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class OFFICIAL_TELEGRAPH_Service {
    @Autowired
    private OFFICIAL_TELEGRAPH_Repo official_telegraph_repo;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public Official_telegraph_format getOfficialTelegraphPage(long tgh_id,String TghCode){
        OFFICIAL_TELEGRAPH_Response response = official_telegraph_repo.getOfficialTelegraphPage(tgh_id,TghCode);

        Official_telegraph_format official_telegraph_format = new Official_telegraph_format();
        official_telegraph_format = getOfficialPage(response);
        return official_telegraph_format;
    }

    private Official_telegraph_format getOfficialPage(OFFICIAL_TELEGRAPH_Response response) {
        Official_telegraph_format format = new Official_telegraph_format();
        //Official_telegraph_body
        Official_telegraph_body body = new Official_telegraph_body();
        body.setTghId(response.getTGH_ID());
        body.setOutgoingOffice(response.getOUTGOING_OFFICE());
        body.setOfficeName(response.getOFFICE_NAME());
        body.setType(response.getTYPE());
        body.setTghDate(response.getTGH_DATE());
        body.setActionDate(response.getaction_Date());
        body.setTghCode(response.getTGH_CODE());
        body.setSeqNo(response.getSEQ_NO());
        body.setBillTelNo(response.getBILL_TEL_NO());
        body.setCallerName(response.getCALLER_NAME());
        body.setCountryCode(response.getCOUNTRY_CODE());
        body.setNoOfWords(response.getNO_OF_WORDS());
        //footer
        Official_telegraph_footer footer = new Official_telegraph_footer();
        footer.setDelivery(response.getDELIVERY());
        footer.setRecName(response.getREC_NAME());
        footer.setAddress(response.getADDRESS());
        footer.setOfficeName(response.getOFFICE_NAME());
        String message = Strings.trimAllWhitespace(checkNullInString(response));
        footer.setMessage(message);
        footer.setSenderName(response.getSENDER_NAME());
        footer.setSenderAddress(response.getSENDER_ADDRESS());
        //Report_No
        format.setReportNO(response.getReport_NO());
        format.setOfficialTelegraphBody(body);
        format.setOfficialTelegraphFooter(footer);
        return format;
    }

    public String checkNullInString(OFFICIAL_TELEGRAPH_Response response){
        String message ="";

        if(Objects.nonNull(response.getMESSAGE())){
            message+=response.getMESSAGE();
        }
        if(Objects.nonNull(response.getMESSAGE2())){
            message+=" ";
            message+=response.getMESSAGE2();
        }
        if(Objects.nonNull(response.getMESSAGE3())){
            message+=" ";
            message+=response.getMESSAGE3();
        }
        if(Objects.nonNull(response.getMESSAGE4())){
            message+=" ";
            message+=response.getMESSAGE4();
        }
        if(Objects.nonNull(response.getMESSAGE5())){
            message+=" ";
            message+=response.getMESSAGE5();
        }
        if(Objects.nonNull(response.getMESSAGE6())){
            message+=" ";
            message+=response.getMESSAGE6();
        }
        if(Objects.nonNull(response.getMESSAGE7())){
            message+=" ";
            message+=response.getMESSAGE2();
        }
        if(Objects.nonNull(response.getMESSAGE7())){
            message+=" ";
            message+=response.getMESSAGE2();
        }
        if(Objects.nonNull(response.getMESSAGE8())){
            message+=" ";
            message+=response.getMESSAGE8();
        }
        if(Objects.nonNull(response.getMESSAGE9())){
            message+=" ";
            message+=response.getMESSAGE9();
        }
        if(Objects.nonNull(response.getMESSAGE10())){
            message+=" ";
            message+=response.getMESSAGE10();
        }
        return message;
    }

    public ResponseEntity<APIResponse> search(String user, OfficialTelegraphSearchSeq searchRequest) {

        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println("Current Date and Time: " + currentDateTime);

        APIResponse apiResponse = new APIResponse();
        List<Object> queryParams = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("select * from tgh_post_office p , OFFICIAL_Telegraph tgh,TGH_POST_OFFICE p1 \n" +
                "where    tgh.ORIGIN_OFFICE = p.OFFICE_CODE\n" +
                "                        AND tgh.OFFICE_CODE=p1.OFFICE_CODE \n" );
        if (!(searchRequest.getTghCode() == null || searchRequest.getTghCode().equals(""))) {
            queryBuilder.append(" AND tgh.TGH_CODE= ?");
            queryParams.add(searchRequest.getTghCode());
        }
        if (!(searchRequest.getSeqNo() == null || searchRequest.getSeqNo().equals(""))) {
            queryBuilder.append(" AND tgh.SEQ_NO= ?");
            queryParams.add(searchRequest.getSeqNo());
        }
        if (!(searchRequest.getBillTelNo() == null || searchRequest.getBillTelNo().equals(""))) {
            if(searchRequest.getBillTelNo().length()==10&&searchRequest.getBillTelNo().startsWith("02")){
                queryBuilder.append(" AND tgh.BILL_TEL_NO=?");
                queryParams.add(searchRequest.getBillTelNo());
            }else{
                apiResponse.setStatus(HttpStatus.BAD_REQUEST);
                apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
                apiResponse.setClientMessage("YOu Must Enter Valid Bill Tel No that 10 digits and start with \"02\"");
                return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.BAD_REQUEST);
            }
        }
        if (!(searchRequest.getGenId() == null || searchRequest.getGenId() ==0)) {
            queryBuilder.append(" AND tgh.GEN_ID= ?");
            queryParams.add(searchRequest.getGenId());
        }

        if (!(searchRequest.getOutgoingSeq() == null || searchRequest.getOutgoingSeq().equals(""))) {
            queryBuilder.append(" AND tgh.OFFICE_SEQ = ?");
            queryParams.add(searchRequest.getOutgoingSeq());
        }
//                "--                        and tgh_id=1000112568\n" +
//                "                        and   tgh.SEQ_NO= :seqNo  \n" +

        queryBuilder.append( "and  ((\n" +
                " SELECT COUNT(*)\n " +
                " FROM MTS_SECURITY.SC_USER_PERMISSION sec\n " +
                " WHERE\n " +
                "    lower(sec.USER_NAME) = lower(?)\n " +
                "    AND sec.MODULE_ID = 7\n" +
                "    AND sec.permission_Name = 'OfficialTelegraph.view'\n" +
                "\n" +
                "                        ) = 1\n" +
                "                        and p.office_code in (SELECT s.VALUE\n" +
                " FROM MTS_SECURITY.SC_USERS u, MTS_SECURITY.SC_USERROLE r, MTS_SECURITY.SC_ROLESCOPES s\n" +
                " WHERE\n" +
                "    u.USER_ID = r.USER_ID\n" +
                "    AND r.ROLE_ID = s.ROLE_ID\n" +
                "    AND s.SCOPE_ID = 2\n" +
                "    AND u.USER_NAME = ? ) or (\n" +
                " SELECT COUNT(*)\n" +
                " FROM MTS_SECURITY.SC_USER_PERMISSION sec\n" +
                " WHERE\n" +
                "    lower(sec.USER_NAME) = lower(?)\n" +
                "    AND sec.MODULE_ID = 7\n" +
                "    AND sec.permission_Name in  ('OfficialTelegraph.viewAll','OfficialTelegraph.view')\n" +
                "\n" +
                "                        ) = 2)");
        queryParams.add(user);
        queryParams.add(user);
        queryParams.add(user);
        System.out.println("queryBuilder="+queryBuilder);

        String query = queryBuilder.toString();
        System.out.println("query="+query);

        Object[] params = queryParams.toArray();

        // Execute the query
        @SuppressWarnings("deprecation")
        List<OfficialTelegraphSearchResponse> results = jdbcTemplate.query(query, params, (rs, rowNum) -> {
            // Map the result rows to your Result class
            OfficialTelegraphSearchResponse result = new OfficialTelegraphSearchResponse();

            result.setSeqNo(rs.getString("SEQ_NO"));

            result.setTghId(rs.getLong("TGH_ID"));

            result.setBillTelNo(rs.getString("BILL_TEL_NO"));

            result.setCallerName(rs.getString("CALLER_NAME"));

            result.setNoOfWords(rs.getString("NO_OF_WORDS"));

            result.setRecName(rs.getString("REC_NAME"));

            result.setSenderName(rs.getString("SENDER_NAME"));

            result.setTghDate(rs.getDate("TGH_DATE"));

            result.setTghCode(rs.getString("TGH_CODE"));

            result.setOfficeName(rs.getString("OFFICE_NAME"));

            result.setOriginOffice(rs.getString("ORIGIN_OFFICE"));

            System.out.println("result="+result);
            return result;
        });
        try {

            if(results.isEmpty()) {

               apiResponse.setStatus(HttpStatus.OK);
               apiResponse.setStatusCode(HttpStatus.OK.value());
               apiResponse.setClientMessage("No data Found");
               return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);

            }else{
                //System.out.println("results="+results);
                currentDateTime = LocalDateTime.now();
                System.out.println("Current Date and Time: " + currentDateTime);
                apiResponse.setStatus(HttpStatus.OK);
                apiResponse.setStatusCode(HttpStatus.OK.value());
                apiResponse.setBody(results);
                return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);
            }

        }
        catch(Exception e) {
            e.printStackTrace();
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            apiResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            apiResponse.setClientMessage(e.getMessage());
            apiResponse.setDeveloperMessage(e.getCause().toString());
            return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
//		List<Queue_Entity> tghQueue = queueRepo.findByGenId(queue.getGenId());


    }
}

// StringBuilder queryBuilder = new StringBuilder("SELECT tgh.TGH_ID, tgh.TGH_CODE, tgh.TGH_DATE,\n" +
//                "                    tgh.SEQ_NO, tgh.SENDER_NAME, tgh.REC_NAME, \n" +
//                "                    tgh.BILL_TEL_NO,tgh.CALLER_NAME,tgh.NO_OF_WORDS,\n" +
//                "                    tgh.origin_office,p.OFFICE_NAME\n" +
//                "                    \n" +
//                "                FROM\n" +
//                "                    tgh_post_office p , OFFICIAL_Telegraph tgh,TGH_POST_OFFICE p1\n" +
//                "                    \n" +
//                "                WHERE\n" +
//                "                    (\n" +
//                "                        (\n" +
//                "SELECT COUNT(*)\n" +
//                "FROM MTS_SECURITY.SC_USER_PERMISSION sec\n" +
//                "WHERE\n" +
//                "    lower(sec.USER_NAME) = lower(?)\n" +
//                "    AND sec.MODULE_ID = 7\n" +
//                "    AND sec.permission_Name = 'OfficialTelegraph.view'\n" +
//                "                        ) = 1\n" +
//                "                        AND tgh.OFFICE_CODE IN (\n" +
//                "SELECT s.VALUE\n" +
//                "FROM MTS_SECURITY.SC_USERS u, MTS_SECURITY.SC_USERROLE r, MTS_SECURITY.SC_ROLESCOPES s\n" +
//                "WHERE\n" +
//                "    u.USER_ID = r.USER_ID\n" +
//                "    AND r.ROLE_ID = s.ROLE_ID\n" +
//                "    AND s.SCOPE_ID = 2\n" +
//                "    AND u.USER_NAME = ? \n" +
//                "                        )\n" +
//                "                        AND tgh.ORIGIN_OFFICE = p.OFFICE_CODE\n" +
//                "                        AND tgh.OFFICE_CODE=p1.OFFICE_CODE\n" );
//        queryParams.add(user);
//        queryParams.add(user);
//        if (!(searchRequest.getTghCode() == null || searchRequest.getTghCode().equals(""))) {
//            queryBuilder.append(" AND tgh.TGH_CODE= ?");
//            queryParams.add(searchRequest.getTghCode());
//        }
//        if (!(searchRequest.getSeqNo() == null || searchRequest.getSeqNo().equals(""))) {
//            queryBuilder.append(" AND tgh.SEQ_NO= ?");
//            queryParams.add(searchRequest.getSeqNo());
//        }
//        if (!(searchRequest.getBillTelNo() == null || searchRequest.getBillTelNo().equals(""))) {
//            if(searchRequest.getBillTelNo().length()==10&&searchRequest.getBillTelNo().startsWith("02")){
//                queryBuilder.append(" AND tgh.BILL_TEL_NO=?");
//                queryParams.add(searchRequest.getBillTelNo());
//            }else{
//                apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//                apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
//                apiResponse.setClientMessage("YOu Must Enter Valid Bill Tel No that 10 digits and start with \"02\"");
//                return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.BAD_REQUEST);
//            }
//        }
//        if (!(searchRequest.getGenId() == null || searchRequest.getGenId() ==0)) {
//            queryBuilder.append(" AND tgh.GEN_ID= ?");
//            queryParams.add(searchRequest.getGenId());
//        }
//
//        if (!(searchRequest.getOutgoingSeq() == null || searchRequest.getOutgoingSeq().equals(""))) {
//            queryBuilder.append(" AND tgh.OFFICE_SEQ = ?");
//            queryParams.add(searchRequest.getOutgoingSeq());
//        }
//        queryBuilder.append("                    )\n" +
//                "                    OR\n" +
//                "                    (\n" +
//                "                        (\n" +
//                "SELECT COUNT(*)\n" +
//                "FROM MTS_SECURITY.SC_USER_PERMISSION sec\n" +
//                "WHERE\n" +
//                "    lower(sec.USER_NAME) = lower(?)\n" +
//                "    AND sec.MODULE_ID = 7\n" +
//                "    AND (sec.permission_Name = 'OfficialTelegraph.view' OR sec.permission_Name = 'OfficialTelegraph.viewAll')\n" +
//                "                        ) = 2\n" +
//                "                        AND tgh.ORIGIN_OFFICE = p.OFFICE_CODE\n" +
//                "                        AND tgh.OFFICE_CODE=p1.OFFICE_CODE\n" +
//                "                    ");
//
//
//        queryParams.add(user);
//
//
//
//        if (!(searchRequest.getTghCode() == null || searchRequest.getTghCode().equals(""))) {
//            queryBuilder.append(" AND tgh.TGH_CODE= ?");
//            queryParams.add(searchRequest.getTghCode());
//        }
//        if (!(searchRequest.getSeqNo() == null || searchRequest.getSeqNo().equals(""))) {
//            queryBuilder.append(" AND tgh.SEQ_NO= ?");
//            queryParams.add(searchRequest.getSeqNo());
//        }
//        if (!(searchRequest.getBillTelNo() == null || searchRequest.getBillTelNo().equals(""))) {
//            if(searchRequest.getBillTelNo().length()==10&&searchRequest.getBillTelNo().startsWith("02")){
//                queryBuilder.append(" AND tgh.BILL_TEL_NO=?");
//                queryParams.add(searchRequest.getBillTelNo());
//            }else{
//                apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//                apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
//                apiResponse.setClientMessage("YOu Must Enter Valid Bill Tel No that 10 digits and start with \"02\"");
//                return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.BAD_REQUEST);
//            }
//        }
//        if (!(searchRequest.getGenId() == null || searchRequest.getGenId() ==0)) {
//            queryBuilder.append(" AND tgh.GEN_ID= ?");
//            queryParams.add(searchRequest.getGenId());
//        }
//
//        if (!(searchRequest.getOutgoingSeq() == null || searchRequest.getOutgoingSeq().equals(""))) {
//            queryBuilder.append(" AND tgh.OFFICE_SEQ = ?");
//            queryParams.add(searchRequest.getOutgoingSeq());
//        }
//        queryBuilder.append(")");
//
//        System.out.println("queryBuilder="+queryBuilder);
//
//        String query = queryBuilder.toString();
//        System.out.println("query="+query);
//
//        Object[] params = queryParams.toArray();
//
//        // Execute the query
//        @SuppressWarnings("deprecation")
//        List<OfficialTelegraphSearchResponse> results = jdbcTemplate.query(query, params, (rs, rowNum) -> {
//            // Map the result rows to your Result class
//            OfficialTelegraphSearchResponse result = new OfficialTelegraphSearchResponse();
//
//            result.setSeqNo(rs.getString("SEQ_NO"));
//
//            result.setTghId(rs.getLong("TGH_ID"));
//
//            result.setBillTelNo(rs.getString("BILL_TEL_NO"));
//
//            result.setCallerName(rs.getString("CALLER_NAME"));
//
//            result.setNoOfWords(rs.getString("NO_OF_WORDS"));
//
//            result.setRecName(rs.getString("REC_NAME"));
//
//            result.setSenderName(rs.getString("SENDER_NAME"));
//
//            result.setTghDate(rs.getDate("TGH_DATE"));
//
//            result.setTghCode(rs.getString("TGH_CODE"));
//
//            result.setOfficeName(rs.getString("OFFICE_NAME"));
//
//            result.setOriginOffice(rs.getString("ORIGIN_OFFICE"));
//
//            System.out.println("result="+result);
//            return result;
//        });
//