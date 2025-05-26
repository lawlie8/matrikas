package com.persistent.matrikas.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "side_cars")
public class SideCars {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "lib_id", insertable = false, updatable = false)
    private Long libId;

    @Column(name = "side_car_name")
    private String sideCarName;

    @Column(name = "api_url")
    private String apiUrl;

    @ManyToOne
    @JoinColumn(name = "lib_id", referencedColumnName = "id", nullable = false)
    private Library library;


    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRepoId() {
        return libId;
    }

    public void setRepoId(Long libId) {
        this.libId = libId;
    }

    public String getSideCarName() {
        return sideCarName;
    }

    public void setSideCarName(String sideCarName) {
        this.sideCarName = sideCarName;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @Override
    public String toString() {
        return "SideCars{" +
                "id=" + id +
                ", libId=" + libId +
                ", sideCarName='" + sideCarName + '\'' +
                ", apiUrl='" + apiUrl + '\'' +
                ", library=" + library +
                '}';
    }
}
