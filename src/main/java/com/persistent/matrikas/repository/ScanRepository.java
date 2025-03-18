package com.persistent.matrikas.repository;

import com.persistent.matrikas.entity.Scan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScanRepository  extends JpaRepository<Scan, Long> {
    List<Scan> findByTag_Id(Long tagId);
}
