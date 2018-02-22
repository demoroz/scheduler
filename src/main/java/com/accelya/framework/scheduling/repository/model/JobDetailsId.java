package com.accelya.framework.scheduling.repository.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@ToString
@EqualsAndHashCode
@Embeddable
public class JobDetailsId implements Serializable {

    @Column(name = "sched_name")
    private String schedulerName;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "job_group")
    private String jobGroup;

    public JobDetailsId() {
    }

    public JobDetailsId(String schedulerName, String jobName, String jobGroup) {
        this.schedulerName = schedulerName;
        this.jobName = jobName;
        this.jobGroup = jobGroup;
    }
}
