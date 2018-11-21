package com.exem.imx.sms.scheduler;

import java.sql.SQLException;

import com.exem.imx.dbquery.DbSelect;
import com.exem.imx.smsinsert.SmsInsert;

public class IMXDBQueryTask {

	public void queryTask1() {
    	DbSelect dbSelect = new DbSelect();
    	SmsInsert smsInsert = new SmsInsert();
    	
			dbSelect.query1();
			try {
				smsInsert.query1(dbSelect.selectedMetricsArray);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
