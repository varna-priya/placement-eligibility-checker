package com.placement.placementeligibilitychecker.service;

import com.placement.placementeligibilitychecker.model.Student;
import com.placement.placementeligibilitychecker.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EligibilityService {

    @Autowired
    private StudentRepository repository;

    public Student saveStudent(Student student) {
        return repository.save(student);
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Student getStudentById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public Student updateStudent(Integer id, Student student) {

        Student existingStudent = repository.findById(id).orElse(null);

        if (existingStudent != null) {
            existingStudent.setName(student.getName());
            existingStudent.setCgpa(student.getCgpa());
            existingStudent.setDepartment(student.getDepartment());
            existingStudent.setEmail(student.getEmail());

            return repository.save(existingStudent);
        }

        return null;
    }
    public String deleteStudent(Integer id)
    {
        repository.deleteById(id);
        return "Student Deleted Successfully";
    }
    public List<Student> getStudentsByDepartment(String department)
    {
        return repository.findByDepartment(department);
    }
    public List<Student> getStudentsByName(String name)
    {
        return repository.findByName(name);
    }
    public List<Student> getStudentsByEmail(String email)
    {
        return repository.findByEmail(email);
    }
    public List<Student> getStudentsByCgpa(double cgpa)
    {
        return repository.findByCgpa(cgpa);
    }
    public List<Student> getStudentsByCgpaGreaterThan(double cgpa)
    {
        return repository.findByCgpaGreaterThan(cgpa);
    }
    public List<Student> getEligibleStudents(String department, double cgpa)
    {
        return repository.findByDepartmentAndCgpaGreaterThan(department, cgpa);
    }
    public Student updatePlacementStatus(Integer id, String status)
    {
        Student student = repository.findById(id).orElse(null);

        if(student == null)
        {
            return null;
        }

        student.setPlacementStatus(status);

        return repository.save(student);
    }
    public long getTotalStudents()
    {
        return repository.count();
    }

}