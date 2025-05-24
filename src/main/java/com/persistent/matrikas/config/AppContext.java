package com.persistent.matrikas.config;


import com.persistent.matrikas.service.TagsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class AppContext implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(ApplicationListener.class);
    @Autowired
    private TagsService tagsService;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Starting Application Startup Sequence : ");

        tagsService.fetchAndStoreGitHubTags();
        //add check latest images from all sources here
    }
}
