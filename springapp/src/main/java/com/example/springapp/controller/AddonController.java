package com.example.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.springapp.model.Addon;
import com.example.springapp.model.Payment;
import com.example.springapp.repository.PaymentRepository;
// import com.example.springapp.model.Payment;
import com.example.springapp.repository.PlanRepository;
import com.example.springapp.service.AddonService;

import java.util.List;
import com.example.springapp.model.Plan;

@RestController
@RequestMapping("/admin")
public class AddonController {

    @Autowired
    private AddonService addonService;

    @Autowired
    private PaymentRepository paymentRepo;

    @GetMapping("/getaddon")
    public ResponseEntity<List<Addon>> getAllAddons() {
        List<Addon> addons = addonService.getAllAddons();
        return ResponseEntity.ok(addons);
    }

    @PostMapping("/addaddon")
    public ResponseEntity<Addon> addAddon(@RequestBody Addon addon) {
        Addon addedAddon = addonService.addAddon(addon);
        return ResponseEntity.ok(addedAddon);
    }

    @PutMapping("/editaddon/{addonId}")
    public ResponseEntity<Addon> editAddon(@PathVariable Long addonId, @RequestBody Addon updatedAddon) {
        Addon editedAddon = addonService.editAddon(addonId, updatedAddon);
        return editedAddon != null ? ResponseEntity.ok(editedAddon) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteaddon/{addonId}")
    public ResponseEntity<Void> deleteAddon(@PathVariable Long addonId) {
        addonService.deleteAddon(addonId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getpaymenthistory")
    public ResponseEntity<List<Payment>> getPayments() {
        List<Payment> payments = paymentRepo.findAll();
        return new ResponseEntity<List<Payment>>(payments,HttpStatus.OK);
    }
}

