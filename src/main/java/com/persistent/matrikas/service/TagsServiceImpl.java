package com.persistent.matrikas.service;

import com.persistent.matrikas.entity.Library;
import com.persistent.matrikas.entity.Tags;
import com.persistent.matrikas.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagsServiceImpl implements TagsService{

    @Autowired
    TagsRepository tagsRepository;

    @Override
    public Tags createTags(Tags tag) {
        return tagsRepository.save(tag);
    }

    @Override
    public List<Tags> getTagsByLibraryId(Long id) throws Exception {
        return tagsRepository.findByLibraryId(id);
    }

    @Override
    public List<Tags> getAllTags() {
        return List.of();
    }

    @Override
    public void deleteTags(Long id) throws Exception {

    }
}
