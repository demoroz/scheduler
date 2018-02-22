package com.accelya.framework.scheduling.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "qrtz_job_details")
public class JobDetails {

    @EmbeddedId
    private JobDetailsId id;

    JobDetails() {
    }

    private String description;

}
