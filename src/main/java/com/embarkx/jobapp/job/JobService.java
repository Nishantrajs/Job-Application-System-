package com.embarkx.jobapp.job;

import java.util.List;

public interface JobService {
    List<Job> findAll();
    void createJob(Job job);
    Job getJobByID(Long Id);
    boolean deleteJobById(Long Id);
    boolean updateJob(Long id, Job updatedJob);
}
