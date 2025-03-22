package com.persistent.matrikas.service;

import com.persistent.matrikas.entity.Library;
import com.persistent.matrikas.entity.Scan;

import java.util.List;
import java.util.Map;

public interface ScanService {
    Scan createScan(Scan scan);

    Scan getScanById(Long id) throws Exception;

    List<Scan> getAllScans();

    List<Scan> getScansByTagId(Long tagId);


    Scan updateScan(Long scanId, Scan scan) throws Exception;
//    public void createDockerAndScan(Long tagId) throws Exception;
    void deleteScan(Long id) throws Exception;

    List<Map<String, String>> getFixedCVEs(String imageName, String oldTag, String newTag);
}
