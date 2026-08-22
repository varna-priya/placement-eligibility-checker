package com.placement.placementeligibilitychecker.repository;

import com.placement.placementeligibilitychecker.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    List<Company> findByCompanyName(String companyName);
    @Query("SELECT c FROM Company c WHERE (LOWER(c.department) LIKE LOWER(CONCAT('%', :dept, '%')) OR LOWER(c.department) = 'all') AND c.minCgpa <= :cgpa")
    List<Company> findEligibleCompanies(@Param("dept") String dept, @Param("cgpa") double cgpa);
}