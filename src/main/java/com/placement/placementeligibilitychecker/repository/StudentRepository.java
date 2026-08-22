package com.placement.placementeligibilitychecker.repository;

import com.placement.placementeligibilitychecker.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>
{
    List<Student> findByDepartment(String department);
    List<Student> findByName(String name);
    List<Student> findByEmail(String email);
    List<Student> findByCgpa(double cgpa);
    List<Student> findByCgpaGreaterThan(double cgpa);
    List<Student> findByDepartmentAndCgpaGreaterThan(String department, double cgpa);
    Student findByEmailAndPassword(String email, String password);
    long countByPlacementStatus(String placementStatus);
}
