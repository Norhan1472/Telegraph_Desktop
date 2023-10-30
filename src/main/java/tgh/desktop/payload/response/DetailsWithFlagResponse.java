package tgh.desktop.payload.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailsWithFlagResponse {
    private DetailsResponse detailsResponse;
    @JsonProperty("tghType")
    private String flag;
}
