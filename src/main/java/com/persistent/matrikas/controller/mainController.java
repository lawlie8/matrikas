package com.persistent.matrikas.controller;

import com.persistent.matrikas.dockerscan;
//import org.hibernate.boot.archive.scan.spi.ScanResult;
import com.persistent.matrikas.entity.Scan;
import com.persistent.matrikas.entity.Tags;
import com.persistent.matrikas.repository.ScanRepository;
import com.persistent.matrikas.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;


 class ScanResult {
    private List<String> cveList;
    private List<String> severityList;

    public ScanResult(List<String> cveList, List<String> severityList) {
        this.cveList = cveList;
        this.severityList = severityList;
    }

    public List<String> getCveList() {
        return cveList;
    }

    public List<String> getSeverityList() {
        return severityList;
    }
}



@RestController
@RequestMapping("/api/scan1")
public class mainController {

     @Autowired
    dockerscan d;
     @Autowired
    TagsRepository tagsRepository;

    @Autowired
    ScanRepository scanRepository;

    public void getScan(String imageName, String tag) {
        d.scanImage(imageName + ":" + tag);
    }


    public  mainController(dockerscan d) {
        this.d = d;
    }

    @PostMapping("")
    public ResponseEntity<?> scanImage(@RequestBody ScanRequest request) {
        try {
            String fullImage = request.getImageName() + ":" + request.getTag();

            System.out.println("Scanning: " + fullImage);

            com.persistent.matrikas.Pair<List<String>, List<String>> result = d.scanImage(fullImage);

            if (!result.getFirst().isEmpty()) {
                List<String> CVEList = result.getFirst();
                List<String> Severity = result.getSecond();

                Scan scan = new Scan();

                Tags tag = tagsRepository.findById(1L).orElseThrow(() -> new RuntimeException("Tag not found with id: " + request.getTag()));
                scan.setTag(tag);
                scan.setScanDate(LocalDate.now());

// Create separate lists for each severity type
                List<String> highCves = new ArrayList<>();
                List<String> mediumCves = new ArrayList<>();
                List<String> lowCves = new ArrayList<>();
                List<String> criticalCves = new ArrayList<>();

// Map CVEs based on severity
                for (int i = 0; i < CVEList.size(); i++) {
                    String cve = CVEList.get(i);
                    String severity = Severity.get(i);

                    switch (severity.toUpperCase()) {
                        case "HIGH":
                            highCves.add(cve);
                            break;
                        case "MEDIUM":
                            mediumCves.add(cve);
                            break;
                        case "LOW":
                            lowCves.add(cve);
                            break;
                        case "CRITICAL":
                            criticalCves.add(cve);
                            break;
                    }
                }

// Save as comma-separated strings
                scan.setHigh(String.join(",", highCves));
                scan.setMedium(String.join(",", mediumCves));
                scan.setLow(String.join(",", lowCves));
                scan.setCritical(String.join(",", criticalCves));

// Save total CVEs count
                scan.setTotal(CVEList.size());

                scanRepository.save(scan);
                return ResponseEntity.ok(new ScanResult(result.getFirst(), result.getSecond()));
            } else {
                return ResponseEntity.ok("No vulnerabilities found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to scan image: " + e.getMessage());
        }
    }

    public static class ScanRequest {
        private String imageName;
        private String tag;

        public String getImageName() {
            return imageName;
        }

        public void setImageName(String imageName) {
            this.imageName = imageName;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }
    }
}
