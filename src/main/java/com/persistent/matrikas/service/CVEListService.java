package com.persistent.matrikas.service;

import com.persistent.matrikas.entity.CVEList;

import java.util.List;

public interface CVEListService {
    CVEList createCVE(CVEList cveList);

    CVEList getCVEById(Long id) throws Exception;

    List<CVEList> getAllCVEs();

    List<CVEList> getCVEsByScanId(Long scanId);

    CVEList updateCVE(Long cveId, CVEList cveList) throws Exception;

    void deleteCVE(Long id) throws Exception;
}
