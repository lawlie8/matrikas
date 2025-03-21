package com.persistent.matrikas.service;

import com.persistent.matrikas.entity.Tags;
import com.persistent.matrikas.entity.Library;
import java.util.List;

public interface TagsService {
    Tags createTags(Tags tag);
    Tags getTagById(Long id) throws Exception;
    List<Tags> getTagsByLibraryId(Long id) throws Exception;
    List<Tags> getAllTags();
    void deleteTags(Long id) throws Exception;
    void fetchAndStoreGitHubTags(); // Added this method
}
