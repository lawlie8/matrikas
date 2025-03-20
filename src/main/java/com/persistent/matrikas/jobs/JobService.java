package com.persistent.matrikas.jobs;

import com.persistent.matrikas.entity.Jobs;
import com.persistent.matrikas.repository.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JobService {

    @Autowired
    private JobRepo jobRepo;

    @Async
    public void startJobWithId(Long id) {
        //Start Scan Code Here
    }

    public boolean createNewJob(JobCreationDTO jobCreationDTO) {
        try {
            Jobs jobs = new Jobs();
            jobs.setJobName(jobCreationDTO.getJobName());
            jobs.setStatus("Un-Triggered");
            jobs.setCreationDate(new Date());
            jobs.setImageName(jobCreationDTO.getImageName());
            jobs.setImageVersion(jobCreationDTO.getImageVersion());
            jobRepo.save(jobs);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
