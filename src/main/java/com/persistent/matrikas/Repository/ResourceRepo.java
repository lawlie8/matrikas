package com.persistent.matrikas.Repository;

import com.persistent.matrikas.Entity.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepo extends JpaRepository<ResourceEntity, Long> {


}
