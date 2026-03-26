package com.example.repository;

import com.example.model.DetailDevis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailDevisRepository extends JpaRepository<DetailDevis, Integer> {
}
