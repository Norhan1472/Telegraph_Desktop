package tgh.desktop.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import tgh.desktop.repos.CalculateMSG_DAO;

@Service
public class getTGHcost {
	@Autowired
	CalculateMSG_DAO calcMSGdao;
	
	public Integer getMessageCost(String planCode,
			 Integer template,
			 Integer delivery, 
			 Integer urgent ,
			 Integer decoration ,
			 String message, 
			 String sender,
			 String rec,
			 String postalOffice) {
		return calcMSGdao.getCost(planCode, template, delivery, urgent, decoration, message, sender, rec, postalOffice) ;
	}
	
	
	public Integer countWord(String MSG,Integer maxWordCount,Integer noOfWords) {
		return calcMSGdao.countOfword(MSG ,maxWordCount,noOfWords);
	}
	public Integer totalWord(String MSG,Integer maxWordCount,Integer numOfWords) {
		return calcMSGdao.totalWord(MSG ,maxWordCount,numOfWords);
	}
}
