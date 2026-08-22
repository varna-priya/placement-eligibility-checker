package com.placement.placementeligibilitychecker.controller;

import com.placement.placementeligibilitychecker.model.DashboardDTO;
import com.placement.placementeligibilitychecker.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

    @Autowired
    private DashboardService service;

    @GetMapping("/dashboard")
    public DashboardDTO getDashboard()
    {
        return service.getDashboard();
    }
}