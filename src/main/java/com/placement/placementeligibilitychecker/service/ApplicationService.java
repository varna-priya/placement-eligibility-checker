package com.placement.placementeligibilitychecker.service;

import com.placement.placementeligibilitychecker.dto.AtsEvaluationResponse;
import com.placement.placementeligibilitychecker.model.Application;
import com.placement.placementeligibilitychecker.model.Company;
import com.placement.placementeligibilitychecker.model.Student;
import com.placement.placementeligibilitychecker.repository.ApplicationRepository;
import com.placement.placementeligibilitychecker.repository.CompanyRepository;
import com.placement.placementeligibilitychecker.repository.StudentRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AtsService atsService;

    @Autowired
    private JavaMailSender mailSender;

    // Get All Applications
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    // Get Applications By Student ID
    public List<Application> getApplicationsByStudent(Integer studentId) {
        return applicationRepository.findByStudentId(studentId);
    }

    // Get Applications By Company ID
    public List<Application> getApplicationsByCompany(Integer companyId) {
        return applicationRepository.findByCompanyId(companyId);
    }

    // Get Applications By Status
    public List<Application> getApplicationsByStatus(String status) {
        return applicationRepository.findByStatus(status);
    }

    // Legacy Direct Apply (Without ATS)
    public Application applyCompany(Integer studentId, Integer companyId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        Application existingApplication = applicationRepository.findByStudentIdAndCompanyId(studentId, companyId);
        if (existingApplication != null) {
            throw new RuntimeException("Already applied to this company");
        }

        Application application = new Application();
        application.setStudent(student);
        application.setCompany(company);
        application.setStatus("Applied");

        return applicationRepository.save(application);
    }

    // ATS-Based Company Apply Logic
    public AtsEvaluationResponse applyWithAts(Integer studentId, Integer companyId, MultipartFile file) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        // Check if student has already applied
        Application existingApplication = applicationRepository.findByStudentIdAndCompanyId(studentId, companyId);
        if (existingApplication != null) {
            throw new RuntimeException("Already applied to this company");
        }

        try {
            // 1. Extract plain text from PDF using PDFBox Library
            String resumeText = atsService.extractTextFromPdf(file);

            // 2. Dynamically construct Job Description
            String jobRole = (company.getRole() != null && !company.getRole().isEmpty()) ? company.getRole() : "Software Developer";
            String departmentReq = (company.getDepartment() != null && !company.getDepartment().isEmpty()) ? company.getDepartment() : "ALL";

            String jobDescription = "Company Name: " + company.getCompanyName() + "\n"
                    + "Target Role: " + jobRole + "\n"
                    + "Eligible Departments: " + departmentReq + "\n"
                    + "Job Profile: Core technical engineering, software development, problem solving, and skills aligned with " + jobRole + ".";

            // 3. Evaluate Resume text against JD using Gemini AI Service
            AtsEvaluationResponse response = atsService.evaluateResume(resumeText, jobDescription);

            // 4. Save to Database WITH RESUME FILE BYTES ONLY IF Score >= 50%
            if (response.isEligible() || response.getScore() >= 50) {
                Application application = new Application();
                application.setStudent(student);
                application.setCompany(company);
                application.setStatus("Applied");

                // SAVE RESUME PDF BYTES
                if (file != null && !file.isEmpty()) {
                    application.setResumeData(file.getBytes());
                }

                applicationRepository.save(application);
            }

            return response;

        } catch (Exception e) {
            throw new RuntimeException("ATS Application Process Failed: " + e.getMessage(), e);
        }
    }

    // Update Application Status (Selected/Rejected/Applied)
    public Application updateStatus(Integer applicationId, String status) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setStatus(status);
        return applicationRepository.save(application);
    }

    // EXTRACT APPLICANTS & SEND PDF RESUME ATTACHMENTS TO COMPANY HR
    public void sendApplicantsToCompany(Integer companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        List<Application> applications = applicationRepository.findByCompanyIdAndStatus(companyId, "Applied");

        if (applications.isEmpty()) {
            throw new RuntimeException("No applicants found for this company.");
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // true = multipart for attachments

            helper.setTo(company.getEmail());
            helper.setSubject("Applied Candidates List & Resumes - " + company.getCompanyName());

            StringBuilder emailText = new StringBuilder();
            emailText.append("Hello ").append(company.getCompanyName()).append(" HR Team,\n\n");
            emailText.append("Please find attached the applicant details and PDF resumes for your placement drive:\n\n");

            for (Application app : applications) {
                Student student = app.getStudent();
                String studentName = (student != null) ? student.getName() : "Student ID " + app.getStudent().getId();

                emailText.append("• Student: ").append(studentName)
                        .append(" | Status: ").append(app.getStatus()).append("\n");

                // Attach Resume PDF if available
                if (app.getResumeData() != null && app.getResumeData().length > 0) {
                    String filename = "Resume_" + studentName.replaceAll("\\s+", "_") + ".pdf";
                    helper.addAttachment(filename, new ByteArrayResource(app.getResumeData()));
                }
            }

            emailText.append("\nRegards,\nPlacement Cell Admin");
            helper.setText(emailText.toString());

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send email with attachments: " + e.getMessage(), e);
        }
    }
}