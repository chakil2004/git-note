package com.example.repository;

import com.example.model.Statut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatutRepository extends JpaRepository<Statut, Integer> {
}
