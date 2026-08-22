package com.placement.placementeligibilitychecker.controller;

import com.placement.placementeligibilitychecker.dto.AtsEvaluationResponse;
import com.placement.placementeligibilitychecker.service.AtsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/ats")
public class AtsController {

    @Autowired
    private AtsService atsService;

    @PostMapping("/evaluate")
    public ResponseEntity<?> evaluateResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam("jobDescription") String jobDescription) {
        try {
            String resumeText = atsService.extractTextFromPdf(file);
            AtsEvaluationResponse response = atsService.evaluateResume(resumeText, jobDescription);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}