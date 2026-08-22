package com.placement.placementeligibilitychecker.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String companyName;
    private String role;
    private double packageAmount; // e.g., 6.5 (LPA)
    private String department;    // Target Department (e.g., CSE, ECE, ALL)
    private double minCgpa;       // Cutoff CGPA
    private String email;         // HR Email
    private String location;      // Work Location

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate driveDate;  // Interview / Drive Date

    // Default Constructor (Required by JPA/Hibernate)
    public Company() {
    }

    // Parameterized Constructor
    public Company(Integer id, String companyName, String role, double packageAmount, String department, double minCgpa, String email, String location, LocalDate driveDate) {
        this.id = id;
        this.companyName = companyName;
        this.role = role;
        this.packageAmount = packageAmount;
        this.department = department;
        this.minCgpa = minCgpa;
        this.email = email;
        this.location = location;
        this.driveDate = driveDate;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getPackageAmount() {
        return packageAmount;
    }

    public void setPackageAmount(double packageAmount) {
        this.packageAmount = packageAmount;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getMinCgpa() {
        return minCgpa;
    }

    public void setMinCgpa(double minCgpa) {
        this.minCgpa = minCgpa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDriveDate() {
        return driveDate;
    }

    public void setDriveDate(LocalDate driveDate) {
        this.driveDate = driveDate;
    }
}