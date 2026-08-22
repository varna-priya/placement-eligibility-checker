package com.placement.placementeligibilitychecker.controller;

import com.placement.placementeligibilitychecker.model.StudentDashboardDTO;
import com.placement.placementeligibilitychecker.service.StudentDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/studentDashboard")
public class StudentDashboardController {

    @Autowired
    private StudentDashboardService service;

    @GetMapping("/{studentId}")
    public StudentDashboardDTO getDashboard(@PathVariable Integer studentId)
    {
        return service.getDashboard(studentId);
    }
}