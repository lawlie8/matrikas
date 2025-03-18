package com.persistent.matrikas.service;

import com.persistent.matrikas.entity.CVEList;
import com.persistent.matrikas.repository.CVEListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CVEListServiceImpl implements CVEListService{
    private final CVEListRepository cveListRepository;

    @Autowired
    public CVEListServiceImpl(CVEListRepository cveListRepository) {
        this.cveListRepository = cveListRepository;
    }

    @Override
    public CVEList createCVE(CVEList cveList) {
        return cveListRepository.save(cveList);
    }

    @Override
    public CVEList getCVEById(Long id) throws Exception {
        return cveListRepository.findById(id)
                .orElseThrow(() -> new Exception("CVE not found with id: " + id));
    }

    @Override
    public List<CVEList> getAllCVEs() {
        return cveListRepository.findAll();
    }

    @Override
    public List<CVEList> getCVEsByScanId(Long scanId) {
        return cveListRepository.findByScan_Id(scanId);
    }

    @Override
    public CVEList updateCVE(Long cveId, CVEList cveList) throws Exception {
        CVEList existingCVE = getCVEById(cveId);
        existingCVE.setScanDate(cveList.getScanDate());
        existingCVE.setCveId(cveList.getCveId());
        existingCVE.setDescription(cveList.getDescription());
        existingCVE.setType(cveList.getType());
        return cveListRepository.save(existingCVE);
    }

    @Override
    public void deleteCVE(Long id) throws Exception {
        CVEList existingCVE = getCVEById(id);
        cveListRepository.delete(existingCVE);
    }
}
