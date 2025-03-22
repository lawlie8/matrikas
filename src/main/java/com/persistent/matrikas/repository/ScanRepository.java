package com.persistent.matrikas.repository;

import com.persistent.matrikas.entity.Scan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScanRepository  extends JpaRepository<Scan, Long> {
    List<Scan> findByTag_Id(Long tagId);

    @Query("""
        SELECT s FROM Scan s
        JOIN s.tag t
        JOIN t.library l
        WHERE l.imageName = :imageName AND t.version = :tagName
        """)
    List<Scan> findByLibraryNameAndTag(@Param("imageName") String imageName, @Param("tagName") String tagName);

}
