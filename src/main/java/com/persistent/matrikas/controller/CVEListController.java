package com.persistent.matrikas.controller;
import com.persistent.matrikas.entity.CVEList;
import com.persistent.matrikas.service.CVEListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/cves")
public class CVEListController {

    @Autowired
    private CVEListService cveListService;

    @PostMapping
    public CVEList createCVE(@RequestBody CVEList cveList) {
        return cveListService.createCVE(cveList);
    }

    @GetMapping("/{id}")
    public CVEList getCVEById(@PathVariable Long id) throws Exception {
        return cveListService.getCVEById(id);
    }

    @GetMapping
    public List<CVEList> getAllCVEs() {
        return cveListService.getAllCVEs();
    }

    @GetMapping("/by-scan/{scanId}")
    public List<CVEList> getCVEsByScanId(@PathVariable Long scanId) {
        return cveListService.getCVEsByScanId(scanId);
    }

    @PutMapping("/{id}")
    public CVEList updateCVE(@PathVariable Long id, @RequestBody CVEList cveList) throws Exception {
        return cveListService.updateCVE(id, cveList);
    }

    @DeleteMapping("/{id}")
    public void deleteCVE(@PathVariable Long id) throws Exception {
        cveListService.deleteCVE(id);
    }
}
