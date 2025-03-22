package com.persistent.matrikas.jobs;

import com.persistent.matrikas.entity.Jobs;
import com.persistent.matrikas.entity.Library;
import com.persistent.matrikas.entity.Tags;
import com.persistent.matrikas.repository.JobRepo;
import com.persistent.matrikas.service.LibraryService;
import com.persistent.matrikas.service.TagsService;
import com.persistent.matrikas.service.TagsServiceImpl;
import com.persistent.matrikas.service.mainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@EnableScheduling
@Service
public class JobService {

    @Autowired
    TagsService tagsService;

    @Autowired
    LibraryService libservice;

    @Autowired
    JobRepo jobRepo;

    @Autowired
    TagsServiceImpl tagsServiceimpl;

    @Autowired
    mainService m;
    @Async()
    public void startJobWithId(Long id) throws Exception {
        Optional<Jobs> jobs = jobRepo.findById(id);
        jobs.get().setStatus("Running");
        jobRepo.save(jobs.get());
        //Start Scan Code Here
        String imageName = libservice.getLibraryById( Long.parseLong(jobs.get().getImageName()) ).getSource();
        Tags tags = tagsServiceimpl.getTagById(Long.parseLong(jobs.get().getImageVersion()));
        m.scanImage(imageName, tags);
    }
    @Scheduled(cron = "0 27 12 * * ?")
    public void callScheduleJob() {
        tagsService.fetchAndStoreGitHubTags();
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

    public List<Jobs> getAllJobs(){
        return jobRepo.findAll();
    }

}
