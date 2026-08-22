package com.placement.placementeligibilitychecker.controller;

import com.placement.placementeligibilitychecker.model.Company;
import com.placement.placementeligibilitychecker.model.Student;
import com.placement.placementeligibilitychecker.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")

public class CompanyController {

    @Autowired
    private CompanyService service;

    // Add Company (Admin)
    @PostMapping
    public Company addCompany(@RequestBody Company company) {
        return service.saveCompany(company);
    }

    // Get All Companies (Admin & General view)
    @GetMapping
    public List<Company> getAllCompanies() {
        return service.getAllCompanies();
    }

    // Get Company By ID
    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable Integer id) {
        return service.getCompanyById(id);
    }

    // Update Company (Admin)
    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable Integer id, @RequestBody Company company) {
        return service.updateCompany(id, company);
    }

    // Delete Company (Admin)
    @DeleteMapping("/{id}")
    public String deleteCompany(@PathVariable Integer id) {
        service.deleteCompany(id);
        return "Company Deleted Successfully";
    }

    // Get Companies by Name
    @GetMapping("/name/{companyName}")
    public List<Company> getCompanyByName(@PathVariable String companyName) {
        return service.getCompanyByName(companyName);
    }

    // Get Total Count of Companies
    @GetMapping("/count")
    public long getTotalCompanies() {
        return service.getTotalCompanies();
    }

    // 1. Get Eligible Companies for Logged-in Student (For Student Dashboard)
    @GetMapping("/eligibleStudent/{studentId}")
    public List<Company> getEligibleCompanies(@PathVariable Integer studentId) {
        return service.getEligibleCompanies(studentId);
    }

    // 2. Get Eligible Students for a Specific Company Drive (For Admin View)
    @GetMapping("/{companyId}/eligibleStudents")
    public List<Student> getEligibleStudents(@PathVariable Integer companyId) {
        return service.getEligibleStudents(companyId);
    }
}