package br.org.cip.CRMMock.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class StartH2Console implements Runnable{

	@Override
	public void run() {
		Connection conn;
		try {
			conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/webappdb", "sa", "");
			org.h2.tools.Server.startWebServer(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
}
