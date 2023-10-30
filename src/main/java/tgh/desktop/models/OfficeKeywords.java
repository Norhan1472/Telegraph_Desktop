package tgh.desktop.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="TGH_OFFICE_KEYWORD")
public class OfficeKeywords {

	
	@Id
	@Column(name = "KEYWORD_CODE" )
	private Integer keywordCode;
	
	@Column(name = "KEYWORD_NAME")
		private String keywordName;
	
	@Column(name = "NUMBER_RANGE" )
	private Integer numberRange;

	@Column(name = "RANGE_START" )
	private Integer rangeStart;

	@Column(name = "RANGE_END" )
	private Integer rangeEnd;
	
	@Column(name = "OFFICE_CODE" )
	private String officeCODE ;

	@Column(name = "INSERT_DATE" )
	private Timestamp insertDate;

	@Column(name = "NUMBER_RANGE_TYPE" )
	private Integer numberRangeType ;
	
	@Column(name = "KEYWORD_NAME_DIC" )
	private String keywordNameDic;
}
