package com.exem.imx.smsinsert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.exem.imx.util.PropertyRead;

public class SmsInsert {

	Connection db;
	PreparedStatement ps;

	String ip, port, sid, user, password;
	String query1;
	PropertyRead prop = PropertyRead.getInstance();
	ArrayList userList;
	HashMap<String, String> bindVariablesMap;

	public SmsInsert() {
		ip = prop.sms_conn_ip;
		port = prop.sms_conn_port;
		sid = prop.sms_sid;
		user = prop.sms_user;
		password = prop.sms_password;
		query1 = prop.sms_insert_query;
		userList = prop.userList;
		bindVariablesMap = prop.bindVariablesMap;
	}

	public void query1(ArrayList<HashMap<String, String>> selectedMetricsArray)
			throws ClassNotFoundException, SQLException {

		String url = "jdbc:oracle:thin:@" + ip + ":" + port + ":" + sid;
		String usr = user;
		String pwd = password;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		db = DriverManager.getConnection(url, usr, pwd);
		ps = db.prepareStatement(query1);

		make_InsertQuery(selectedMetricsArray); // 바인드변수와 수집한 지표를 매핑시켜서 sql문장을
												// 만들어 낸다.
		ps.close();
		db.close();
	}

	private void make_InsertQuery(ArrayList<HashMap<String, String>> selectedMetricsArray) {
		// p$컬럼명$을 기준으로 바인드변수를 찾는다. 예) p$active_txns$
		// selectedMetrics : ( active_txns , 1 ) ( txn_end_count , 5 )
		// bindVariablesMap : ( b1 , p$active_txns$ ) ( b2 , p$txn_end_count$ )

		for (HashMap selectedMetrics : selectedMetricsArray) {
			for (Map.Entry<String, String> elem : bindVariablesMap.entrySet()) {
				String strBind = elem.getKey(); // b1, b2, b3
				int b = Integer.valueOf(strBind.substring(1)); // 1, 2, 3, 4
				String strColumn = elem.getValue(); // p$active_txns$ ,
													// p$txn_end_count$
				String strColumn2 = strColumn.substring(2, strColumn.length() - 1); // active_txn
				String columnValue = (String) selectedMetrics.get(strColumn2); // 조회한 컬럼(자표)의 값

				// System.out.println( "bind : " + b + " , Value : " + columnValue );

				try {
					ps.setString(b, columnValue);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			int ret=0;
			try {
				ret = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("result: (" + ret + ")");
		}
	}
}