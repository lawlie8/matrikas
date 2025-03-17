package com.persistent.matrikas.DTO;

public class ResourceDTO {

    private Long id;
    private String imageName;
    private String resourceUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    @Override
    public String toString() {
        return "ResourceDTO{" +
                "id=" + id +
                ", imageName='" + imageName + '\'' +
                ", resourceUrl='" + resourceUrl + '\'' +
                '}';
    }
}
