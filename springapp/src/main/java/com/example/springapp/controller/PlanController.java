package com.example.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springapp.model.Plan;
import com.example.springapp.service.PlanService;

@RestController
@RequestMapping("/admin")
public class PlanController {

    @Autowired
    private PlanService planService;

    @GetMapping("/getallplan")
    public ResponseEntity<List<Plan>> getAllPlans() {
        List<Plan> plans = planService.getAllPlans();
        return ResponseEntity.ok(plans);
    }

    @PostMapping("/addplan")
    public ResponseEntity<Plan> addPlan(@RequestBody Plan plan) {
        Plan addedPlan = planService.addPlan(plan);
        return ResponseEntity.ok(addedPlan);
    }

    @PutMapping("/editplan/{planId}")
    public ResponseEntity<Plan> editPlan(@PathVariable Long planId, @RequestBody Plan updatedPlan) {
        Plan editedPlan = planService.editPlan(planId, updatedPlan);
        return editedPlan != null ? ResponseEntity.ok(editedPlan) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteplan/{planId}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long planId) {
        planService.deletePlan(planId);
        return ResponseEntity.noContent().build();
    }
}
