package com.accelya.framework.scheduling.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
public class FRINotificationJob extends QuartzJobBean implements InterruptableJob {
	
	private volatile boolean toStopFlag = true;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		JobKey key = context.getJobDetail().getKey();
		log.debug("FRINotificationJob started with key: " + key.getName() + ", Group: " + key.getGroup() + ", Thread Name:" + Thread.currentThread().getName());

		JobDataMap dataMap = context.getMergedJobDataMap();

		log.debug("Document Number is " + dataMap.getString("documentNumber"));
		log.debug("Weight is " + dataMap.getFloat("weight"));

		//*********** For retrieving stored object, It will try to deserialize the bytes Object. ***********/
		/*
		SchedulerContext schedulerContext = null;
        try {
            schedulerContext = jobExecutionContext.getScheduler().getContext();
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
        YourClass yourClassObject = (YourClass) schedulerContext.get("storedObjectKey");
		 */

		int i = 0;
		while(i < 15){
			try {
				log.debug("FRINotificationJob sleeping at the thread: "+ Thread.currentThread().getName());
				Thread.sleep(1000);
				i++;
			} catch (InterruptedException e) {
				log.error(e.getMessage());
			}
		}

		log.debug("FRINotificationJob completed. thread: "+ Thread.currentThread().getName() +" released");
	}

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		log.debug("Execution thread was interrupted...");
		toStopFlag = false;
	}

}