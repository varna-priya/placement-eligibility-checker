package com.placement.placementeligibilitychecker.repository;

import com.placement.placementeligibilitychecker.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>
{
    Admin findByUsernameAndPassword(String username,
                                    String password);
}