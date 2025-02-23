package com.embarkx.jobapp.job.impl;

import com.embarkx.jobapp.job.Job;
import com.embarkx.jobapp.job.JobRepository;
import com.embarkx.jobapp.job.JobService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    JobRepository jobRepository;

    private Long id = 1L;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public void createJob(Job job) {
        job.setId(id++);
        jobRepository.save(job);
    }

    @Override
    public Job getJobByID(Long Id)
    {
        return  jobRepository.findAll().stream().
                filter(job -> job.getId().equals(Id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean deleteJobById(Long Id) {

        try {
            jobRepository.deleteById(Id);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {

        Optional<Job> jobOptional = jobRepository.findById(id);

            if(jobOptional.isPresent()) {

                Job job = jobOptional.get();

                if (job.getId().equals(id)) {

                    job.setTitle(updatedJob.getTitle());
                    job.setDescription(updatedJob.getDescription());
                    job.setMinSalary(updatedJob.getMinSalary());
                    job.setMaxSalary(updatedJob.getMaxSalary());
                    job.setLocation(updatedJob.getLocation());

                    jobRepository.save(job);

                    return true;
                }
            }

        return false;
    }


}
