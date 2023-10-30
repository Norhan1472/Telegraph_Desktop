package tgh.desktop.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tgh.desktop.models.TGH_TELEGRAPH;


@Repository
public interface IncomingRepo extends JpaRepository<TGH_TELEGRAPH , Integer>{
    @Query(value = "select count(tgh_id)from TGH_TELEGRAPH where status_code = 2 and OFFICE_CODE= :currentOffice",nativeQuery = true)
    Integer getCountStatusWaiting(String currentOffice);

    
}
