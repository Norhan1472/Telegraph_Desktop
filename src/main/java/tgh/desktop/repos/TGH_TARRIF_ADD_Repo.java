package tgh.desktop.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tgh.desktop.models.TGH_TARRIF_ADD;

@Repository
public interface TGH_TARRIF_ADD_Repo extends JpaRepository<TGH_TARRIF_ADD,Integer>{

	
	@Query(value =
	"SELECT PLAN_CODE , ITEM_NO ,TAX_ENABLED,VALID , TEMP_ENABLED\n"
	+ "	from TGH_TARRIF_ADD where PLAN_CODE =:planCode"
	, nativeQuery = true)

public List<TGH_TARRIF_ADD> findTarrifAdd(String planCode);
}
