package com.example.springapp.model;

import java.util.List;

import javax.persistence.*;

@Entity
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    private String planType;
    private String planName;
    private String planValidity;
    private String planDetails;
    private double planPrice;

    // Getters and setters, constructors, other methods

    // Relationships
    @OneToMany(mappedBy = "plan")
    private List<Recharge> recharges;
    
    public Long getPlanId() {
        return planId;
    }
    public void setPlanId(Long planId) {
        this.planId = planId;
    }
    public String getPlanType() {
        return planType;
    }
    public void setPlanType(String planType) {
        this.planType = planType;
    }
    public String getPlanName() {
        return planName;
    }
    public void setPlanName(String planName) {
        this.planName = planName;
    }
    public String getPlanValidity() {
        return planValidity;
    }
    public void setPlanValidity(String planValidity) {
        this.planValidity = planValidity;
    }
    public String getPlanDetails() {
        return planDetails;
    }
    public void setPlanDetails(String planDetails) {
        this.planDetails = planDetails;
    }
    public double getPlanPrice() {
        return planPrice;
    }
    public void setPlanPrice(double planPrice) {
        this.planPrice = planPrice;
    }
    
    public Plan(Long planId, String planType, String planName, String planValidity, String planDetails,
            double planPrice) {
        this.planId = planId;
        this.planType = planType;
        this.planName = planName;
        this.planValidity = planValidity;
        this.planDetails = planDetails;
        this.planPrice = planPrice;
    }

    public Plan() {
    }   

    // Getters and Setters

    
}


