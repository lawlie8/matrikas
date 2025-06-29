package com.persistent.matrikas.controller;
import com.persistent.matrikas.entity.Scan;
import com.persistent.matrikas.service.ScanDTO;
import com.persistent.matrikas.service.ScanService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/scans")
@CrossOrigin(originPatterns = "*")
public class ScanController {

    @Autowired
    private ScanService scanService;

    @PostMapping
    public Scan createScan(@RequestBody Scan scan) {
        return scanService.createScan(scan);
    }

    @PostMapping("/new/{tagId}")
    public ResponseEntity<String> scanTag(@PathVariable Long tagId) {
        try {
//            scanService.createDockerAndScan(tagId);
            return ResponseEntity.ok("Scan triggered successfully for tag ID: " + tagId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error during scan: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Scan getScanById(@PathVariable Long id) throws Exception {
        return scanService.getScanById(id);
    }

    @GetMapping
    public List<Scan> getAllScans() {


        return scanService.getAllScans();
    }

    @GetMapping("/by-tag/{tagId}")
    public List<Scan> getScansByTagId(@PathVariable Long tagId) {
        return scanService.getScansByTagId(tagId);
    }

    @GetMapping("/by-tag/sidecar/{tagId}/{sideCarId}")
    public List<Scan> getScansByTagId(@PathVariable Long tagId,@PathVariable Long sideCarId) {
       return scanService.getScansByTagIdAndSideCarID(tagId,sideCarId);
    }

    @PutMapping("/{id}")
    public Scan updateScan(@PathVariable Long id, @RequestBody Scan scan) throws Exception {
        return scanService.updateScan(id, scan);
    }

    @PostMapping("/fixedcves")
    public ResponseEntity<?> getFixedCVEs(@RequestBody CVERequest request) {
        // Log the incoming data to verify the parameters are mapped correctly
        System.out.println("here");
        System.out.println("image name is - " + request.getImageName());
        System.out.println("old tag is - " + request.getOldTag());
        System.out.println("new tag is - " + request.getNewTag());

        // Use the request parameters to fetch the fixed CVEs
        List<ScanDTO> fixedcvelist = scanService.getFixedCVEs(
                request.getImageName(), request.getOldTag(), request.getNewTag(),request.getSideCarId()
        );

        // Return the response
        return ResponseEntity.ok(fixedcvelist);
    }

    @DeleteMapping("/{id}")
    public void deleteScan(@PathVariable Long id) throws Exception {
        scanService.deleteScan(id);
    }
}


