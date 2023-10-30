package tgh.desktop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tgh.desktop.models.Template_Entity;
import tgh.desktop.repos.TEMPLATE_reposatiry;

@Service
public class Template_Service {
	@Autowired
	private TEMPLATE_reposatiry repo;	
	
	public List<Template_Entity> gettemp() {
		
		return repo.findAll(); 
		
	}	
}
