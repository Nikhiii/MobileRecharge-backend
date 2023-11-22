package com.example.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springapp.model.Payment;
import com.example.springapp.model.Recharge;
import com.example.springapp.repository.PaymentRepository;
import com.example.springapp.repository.RechargeRepository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class RechargeService {

    @Autowired
    private RechargeRepository rechargeRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Recharge> getAllRecharges() {
        return rechargeRepository.findAll();
    }

    public Optional<Recharge> getRechargeById(Long rechargeId) {
        return rechargeRepository.findById(rechargeId);
    }

    public Recharge addRecharge(Recharge recharge) {
        return rechargeRepository.save(recharge);
    }

    public Recharge editRecharge(Long rechargeId, Recharge updatedRecharge) {
        Optional<Recharge> existingRecharge = rechargeRepository.findById(rechargeId);
        if (existingRecharge.isPresent()) {
            Recharge rechargeToUpdate = existingRecharge.get();
            // Update the fields as needed
            rechargeToUpdate.setMobile(updatedRecharge.getMobile());
            rechargeToUpdate.setRechargePrice(updatedRecharge.getRechargePrice());
            rechargeToUpdate.setStatus(updatedRecharge.getStatus());
            // ... update other fields
            return rechargeRepository.save(rechargeToUpdate);
        } else {
            // Handle error, throw exception, or return appropriate response
            return null;
        }
    }

    public void deleteRecharge(Long rechargeId) {
        rechargeRepository.deleteById(rechargeId);
    }

    @Transactional
    public Payment makePayment(Payment payment) {
        Recharge recharge = payment.getRecharge();
    
        // Save the Recharge if it's a new instance
        if (recharge != null && recharge.getRechargeId() == null) {
            recharge.setPayment(payment);
            recharge = rechargeRepository.save(recharge);
        }
    
        // Set the Recharge in the Payment
        payment.setRecharge(recharge);
    
        // Save the Payment
        payment = paymentRepository.save(payment);
    
        return payment;
    }
}
