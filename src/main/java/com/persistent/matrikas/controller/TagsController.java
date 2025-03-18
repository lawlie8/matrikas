package com.persistent.matrikas.controller;
import com.persistent.matrikas.entity.Tags;
import com.persistent.matrikas.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagsController {

    @Autowired
    private TagsService tagsService;

    @PostMapping
    public Tags createTag(@RequestBody Tags tag) {
        return tagsService.createTags(tag);
    }

//    @GetMapping("/{id}")
//    public Tags getTagById(@PathVariable Long id) throws Exception {
//        return tagsService(id);
//    }

    @GetMapping
    public List<Tags> getAllTags() {
        return tagsService.getAllTags();
    }

    @GetMapping("/by-library/{libraryId}")
    public List<Tags> getTagsByLibraryId(@PathVariable Long libraryId) throws Exception {
        return tagsService.getTagsByLibraryId(libraryId);
    }

//    @PutMapping("/{id}")
//    public Tags updateTag(@PathVariable Long id, @RequestBody Tags tag) throws Exception {
//        return tagsService.(id, tag);
//    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable Long id) throws Exception {
        tagsService.deleteTags(id);
    }
}
