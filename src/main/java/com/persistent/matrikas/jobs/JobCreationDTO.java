package com.persistent.matrikas.jobs;

public class JobCreationDTO {


    private String jobName;

    private String imageName;

    private String imageVersion;


    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageVersion() {
        return imageVersion;
    }

    public void setImageVersion(String imageVersion) {
        this.imageVersion = imageVersion;
    }

    @Override
    public String toString() {
        return "JobCreationDTO{" +
                ", jobName=" + jobName +
                ", imageName='" + imageName + '\'' +
                ", imageVersion='" + imageVersion + '\'' +
                '}';
    }
}
