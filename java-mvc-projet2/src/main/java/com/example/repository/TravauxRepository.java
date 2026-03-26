package com.example.repository;

import com.example.model.Travaux;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravauxRepository extends JpaRepository<Travaux, Integer> {
}
