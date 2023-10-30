package tgh.desktop.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tgh.desktop.models.ADD_DATA_DICTIONARY;

@Repository
public interface ADD_DATA_DICTIONARY_Repo  extends JpaRepository<ADD_DATA_DICTIONARY , Integer>{

	
}
