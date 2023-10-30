package tgh.desktop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tgh.desktop.models.WLSConfig;
import tgh.desktop.repos.SysConfigDAO;

@Service
public class SysConfigService {

	@Autowired
	SysConfigDAO dao;
	
	
	public WLSConfig getWLSConfig() {
		return dao.getWLSConfig();
	}
	
}
