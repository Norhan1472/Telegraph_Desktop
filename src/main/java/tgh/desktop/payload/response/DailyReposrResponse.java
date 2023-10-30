package tgh.desktop.payload.response;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyReposrResponse {

	
List<DailyRepeortResponse> dailyRepeortResponses;

	
	Integer totalCost;
}
