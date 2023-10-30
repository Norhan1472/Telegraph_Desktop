package tgh.desktop.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Official_telegraph_format {
    @JsonProperty("رقم التقرير")
    private long reportNO;
    @JsonProperty("officialTelegraphMessageDetails")
    private Official_telegraph_body officialTelegraphBody;
    @JsonProperty("officialTelegraphDetails")
    private Official_telegraph_footer officialTelegraphFooter;

}
