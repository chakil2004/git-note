package com.example.service;

import com.example.model.Devis;
import com.example.repository.DevisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour la gestion des devis
 */
@Service
public class DevisService {
    
    @Autowired
    private DevisRepository devisRepository;
    
    /**
     * Récupère tous les devis
     */
    public List<Devis> getAllDevis() {
        return devisRepository.findAll();
    }
    
    /**
     * Récupère un devis par son ID
     */
    public Devis getDevisById(int id) {
        return devisRepository.findById(id).orElse(null);
    }
    
    /**
     * Récupère les devis par demande
     */
    public List<Devis> getDevisByDemande(int demandeId) {
        return devisRepository.findAll().stream()
                .filter(devis -> devis.getDemandeId() == demandeId)
                .toList();
    }
    
    /**
     * Crée un nouveau devis
     */
    public Devis createDevis(Devis devis) {
        return devisRepository.save(devis);
    }
    
    /**
     * Met à jour un devis
     */
    public Devis updateDevis(Devis devis) {
        return devisRepository.save(devis);
    }
    
    /**
     * Supprime un devis
     */
    public void deleteDevis(int id) {
        devisRepository.deleteById(id);
    }
}
