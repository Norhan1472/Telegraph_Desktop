package tgh.desktop.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomingSearchCustom {
    @JsonProperty("data")
    List<InComingResponse> inComingResponseList;
    Integer count;
}
