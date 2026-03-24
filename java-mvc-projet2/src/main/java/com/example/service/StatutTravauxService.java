package com.example.service;

import com.example.model.StatutTravaux;
import com.example.repository.StatutTravauxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour la gestion des statuts de travaux
 */
@Service
public class StatutTravauxService {
    
    @Autowired
    private StatutTravauxRepository statutTravauxRepository;
    
    /**
     * Récupère tous les statuts de travaux
     */
    public List<StatutTravaux> getAllStatuts() {
        return statutTravauxRepository.findAll();
    }
    
    /**
     * Récupère un statut par son ID
     */
    public StatutTravaux getStatutById(int id) {
        return statutTravauxRepository.findById(id).orElse(null);
    }
    
    /**
     * Crée un nouveau statut
     */
    public StatutTravaux createStatut(StatutTravaux statut) {
        return statutTravauxRepository.save(statut);
    }
    
    /**
     * Met à jour un statut
     */
    public StatutTravaux updateStatut(StatutTravaux statut) {
        return statutTravauxRepository.save(statut);
    }
    
    /**
     * Supprime un statut
     */
    public void deleteStatut(int id) {
        statutTravauxRepository.deleteById(id);
    }
}
