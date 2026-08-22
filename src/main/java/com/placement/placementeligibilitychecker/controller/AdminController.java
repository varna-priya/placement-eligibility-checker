package com.placement.placementeligibilitychecker.controller;

import com.placement.placementeligibilitychecker.model.Admin;
import com.placement.placementeligibilitychecker.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService service;

    @PostMapping("/login")
    public String login(@RequestBody Admin admin)
    {
        return service.login(admin);
    }
}