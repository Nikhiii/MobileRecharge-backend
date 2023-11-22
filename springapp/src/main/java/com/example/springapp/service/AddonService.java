package com.example.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springapp.model.Addon;
import com.example.springapp.model.Payment;
import com.example.springapp.repository.AddonRepository;
import com.example.springapp.repository.PaymentRepository;

@Service
public class AddonService {

    @Autowired
    private AddonRepository addonRepository;

    @Autowired
    private PaymentRepository paymentRepository;


    public List<Addon> getAllAddons() {
        return addonRepository.findAll();
    }

    public Addon addAddon(Addon addon) {
        return addonRepository.save(addon);
    }

    public Optional<Addon> getAddonById(Long addonId) {
        return addonRepository.findById(addonId);
    }

    public Addon editAddon(Long addonId, Addon updatedAddon) {
        Optional<Addon> existingAddon = addonRepository.findById(addonId);
        if (existingAddon.isPresent()) {
            Addon addonToUpdate = existingAddon.get();
            // Update the fields as needed
            addonToUpdate.setAddonName(updatedAddon.getAddonName());
            addonToUpdate.setAddonPrice(updatedAddon.getAddonPrice());
            addonToUpdate.setAddonDetails(updatedAddon.getAddonDetails());
            addonToUpdate.setAddonValidity(updatedAddon.getAddonValidity());
            return addonRepository.save(addonToUpdate);
        } else {
            // Handle error, throw exception, or return appropriate response
            return null;
        }
    }

    public void deleteAddon(Long addonId) {
        addonRepository.deleteById(addonId);
    }
    
    public Optional<Payment> getPaymentdetails(Long paymentId)
    {
        return paymentRepository.findById(paymentId);
    }
}
