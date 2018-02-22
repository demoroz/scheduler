package com.accelya.framework.scheduling.repository;

import com.accelya.framework.scheduling.repository.model.JobDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<JobDetails, Long> {
}
