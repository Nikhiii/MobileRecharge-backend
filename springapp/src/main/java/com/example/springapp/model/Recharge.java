package com.example.springapp.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Recharge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rechargeId;
    private String mobile;
    private double rechargePrice;
    private String status;
    private Date date;

    // Getters and setters, constructors, other methods

    // Relationships
    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
      name = "recharge_addons", 
      joinColumns = @JoinColumn(name = "recharge_id"), 
      inverseJoinColumns = @JoinColumn(name = "addon_id"))
    private List<Addon> addons;

    @OneToOne(mappedBy = "recharge", cascade = CascadeType.ALL)
    @JsonBackReference
    private Payment payment;

    public Payment getPayment() {
        return payment;
    }
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    
    public Long getRechargeId() {
        return rechargeId;
    }
    public void setRechargeId(Long rechargeId) {
        this.rechargeId = rechargeId;
    }
    public Plan getPlan() {
        return plan;
    }
    public void setPlan(Plan plan) {
        this.plan = plan;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public double getRechargePrice() {
        return rechargePrice;
    }
    public void setRechargePrice(double rechargePrice) {
        this.rechargePrice = rechargePrice;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public Recharge(Long rechargeId, String mobile, double rechargePrice, String status,
            Date date) {
        this.rechargeId = rechargeId;
        this.mobile = mobile;
        this.rechargePrice = rechargePrice;
        this.status = status;
        this.date = date;
    }

    
    public Recharge() {
    }

    // Getters and Setters
}

