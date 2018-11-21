package com.exem.imx.test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.exem.imx.smsinsert.SmsInsert;
import com.exem.imx.util.PropertyRead;

public class SmsInsertTest {
	
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @Before
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private String getOutput() {
        return testOut.toString();
    }

    @After
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

	@Test
	public void testQuery1() {
		
		SmsInsert smsInsert = new SmsInsert();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("user_name", "홍길동");
		map.put("phone_number", "11112222");
		
//		try {
//			smsInsert.query1(map);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		final String testString = "오명훈";
        provideInput(testString);
        
		assertEquals(testString, getOutput());
	}

}
