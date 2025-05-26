package com.persistent.matrikas.entity;

import java.util.List;

public class LibraryDTO {

    private Long id;

    private String apiUrl;

    private String imageName;

    private boolean isSideCar;

    private String source;

    private List<SideCars> sideCarsList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public boolean isSideCar() {
        return isSideCar;
    }

    public void setSideCar(boolean sideCar) {
        isSideCar = sideCar;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<SideCars> getSideCarsList() {
        return sideCarsList;
    }

    public void setSideCarsList(List<SideCars> sideCarsList) {
        this.sideCarsList = sideCarsList;
    }
}
