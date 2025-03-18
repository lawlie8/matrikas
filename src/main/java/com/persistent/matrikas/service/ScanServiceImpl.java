package com.persistent.matrikas.service;

import com.persistent.matrikas.entity.Scan;
import com.persistent.matrikas.repository.ScanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
public class ScanServiceImpl implements ScanService{
    @Autowired
    private  final ScanRepository scanRepository;


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
