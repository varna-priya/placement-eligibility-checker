package com.placement.placementeligibilitychecker.service;

import com.placement.placementeligibilitychecker.model.Admin;
import com.placement.placementeligibilitychecker.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository repository;

    public String login(Admin admin)
    {
        Admin existingAdmin = repository.findByUsernameAndPassword(
                admin.getUsername(),
                admin.getPassword()
        );

        if(existingAdmin != null)
        {
            return "Admin Login Successful";
        }

        return "Invalid Username or Password";
    }
}