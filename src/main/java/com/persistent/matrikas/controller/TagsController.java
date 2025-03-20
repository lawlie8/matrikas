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

    // ✅ Create a tag manually
    @PostMapping("")
    public Tags createTag(@RequestBody Tags tag) {
        return tagsService.createTags(tag);
    }

    // ✅ Get a single tag by ID
    @GetMapping("/{id}")
    public Tags getTagById(@PathVariable Long id) throws Exception {
        return tagsService.getTagById(id);
    }

    // ✅ Get all tags
    @GetMapping("")
    public List<Tags> getAllTags() {
        return tagsService.getAllTags();
    }

    // ✅ Get tags by library ID
    @GetMapping("/by-library/{libraryId}")
    public List<Tags> getTagsByLibraryId(@PathVariable Long libraryId) throws Exception {
        return tagsService.getTagsByLibraryId(libraryId);
    }

    // ✅ Delete a tag by ID
    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable Long id) throws Exception {
        tagsService.deleteTags(id);
    }

    // ✅ Fetch and store tags from GitHub for all libraries
    @PostMapping("/fetch")
    public String fetchAndStoreTags() {
        tagsService.fetchAndStoreGitHubTags();
        return "Tags fetched and stored successfully";
    }
}
