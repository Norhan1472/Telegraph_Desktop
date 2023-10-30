package tgh.desktop.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tgh.desktop.models.SC_USER_MODULE;

public interface SC_USER_MODULE_REPO extends JpaRepository<SC_USER_MODULE, String> {
	
	@Query(value="select USER_ID,MODULE_ID,"
			+ "MODULE_NAME,MODULE_NAME_AR,PATH from MTS_SECURITY.SC_USER_MODULE"
			+ " where lower(USER_NAME)=lower(:USER_NAME)", nativeQuery = true)
	public List getUserModulesByUSER_NAME(String USER_NAME);

}
