package com.persistent.matrikas.controller;
import com.persistent.matrikas.entity.Scan;
import com.persistent.matrikas.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scans")
public class ScanController {

    @Autowired
    private ScanService scanService;

    @PostMapping
    public Scan createScan(@RequestBody Scan scan) {
        return scanService.createScan(scan);
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

    @PutMapping("/{id}")
    public Scan updateScan(@PathVariable Long id, @RequestBody Scan scan) throws Exception {
        return scanService.updateScan(id, scan);
    }

    @DeleteMapping("/{id}")
    public void deleteScan(@PathVariable Long id) throws Exception {
        scanService.deleteScan(id);
    }
}
