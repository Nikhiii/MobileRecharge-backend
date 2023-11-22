package com.example.springapp.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    // private Long applicationId;
    private String status;
    private Double totalAmount;
    private Date paymentDate;
    private String modeOfPayment;

    @OneToOne
    @JoinColumn(name = "recharge_id", referencedColumnName = "rechargeId")
    @JsonBackReference
    private Recharge recharge;


    public Recharge getRecharge() {
        return recharge;
    }

    public void setRecharge(Recharge recharge) {
        this.recharge = recharge;
    }

    public Long getPaymentId() {
        return id;
    }
    public void setPaymentId(Long paymentId) {
        this.id = paymentId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public Date getPaymentDate() {
        return paymentDate;
    }
    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
    public String getModeOfPayment() {
        return modeOfPayment;
    }
    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public Payment(Long id, Long userId, String status, Double totalAmount, Date paymentDate,
            String modeOfPayment) {
        this.id = id;
        this.userId = userId;
        // this.applicationId = applicationId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.paymentDate = paymentDate;
        this.modeOfPayment = modeOfPayment;
    }
    
    public Payment() {
        
    }
    
}
