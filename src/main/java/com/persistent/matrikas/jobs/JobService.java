package com.persistent.matrikas.jobs;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    @Async("threadPoolTaskExecutor")
    public void startJobWithId(Long id){
        //Start Scan Code Here
    }

}
