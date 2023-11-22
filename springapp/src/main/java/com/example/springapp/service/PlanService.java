package com.example.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springapp.model.Plan;
import com.example.springapp.repository.PlanRepository;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    @Autowired
    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }

    public Plan addPlan(Plan plan) {
        return planRepository.save(plan);
    }

    public Optional<Plan> getPlanById(Long planId) {
        return planRepository.findById(planId);
    }

    public Plan editPlan(Long planId, Plan updatedPlan) {
        Optional<Plan> existingPlan = planRepository.findById(planId);
        if (existingPlan.isPresent()) {
            Plan planToUpdate = existingPlan.get();
            // Update the fields as needed
            planToUpdate.setPlanType(updatedPlan.getPlanType());
            planToUpdate.setPlanName(updatedPlan.getPlanName());
            planToUpdate.setPlanValidity(updatedPlan.getPlanValidity());
            planToUpdate.setPlanDetails(updatedPlan.getPlanDetails());
            planToUpdate.setPlanPrice(updatedPlan.getPlanPrice());
            return planRepository.save(planToUpdate);
        } else {
            // Handle error, throw exception, or return appropriate response
            return null;
        }
    }

    public void deletePlan(Long planId) {
        planRepository.deleteById(planId);
    }
}
