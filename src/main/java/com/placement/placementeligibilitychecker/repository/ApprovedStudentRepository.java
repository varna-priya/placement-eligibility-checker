package com.placement.placementeligibilitychecker.repository;

import com.placement.placementeligibilitychecker.model.ApprovedStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ApprovedStudentRepository extends JpaRepository<ApprovedStudent, Integer> {
    Optional<ApprovedStudent> findByEmail(String email);
}