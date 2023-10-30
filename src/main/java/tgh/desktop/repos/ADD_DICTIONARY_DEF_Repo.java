package tgh.desktop.repos;

import java.util.Dictionary;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tgh.desktop.models.ADD_DICTIONARY_DEF;

@Repository
public interface ADD_DICTIONARY_DEF_Repo  extends JpaRepository<ADD_DICTIONARY_DEF , Integer>{


	@Query(value = "Select b.word ,a.group_name "
			+ "from add_data_dictionary b ,add_dictionary_def a where a.dic_id=b.dic_id"
			+ " GROUP BY b.word, a.group_name"
			,nativeQuery=true)
	List<Object[]>get_dic();
	 	
	
}
