package com.placement.placementeligibilitychecker.controller;

import com.placement.placementeligibilitychecker.model.Student;
import com.placement.placementeligibilitychecker.model.LoginRequest;
import com.placement.placementeligibilitychecker.model.ChangePasswordDTO;
import com.placement.placementeligibilitychecker.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService service;

    // Add Student
    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return service.addStudent(student);
    }

    // Get All Students
    @GetMapping
    public List<Student> getAllStudents() {
        return service.getAllStudents();
    }

    // Get Student By Id
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Integer id) {
        return service.getStudentById(id);
    }

    // Update Student
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Integer id, @RequestBody Student student) {
        return service.updateStudent(id, student);
    }

    // Delete Student
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        return service.deleteStudent(id);
    }

    // Department
    @GetMapping("/department/{department}")
    public List<Student> getStudentsByDepartment(@PathVariable String department) {
        return service.getStudentsByDepartment(department);
    }

    // Name
    @GetMapping("/name/{name}")
    public List<Student> getStudentsByName(@PathVariable String name) {
        return service.getStudentsByName(name);
    }

    // Email
    @GetMapping("/email/{email}")
    public List<Student> getStudentsByEmail(@PathVariable String email) {
        return service.getStudentsByEmail(email);
    }

    // CGPA
    @GetMapping("/cgpa/{cgpa}")
    public List<Student> getStudentsByCgpa(@PathVariable double cgpa) {
        return service.getStudentsByCgpa(cgpa);
    }

    // CGPA Greater Than
    @GetMapping("/cgpaGreaterThan/{cgpa}")
    public List<Student> getStudentsByCgpaGreaterThan(@PathVariable double cgpa) {
        return service.getStudentsByCgpaGreaterThan(cgpa);
    }

    // Eligible Students
    @GetMapping("/eligible/{department}/{cgpa}")
    public List<Student> getEligibleStudents(@PathVariable String department, @PathVariable double cgpa) {
        return service.getEligibleStudents(department, cgpa);
    }

    // Update Placement Status
    @PutMapping("/{id}/status")
    public Student updatePlacementStatus(@PathVariable Integer id, @RequestParam String status) {
        return service.updatePlacementStatus(id, status);
    }

    // Count Students
    @GetMapping("/count")
    public long getTotalStudents() {
        return service.getTotalStudents();
    }

    // Login (Updated with ResponseEntity wrapper for full JSON payload)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if (request == null || request.getEmail() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest().body("Email and Password are required");
        }

        Student student = service.login(request.getEmail(), request.getPassword());

        if (student != null) {
            return ResponseEntity.ok(student);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Email or Password");
    }

    @GetMapping("/profile/{id}")
    public Student getProfile(@PathVariable Integer id) {
        return service.getProfile(id);
    }

    @PutMapping("/changePassword/{id}")
    public String changePassword(@PathVariable Integer id, @RequestBody ChangePasswordDTO passwordDTO) {
        return service.changePassword(id, passwordDTO);
    }

    @PostMapping("/{id}/uploadResume")
    public String uploadResume(@PathVariable Integer id, @RequestParam("file") MultipartFile file) throws IOException {
        return service.uploadResume(id, file);
    }

    @GetMapping("/{id}/resume")
    public ResponseEntity<Resource> viewResume(@PathVariable Integer id) throws IOException {
        Student student = service.getStudentById(id);

        if (student == null || student.getResume() == null) {
            return ResponseEntity.notFound().build();
        }

        Path path = Paths.get("uploads/" + student.getResume());
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + student.getResume() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}