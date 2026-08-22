package com.placement.placementeligibilitychecker.service;

import com.placement.placementeligibilitychecker.model.Student;
import com.placement.placementeligibilitychecker.model.StudentDashboardDTO;
import com.placement.placementeligibilitychecker.repository.ApplicationRepository;
import com.placement.placementeligibilitychecker.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentDashboardService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    public StudentDashboardDTO getDashboard(Integer studentId)
    {
        Student student = studentRepository.findById(studentId).orElse(null);

        if(student == null)
        {
            return null;
        }

        long appliedCompanies =
                applicationRepository.countByStudentId(studentId);

        long selectedCompanies =
                applicationRepository.countByStudentIdAndStatus(studentId, "Selected");

        long rejectedCompanies =
                applicationRepository.countByStudentIdAndStatus(studentId, "Rejected");

        long pendingCompanies =
                applicationRepository.countByStudentIdAndStatus(studentId, "Applied");

        return new StudentDashboardDTO(
                student.getName(),
                appliedCompanies,
                selectedCompanies,
                rejectedCompanies,
                pendingCompanies
        );
    }
}