package com.exem.imx.sms.scheduler;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.exem.imx.dbquery.DbSelect;
 
public class Bootstrap {
     
    public static void main (String[] args) throws Exception {
 
    	System.out.println("[USAGE] : java -jar IMX_SMS_Scheduler.jar");
    	System.out.println("[USAGE] : config file IMXScheduler.xml : DB setting, SMS USER list");
    	System.out.println("[USAGE] : config file quartz2.2.1.xml : Batch Schedule Setting");
		System.out.println("----------------------");
		System.out.println("----------------------");
        new FileSystemXmlApplicationContext("quartz2.2.1.xml");
 
    }
 
}