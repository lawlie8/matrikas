package com.persistent.matrikas.service;

public class ScanDTO {

    private String cveID;
    private String severity;
    private String status;

    public String getCveID() {
        return cveID;
    }

    public void setCveID(String cveID) {
        this.cveID = cveID;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ScanDTO{" +
                "cveID='" + cveID + '\'' +
                ", severity='" + severity + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
