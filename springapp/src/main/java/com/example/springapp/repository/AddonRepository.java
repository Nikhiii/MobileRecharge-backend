package com.example.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springapp.model.Addon;

@Repository
public interface AddonRepository extends JpaRepository<Addon, Long> {
}

