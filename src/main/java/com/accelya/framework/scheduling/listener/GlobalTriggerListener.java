package com.accelya.framework.scheduling.listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GlobalTriggerListener implements TriggerListener {

    @Override
    public String getName() {
        return GlobalTriggerListener.class.getSimpleName();
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        log.debug("Trigger " + trigger.getKey() + " (" + trigger.getDescription() + ") fired");
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        boolean veto = false;
        log.debug("Trigger " + trigger.getKey() + " veto on job " + trigger.getJobKey().getName() + " execution: " + veto);
        return veto;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        log.debug("Job name: " + trigger.getJobKey().getName() + " is misfired");
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, CompletedExecutionInstruction triggerInstructionCode) {
        log.debug("Trigger " + trigger.getKey() + " (" + trigger.getDescription() + ") complete execution");
    }
}
