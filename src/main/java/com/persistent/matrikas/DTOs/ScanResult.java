package com.persistent.matrikas.DTOs;

import java.util.List;

public class ScanResult {
    private List<String> cveList;
    private List<String> severityList;

    public ScanResult(List<String> cveList, List<String> severityList) {
        this.cveList = cveList;
        this.severityList = severityList;
    }

    public List<String> getCveList() {
        return cveList;
    }

    public void setCveList(List<String> cveList) {
        this.cveList = cveList;
    }

    public List<String> getSeverityList() {
        return severityList;
    }

    public void setSeverityList(List<String> severityList) {
        this.severityList = severityList;
    }
}
