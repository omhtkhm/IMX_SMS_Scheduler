package com.exem.imx.test;

import static org.junit.Assert.*;
import org.junit.Test;

import com.exem.imx.util.PropertyRead;

public class PropertyReadTest2 {


	@Test
	public void testReadConfigFile() {
		PropertyRead propertyRead = PropertyRead.getInstance();
		propertyRead.readConfigFile();
		
		assertEquals("10.10.30.98", propertyRead.conn_ip);
		assertEquals("10.10.102.97", propertyRead.sms_conn_ip);
		assertEquals("select active_txns from xapm_was_stat where was_id=30101 order by time desc limit 1;", 
				propertyRead.query1);
		assertEquals("select * from  XM_SMS_TEST where rownum =1;", 
				propertyRead.sms_insert_query);
		
	}

}
