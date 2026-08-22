package com.placement.placementeligibilitychecker.service;

import com.placement.placementeligibilitychecker.dto.CompanyDashboardDTO;
import com.placement.placementeligibilitychecker.model.Company;
import com.placement.placementeligibilitychecker.repository.ApplicationRepository;
import com.placement.placementeligibilitychecker.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyDashboardService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public CompanyDashboardDTO getDashboard(Integer companyId) {

        Company company = companyRepository.findById(companyId).orElse(null);

        if (company == null) {
            return null;
        }

        long totalApplications =
                applicationRepository.countByCompanyId(companyId);

        long selectedStudents =
                applicationRepository.countByCompanyIdAndStatus(
                        companyId, "Selected");

        long rejectedStudents =
                applicationRepository.countByCompanyIdAndStatus(
                        companyId, "Rejected");

        long pendingStudents =
                applicationRepository.countByCompanyIdAndStatus(
                        companyId, "Applied");

        return new CompanyDashboardDTO(
                company.getCompanyName(),
                totalApplications,
                selectedStudents,
                rejectedStudents,
                pendingStudents
        );
    }
}