package tgh.desktop.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficialTelegraphSearchResponse {
    private String seqNo;
    private Long tghId;
    private String billTelNo;
    private String callerName;
    private String noOfWords;
    private String recName;
    private String senderName;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date tghDate;
    private String tghCode;
    private String officeName;
    private String originOffice;

}
