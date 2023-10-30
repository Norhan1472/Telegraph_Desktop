package tgh.desktop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import tgh.desktop.payload.response.DetailsResponse;
import tgh.desktop.repos.Queue_Repo;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

@Service
public class LockService {
    Map<String,Long> rowSCN = new HashMap();

    Map<Integer,Integer> rowSCN2 = new HashMap();
    Map<Integer, Date>lockData = new HashMap<>();
    @Autowired
    Queue_Repo queue_Repo;

    public Map<String,Long> startUpdating(List<String>tghIds){
        for(String id:tghIds){
            System.out.println("here");
            System.out.println(queue_Repo.startUpdating(id));
            rowSCN.put(id,queue_Repo.startUpdating(id));
        }
        return rowSCN;

    }

    public Map<Integer,Integer> startUpdatingRow(Integer tghIds,Integer rowscn,Date lastLDate){
        System.out.println("rowscn");
        rowSCN2.put(tghIds,rowscn);
        System.out.println(tghIds);

        System.out.println(rowscn);
        lockData.put(tghIds,lastLDate);
        return rowSCN2;

    }
    public boolean checkLock(String tghId){
        System.out.println(tghId);
        System.out.println(rowSCN2);
        Long start2 = null;
            Integer start = rowSCN2.get(Integer.valueOf(tghId));
            System.out.println("here987");
            System.out.println(start);
             start2 = Long.valueOf(start);

        Long lastModify =  queue_Repo.startUpdating(tghId);
        System.out.println("here");
        System.out.println(lastModify);
        DetailsResponse details = queue_Repo.getTGHDetails(Integer.valueOf(tghId));

        return !start2.equals(lastModify);
    }
}
