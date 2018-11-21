package com.exem.imx.dbquery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.exem.imx.util.PropertyRead;

public class DbSelect {

	Connection db;
	String ip, port, sid, user, password;
	String query1;
	PropertyRead prop = PropertyRead.getInstance();
	public String sms_message;

	public ArrayList<HashMap<String, String>> selectedMetricsArray;

	public DbSelect() {
		ip = prop.conn_ip;
		port = prop.conn_port;
		sid = prop.sid;
		user = prop.user;
		password = prop.password;
		query1 = prop.query1;
		
		selectedMetricsArray = new ArrayList<HashMap<String, String>>();
	}

	public void query1() {

		String url = "jdbc:postgresql://" + ip + ":" + port + "/" + sid;
		String usr = user;
		String pwd = password;
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			db = DriverManager.getConnection(url, usr, pwd);
			doexample();
			db.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void doexample() {
		try {
			PreparedStatement ps = db.prepareStatement(query1);
			ResultSet rs;
		
			rs = ps.executeQuery();
			
			if (rs != null) {
				
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				System.out.println("[sms message]");
				
				while (rs.next()) {
					// 여러컬럼을 입력 받도록 수정 + 여러행을 입력 받도록 수정
					HashMap selectedMetrics = new HashMap<>();
					
					for (int i = 1; i <= columnCount; i++) {
						selectedMetrics.put(rsmd.getColumnName(i), rs.getString(rsmd.getColumnName(i)));
						System.out.print("[" + rsmd.getColumnName(i) + " : " + rs.getString(rsmd.getColumnName(i)) + "] , ");
					}
					System.out.println("");
					selectedMetricsArray.add(selectedMetrics);
				}
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
