package tgh.desktop.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tgh.desktop.models.TGH_RECEPIENT;

@Repository
public interface RECEPIENT_Repo extends JpaRepository<TGH_RECEPIENT, Integer> {

}
