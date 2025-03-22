package com.persistent.matrikas.controller;

import com.persistent.matrikas.dockerscan;
//import org.hibernate.boot.archive.scan.spi.ScanResult;
import com.persistent.matrikas.entity.Scan;
import com.persistent.matrikas.entity.Tags;
import com.persistent.matrikas.repository.ScanRepository;
import com.persistent.matrikas.repository.TagsRepository;

import java.time.LocalDate;
import java.util.*;

import com.persistent.matrikas.service.mainService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.persistent.matrikas.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scan1")
public class mainController {

    @Autowired
    private mainService mainService;

//    @PostMapping("")
//    public ResponseEntity<?> scanImage(@RequestBody ScanRequest request) {
//        try {
//            mainService.ScanResult result = mainService.scanImage(request.getImageName(), request.getTag());
//            return ResponseEntity.ok(result);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Failed to scan image: " + e.getMessage());
//        }
//    }

    @Setter
    @Getter
    public static class ScanRequest {
        private String imageName;
        private String tag;

    }
}
