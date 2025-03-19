package com.persistent.matrikas.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.persistent.matrikas.entity.CVEList;
import com.persistent.matrikas.entity.Library;
import com.persistent.matrikas.entity.Scan;
import com.persistent.matrikas.entity.Tags;
import com.persistent.matrikas.repository.CVEListRepository;
import com.persistent.matrikas.repository.ScanRepository;
import com.persistent.matrikas.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
@Service
public class ScanServiceImpl implements ScanService{
    @Autowired
    private  final ScanRepository scanRepository;

    @Autowired
    private TagsRepository tagsRepository;


    @Autowired
    private CVEListRepository cveListRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

//    @Override
//    public void createDockerAndScan(Long tagId) throws Exception {
//        Tags tag = tagsRepository.findById(tagId)
//                .orElseThrow(() -> new RuntimeException("Tag not found with id: " + tagId));
//
//        Library library = tag.getLibrary();
//
//        if (library == null) {
//            throw new RuntimeException("Library not found for tag id: " + tagId);
//        }
//
//        String libName = library.getImageName(); // e.g., "distribution/distribution"
//        String version = tag.getVersion(); // e.g., "v2.8.3"
//
//        // 1. Create Dockerfile
//        String dockerfileContent = """
//                FROM alpine
//                USER root
//                RUN curl -L -o %s.tar.gz https://github.com/%s/archive/refs/tags/%s.tar.gz
//                RUN tar -xzf %s.tar.gz && cd %s-%s
//                """.formatted(version, libName, version, version, libName.split("/")[1], version);
//
//        Files.write(Paths.get("Dockerfile"), dockerfileContent.getBytes());
//        System.out.println("Dockerfile created");
//
//        // 2. Build Docker image
//        String imageName = libName.replace("/", "_") + ":" + version;
//        new ProcessBuilder("docker", "build", "-t", imageName, ".")
//                .inheritIO()
//                .start()
//                .waitFor();
//
//        // 3. Run Trivy scan
//        new ProcessBuilder("trivy", "image", "--format", "json", "--output", "trivy-result.json", imageName)
//                .inheritIO()
//                .start()
//                .waitFor();
//
//        // 4. Parse Trivy output
//        String trivyOutput = new String(Files.readAllBytes(Paths.get("trivy-result.json")));
//        JsonNode rootNode = objectMapper.readTree(trivyOutput);
//
//        // 5. Save Scan details
//        Scan scan = new Scan();
//        scan.setTag(tag);
//        scan.setScanDate(LocalDate.now());
//        scan.setTotal(rootNode.get("Results").size());
//        scan.setHigh((int) rootNode.findValues("Severity").stream().filter(v -> v.asText().equals("HIGH")).count());
//        scan.setMedium((int) rootNode.findValues("Severity").stream().filter(v -> v.asText().equals("MEDIUM")).count());
//        scan.setLow((int) rootNode.findValues("Severity").stream().filter(v -> v.asText().equals("LOW")).count());
//        scan.setCritical((int) rootNode.findValues("Severity").stream().filter(v -> v.asText().equals("CRITICAL")).count());
//
//        scan = scanRepository.save(scan);
//
//        // 6. Save individual CVEs
//        for (JsonNode result : rootNode.get("Results")) {
//            for (JsonNode vulnerability : result.get("Vulnerabilities")) {
//                CVEList cve = new CVEList();
//                cve.setScan(scan);
//                cve.setScanDate(LocalDate.now());
//                cve.setCveId(vulnerability.get("VulnerabilityID").asText());
//                cve.setDescription(vulnerability.get("Description").asText());
//                cve.setType(vulnerability.get("Severity").asText());
//
//                cveListRepository.save(cve);
//            }
//        }
//
//        System.out.println("Trivy scan complete and results saved.");
//    }

    public ScanServiceImpl(ScanRepository scanRepository) {
        this.scanRepository = scanRepository;
    }

    @Override
    public Scan createScan(Scan scan) {
        return scanRepository.save(scan);
    }

    @Override
    public Scan getScanById(Long id) throws Exception {
        return scanRepository.findById(id)
                .orElseThrow(() -> new Exception("Scan not found with id: " + id));
    }

    @Override
    public List<Scan> getAllScans() {
        return scanRepository.findAll();
    }

    @Override
    public List<Scan> getScansByTagId(Long tagId) {
        return scanRepository.findByTag_Id(tagId);
    }


    @Override
    public Scan updateScan(Long scanId, Scan scan) throws Exception {
        Scan existingScan = getScanById(scanId);
        existingScan.setScanDate(scan.getScanDate());
        existingScan.setTotal(scan.getTotal());
        existingScan.setHigh(scan.getHigh());
        existingScan.setLow(scan.getLow());
        existingScan.setMedium(scan.getMedium());
        existingScan.setCritical(scan.getCritical());
        return scanRepository.save(existingScan);
    }

    @Override
    public void deleteScan(Long id) throws Exception {
        Scan existingScan = getScanById(id);
        scanRepository.delete(existingScan);
    }
}
