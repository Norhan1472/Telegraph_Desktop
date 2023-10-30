package tgh.desktop.payload.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Official_telegraph_body {
    @JsonProperty("مكتب الصادر")
    String outgoingOffice ;
    @JsonProperty("مكتب الوارد")
    String officeName;
    @JsonProperty("نوع الرساله")
    String type;
    @JsonProperty("تاريخ و ساعه الاستقبال")
    String tghDate;
    @JsonProperty("تاريخ و ساعه البعث")
    String actionDate;
    @JsonProperty("الرقم الاصلي للبرقيه")
    String tghCode;
    @JsonProperty("رقم التتابعي")
    String seqNo;
    @JsonProperty("رقم المستند")
    String tghId;
    @JsonProperty("رقم المحاسبه")
    String billTelNo;
    @JsonProperty("اسم المبلغ")
    String callerName;
    @JsonProperty("كود الدوله")
    String countryCode;
    @JsonProperty("عدد الكلمات")
    String noOfWords;
}
