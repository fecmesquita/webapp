package br.org.cip.CRMMock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cip.CRMMock.dao.ThemeDao;
import br.org.cip.CRMMock.model.theme.Config;

@Service
public class ThemeService {

	@Autowired
	private ThemeDao themeDao;
	
	public Config update(Config theme) {
		return themeDao.update(theme);
	}
}
