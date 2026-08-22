package com.placement.placementeligibilitychecker.model;

public class StudentDashboardDTO {

    private String studentName;
    private long appliedCompanies;
    private long selectedCompanies;
    private long rejectedCompanies;
    private long pendingCompanies;

    public StudentDashboardDTO() {
    }

    public StudentDashboardDTO(String studentName,
                               long appliedCompanies,
                               long selectedCompanies,
                               long rejectedCompanies,
                               long pendingCompanies) {

        this.studentName = studentName;
        this.appliedCompanies = appliedCompanies;
        this.selectedCompanies = selectedCompanies;
        this.rejectedCompanies = rejectedCompanies;
        this.pendingCompanies = pendingCompanies;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public long getAppliedCompanies() {
        return appliedCompanies;
    }

    public void setAppliedCompanies(long appliedCompanies) {
        this.appliedCompanies = appliedCompanies;
    }

    public long getSelectedCompanies() {
        return selectedCompanies;
    }

    public void setSelectedCompanies(long selectedCompanies) {
        this.selectedCompanies = selectedCompanies;
    }

    public long getRejectedCompanies() {
        return rejectedCompanies;
    }

    public void setRejectedCompanies(long rejectedCompanies) {
        this.rejectedCompanies = rejectedCompanies;
    }

    public long getPendingCompanies() {
        return pendingCompanies;
    }

    public void setPendingCompanies(long pendingCompanies) {
        this.pendingCompanies = pendingCompanies;
    }
}