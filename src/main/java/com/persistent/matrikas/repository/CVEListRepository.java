package com.persistent.matrikas.repository;

import com.persistent.matrikas.entity.CVEList;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface CVEListRepository  extends JpaRepository<CVEList, Long> {
    List<CVEList> findByScan_Id(Long scanId);
}
