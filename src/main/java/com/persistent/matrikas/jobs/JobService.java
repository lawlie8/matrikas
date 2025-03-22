package com.persistent.matrikas.jobs;

import com.persistent.matrikas.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
@EnableScheduling
@Service
public class JobService {

    @Autowired
    TagsService tagsService;

    @Async("threadPoolTaskExecutor")
    public void startJobWithId(Long id){
        //Start Scan Code Here
    }
    @Scheduled(cron = "0 27 12 * * ?")
    public void callScheduleJob() {
        tagsService.fetchAndStoreGitHubTags();
    }

}
