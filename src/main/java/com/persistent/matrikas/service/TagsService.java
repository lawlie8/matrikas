package com.persistent.matrikas.service;

import com.persistent.matrikas.entity.Library;
import com.persistent.matrikas.entity.Tags;

import java.util.List;

public interface TagsService {
    Tags createTags(Tags tag);
    List<Tags> getTagsByLibraryId(Long id) throws Exception;
    List<Tags> getAllTags();
    void deleteTags(Long id) throws Exception;
}
