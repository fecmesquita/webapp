package br.org.cip.CRMMock.config;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.persistence.NoResultException;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.org.cip.CRMMock.dao.FeriadoDao;
import br.org.cip.CRMMock.dao.FeriadoDaoImpl;
import br.org.cip.CRMMock.dao.ThemeDao;
import br.org.cip.CRMMock.dao.UserDao;
import br.org.cip.CRMMock.dao.UserDaoImpl;
import br.org.cip.CRMMock.model.User;
import br.org.cip.CRMMock.model.theme.Config;
import br.org.cip.CRMMock.model.theme.ThemeType;

@Component
public class StartupCode {

	@Autowired
	private ThemeDao themeDao;
	
	@EventListener(ContextRefreshedEvent.class)
	public void contextRefreshedEvent() {

		@SuppressWarnings("unused")
		Server server = null;
		try {
			server = Server.createTcpServer("-tcpAllowOthers").start();
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/webappdb", "sa", "");
			System.out.println(
					"Connection Established: " + conn.getMetaData().getDatabaseProductName() + "/" + conn.getCatalog());

			

		} catch (Exception e) {
			e.printStackTrace();
		}

		UserDao userDao = new UserDaoImpl();
		User user = new User();
		user.setId(1L);
		user.setUsername("admin");
		user.setPassword("admin");
		userDao.save(user);
		
		//Theme.theme = ThemeType.CRM;
//		Theme theme = themeDao.getTheme();
//		Theme.setThemeType(theme.getThemeType());
		
		try {
			
			Config.getInstance().setThemeType(themeDao.getTheme().getThemeType());
		} catch (NoResultException e) {
			Config.getInstance().setThemeType(ThemeType.CRM);
			themeDao.update(Config.getInstance());
		}
		
		
	
	}
}
