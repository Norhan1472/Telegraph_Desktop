package tgh.desktop.repos;

import javax.persistence.EntityManager;

import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Repository;

import tgh.desktop.services.CalCostInterface;

@Repository
public class getTGHCost implements CalCostInterface{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public double calcCost(String planCode, Integer template, Integer delivery, Integer urgent,
			Integer decoration,String message, String sender, String rec, String postalOffice) {
		
		StoredProcedureQuery procedureQuery =
				entityManager.createNamedStoredProcedureQuery("CALCULATE_MSG_COST_PROC");
		
		procedureQuery.registerStoredProcedureParameter("TAR_PLAN", String.class, ParameterMode.IN);
        procedureQuery.registerStoredProcedureParameter("TEMP", Integer.class, ParameterMode.IN);
        procedureQuery.registerStoredProcedureParameter("DELIVERY", Integer.class, ParameterMode.IN);
        procedureQuery.registerStoredProcedureParameter("URG", Integer.class, ParameterMode.IN);
        procedureQuery.registerStoredProcedureParameter("DECOR", Integer.class, ParameterMode.IN);
        procedureQuery.registerStoredProcedureParameter("MSG", String.class, ParameterMode.IN);
        procedureQuery.registerStoredProcedureParameter("SENDER", String.class, ParameterMode.IN);
        procedureQuery.registerStoredProcedureParameter("REC", String.class, ParameterMode.IN);
        procedureQuery.registerStoredProcedureParameter("PO", String.class, ParameterMode.IN);
        
        procedureQuery.setParameter("TAR_PLAN", planCode);
        procedureQuery.setParameter("TEMP", template);
        procedureQuery.setParameter("DELIVERY", delivery);
        procedureQuery.setParameter("URG", urgent);
        procedureQuery.setParameter("DECOR", decoration);
        procedureQuery.setParameter("MSG", message);
        procedureQuery.setParameter("SENDER", sender);
        procedureQuery.setParameter("REC", rec);
        procedureQuery.setParameter("PO", postalOffice);
        procedureQuery.execute();
        
        Object singleResult = procedureQuery.getSingleResult();
        System.out.println("Cost = " + singleResult);
	
	return (Double) singleResult;
	}
	


}
