package com.accelya.framework.scheduling;

import com.accelya.framework.scheduling.job.ArchiverJob;
import com.accelya.framework.scheduling.job.FRINotificationJob;
import com.accelya.framework.scheduling.util.QuartzUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduling implements CommandLineRunner {

    private final SchedulerFactoryBean schedulerFactoryBean;

    @Override
    public void run(String... strings) throws Exception {

        log.debug("Scheduling started ...");

        createArchiverJob();
        createFRINotificationJob();
    }

    private void createArchiverJob() throws ParseException, SchedulerException {

        String jobName = "Archiver";
        String groupName = "General";

        if (isJobWithNameExists(groupName, jobName)) return;

        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        Date dt = scheduler
                .scheduleJob(
                        QuartzUtils
                                .jobBuilder()
                                .jobClass(ArchiverJob.class)
                                .name(jobName)
                                .description("Archiver job")
                                .group(groupName)
                                .durability(true)
                                .requestRecovery(true)
                                .build(),
                        QuartzUtils
                                .cronTriggerBuilder()
                                .name(jobName)
                                .description("Cron Trigger for " + jobName)
                                .startTime(Calendar.getInstance().getTime())
                                .cronExpression("*/5 * * * * ?")
                                .misfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW)
                                .priority(1)
                                .build());
    }

    private void createFRINotificationJob() throws SchedulerException, ParseException {

        String documentNo = String.valueOf(new Random().nextInt(9999999)  + 10000000);
        String jobName = "FRI/AWB/016-" + documentNo;
        String groupName = "RegBroker";

        if (isJobWithNameExists(groupName, jobName)) return;

        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        //SimpleScheduleBuilder.simpleSchedule().build()

        Date dt = scheduler
                .scheduleJob(
                        QuartzUtils
                                .jobBuilder()
                                .jobClass(FRINotificationJob.class)
                                .name(jobName)
                                .description("Freight Report Inbound message")
                                .group(groupName)
                                .durability(true)
                                .requestRecovery(true)
                                .addJobData("documentNumber", documentNo)
                                .addJobData("weight", 0.015f)
                                .build(),
                        QuartzUtils
                                .simpleTriggerBuilder()
                                .name(jobName)
                                .description("Simple Trigger for " + jobName)
                                .startTime(Calendar.getInstance().getTime())
                                .repeatCount(1) // 0 for infinitely
                                .repeatInterval(24L * 60L * 60L * 1000L) // every 24 Hours
                                .misfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW)
                                .build());
    }

    boolean isJobWithNameExists(String groupName, String jobName) {
        try {
            JobKey jobKey = new JobKey(jobName, groupName);
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            if (scheduler.checkExists(jobKey)){
                log.warn("Job " + jobName + " (" + groupName + ") already exists");
                return true;
            }
        } catch (SchedulerException e) {
            log.error("SchedulerException while checking job with name and group exist: " + e.getMessage());
        }
        return false;
    }
}
