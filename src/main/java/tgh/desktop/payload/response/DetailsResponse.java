package tgh.desktop.payload.response;

import java.sql.Clob;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
		"كود االمحرر",
		"كود البرقيه",
		"تاريخ البرقيه",
		"حالة البرقيه",
		"رقم الورود",
		"كود الموظف",
		"رقم الايصال",
		"ملاحظات",
		"اسم المبلغ",
		"تاريخ الارسال",
		"الرقم التتابعى",
		"نموذج",
		"الزركشة",
		"علم الوصول",
		"رد خالص",
		"رقم المبلغ",
		"اداري",
		"دولى",
		"التكلفة",
		"اجمالي التكلفه",
		"المرسل الية",
		"المرسل الية الاضافي",
		"المرسل اليه مهم",
		"كود مكتب الورود",
		"العنوان",
		"ص.ب",
		"الرسالة",
		"الاسم الفعلى للمستلم",
		"التاريخ الفعلى للاستلام",
		"ملاحظات اعادة التوجيه",
		"الراسل",
		"الراسل الاضافى",
		"عنوان الراسل"

})
public interface DetailsResponse {

	@JsonProperty("كود االمحرر")
	Integer getGEN_ID ();
	@JsonProperty("كود البرقيه")
	String  getTGH_CODE();

	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	@JsonProperty("تاريخ البرقيه")
	Date  getTGH_DATE();
	
	@JsonProperty("حالة البرقيه")
	String getSTATUS_CODE();
	@JsonProperty("رقم الورود")
	Integer getSEQ_NO ();
	@JsonProperty("كود الموظف")
	String  getUSER_CODE ();
	@JsonProperty("رقم الايصال")
	String getRECEIPT_NO();
	
	@JsonProperty("ملاحظات")
	String getNOTES ();
	@JsonProperty("اسم المبلغ")
	String  getCALLER_NAME();
	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonProperty("تاريخ الارسال")
	Date getSEND_DATE();
	@JsonProperty("الرقم التتابعى")
	Integer getTGH_ID ();
	@JsonProperty("نموذج")
	Integer getTEMPLATE ();
	@JsonProperty("الزركشة")
	Integer getDECORATION();

	@JsonProperty("علم الوصول")
	Integer getDELIVERY_NOTICE();
	@JsonProperty("رد خالص")
	Integer getURGENT ();
	@JsonProperty("رقم المبلغ")
	String getCALLER_TEL_NO();
	@JsonProperty("اداري")
	Integer getADMIN ();
	@JsonProperty("دولى")
	Integer  getINTERNATIONAL  ();
	@JsonProperty("التكلفة")
	Integer  getTGH_COST ();
	@JsonProperty("اجمالي التكلفه")
	Integer getPAID_AMOUNT();
	/////////////////////////////////////////
     @JsonProperty("المرسل الية")
     String  getREC_NAME();
     
     @JsonProperty("المرسل الية الاضافي")
     String getREC_NAME2 ();
	@JsonProperty("المرسل اليه مهم")
	Integer getREC_VIP();
	@JsonProperty("كود مكتب الورود")
	String  getOFFICE_CODE();
	@JsonProperty("العنوان")
	String  getADDRESS ();
	@JsonProperty("ص.ب")
	String getPOSTAL_OFFICE();
	////////////////////////////////////////////
     @JsonProperty("الاسم الفعلى للمستلم")
     String  getACTUAL_REC_NAME();
     
     @JsonFormat(pattern="dd/MM/yyyy")
     @JsonProperty("التاريخ الفعلى للاستلام")
     Date  getACTUAL_REC_DATE ();
     
     @JsonProperty("ملاحظات اعادة التوجيه")
     String getREDIRECT_NOTES  ();
     
     @JsonProperty("الرسالة")
     Clob getMESSAGE ();
     

     
     @JsonProperty("الراسل")
     String getSENDER_NAME ();
     
     @JsonProperty("الراسل الاضافى")
     String getSENDER_NAME2 ();
     
     @JsonProperty("عنوان الراسل")
     String  getSENDER_ADDRESS();
	 @JsonIgnore
	 Date getDATE_LOCK();
//	 @JsonIgnore
//	 String getOFFICE_CODE();

	@JsonIgnore
	Integer getORA_ROWSCN();
}
