package com.acme.acmevendor.viewmodel;

public class SiteDetail {
    private String site_id;
    private String site_name;
    private String location;
    private String last_inspection;
    private String next_inspection;
    private String inspector_name;

    // Getters and Setters for each field

    public String getSiteId() {
        return site_id;
    }

    public void setSiteId(String site_id) {
        this.site_id = site_id;
    }

    public String getSiteName() {
        return site_name;
    }

    public void setSiteName(String site_name) {
        this.site_name = site_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLastInspection() {
        return last_inspection;
    }

    public void setLastInspection(String last_inspection) {
        this.last_inspection = last_inspection;
    }

    public String getNextInspection() {
        return next_inspection;
    }

    public void setNextInspection(String next_inspection) {
        this.next_inspection = next_inspection;
    }

    public String getInspectorName() {
        return inspector_name;
    }

    public void setInspectorName(String inspector_name) {
        this.inspector_name = inspector_name;
    }
}