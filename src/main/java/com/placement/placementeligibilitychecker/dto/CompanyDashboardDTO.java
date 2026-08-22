package com.placement.placementeligibilitychecker.dto;

public class CompanyDashboardDTO {

    private String companyName;
    private long totalApplications;
    private long selectedStudents;
    private long rejectedStudents;
    private long pendingStudents;

    public CompanyDashboardDTO(
            String companyName,
            long totalApplications,
            long selectedStudents,
            long rejectedStudents,
            long pendingStudents) {

        this.companyName = companyName;
        this.totalApplications = totalApplications;
        this.selectedStudents = selectedStudents;
        this.rejectedStudents = rejectedStudents;
        this.pendingStudents = pendingStudents;
    }

    public String getCompanyName() {
        return companyName;
    }

    public long getTotalApplications() {
        return totalApplications;
    }

    public long getSelectedStudents() {
        return selectedStudents;
    }

    public long getRejectedStudents() {
        return rejectedStudents;
    }

    public long getPendingStudents() {
        return pendingStudents;
    }
}