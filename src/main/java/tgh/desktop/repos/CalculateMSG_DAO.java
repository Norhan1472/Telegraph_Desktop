package tgh.desktop.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CalculateMSG_DAO {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("deprecation")
	public Integer getCost (String planCode,
	 Integer template,
	 Integer delivery, 
	 Integer urgent ,
	 Integer decoration ,
	 String message, 
	 String sender,
	 String rec,
	 String postalOffice) {
		String sqlQuery="SELECT CALCULATE_MSG_COST (?,?,?,?,?,?,?,?,?) FROM DUAL";
		
		return jdbcTemplate.queryForObject(sqlQuery ,new Object[] {planCode ,template ,delivery ,urgent ,decoration ,message ,sender ,rec, postalOffice}, Integer.class);
		
	}

	public Integer totalWord (String MSG, Integer maxWordCount,Integer numOfWords) {
		String sqlQuery="SELECT CALCULATE_COUNT_WORD (?,?,?) FROM DUAL";

		return jdbcTemplate.queryForObject(sqlQuery ,new Object[] {MSG ,maxWordCount,numOfWords}, Integer.class);

	}
	
	@SuppressWarnings("deprecation")
	public Integer countOfword (String MSG, Integer maxWordCount,Integer noOfWords) {
		String sqlQuery="SELECT MSG_WORD_COUNT_PERFORMANCE (?,?,?) FROM DUAL";
		
		return jdbcTemplate.queryForObject(sqlQuery ,new Object[] {MSG ,maxWordCount ,noOfWords}, Integer.class);
		
	}
}
