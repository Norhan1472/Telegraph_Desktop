package tgh.desktop.services;


import java.text.SimpleDateFormat;

import java.time.Instant;

import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import io.swagger.models.auth.In;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tgh.desktop.globalControllers.AuthController;
import tgh.desktop.models.*;

import tgh.desktop.payload.request.DailyReportRequest;
import tgh.desktop.payload.request.TGH_Draft_Request;
import tgh.desktop.payload.request.calculateMSG_Request;
import tgh.desktop.payload.response.CalculateCostResponse;
import tgh.desktop.payload.response.DailyRepeortResponse;
import tgh.desktop.payload.response.DailyReposrResponse;
import tgh.desktop.payload.response.PrintMessageForCost_Response;
import tgh.desktop.payload.response.TGH_Draft_Response;
import tgh.desktop.payload.response.address_Response;
import tgh.desktop.payload.response.calculateMSG_Response;
import tgh.desktop.payload.response.lineInfo_Response;
import tgh.desktop.payload.response.printTGHCostResponse;
import tgh.desktop.repos.*;

import javax.servlet.http.HttpServletRequest;


@Service
@RequiredArgsConstructor
public class TGH_Service{

//	@Autowired
//	private TGH_TARRIF_ADD_Repo tarrif_ADD_Repo;
	
	@Autowired
	private TGH_VIP_Repo vipRepo;
	
	@Autowired
	private SC_USERS_Repo users_Repo;
	
	@Autowired
	private TGH_DRAFT_Repo draft_Repo;
	@Autowired
	private POST_OFFICE_Repo postOffice_Repo;
	@Autowired
	getTGHcost getCost;
	@Autowired
	TGH_ADD_VALUE_Repo add_VALUE_Repo;
	@Autowired
	private SENDER_DRAFT_Repo sender_DRAFT_Repo;
	@Autowired 
	private REC_DRAFT_Repo rec_DRAFT_Repo;
	
	@Autowired
	private Queue_Repo queueRepo;
	
	
	@Autowired
	private TGH_ADD_VALUE_Repo add_Repo;
	@Autowired
	private TGH_Repo repo;
	@Autowired
	private TGH_Country_Repo country_Repo;
	@Autowired
	private TGH_CITY_Repo city_Repo;
	@Autowired
	private POST_OFFICE_Repo office_Repo;
	@Autowired
	private OfficeKeyword_Repo  keyword_Repo;
	
	@Autowired
	private TARRIF_PLAN_Repo plan_Repo;
	
	@Autowired
	private TGH_VIP_Repo vip_Repo;
	@Autowired
	private SUBS_Repo subs_Repo;
	
	@Autowired
	private SENDER_Repo sender_Repo;
	@Autowired
	private RECEPIENT_Repo recepient_Repo;
	
	@Autowired
	private TEMPLATE_TYPE_reposatiry templateType_reposatiry ;
	@Autowired
	private Queue_Service queueService;
	@Autowired
	TGH_COUNTRY_LANGUAGE_Repo language_Repo;
	private final AuthController authController;

	@Autowired
	HttpServletRequest request;
	String addressRec = ""; 
	
	public TGHCreate_respose  CreateTelegraoh(TGH_Entity tghReq) {
		
		
//		tghReq.setCallDate(new Date());
//		tghReq.setSendDate(new Date());
		
		Set<TGH_SENDER>  lst_senders=tghReq.getSenders();
		Set<TGH_RECEPIENT> lst_rec = tghReq.getRecepients();
		
		
		
		String tempCode =tghReq.getTempCode();
		String TempTypeCode = tghReq.getTempTypeCode();

		
		System.out.println("GENID ==> " + tghReq.getGenId() );
		
		if (tghReq.getTemplate() == 0)
		{
			tempCode = null;
			TempTypeCode = null;
		}

		Integer genId = tghReq.getGenId();
		
		if ( draft_Repo.checkGenId (genId) != 0)
		{
				genId = repo.getgenId();
				System.out.println("GenID  IF== >"+genId);
		}
		

		SOAPConnector connector = new SOAPConnector();
		
//		  String billTelNo = tghReq.getBillTelNo();
		  
//		  StringBuffer buffer = new StringBuffer(billTelNo);
//		  buffer.deleteCharAt(0);
//		   System.out.println("buffer.toString()"+buffer.toString());
//		lineInfo_Response infoRespone =
//				connector.getGeneralLineInfo(buffer.toString());

//		System.out.println("infoRespone" +infoRespone);
//
//
//		Integer	status= Integer.parseInt(infoRespone.getSubscriberStatus());
//
//		 Double balance=Double.parseDouble(infoRespone.getBalance());
		
		 
		 
		 
//		System.out.println("status   "+status);
//		System.out.println("balance   "+balance);

			repo.setGenId(genId, tghReq.getUserCode(),tghReq.getCallDate(),tghReq.getCallerTelNo(),
				tghReq.getCallerName(), tghReq.getSendDate(),tghReq.getCountryCode(),
				tghReq.getLangCode(), tghReq.getPlanCode(), tghReq.getTemplate(),
				TempTypeCode, tempCode,tghReq.getDeliveryNotic() 
				,tghReq.getDecoration(), tghReq.getUrgent(), tghReq.getAdmin(), 
				tghReq.getInternational(), tghReq.getNotes(), tghReq.getMessage(),
				 tghReq.getSms(),0);


		
		System.out.println("*-* TGH add Successful *-* ");
		for (TGH_SENDER child :lst_senders) {
            child.setTgh_Entity(tghReq);
            child.setGenId(genId);
            // save sender 
            sender_Repo.save(child);

        }
		System.out.println("*-* TGH ID Add at Sender *-* ");
		
		for (TGH_RECEPIENT child :lst_rec) {
			addressRec = child.getRecName();
            child.setTgh_Entity(tghReq);
            child.setGenId(genId);
            // save rec
            recepient_Repo.save(child);
            System.out.println("*-*REC GenID ------>"+child.getGenId().toString());
            
//            System.out.println("*-*REC ID ------>"+child.getRecId().toString());
        }
		System.out.println("*-* TGH ID Add at Recep *-* ");

		changeStatus(genId,tghReq.getUserCode());


		TGHCreate_respose  response = new TGHCreate_respose();
		
		response.setClientMessage("-*- Telegraph Added Successfully -*-");
		response.setGenId(genId);
		
		return response ;
	}
	public void changeStatus(Integer genId,String username){
	try{
		List<TGH_TELEGRAPH> telegraphs =queueService.showTgh(genId);
		for(TGH_TELEGRAPH telegraph:telegraphs){
			if(telegraph.getStatus().equals("2")){
				int n = queueRepo.updateStatusCode(telegraph.getSeqNo(),username,7,627);
				System.out.println("kkjj");
				System.out.println(n);
			}
		}
	}
	catch (Exception ex){
		ex.printStackTrace();
	}

	}
		
	
	public List<Template_Type> get_types() {

				return templateType_reposatiry.findAll() ;	
	
		}
	
	public List<TGH_Country>  gelocalCountry() {
		
		return country_Repo.findLocalCountry();
	} 
	
//public List<TGH_Country>  getInterCountry() {
//		
//		return country_Repo.findInterCountry();
//	} 
	
	public Integer  getGenId() {
		
		return repo.getgenId();
	} 
	
	public List<TGH_CITY>  getLocalcity() {
		
		return city_Repo.findlocalCities();
	} 	
	
	public List<TGH_TARRIF_PLAN>  getplanNoAdmin() {
		
		return plan_Repo.findPlanNoAdmin();
	} 
 
	
//	public List<POST_OFFICE>  getlocalOffice() {
//	
//	return office_Repo.findLocalPostalOffice();
//}

	
	public List<POST_OFFICE> getPostalOffice(String cityCode) {
		System.out.println("cityCode "+cityCode);
		String officeCode = authController.getOfficeName();
		if(cityCode=="null"||cityCode.equals("null")||cityCode==null||cityCode.equals(null)){
			System.out.println("jj");
			return office_Repo.findInterPostalOffice("");
		}

		
	return office_Repo.	findInterPostalOffice(cityCode);
}
	public List<POST_OFFICE> getPostalOfficeWithoutCurrentOffice(String cityCode) {
		System.out.println("cityCode "+cityCode);
		String officeCode = authController.getOfficeName();
		if(cityCode=="null"||cityCode.equals("null")||cityCode==null||cityCode.equals(null)){
			System.out.println("jj");
			return office_Repo.findInterPostalOfficewithoutCurrentOffice("",officeCode);
		}


		return office_Repo.	findInterPostalOfficewithoutCurrentOffice(cityCode,officeCode);
	}

public List<POST_OFFICE>findOriginOffice(String cityCode){
	return office_Repo.	findOriginOffice(cityCode);
}


public List<TGH_VIP>  getVip(String code) {
	
	return vip_Repo.findByCountryCode(code);
} 

public String getCallerName(Integer telNo) {

	 return subs_Repo.findByName(telNo);
	 
	}
	 
public String saveTghDraft(TGH_DRAFT tghReq) {
	
//	tghReq.setCallDate(new Date());
//	tghReq.setSendDate(new Date());
//	tghReq.setDraftDate(new Date());
//	TGH_DRAFT draft = new TGH_DRAFT();
	
	String typeCode ;
	String tempCode ;
	if (tghReq.getTemplate().equals("0")) {
		 typeCode = "0";
		 tempCode = "0";
	}
	else {
		typeCode = tghReq.getTempTypeCode();
		tempCode= tghReq.getTempCode();
	}
	
	Integer genId = tghReq.getGenId();
	List<TGH_SENDER_DRAFT>  lst_senders=tghReq.getSenders();
	List<TGH_RECEPIENT_DRAFT> lst_rec = tghReq.getRecepients();
	
	
	//genid already at DB 
	if (draft_Repo.checkGenId(tghReq.getGenId())!=0) {
	
	draft_Repo.updateDraft(genId, tghReq.getUserCode(),tghReq.getCallDate(),tghReq.getCallerTelNo(),
			tghReq.getCallerName(),tghReq.getSendDate(), tghReq.getCountryCode(),
			tghReq.getLangCode(), tghReq.getPlanCode(), tghReq.getTemplate(),
			typeCode, tempCode,tghReq.getDeliveryNotic() 
			,tghReq.getDecoration(), tghReq.getUrgent(), tghReq.getAdmin(), 
			tghReq.getInternational(), tghReq.getNotes(), tghReq.getMessage(), 
			tghReq.getSms());
	
	System.out.println("Update TGH Draft Successfully");
	
	for (TGH_RECEPIENT_DRAFT child :lst_rec) {
		
		String vip = child.getVipNo();
	     if(child.getRecVip().equals("0")) {
	    	 vip= "0" ;
	    	
	     }
	    	 
	     
        rec_DRAFT_Repo.updateRecDraft( genId,
        		 child.  getRecName(), child. getTelNo()
   			 , child. getAddress(), child. getPostalOffice(), 
   			 child. getOfficeCode(), child. getRecVip()
			 ,vip, child. getSenderResp()
			 , child. getRecName2(),child. getCityCode());

    }
	System.out.println("Update Rec Draft Successfully");
	
	for (TGH_SENDER_DRAFT child :lst_senders) {
       	
		sender_DRAFT_Repo.updateSenderDraft( genId,
		child.getSenderName() ,
		child. getAddress(),child. getSenderName2(),child.getEmail(),
		child. getMobileNo(),child. getNationalId());

}
System.out.println("Update Sneder Draft Successfully");

	

	}
	
	else 
	{
		
		draft_Repo.drafts(genId, tghReq.getUserCode(),tghReq.getCallDate(),tghReq.getCallerTelNo(),
				tghReq.getCallerName(),tghReq.getSendDate(), tghReq.getCountryCode(),
				tghReq.getLangCode(), tghReq.getPlanCode(), tghReq.getTemplate(),
				typeCode, tempCode,tghReq.getDeliveryNotic() 
				,tghReq.getDecoration(), tghReq.getUrgent(), tghReq.getAdmin(), 
				tghReq.getInternational(), tghReq.getNotes(), tghReq.getMessage(), 
				tghReq.getSms());	
		
	
	System.out.println("*-* TGH Add at  TGH Draft Successfully*-* ");
	
	for (TGH_SENDER_DRAFT child :lst_senders) {
        child.setTghDraft(tghReq);
        child.setGenId(genId);
        
        // save sender 
        sender_DRAFT_Repo.save(child);
    
	}
	System.out.println("*-* TGH ID Add at Sender Draft *-* ");
	
	for (TGH_RECEPIENT_DRAFT child :lst_rec) {
        child.setTghDraft(tghReq);
        child.setGenId(genId);
        // save rec
        rec_DRAFT_Repo.save(child);
	}
	System.out.println("*-* TGH ID Add at Recep Draft *-* ");
	}
	
	return tghReq.toString() ;
	
}


public Optional<TGH_DRAFT> DraftByGenId(TGH_Draft_Request req) {
	
	 
	Optional<TGH_DRAFT> draftReq = draft_Repo.findById(req.getGenId());
	 
	System.out.println("draftReq" +draftReq);
	
	draftReq.get().setGenId(getGenId());	
	for (int i = 0 ; i <draftReq.get().getRecepients().size();i++) {
		
	
	if (req.getRecFlag() ==0) {
//		draftReq.get().setRecepients(null);
		
		draftReq.get().getRecepients().get(i).setAddress(null);
		draftReq.get().getRecepients().get(i).setRecId(null);
		draftReq.get().getRecepients().get(i).setRecName(null);
		draftReq.get().getRecepients().get(i).setRecName2(null);
		draftReq.get().getRecepients().get(i).setRecVip(null);
		draftReq.get().getRecepients().get(i).setTelNo(null);
		
		draftReq.get().getRecepients().get(i).setPostalOffice(null);
		draftReq.get().getRecepients().get(i).setOfficeCode(null);
		draftReq.get().getRecepients().get(i).setVipNo(null);
		draftReq.get().getRecepients().get(i).setSenderResp(null);
		draftReq.get().getRecepients().get(i).setCityCode(null);
		draftReq.get().getRecepients().get(i).setId(null);
		
	}
	}	
		
	
	
//	System.out.println("rec => "+  draftReq.get().getRecepients().get(0).getAddress());
	
	
	for (int i = 0 ; i <draftReq.get().getSenders().size();i++) {
		
		
		if (req.getSenderFlag() == 0) {
			
			draftReq.get().getSenders().get(i).setAddress(null);
			draftReq.get().getSenders().get(i).setSenderId(null);
			draftReq.get().getSenders().get(i).setSenderName(null);
			draftReq.get().getSenders().get(i).setSenderName2(null);
			draftReq.get().getSenders().get(i).setEmail(null);
			draftReq.get().getSenders().get(i).setMobileNo(null);
			draftReq.get().getSenders().get(i).setId(null);
			draftReq.get().getSenders().get(i).setNationalId(null);
		}	
		}
		

	
	if (req.getMessageFlag() ==0) {
		draftReq.get().setMessage(null);
		draftReq.get().setTemplate("0");
		draftReq.get().setTempCode("0");
		draftReq.get().setTempTypeCode("0");
		draftReq.get().setUrgent("0");
		draftReq.get().setDecoration("0");
		draftReq.get().setDeliveryNotic("0");
		draftReq.get().setSms("0");
		
	}
	return draftReq;
	
} 

public List<TGH_Draft_Response> getTghDraft(String user) {
	
	List<TGH_Draft_Response> draftResponse= new ArrayList<>();
	
	List<TGH_DRAFT> drafts = new ArrayList<>();
	
	//user==null || user=="" ||user.equals("null") || user.equals(null)||user.equals("")
	//if (telNo!=null || !telNo.equals(""))

//	if (!(telNo==null || telNo=="" ||telNo.equals("null") ||
//			telNo.equals(null)||telNo.equals("")))
//		drafts = draft_Repo.findByBillTelNo(telNo);

	
	if(!(user==null || user=="" ||user.equals("null") ||
			user.equals(null)||user.equals(""))){
		drafts = draft_Repo.findByUserCode(user);
	}
	
	
	
	//System.out.println("user = >" + user + "TEL = >" + telNo);
	for(TGH_DRAFT i : drafts) {
		 
		TGH_Draft_Response Response= new TGH_Draft_Response();
		BeanUtils.copyProperties(i,Response);
		draftResponse.add(Response);
		
	}
	return  draftResponse;
	}

public List<OfficeKeywords>  getOfficeByKeywords(String word,String code) {
	
	return	keyword_Repo.officeKeywords(word,code);

}


	//961
	public calculateMSG_Response  calculateCost( calculateMSG_Request calmst) {

		calculateMSG_Response responseCalculate= new calculateMSG_Response();
		String officeName = postOffice_Repo.getOfficesByOfficeCode(authController.getOfficeName());
		responseCalculate.setOfficeName(officeName);
		MessageForCost msgCostObj = new MessageForCost();
		List<MessageForCost>  messageForCost = new ArrayList<>();
		TGH_TARRIF_PLAN plan = plan_Repo.findByPlanCodeQ(calmst.getPlanCode());
		List<CalculateCostResponse> item = add_VALUE_Repo.getCalItems(plan.getPlanCode());

		List<String> taxsBefore = new ArrayList<>();
		List<String> taxsAfter = new ArrayList<>();
		double totaltaxesBefore = 0;
		double totaltaxesAfter = 0;

		double decPrice = plan.getDecorationEnable() == 1? plan.getDecorationPrice():0;
		double deliveryPrice = plan.getDeliveryEnable() == 1? plan.getDeliveryPrice():0;
		double tempPrice = plan.getTemplateEnable() == 1? plan.getTemplatePrice():0;
		double urgPrice = plan.getUrgentEnable() == 1? plan.getUrgentPrice():0;
		double smsPrice = plan.getSMSEnable() == 1? plan.getSMSPrice():0;
		double poPrice = plan.getPoPrice();
		double salesTax = plan.getSalesTax();
		double wordPrice = plan.getWordPrice();
		double itemPrice = 0;

		for (CalculateCostResponse items : item) {
			Integer AddToTax = items.getTAX_ENABLED();
			Integer AddToTemp =items.getTEMP_ENABLED();

			if (calmst.getTemplate() > 0) {
				if (AddToTemp > 0) {
					if (AddToTax > 0) {
						totaltaxesBefore += items.getADD_PRICE();
						itemPrice += items.getADD_PRICE();
						taxsBefore.add(items.getITEM_NAME() + " = " + items.getADD_PRICE());
					}
					else {
						totaltaxesAfter += items.getADD_PRICE();
						itemPrice += items.getADD_PRICE();
						taxsAfter.add(items.getITEM_NAME() + " = " + items.getADD_PRICE());
					}
				}
			}
			else{
				if (AddToTax > 0) {
					totaltaxesBefore += items.getADD_PRICE();
					itemPrice += items.getADD_PRICE();
					taxsBefore.add(items.getITEM_NAME() + " = " + items.getADD_PRICE());
				}
				else {
					totaltaxesAfter +=  items.getADD_PRICE();
					itemPrice += items.getADD_PRICE();
					taxsAfter.add
							(items.getITEM_NAME() + " = " + items.getADD_PRICE());
				}
			}
		}
		String taxBeforeStr = "";
		String taxAfterStr = "";
		for (int i = 0; i < taxsBefore.size(); i++) {
			taxBeforeStr += " " + taxsBefore.get(i);
		}
		for (int i = 0; i < taxsAfter.size(); i++) {
			taxAfterStr += " " + taxsAfter.get(i);
		}
		responseCalculate.setPlanName(plan.getPlanName());
		responseCalculate.setWordPrice(plan.getWordPrice());
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		responseCalculate.setTime(format.format(new Date()));
		Double totalCost = 0.0;
		Integer newNoOfWords = 0;

		for (TGH_RECEPIENT rec : calmst.getRecepients()) {

			String Rec_name =   rec.getRecName();
			String	message = calmst.getMessage();
			String	address = rec.getAddress();
			String rec2 =  rec.getRecName2();
			String addressDetails ="";
			for (TGH_SENDER senders : calmst.getSenders()) {

				Double cost = 0.0;
				Double messageCost=0.0;
				Integer numWords=0;
				Integer numWordsExtra=0;
				String sender_name = senders.getSenderName();
				String sender2 = senders.getSenderName2();
				String address2 = "";
				if(senders.getAddress() != null)
					address2 += senders.getAddress();
				if( !(rec.getAddressDetails() ==null || rec.getAddressDetails().equals("")))
					addressDetails = rec.getAddressDetails();
				if (calmst.getTemplate()>0) {
					numWords = 0;
				}
				else{
					String ss =message + " "+address+" " +address2
							+" "+Rec_name+" "+sender_name +" " + addressDetails;

					numWords += getCost.countWord(ss,plan.getCharWord(),plan.getWordsCount());
					newNoOfWords = getCost.totalWord(ss,plan.getCharWord(),plan.getWordsCount()	);

					if(numWords <= 7 && plan.getWordsCount() == 1) {
						numWords = 7;
					}
					if(calmst.getAdmin() > 0) {
						numWords = 0;
					}

				}

				if ( !(rec2 == null || rec2.equals(null) ||rec2 == "" ||rec2.equals("") )){
					numWordsExtra +=  getCost.countWord(rec2,plan.getCharWord(),plan.getWordsCount());
				}
				if ( !( sender2 == null || sender2 == "" ||sender2.equals(null) ||sender2.equals("") )){
					numWordsExtra +=  getCost.countWord(sender2,plan.getCharWord(),plan.getWordsCount());
				}

				double ExtraTaxes = 0.0;
				double po = 0;
				double costExtra = 0;
				Double Div= 0.0;
				Double TAX= 0.0;
				Integer VIPFees = 0;
				if (!(rec.getPostalOffice()==null||rec.getPostalOffice().equals("")|| rec.getPostalOffice().isEmpty())) {
					po=poPrice;
					cost += poPrice;
					ExtraTaxes +=poPrice*(salesTax/100);
				}
				if(calmst.getUrgent()>0) {
					responseCalculate.setUrgentPrice(urgPrice);
					cost+=urgPrice;
					ExtraTaxes+=urgPrice*(salesTax/100);
				}
				else
					responseCalculate.setUrgentPrice(0L);
				if(calmst.getTemplate()>0) {
					responseCalculate.setTemplatePrice(tempPrice);
					cost += tempPrice;
					ExtraTaxes += tempPrice* (salesTax/100);
				}
				if(calmst.getTemplate()==0) {
					cost+= wordPrice * numWords;
					responseCalculate.setTemplatePrice(0L);
				}
				if(calmst.getDecoration()>0) {
					responseCalculate.setDecorationPrice(decPrice);
					cost+= decPrice;
				}
				else
					responseCalculate.setDecorationPrice(0L);
				if(calmst.getDelivery()>0) {
					responseCalculate.setDeliveryPrice(deliveryPrice);
					cost += deliveryPrice;
					ExtraTaxes += deliveryPrice* (salesTax/100);
				}
				else
					responseCalculate.setDeliveryPrice(0L);
				if(calmst.getSms()>0) {
					responseCalculate.setSMSPrice(smsPrice);
					cost += smsPrice;
					ExtraTaxes += smsPrice* (salesTax/100);
				}
				else
					responseCalculate.setSMSPrice(0L);

				messageCost +=cost;


				if (rec2 != "" || sender2 != "") {
					cost += wordPrice*numWordsExtra;
					ExtraTaxes += wordPrice*numWordsExtra *(salesTax/100);
				}
				costExtra = wordPrice*numWordsExtra;
				Integer vipFees = 0;
				if(rec.getRecVip()==1) {
					vipFees=  vipRepo.fees(rec.getVipNo());
					cost+= vipFees;
				}
				double Diff ;
				double tax ;
				if(calmst.getTemplate()==0) {
					if (taxsBefore.size() > 0 || taxsAfter.size() > 0) {
						responseCalculate.setAddedPrice(taxBeforeStr+" "+taxAfterStr);
					}
//					messageCost += (double) (taxBeforeStr+" "+taxAfterStr);

					cost += totaltaxesBefore;
					messageCost += itemPrice;
					tax = cost * (salesTax/100.0);
					cost += totaltaxesAfter;
					cost += tax;
					Diff = cost % 100.0;
					if (Diff > 0 && Diff <= 25) {
						Diff = 25.0 - Diff;
					}
					else if (Diff > 25 && Diff <= 50) {
						Diff = 50.0 - Diff;
					}
					else if (Diff > 50 && Diff <= 75){
						Diff = 75.0 - Diff;
					}
					else if (Diff > 75 && Diff <= 99.99) {
						Diff = 100.0 - Diff;
					}
					double DiffTax = Diff * (salesTax/ 100.0);
					cost += Diff;
					DiffTax = Math.round(DiffTax*100.0)/100.0;
					Diff = Diff - DiffTax;
					tax += DiffTax;
					tax = Math.floor(tax * 100.0) / 100.0;
				}
				else {
					if (taxsBefore.size() > 0 || taxsAfter.size() > 0) {
						responseCalculate.setAddedPrice(taxBeforeStr+" "+taxAfterStr);
					}
					cost += totaltaxesBefore+totaltaxesAfter;
					tax = totaltaxesBefore * (salesTax / 100.0);
					tax += ExtraTaxes;
					cost += tax;
					Diff = cost % 100.0;
					Diff =  (Diff*100.0)/100.0;
					if (Diff > 0 && Diff <= 25) {
						Diff = 25.0 - Diff;
					}
					else if (Diff > 25 && Diff <= 50){
						Diff = 50.0 - Diff;
					}
					else if (Diff > 50 && Diff <= 75){
						Diff = 75.0 - Diff;
					}
					else if (Diff > 75 && Diff <= 99.99) {
						Diff = 100.0 - Diff;
					}
					cost += Diff;
					double DiffTax = Diff * (salesTax / 100.0);
					DiffTax = (DiffTax*100.0)/100.0;
					Diff = Diff - DiffTax;
					tax += DiffTax;
					tax = Math.floor(tax * 100.0) / 100.0;
				}
				totalCost += cost;
				msgCostObj = new MessageForCost(Rec_name,address,plan.getWordsCount(),newNoOfWords, po,
						messageCost, Diff, tax, numWordsExtra,costExtra,vipFees, cost);
				messageForCost.add(msgCostObj);
				responseCalculate.setMessages(messageForCost);
			}
		}
		totalCost= totalCost/100;
		responseCalculate.setTotalcost(totalCost+"");
		return responseCalculate;
	}





public List<String>  getUsersName(String office) {
	
	return users_Repo.userName(office); 

}

public printTGHCostResponse  printCalculateCost(calculateMSG_Request calmst) {
	
	printTGHCostResponse costResponse= new printTGHCostResponse();
	List <PrintMessageForCost_Response> response = new ArrayList<>();
	
	calculateMSG_Response calculateMSG_Response = calculateCost(calmst);
	
	System.out.println("calculateMSG_Response => "+calculateMSG_Response);
	
	for(MessageForCost i : calculateMSG_Response.getMessages()) {
		 
		PrintMessageForCost_Response printResponse= new PrintMessageForCost_Response();
		BeanUtils.copyProperties(i,printResponse);

		if(i.getPostalOfficePrice()==0)
			printResponse.setPostalOfficePrice(null);
		
		if(i.getMessageCost()==0)
			printResponse.setMessageCost(null);
		
		printResponse.setUrgentPrice(calculateMSG_Response.getUrgentPrice());
		
		printResponse.setDecorationPrice(calculateMSG_Response.getDecorationPrice());
		printResponse.setSMSPrice(calculateMSG_Response.getSMSPrice());
		printResponse.setDeliveryPrice(calculateMSG_Response.getDeliveryPrice());
		printResponse.setTemplatePrice(calculateMSG_Response.getTemplatePrice());
		
		
		
		if(printResponse.getDecorationPrice()==0)
			printResponse.setDecorationPrice(null);
		
			if(printResponse.getUrgentPrice()==0)
				printResponse.setUrgentPrice(null);
			
				if(printResponse.getDeliveryPrice()==0)
					printResponse.setDeliveryPrice(null);
				
					if(printResponse.getTemplatePrice()==0)
						printResponse.setTemplatePrice(null);
		

					if(printResponse.getSMSPrice()==0)
						printResponse.setSMSPrice(null);
		
		
		response.add(printResponse);
		

	}

	BeanUtils.copyProperties(calculateMSG_Response, costResponse);
	System.out.println("costResponse = "+costResponse);
	costResponse.setMessages(response);
	
	
	
	

				return costResponse;
}


	public DailyReposrResponse DailyReport(DailyReportRequest req) {
	
		
		List<DailyRepeortResponse> dailyReposrResponse =
				queueRepo.getDailyReport(req.getTGHDateFrom(), req.getTGHDateTo(),
				req.getUserName());
		
		Integer totalCost = queueRepo.getDailyReportCost(req.getTGHDateFrom(), req.getTGHDateTo(),
				req.getUserName());
		DailyReposrResponse response = 
				new DailyReposrResponse(dailyReposrResponse, totalCost);
		return response;
	}
	
	public List<TGH_TARRIF_PLAN> plan() {
		return plan_Repo.findAll();
	}
	
	

	@Autowired
	ADD_DICTIONARY_DEF_Repo def_Repo;
	
	
	Map<String, String> Key_dic =  new HashMap<>();
	
	@Cacheable ("mycach")
	 public Map<String, String> get_dic()
     {
		
		 List<Object[]> lst = def_Repo.get_dic();
		 for (Object[] objects : lst) {
			 if(!Key_dic.containsKey(objects[0])) {
				 Key_dic.put(objects[0].toString(),objects[1].toString()); 
			 }
		}
		 
//		 Thread.sleep(40000);
		return Key_dic;

     }

//Composer/getOfficeByKeywords?word=asd&code=GZ

	public List<address_Response> replaceAddress(String address ,String cityCode) {

		if(Key_dic.isEmpty()) {
			get_dic();
		}
		int index = checkAddressFormat(address);
		System.out.println("index "+index);
		String numAddress = address.substring(0,index+1);
		address=address.substring(index+1,address.length());

		System.out.println("numAddress "+numAddress);
		System.out.println("address "+address);

		List<String> source = new ArrayList<>();
		String s=address.replace('أ','ا' );
		s = s.replace('ؤ','و');
		s = s.replace('ء','و');
		s = s.replace('آ','ا');
		s = s.replace('أ','ا');
		s = s.replace('إ','ا');
		s = s.replace('ي','ى');
		s = s.replace("يي","ى");
		s = s.replace("ىى","ى");
		s = s.replace('ئ','ى');
		s = s.replace("يء","ى");
		s = s.replace("ىء","ى");
		s = s.replace('ة','ه');
		s = s.replace( "لل","ل");
		s = s.replace("عبد ", "عبد");
		s = s.replace("ابو ", "ابو");

		List <address_Response> address_Responses = new ArrayList<>();

		if (source.size() <= 0){
			System.out.println(source.size());
			String [] Words= s.trim().split(" ");
			System.out.println("Words.length: "+Words.length);
			if (Words.length > 0){
				for (String word : Words) {
					if ( Key_dic.containsKey(word)) {
						s = s.replace(word, Key_dic.get(word));
						System.out.println("s=: "+s);
						s =s.trim();
					}
					System.out.println(s);
					System.out.println(address);
					if(index == -1){
						address_Responses=keyword_Repo.getAddress(address, s, cityCode);
					}else{
						address_Responses=keyword_Repo.getAddressWithRange(address, s, cityCode,Integer.parseInt(numAddress));
					}

				}

			}
		}
		return address_Responses;

	}

	private int checkAddressFormat(String address) {

		Pattern pattern = Pattern.compile("\\d");
		Matcher matcher = pattern.matcher(address);

		int lastDigitIndex = -1;
		while (matcher.find()) {
			lastDigitIndex = matcher.start();
		}

//		if (lastDigitIndex != -1) {
//			System.out.println("Index of the last digit: " + lastDigitIndex);
//		} else {
//			System.out.println("No digits found in the string");
//		}
		return lastDigitIndex;
	}

	public List <String> getLanguage(String countryCode) {

		return language_Repo.findByCountryCode(countryCode);
	}

}
