package com.persistent.matrikas.repository;

import com.persistent.matrikas.entity.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepo extends JpaRepository<Jobs, Long> {

}
