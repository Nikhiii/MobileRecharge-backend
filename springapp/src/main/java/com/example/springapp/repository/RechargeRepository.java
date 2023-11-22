package com.example.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springapp.model.Recharge;

@Repository
public interface RechargeRepository extends JpaRepository<Recharge, Long> {
    // Add custom query methods if needed
}

