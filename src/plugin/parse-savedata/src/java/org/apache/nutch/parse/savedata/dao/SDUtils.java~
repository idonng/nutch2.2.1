package org.apache.nutch.parse.savedata.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.nutch.parse.savedata.dao.SDUtils;

/**
 * NUTCH存储数据库 JDBC封装
 * @author zhuerdong
 *
 */
public class SDUtils {
	Logger logger = Logger.getLogger(getClass());
	
	private Connection conn ;
	private Statement state ;
	
	private String username ;
	
	private String passwd ;
	
	private String url ;
	
	private String driver ;
	
	public SDUtils() {
		Properties pro = new Properties();
		try {
			pro.load(SDUtils.class.getClassLoader().getResourceAsStream("jdbc.properties"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error("未找到JDBC配置文件", e1);
		}      			
		username = pro.getProperty ("jdbc.username");
		passwd = pro.getProperty ("jdbc.password");
		url=pro.getProperty ("jdbc.url");
		driver=pro.getProperty ("jdbc.driverClassName");
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("未找到JDBC驱动类", e);
		}
		
	}
	
	
	public Connection getConn() {
			
		try {
			
			if(conn != null && !conn.isClosed()) {
				return conn ;
			}	
			logger.info("url:"+ url);
			logger.info("username"+ username);
			logger.info("passwd"+passwd);
			conn = DriverManager.getConnection(url, username, passwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn ;
		
		
	}
	
	
	public Statement getState() {
		
	
		
		try {
			if(state != null && !state.isClosed()) {
				return state ;
			}
			
			state = getConn().createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return state ;
	}
	
	
	public void close() {
		
		try {
			if(state != null && !state.isClosed()) {
				state.close();
			}
			
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
