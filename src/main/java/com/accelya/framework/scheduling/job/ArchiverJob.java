package com.accelya.framework.scheduling.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
public class ArchiverJob extends QuartzJobBean implements InterruptableJob {

    @Override
    public void interrupt() throws UnableToInterruptJobException {

    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        JobKey key = context.getJobDetail().getKey();
        log.debug("ArchiverJob started with key: " + key.getName() + ", Group: " + key.getGroup() + ", Thread Name:" + Thread.currentThread().getName());
    }
}
