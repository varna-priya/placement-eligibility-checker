package com.placement.placementeligibilitychecker.model;

public class DashboardDTO {

    private long totalStudents;
    private long totalCompanies;
    private long totalApplications;
    private long placedStudents;
    public DashboardDTO() {
    }

    public DashboardDTO(long totalStudents,
                        long totalCompanies,
                        long totalApplications,
                        long placedStudents)
    {
        this.totalStudents = totalStudents;
        this.totalCompanies = totalCompanies;
        this.totalApplications = totalApplications;
        this.placedStudents = placedStudents;
    }

    public long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public long getTotalCompanies() {
        return totalCompanies;
    }

    public void setTotalCompanies(long totalCompanies) {
        this.totalCompanies = totalCompanies;
    }
    public long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(long totalApplications) {
        this.totalApplications = totalApplications;
    }

    public long getPlacedStudents() {
        return placedStudents;
    }

    public void setPlacedStudents(long placedStudents) {
        this.placedStudents = placedStudents;
    }

}