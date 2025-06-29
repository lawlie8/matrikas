package com.persistent.matrikas.repository;

import com.persistent.matrikas.entity.Scan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScanRepository extends JpaRepository<Scan, Long> {
    List<Scan> findByTag_Id(Long tagId);

    @Query("""
            SELECT s FROM Scan s
            JOIN s.tag t
            JOIN t.library l
            WHERE l.imageName = :imageName AND t.version = :tagName
            """)
    List<Scan> findByLibraryNameAndTag(@Param("imageName") String imageName, @Param("tagName") String tagName);

    @Query(value = """
            select * from scan where tag_id=:tagId and side_car_id=:sideCarId;
            """, nativeQuery = true)
    List<Scan> findByTagIdAndSideCarId(@Param("tagId") Long tagId, @Param("sideCarId") Long sideCarId);

    @Query(value = """
                SELECT s.*
                FROM scan s
                JOIN tags t ON s.tag_id = t.id
                JOIN library l ON t.lib_id = l.id
                WHERE l.image_name = :imageName
                AND t.version = :tagName AND s.side_car_id = :sideCarId;
                                                                                                                                                                
            """, nativeQuery = true)
    List<Scan> findByLibraryNameAndTagAndSideCar(@Param("imageName") String imageName, @Param("tagName") String tagName, @Param("sideCarId") Long sideCarId);


}
