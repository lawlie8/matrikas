package com.persistent.matrikas.controller;

public class CVERequest {


    public CVERequest(String imageName, String oldTag, String newTag,Long sideCarId) {
        this.imageName = imageName;
        this.oldTag = oldTag;
        this.newTag = newTag;
        this.sideCarId = sideCarId;
    }

    private String imageName;
    private String oldTag;
    private String newTag;
    private Long sideCarId;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getOldTag() {
        return oldTag;
    }

    public void setOldTag(String oldTag) {
        this.oldTag = oldTag;
    }

    public String getNewTag() {
        return newTag;
    }

    public void setNewTag(String newTag) {
        this.newTag = newTag;
    }

    public Long getSideCarId() {
        return sideCarId;
    }

    public void setSideCarId(Long sideCarId) {
        this.sideCarId = sideCarId;
    }
}
