package com.persistent.matrikas.service;


import com.persistent.matrikas.repository.LibraryRepository;
import com.persistent.matrikas.entity.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService{
    @Autowired
    LibraryRepository libraryRepository;

    @Override
    public Library createLibrary(Library library) {

        return libraryRepository.save(library);
    }

    @Override
    public Library getLibraryById(Long id) throws Exception {
        return libraryRepository.findById(id).orElseThrow(()-> new Exception("Library not found"));
    }

    @Override
    public List<Library> getAllLibraries() {
        return libraryRepository.findAll();
    }

    @Override
    public Library updateLibrary(Long libraryId, Library library) throws Exception {
       Library lib = getLibraryById(libraryId);
       lib.setImageName(library.getImageName());
       lib.setSource(library.getSource());
       lib.setApiUrl(library.getApiUrl());
       return libraryRepository.save(lib);
    }

    @Override
    public void deleteLibrary(Long id) throws Exception {

        if(!libraryRepository.existsById(id)) {
            throw new Exception("Library not found");
        }

        libraryRepository.deleteById(id);
    }
}
