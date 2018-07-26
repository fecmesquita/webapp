package br.org.cip.CRMMock.dao;

import br.org.cip.CRMMock.model.theme.Config;

public interface ThemeDao {

	Config update(Config theme);

	Config getTheme();
	
}
