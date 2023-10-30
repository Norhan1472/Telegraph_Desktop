package tgh.desktop.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tgh.desktop.models.OfficeKeywords;
import tgh.desktop.payload.response.address_Response;

@Repository
public interface OfficeKeyword_Repo extends JpaRepository<OfficeKeywords , Integer>{

	@Query(value = "SELECT tok.KEYWORD_CODE , tok.KEYWORD_NAME , tok.NUMBER_RANGE , \r\n"
			+ "tok.RANGE_START , tok.RANGE_END ,tok.OFFICE_CODE ,tok.INSERT_DATE ,tok.NUMBER_RANGE_TYPE,tok.KEYWORD_NAME_DIC \r\n"
			+ "from( TGH_OFFICE_KEYWORD tok inner join  TGH_POST_OFFICE tpo on  tpo.OFFICE_CODE = tok.OFFICE_CODE )\r\n"
			+ "inner join TGH_CITY tc on tpo.CITY_CODE = tc.CITY_CODE\r\n"
			+ "where  tok.KEYWORD_NAME LIKE ('%'||:WORD ||'%') and tc.CITY_CODE  =:CODE"
			, nativeQuery = true)
	
	List<OfficeKeywords> officeKeywords(String WORD ,String CODE);

	@Query(value = "SELECT DISTINCT\n"
			+ "    k.KEYWORD_NAME  ,k.office_code,\n"
			+ "    CASE\n"
			+ "        WHEN INSTR(k.keyword_name, :address) = 0 THEN 100000\n"
			+ "        ELSE INSTR(k.keyword_name, :address)\n"
			+ "    END AS topIndex ,\n"
			+ "    INSTR(k.keyword_name_dic, :s) AS startIndex \n"
			+ "FROM\n"
			+ "    TGH_OFFICE_KEYWORD k,\n"
			+ "    tgh_post_office po\n"
			+ "WHERE\n"
			+ "    (k.keyword_name_dic LIKE '%' || :s || '%' \n"
			+ "    OR k.keyword_name LIKE '%' || :address || '%'\n"
			+ "    OR k.keyword_name_dic LIKE '%' || :address || '%' \n"
			+ "    OR k.keyword_name LIKE '%' || :s || '%')\n"
			+ "    AND k.office_code = po.office_code\n"
			+ "    AND po.CITY_CODE =:cityCode \n"
			+ "ORDER BY\n"
			+ "    topIndex,\n"
			+ "    startIndex ASC\n"
			+ "    FETCH FIRST 25 ROWS ONLY "
			, nativeQuery = true)
	List<address_Response> getAddress(String address,String s ,String cityCode);
	@Query(value = "SELECT DISTINCT\n" +
			"    k.KEYWORD_NAME  ,k.office_code,\n" +
			"    CASE\n" +
			"        WHEN INSTR(k.keyword_name, :address) = 0 THEN 100000\n" +
			"        ELSE INSTR(k.keyword_name, :address)\n" +
			"    END AS topIndex ,\n" +
			"    INSTR(k.keyword_name_dic, :s) AS startIndex \n" +
			" FROM\n" +
			"    TGH_OFFICE_KEYWORD k,\n" +
			"    tgh_post_office po\n" +
			" WHERE\n" +
			"    (k.keyword_name_dic LIKE '%' || :s || '%' \n" +
			"    OR k.keyword_name LIKE '%' || :address || '%'\n" +
			"    OR k.keyword_name_dic LIKE '%' || :address || '%' \n" +
			"    OR k.keyword_name LIKE '%' || :s || '%')\n" +
			"    AND k.office_code = po.office_code\n" +
			"    AND po.CITY_CODE =:cityCode \n" +
			"                and :numRange between k.range_start and k.range_end\n" +
			" ORDER BY\n" +
			"    topIndex,\n" +
			"    startIndex ASC\n" +
			"                FETCH FIRST 25 ROWS ONLY \n"
			, nativeQuery = true)
	List<address_Response> getAddressWithRange(String address,String s ,String cityCode,int numRange);
	
}
