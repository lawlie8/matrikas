package com.persistent.matrikas.service;


import com.persistent.matrikas.entity.LibraryDTO;
import com.persistent.matrikas.repository.LibraryRepository;
import com.persistent.matrikas.entity.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<LibraryDTO> getAllLibraries() {
        List<Library> libraries =  libraryRepository.findAll();
        List<LibraryDTO> libraryDTOS = new ArrayList<>();
        for(int i = 0;i< libraries.size();i++){
            LibraryDTO libraryDTO = new LibraryDTO();
            libraryDTO.setId(libraries.get(i).getId());
            libraryDTO.setImageName(libraries.get(i).getImageName());
            libraryDTO.setSideCar(libraries.get(i).isSideCar());
            libraryDTO.setApiUrl(libraries.get(i).getApiUrl());
            libraryDTO.setSource(libraries.get(i).getSource());
            libraryDTO.setSideCarsList(libraries.get(i).getSideCarsList());
            libraryDTOS.add(libraryDTO);
        }
        return libraryDTOS;

    }

    @Override
    public Library updateLibrary(Long libraryId, Library library) throws Exception {
       Library lib = getLibraryById(libraryId);
       lib.setImageName(library.getImageName());
       lib.setSource(library.getSource());
       lib.setApiUrl(library.getApiUrl());
       lib.setSideCarsList(library.getSideCarsList());
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
