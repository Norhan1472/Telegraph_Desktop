package tgh.desktop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tgh.desktop.globalControllers.AuthController;
import tgh.desktop.models.OFFICIAL_TELEGRAPH;
import tgh.desktop.models.TGH_TELEGRAPH;
import tgh.desktop.payload.request.InComingRequest;
import tgh.desktop.payload.request.OfficialTelegraphSearchSeq;
import tgh.desktop.payload.request.OutComingResquest;
import tgh.desktop.payload.response.*;
import tgh.desktop.repos.OFFICIAL_TELEGRAPH_Repo;
import tgh.desktop.repos.Queue_Repo;
import tgh.desktop.services.AuthServiceImpl;
import tgh.desktop.services.OFFICIAL_TELEGRAPH_Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@CrossOrigin("*")
@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("OFFICIAL_TELEGRAPH")
public class OFFICIAL_TELEGRAPH_Controller {
    @Autowired
    private OFFICIAL_TELEGRAPH_Service officialTelegraphService;
    @Autowired
    private OFFICIAL_TELEGRAPH_Repo official_telegraph_repo;
    @Autowired
    private Queue_Repo queueRepo;
    @Autowired
    private final AuthServiceImpl authServiceImpl;
    @Autowired
    private HttpServletRequest request;
    private final AuthController authService;
    //OFFICIAL_TELEGRAPH/getOfficialTelegraphPage
    @GetMapping("getOfficialTelegraphPage")
    public ResponseEntity<APIResponse> getOfficialTelegraphPage(@RequestParam Long tghId,@RequestParam String tghCode) {
        OFFICIAL_TELEGRAPH telegraph = official_telegraph_repo.findById(tghId).orElse(null);
//        System.out.println("tghCode -->"+tghCode);
//        System.out.println(telegraph.getBillTelNo());
        APIResponse apiResponse = new APIResponse();
       if(Objects.isNull(telegraph)){
           apiResponse.setStatus(HttpStatus.OK);
           apiResponse.setStatusCode(HttpStatus.OK.value());
           apiResponse.setClientMessage("NO Telegraph with this id");
           return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);
       }

        Official_telegraph_format response=
                officialTelegraphService.getOfficialTelegraphPage(tghId,tghCode);


        try {
            if(Objects.isNull(response)) {
                apiResponse.setStatus(HttpStatus.OK);
                apiResponse.setStatusCode(HttpStatus.OK.value());
                apiResponse.setClientMessage("No data found");
                return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);
            }
            else {
                apiResponse.setStatus(HttpStatus.OK);
                apiResponse.setStatusCode(HttpStatus.OK.value());
                apiResponse.setBody(response);
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
    }

    /*
    SELECT
    tgh.TGH_ID, tgh.TGH_CODE, tgh.TGH_DATE,
    tgh.SEQ_NO, tgh.SENDER_NAME, tgh.REC_NAME, tgh.ADDRESS, tgh.OFFICE_SEQ,
    tgh.TGH_COST, tgh.SEND_DATE, p.OFFICE_NAME AS ORIGIN,tgh.ORIGIN_OFFICE , p.OFFICE_CODE
FROM

    tgh_post_office p , TGH_Telegraph tgh
WHERE
    (
        (
            SELECT COUNT(*)
            FROM MTS_SECURITY.SC_USER_PERMISSION sec
            WHERE
                lower(sec.USER_NAME) = lower('superuser')
                AND sec.MODULE_ID = 7
                AND sec.permission_Name = 'OfficialTelegraph.view'
        ) = 1
        AND tgh.OFFICE_CODE IN (
            SELECT s.VALUE
            FROM MTS_SECURITY.SC_USERS u, MTS_SECURITY.SC_USERROLE r, MTS_SECURITY.SC_ROLESCOPES s
            WHERE
                u.USER_ID = r.USER_ID
                AND r.ROLE_ID = s.ROLE_ID
                AND s.SCOPE_ID = 2
                AND u.USER_NAME = 'superuser'
        )
        AND tgh.ORIGIN_OFFICE = p.OFFICE_CODE
    )
    OR
    (
        (
            SELECT COUNT(*)
            FROM MTS_SECURITY.SC_USER_PERMISSION sec
            WHERE
                lower(sec.USER_NAME) = lower('superuser')
                AND sec.MODULE_ID = 7
                AND (sec.permission_Name = 'OfficialTelegraph.view' OR sec.permission_Name = 'OfficialTelegraph.viewAll')
        ) = 2
        AND tgh.ORIGIN_OFFICE = p.OFFICE_CODE
    );
     */
    @PostMapping("search")
    @PreAuthorize("hasAuthority('TghIncoming.view')")
    public ResponseEntity<APIResponse> searchOfficialTelegraph(@RequestBody OfficialTelegraphSearchSeq SearchRequest){
        APIResponse apiResponse = new APIResponse();


        System.out.println("queue_Entity =>"+SearchRequest);
        try {
            request= ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            System.out.println("request.getRemoteUser() "+request.getSession().getAttribute("userName"));
            ResponseEntity<APIResponse> response=
                    officialTelegraphService.search(authService.getUserNameData(), SearchRequest);//authServiceImpl.retUserName()
            return response;

        }
        catch(Exception e) {
            e.printStackTrace();
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            apiResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            apiResponse.setClientMessage(e.getMessage());
            apiResponse.setDeveloperMessage(e.getCause().toString());
            return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
