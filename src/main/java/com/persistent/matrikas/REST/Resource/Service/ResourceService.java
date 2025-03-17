package com.persistent.matrikas.REST.Resource.Service;

import com.persistent.matrikas.DTO.ResourceDTO;
import com.persistent.matrikas.Entity.ResourceEntity;
import com.persistent.matrikas.Repository.ResourceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepo resourceRepo;

    public List<ResourceDTO> getAllResource() {
        List<ResourceDTO> resourceDTOList = new ArrayList<>();
        List<ResourceEntity> resourceEntityList = resourceRepo.findAll();
        for (int i = 0; i < resourceEntityList.size(); i++){
            ResourceDTO resourceDTO = new ResourceDTO();
            resourceDTO.setId(resourceEntityList.get(i).getId());
            resourceDTO.setResourceUrl(resourceEntityList.get(i).getResourceUrl());
            resourceDTO.setImageName(resourceEntityList.get(i).getResourceName());
            resourceDTOList.add(resourceDTO);
        }
        return resourceDTOList;
    }

}
