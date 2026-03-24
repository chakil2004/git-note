package com.example.repository;

import com.example.model.DemandeStatut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeStatutRepository extends JpaRepository<DemandeStatut, Integer> {
    // Méthode correcte pour JPA avec @ManyToOne
    List<DemandeStatut> findByDemande_Id(int demandeId);
}
