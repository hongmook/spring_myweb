package com.khm.common;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class OracleConn {
	
	private static OracleConn my = new OracleConn();
	private Connection conn;
	
	private OracleConn() {
		oracleConn();
	}
	
	public static OracleConn getInstance() {
		return my;
	}
	
	public void oracleConn() {
		
		Properties pro = new Properties();
		String path = OracleConn.class.getResource("database.properties").getPath();
		
		try {
			path = URLDecoder.decode(path, "utf-8");
			pro.load(new FileReader(path));
			
			String driver = pro.getProperty("driver");
			String url = pro.getProperty("url");
			String id = pro.getProperty("id");
			String pw = pro.getProperty("pw");
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url,id,pw);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	
	}
	
	public Connection getConn(){
		return conn;
	}
	
	
}
