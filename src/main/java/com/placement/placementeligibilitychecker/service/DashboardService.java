package com.placement.placementeligibilitychecker.service;

import com.placement.placementeligibilitychecker.model.DashboardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.placement.placementeligibilitychecker.repository.ApplicationRepository;
import com.placement.placementeligibilitychecker.repository.StudentRepository;
@Service
public class DashboardService {

    @Autowired
    private EligibilityService studentService;

    @Autowired
    private CompanyService companyService;
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private StudentRepository studentRepository;
    public DashboardDTO getDashboard()
    {
        long totalStudents = studentService.getTotalStudents();

        long totalCompanies = companyService.getTotalCompanies();

        long totalApplications = applicationRepository.count();

        long placedStudents =
                studentRepository.countByPlacementStatus("Placed");

        return new DashboardDTO(
                totalStudents,
                totalCompanies,
                totalApplications,
                placedStudents
        );
    }
}