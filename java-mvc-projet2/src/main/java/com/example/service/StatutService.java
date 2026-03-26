package com.example.service;

import com.example.model.Statut;
import com.example.repository.StatutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour la gestion des statuts
 */
@Service
public class StatutService {
    
    @Autowired
    private StatutRepository statutRepository;
    
    /**
     * Récupère tous les statuts
     */
    public List<Statut> getAllStatuts() {
        return statutRepository.findAll();
    }
    
    /**
     * Récupère un statut par son ID
     */
    public Statut getStatutById(int id) {
        return statutRepository.findById(id).orElse(null);
    }
    
    /**
     * Crée un nouveau statut
     */
    public Statut createStatut(Statut statut) {
        return statutRepository.save(statut);
    }
    
    /**
     * Met à jour un statut
     */
    public Statut updateStatut(Statut statut) {
        return statutRepository.save(statut);
    }
    
    /**
     * Supprime un statut
     */
    public void deleteStatut(int id) {
        statutRepository.deleteById(id);
    }
}
