package tgh.desktop.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tgh.desktop.models.TGH_SENDER;

@Repository

public interface SENDER_Repo extends JpaRepository<TGH_SENDER, Integer>{

	
}
