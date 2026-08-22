package com.placement.placementeligibilitychecker.service;

import com.placement.placementeligibilitychecker.model.Company;
import com.placement.placementeligibilitychecker.model.Student;
import com.placement.placementeligibilitychecker.repository.CompanyRepository;
import com.placement.placementeligibilitychecker.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository repository;

    @Autowired
    private StudentRepository studentRepository;

    // Save or Add Company
    public Company saveCompany(Company company) {
        return repository.save(company);
    }

    // Get All Companies
    public List<Company> getAllCompanies() {
        return repository.findAll();
    }

    // Get Company By ID
    public Company getCompanyById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    // Update Company
    public Company updateCompany(Integer id, Company company) {
        company.setId(id);
        return repository.save(company);
    }

    // Delete Company
    public void deleteCompany(Integer id) {
        repository.deleteById(id);
    }

    // Search Company by Name
    public List<Company> getCompanyByName(String companyName) {
        return repository.findByCompanyName(companyName);
    }

    // Get Total Count of Companies
    public long getTotalCompanies() {
        return repository.count();
    }

    // 1. Get Eligible Companies for a Logged-in Student (Used in Student Dashboard)
    public List<Company> getEligibleCompanies(Integer studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);

        if (student == null) {
            return List.of();
        }

        // Uses updated JPQL query in CompanyRepository (handles comma-separated depts like 'CSE, EIE' & 'ALL')
        return repository.findEligibleCompanies(student.getDepartment(), student.getCgpa());
    }

    // 2. Get Eligible Students for a Specific Company Drive (Used in Admin Dashboard)
    public List<Student> getEligibleStudents(Integer companyId) {
        Company company = repository.findById(companyId).orElse(null);

        if (company == null) {
            return List.of();
        }

        // Fetch students whose CGPA >= company cut-off
        List<Student> allStudents = studentRepository.findAll();

        return allStudents.stream()
                .filter(student -> student.getCgpa() >= company.getMinCgpa())
                .filter(student -> "ALL".equalsIgnoreCase(company.getDepartment())
                        || company.getDepartment().contains(student.getDepartment()))
                .toList();
    }
}