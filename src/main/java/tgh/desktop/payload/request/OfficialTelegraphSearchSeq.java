package tgh.desktop.payload.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficialTelegraphSearchSeq {
    private String seqNo;
    private String tghCode;
    private String billTelNo;
    private Long genId;
    private String outgoingSeq;
}
