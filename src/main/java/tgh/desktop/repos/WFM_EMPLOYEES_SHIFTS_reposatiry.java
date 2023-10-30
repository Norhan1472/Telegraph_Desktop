package tgh.desktop.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tgh.desktop.models.WFM_EMPLOYEES_SHIFTS;
import tgh.desktop.payload.response.WFM_EMPLOYEES_SHIFTS_Response;

@Repository
public interface WFM_EMPLOYEES_SHIFTS_reposatiry extends JpaRepository<WFM_EMPLOYEES_SHIFTS , Integer>{ 
//
//	@Query(value = "SELECT First_Name,EMP_ID From WFM_EMPLOYEES_SHIFTS"
//			+ " where TO_CHAR (:date,'dd/MM/yyyy')  between SHIFT_START and SHIFT_END ",nativeQuery=true)
	@Query(value = "SELECT First_Name, EMP_ID\n" +
			"FROM WFM_EMPLOYEES_SHIFTS\n"
//			+
//			"WHERE SHIFT_START >= TO_DATE( :date1, 'DD/MM/YYYY')\n" +
//			"  and SHIFT_END <= TO_DATE( :date2, 'DD/MM/YYYY')  "
			,nativeQuery = true)
	List<WFM_EMPLOYEES_SHIFTS_Response> getEmployee(String date1,String date2);
}
