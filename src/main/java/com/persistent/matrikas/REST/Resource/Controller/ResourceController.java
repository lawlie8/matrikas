package com.persistent.matrikas.REST.Resource.Controller;

import com.persistent.matrikas.DTO.ResourceDTO;
import com.persistent.matrikas.REST.Resource.Service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/app")
public class ResourceController {

    private static final Logger log = LoggerFactory.getLogger(ResourceController.class);
    @Autowired
    private ResourceService resourceService;


    @RequestMapping(path = "/version", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVersion() {
        return  ResponseEntity.ok("0.1");
    }

    @RequestMapping(path = "/resource/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDailyRssBooks() {
        try {
            log.info("Rest Call to Fetch All Resources From Database");
            List<ResourceDTO> resourceDTOList = resourceService.getAllResource();
            return ResponseEntity.ok(resourceDTOList);
        } catch (Exception e) {
            log.error("Exception Occurred While Fetching All Resource from Database Exception is : ", e.toString());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
