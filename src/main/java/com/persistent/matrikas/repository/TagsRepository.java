package com.persistent.matrikas.repository;

import com.persistent.matrikas.entity.Library;
import com.persistent.matrikas.entity.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagsRepository extends JpaRepository<Tags, Long>  {
    List<Tags> findByLibraryId(Long libraryId);
    boolean existsByLibraryAndVersion(Library library, String version);


}
