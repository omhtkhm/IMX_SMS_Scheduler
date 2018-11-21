package com.exem.imx.sms.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class DBJobBean extends QuartzJobBean {
    private IMXDBQueryTask dbTask;
    
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {
 
			dbTask.queryTask1();
 
    }
 
    public void setDbTask(IMXDBQueryTask dbTask) {
        this.dbTask = dbTask;
    }
}
