package com.persistent.matrikas.controller;

import com.persistent.matrikas.entity.Library;
import com.persistent.matrikas.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libraries")
public class LibraryController {

    @Autowired
    LibraryService libraryService;

    @PostMapping("")
    public ResponseEntity<Library> createLibrary(@RequestBody Library library) {
        return ResponseEntity.ok(libraryService.createLibrary(library));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Library> getLibrary(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(libraryService.getLibraryById(id));
    }

    @GetMapping("")
   public ResponseEntity<List<Library>> getAllLibary() {
        return ResponseEntity.ok(libraryService.getAllLibraries());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Library> updateLibrary(@PathVariable long id, @RequestBody Library library) throws Exception {
        return ResponseEntity.ok(libraryService.updateLibrary(id,library));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLibrary(@PathVariable long id) throws Exception {
        libraryService.deleteLibrary(id);
        return ResponseEntity.ok("Library deleted");
    }

}
