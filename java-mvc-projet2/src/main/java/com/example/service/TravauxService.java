package com.example.service;

import com.example.model.Travaux;
import com.example.repository.TravauxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour la gestion des travaux
 */
@Service
public class TravauxService {
    
    @Autowired
    private TravauxRepository travauxRepository;
    
    /**
     * Récupère tous les travaux
     */
    public List<Travaux> getAllTravaux() {
        return travauxRepository.findAll();
    }
    
    /**
     * Récupère un travail par son ID
     */
    public Travaux getTravauxById(int id) {
        return travauxRepository.findById(id).orElse(null);
    }
    
    /**
     * Crée un nouveau travail
     */
    public Travaux createTravaux(Travaux travaux) {
        return travauxRepository.save(travaux);
    }
    
    /**
     * Met à jour un travail
     */
    public Travaux updateTravaux(Travaux travaux) {
        return travauxRepository.save(travaux);
    }
    
    /**
     * Supprime un travail
     */
    public void deleteTravaux(int id) {
        travauxRepository.deleteById(id);
    }
}
