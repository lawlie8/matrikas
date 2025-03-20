package com.persistent.matrikas.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private static final Logger log = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private JobService jobService;

    @RequestMapping(path = "/start/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVersion(@PathVariable(name = "id") Long id) {
        try {
            log.info("Rest Call to Start Job with Id : {}", id);
            jobService.startJobWithId(id);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            log.error("Exception Occurred While Starting Job :", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createJob(@RequestBody JobCreationDTO jobCreationDTO) {
        try {
            log.info("Rest Call to Save New Job with Name : {}", jobCreationDTO.getJobName());
            boolean isCreated =  jobService.createNewJob(jobCreationDTO);
            return ResponseEntity.ok(isCreated);
        } catch (Exception e) {
            log.error("Exception Occurred While Saving New Job :", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
