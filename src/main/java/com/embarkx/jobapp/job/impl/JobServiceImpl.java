package com.embarkx.jobapp.job.impl;

import com.embarkx.jobapp.job.Job;
import com.embarkx.jobapp.job.JobService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    private List<Job> jobs = new ArrayList<>();
    private Long id = 0L;

    @Override
    public List<Job> findAll() {
        return jobs;
    }

    @Override
    public void createJob(Job job) {
        id++;
        job.setId(id);
        jobs.add(job);
    }

    @Override
    public Job getJobByID(Long Id)
    {
        return  jobs.stream().
                filter(job -> job.getId().equals(Id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean deleteJobById(Long Id) {

        for(int iJobId = 0; iJobId < jobs.size(); iJobId++) {

            Job job = jobs.get(iJobId);
            if (job.getId().equals(Id)) {
                jobs.remove(iJobId);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {

        for(int iJobId = 0; iJobId < jobs.size(); iJobId++) {

            Job job = jobs.get(iJobId);
            if (job.getId().equals(id)) {

                job.setTitle(updatedJob.getTitle());
                job.setDescription(updatedJob.getDescription());
                job.setMinSalary(updatedJob.getMinSalary());
                job.setMaxSalary(updatedJob.getMaxSalary());
                job.setLocation(updatedJob.getLocation());

                return true;
            }
        }

        return false;
    }


}
