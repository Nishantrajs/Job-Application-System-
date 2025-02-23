package com.embarkx.jobapp.job;

import com.embarkx.jobapp.company.Company;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JobController
{
    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<Job>>findAll() {
        return ResponseEntity.ok(jobService.findAll());
    }

    @PostMapping("/jobs")
    public ResponseEntity<String> createJob(@RequestBody Job job) {
        try {
            jobService.createJob(job);
            return new ResponseEntity<>("Job added successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception
            return new ResponseEntity<>("Error occurred while adding job: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/jobs/{Id}")
    public ResponseEntity<Job> getJobByID(@PathVariable Long Id){
        Job job =  jobService.getJobByID(Id);
        if(job!=null)
            return new ResponseEntity<>(job, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/jobs/{Id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long Id)
    {
       boolean deletedMsg =  jobService.deleteJobById(Id);

       if(deletedMsg)
           return new ResponseEntity<>("JOB DELETED SUCCESSFULLY !!", HttpStatus.OK);

           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/jobs/{Id}")
    public ResponseEntity<String> updateJob(@PathVariable  Long Id, @RequestBody Job updatedJob)
    {
        boolean updated = jobService.updateJob(Id, updatedJob);

        if(updated)
            return new ResponseEntity<>("JOB UPDATED SUCCESSFULLY !!", HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
