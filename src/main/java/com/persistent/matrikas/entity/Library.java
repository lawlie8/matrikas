package com.persistent.matrikas.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="Library")
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "imageName")
    private String imageName;

    @Column(name = "source")
    private String source;

    @Column(name = "api_url")
    private String apiUrl;

    @Column(name = "is_sidecar")
    private boolean  isSideCar;

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Tags> dataList;

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<SideCars> sideCarsList;

    public List<Tags> getDataList() {
        return dataList;
    }

    public void setDataList(List<Tags> dataList) {
        this.dataList = dataList;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public List<SideCars> getSideCarsList() {
        return sideCarsList;
    }

    public void setSideCarsList(List<SideCars> sideCarsList) {
        this.sideCarsList = sideCarsList;
    }
}
