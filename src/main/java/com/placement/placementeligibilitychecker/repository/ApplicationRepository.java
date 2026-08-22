package com.placement.placementeligibilitychecker.repository;

import com.placement.placementeligibilitychecker.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository
        extends JpaRepository<Application, Integer> {

    List<Application> findByStudentId(Integer studentId);

    // View Applications
    List<Application> findByCompanyId(Integer companyId);

    // Extract Mail & CSV Export
    List<Application> findByCompanyIdAndStatus(Integer companyId, String status);

    Application findByStudentIdAndCompanyId(
            Integer studentId,
            Integer companyId
    );

    void deleteByCompany_Id(Integer companyId);

    long countByStudentId(Integer studentId);

    long countByStudentIdAndStatus(
            Integer studentId,
            String status
    );

    long countByCompanyId(Integer companyId);

    long countByCompanyIdAndStatus(
            Integer companyId,
            String status
    );

    List<Application> findByStatus(String status);
}