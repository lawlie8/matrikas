package com.persistent.matrikas.service;


import com.persistent.matrikas.entity.Library;
import com.persistent.matrikas.entity.LibraryDTO;

import java.util.List;

public interface LibraryService {
    Library createLibrary(Library library);
    Library getLibraryById(Long id) throws Exception;
    List<LibraryDTO> getAllLibraries();
    Library updateLibrary(Long libraryId, Library library) throws Exception;
    void deleteLibrary(Long id) throws Exception;
}
