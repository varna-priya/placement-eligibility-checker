package com.placement.placementeligibilitychecker.service;

import com.placement.placementeligibilitychecker.model.ChangePasswordDTO;
import com.placement.placementeligibilitychecker.model.Student;
import com.placement.placementeligibilitychecker.repository.ApprovedStudentRepository;
import com.placement.placementeligibilitychecker.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ApprovedStudentRepository approvedStudentRepository;


    // Add Student (Robust Case-Insensitive Check & Space Removal)
    public Student addStudent(Student student) {

        if (student.getEmail() == null || student.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email is required!");
        }

        String cleanEmail = student.getEmail().trim().toLowerCase();

        // 1. Check if email is in the pre-approved database table (Case & Space Insensitive)
        boolean isApproved = approvedStudentRepository.findAll().stream()
                .anyMatch(app -> app.getEmail() != null && app.getEmail().trim().equalsIgnoreCase(cleanEmail));

        if (!isApproved) {
            throw new RuntimeException("Access Denied! Your email is not in the college approved list.");
        }

        // 2. Check if student is already registered
        List<Student> existingStudents = studentRepository.findAll();
        for (Student s : existingStudents) {
            if (s.getEmail() != null && s.getEmail().trim().equalsIgnoreCase(cleanEmail)) {
                throw new RuntimeException("Email is already registered!");
            }
        }

        // Set clean data & default status if missing
        student.setEmail(cleanEmail);
        if (student.getPassword() != null) {
            student.setPassword(student.getPassword().trim());
        }
        if (student.getPlacementStatus() == null || student.getPlacementStatus().isEmpty()) {
            student.setPlacementStatus("NOT_PLACED");
        }

        return studentRepository.save(student);
    }


    // Get All Students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }


    // Get Student By Id
    public Student getStudentById(Integer id) {
        return studentRepository.findById(id).orElse(null);
    }


    // Update Student
    public Student updateStudent(Integer id, Student updatedStudent) {

        Student student = studentRepository.findById(id).orElse(null);

        if (student == null) {
            return null;
        }

        student.setName(updatedStudent.getName());
        student.setCgpa(updatedStudent.getCgpa());
        student.setDepartment(updatedStudent.getDepartment());
        student.setEmail(updatedStudent.getEmail());
        student.setPlacementStatus(updatedStudent.getPlacementStatus());
        student.setPassword(updatedStudent.getPassword());

        return studentRepository.save(student);
    }


    // Delete Student
    public String deleteStudent(Integer id) {
        studentRepository.deleteById(id);
        return "Student Deleted Successfully";
    }


    // Department
    public List<Student> getStudentsByDepartment(String department) {
        return studentRepository.findByDepartment(department);
    }


    // Name
    public List<Student> getStudentsByName(String name) {
        return studentRepository.findByName(name);
    }


    // Email
    public List<Student> getStudentsByEmail(String email) {
        return studentRepository.findByEmail(email);
    }


    // CGPA
    public List<Student> getStudentsByCgpa(double cgpa) {
        return studentRepository.findByCgpa(cgpa);
    }


    // CGPA Greater Than
    public List<Student> getStudentsByCgpaGreaterThan(double cgpa) {
        return studentRepository.findByCgpaGreaterThan(cgpa);
    }


    // Eligible Students
    public List<Student> getEligibleStudents(String department, double cgpa) {
        return studentRepository.findByDepartmentAndCgpaGreaterThan(department, cgpa);
    }


    // Update Placement Status
    public Student updatePlacementStatus(Integer id, String status) {

        Student student = studentRepository.findById(id).orElse(null);

        if (student == null) {
            return null;
        }

        student.setPlacementStatus(status);
        return studentRepository.save(student);
    }


    // Count Students
    public long getTotalStudents() {
        return studentRepository.count();
    }


    // Login (Robust Case-Insensitive & Whitespace-Safe Check)
    public Student login(String email, String password) {
        if (email == null || password == null) {
            return null;
        }

        String cleanEmail = email.trim().toLowerCase();
        String cleanPassword = password.trim();

        // 1. Search all students and match email/password iteratively
        List<Student> allStudents = studentRepository.findAll();
        for (Student s : allStudents) {
            if (s.getEmail() != null && s.getPassword() != null) {
                if (s.getEmail().trim().equalsIgnoreCase(cleanEmail) &&
                        s.getPassword().trim().equals(cleanPassword)) {
                    return s;
                }
            }
        }

        return null;
    }


    // Get Profile
    public Student getProfile(Integer id) {
        return studentRepository.findById(id).orElse(null);
    }


    // Change Password
    public String changePassword(Integer id, ChangePasswordDTO passwordDTO) {

        Student student = studentRepository.findById(id).orElse(null);

        if (student == null) {
            return "Student Not Found";
        }

        if (!student.getPassword().equals(passwordDTO.getOldPassword())) {
            return "Old Password is Incorrect";
        }

        student.setPassword(passwordDTO.getNewPassword());
        studentRepository.save(student);

        return "Password Changed Successfully";
    }


    // Upload Resume
    public String uploadResume(Integer id, MultipartFile file) throws IOException {

        Student student = studentRepository.findById(id).orElse(null);

        if (student == null) {
            return "Student Not Found";
        }

        String uploadDir = "uploads/";
        Files.createDirectories(Paths.get(uploadDir));

        String fileName = file.getOriginalFilename();
        Path path = Paths.get(uploadDir + fileName);

        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        student.setResume(fileName);
        studentRepository.save(student);

        return "Resume Uploaded Successfully";
    }
}