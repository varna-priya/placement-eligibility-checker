package com.placement.placementeligibilitychecker.controller;

import com.placement.placementeligibilitychecker.dto.AtsEvaluationResponse;
import com.placement.placementeligibilitychecker.model.Application;
import com.placement.placementeligibilitychecker.repository.ApplicationRepository;
import com.placement.placementeligibilitychecker.service.ApplicationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/applications")
@CrossOrigin(origins = "http://localhost:5173")
public class ApplicationController {

    @Autowired
    private ApplicationService service;

    @Autowired
    private ApplicationRepository applicationRepository; // Added Repository for Direct CSV Query

    // Get All Applications
    @GetMapping
    public List<Application> getAllApplications() {
        return service.getAllApplications();
    }

    // Get Applications By Student ID
    @GetMapping("/student/{studentId}")
    public List<Application> getApplicationsByStudent(@PathVariable Integer studentId) {
        return service.getApplicationsByStudent(studentId);
    }

    // Get Applications By Company ID
    @GetMapping("/company/{companyId}")
    public List<Application> getApplicationsByCompany(@PathVariable Integer companyId) {
        return service.getApplicationsByCompany(companyId);
    }

    // Direct Apply Endpoint (Legacy)
    @PostMapping("/{studentId}/{companyId}")
    public ResponseEntity<?> applyCompany(@PathVariable Integer studentId, @PathVariable Integer companyId) {
        try {
            Application application = service.applyCompany(studentId, companyId);
            return ResponseEntity.ok(application);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ATS-Based Apply Endpoint with PDF Upload
    @PostMapping("/{studentId}/{companyId}/applyWithAts")
    public ResponseEntity<?> applyWithAts(
            @PathVariable Integer studentId,
            @PathVariable Integer companyId,
            @RequestParam("file") MultipartFile file) {
        try {
            AtsEvaluationResponse response = service.applyWithAts(studentId, companyId, file);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get Applications By Status
    @GetMapping("/status/{status}")
    public List<Application> getApplicationsByStatus(@PathVariable String status) {
        return service.getApplicationsByStatus(status);
    }

    // Update Application Status
    @PutMapping("/{applicationId}/status")
    public Application updateStatus(@PathVariable Integer applicationId, @RequestParam String status) {
        return service.updateStatus(applicationId, status);
    }

    // Extract Applicants List and Send Email with Resumes
    @PostMapping("/extract/{companyId}")
    public ResponseEntity<String> extractToCompany(@PathVariable Integer companyId) {
        try {
            service.sendApplicantsToCompany(companyId);
            return ResponseEntity.ok("Applicants list sent successfully to company email!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send email: " + e.getMessage());
        }
    }

    // Export APPLIED Candidates as CSV (Excel)
    @GetMapping("/export-csv/{companyId}")
    public void exportApplicantsToCsv(@PathVariable Integer companyId, HttpServletResponse response) throws IOException {
        // "Applied" மாணவர்களை மட்டும் Filter செய்து எடுக்கிறோம்
        List<Application> applications = applicationRepository.findByCompanyIdAndStatus(companyId, "Applied");

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=Applied_Candidates_Company_" + companyId + ".csv");

        StringBuilder csvBuilder = new StringBuilder();

        // CSV Header Columns
        csvBuilder.append("Application ID,Student ID,Student Name,Department,CGPA,Status\n");

        for (Application app : applications) {
            csvBuilder.append(app.getId()).append(",")
                    .append(app.getStudent() != null ? app.getStudent().getId() : "").append(",")
                    .append(app.getStudent() != null ? app.getStudent().getName() : "").append(",")
                    .append(app.getStudent() != null ? app.getStudent().getDepartment() : "").append(",")
                    .append(app.getStudent() != null ? app.getStudent().getCgpa() : "").append(",")
                    .append(app.getStatus()).append("\n");
        }

        response.getWriter().write(csvBuilder.toString());
    }
}