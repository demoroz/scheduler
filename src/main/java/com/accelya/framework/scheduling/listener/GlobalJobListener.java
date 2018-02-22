package com.accelya.framework.scheduling.listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GlobalJobListener implements JobListener {

	@Override
	public String getName() {
		return "GlobalJobListener";
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		log.debug("Listener of job " + context.getJobDetail().getKey() + " executed");
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		log.debug("Job " + context.getJobDetail().getKey() + " execution vetoed");
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		log.debug("Job " + context.getJobDetail().getKey() + " was executed");
	}

}
