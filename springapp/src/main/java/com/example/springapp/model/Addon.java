package com.example.springapp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Addon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addonId;

    private String addonName;
    private double addonPrice;
    private String addonDetails;
    private String addonValidity;

    // Getters and setters, constructors, other methods

    // Relationships
    @ManyToMany(mappedBy = "addons")
    private List<Recharge> recharges;

    
    public Long getAddonId() {
        return addonId;
    }
    public void setAddonId(Long addonId) {
        this.addonId = addonId;
    }
    public String getAddonName() {
        return addonName;
    }
    public void setAddonName(String addonName) {
        this.addonName = addonName;
    }
    public double getAddonPrice() {
        return addonPrice;
    }
    public void setAddonPrice(double addonPrice) {
        this.addonPrice = addonPrice;
    }
    public String getAddonDetails() {
        return addonDetails;
    }
    public void setAddonDetails(String addonDetails) {
        this.addonDetails = addonDetails;
    }
    public String getAddonValidity() {
        return addonValidity;
    }
    public void setAddonValidity(String addonValidity) {
        this.addonValidity = addonValidity;
    }

    public Addon(Long addonId, String addonName, double addonPrice, String addonDetails, String addonValidity) {
        this.addonId = addonId;
        this.addonName = addonName;
        this.addonPrice = addonPrice;
        this.addonDetails = addonDetails;
        this.addonValidity = addonValidity;
    }


    public Addon() {
        
    }
    

    // Getters and Setters
}

