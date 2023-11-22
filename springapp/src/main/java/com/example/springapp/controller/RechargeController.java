package com.example.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.springapp.model.Addon;
import com.example.springapp.model.Payment;
import com.example.springapp.model.Plan;
import com.example.springapp.model.Recharge;
import com.example.springapp.service.AddonService;
import com.example.springapp.service.PlanService;
import com.example.springapp.service.RechargeService;
import com.example.springapp.repository.PaymentRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class RechargeController {

    @Autowired
    private RechargeService rechargeService;

    @Autowired
    private PlanService planService;

    @Autowired
    private AddonService AddonService;

    @Autowired
    private PaymentRepository paymentRepository;



    @PostMapping("/addrecharge")
    public ResponseEntity<Recharge> addRecharge(@RequestBody Recharge recharge) {
        Recharge addedRecharge = rechargeService.addRecharge(recharge);
        return ResponseEntity.ok(addedRecharge);
    }

    @GetMapping("/getallplan")
    public ResponseEntity<List<Plan>> getAllPlans() {
        List<Plan> plans = planService.getAllPlans();
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/getalladdons")
    public ResponseEntity<List<Addon>> getAllAddons() {
        List<Addon> addons = AddonService.getAllAddons();
        return ResponseEntity.ok(addons);
    }

    @GetMapping("/getrecharge/{rechargeId}")
    public ResponseEntity<Recharge> getRechargeById(@PathVariable Long rechargeId) {
    Optional<Recharge> recharge = rechargeService.getRechargeById(rechargeId);
    if (recharge.isPresent()) {
        return ResponseEntity.ok(recharge.get());
    } else {
        return ResponseEntity.notFound().build();
    }
}
    

    // @PutMapping("/editrecharge/{rechargeId}")
    // public ResponseEntity<Recharge> editRecharge(
    //         @PathVariable Long rechargeId,
    //         @RequestBody Recharge updatedRecharge) {
    //     Recharge editedRecharge = rechargeService.editRecharge(rechargeId, updatedRecharge);
    //     if (editedRecharge != null) {
    //         return ResponseEntity.ok(editedRecharge);
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    // @DeleteMapping("/deleterecharge/{rechargeId}")
    // public ResponseEntity<Void> deleteRecharge(@PathVariable Long rechargeId) {
    //     rechargeService.deleteRecharge(rechargeId);
    //     return ResponseEntity.noContent().build();
    // }


    @PostMapping("/make-payment")
	public ResponseEntity<String> savePayment(@RequestBody Payment a){
			
		boolean s= paymentRepository.save(a)!= null ? true : false;
		if(s) {
			return new ResponseEntity<>("Payment success", HttpStatus.OK);
		}
		return new ResponseEntity<>("Payment failure", HttpStatus.NOT_FOUND);
	}
}

