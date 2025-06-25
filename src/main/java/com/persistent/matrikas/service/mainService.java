package com.persistent.matrikas.service;

import com.persistent.matrikas.DTOs.ScanResult;
import com.persistent.matrikas.dockerscan;
import com.persistent.matrikas.entity.Library;
import com.persistent.matrikas.entity.Scan;
import com.persistent.matrikas.entity.Tags;
import com.persistent.matrikas.repository.ScanRepository;
import com.persistent.matrikas.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class mainService {

    @Autowired
    private dockerscan d;

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private ScanRepository scanRepository;

    public void scanImage(Library library, Tags tag) {
        String fullImage = "";
        if (library.isSideCar()) {
            //Full Image Name is suffixed with sideCar /
            for (int i = 0; i < library.getSideCarsList().size(); i++) {
                fullImage = library.getSource() + "/" + library.getSideCarsList().get(i).getSideCarName() + ":" + tag.getVersion();
                scanImage(fullImage, tag);
            }
        } else {
            fullImage = library.getSource() + ":" + tag.getVersion();
            scanImage(fullImage, tag);
        }


    }

    public ScanResult scanImage(String fullImage, Tags tag) {

        System.out.println("Scanning: " + fullImage);

        com.persistent.matrikas.Pair<List<String>, List<String>> result = d.scanImage(fullImage);

        if (!result.getFirst().isEmpty()) {
            List<String> CVEList = result.getFirst();
            List<String> Severity = result.getSecond();

            Scan scan = new Scan();
            Tags imageTag = tag;
            scan.setTag(imageTag);
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
            return new ScanResult(CVEList, Severity);
        } else {
            System.out.println("No vulnerabilities found.");
            return null;
        }

    }
}
