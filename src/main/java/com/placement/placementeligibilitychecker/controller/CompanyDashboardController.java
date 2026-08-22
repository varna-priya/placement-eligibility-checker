package com.placement.placementeligibilitychecker.controller;

import com.placement.placementeligibilitychecker.dto.CompanyDashboardDTO;
import com.placement.placementeligibilitychecker.service.CompanyDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companyDashboard")
public class CompanyDashboardController {

    @Autowired
    private CompanyDashboardService service;

    @GetMapping("/{companyId}")
    public CompanyDashboardDTO getDashboard(
            @PathVariable Integer companyId) {

        return service.getDashboard(companyId);
    }
}