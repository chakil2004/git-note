package com.example.service;

import com.example.model.Demande;
import com.example.model.DemandeStatut;
import com.example.model.Statut;
import com.example.repository.DemandeRepository;
import com.example.repository.DemandeStatutRepository;
import com.example.repository.StatutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service pour la gestion des demandes de forage
 */
@Service
public class DemandeService {
    
    @Autowired
    private DemandeRepository demandeRepository;
    
    @Autowired
    private DemandeStatutRepository demandeStatutRepository;
    
    @Autowired
    private StatutRepository statutRepository;
    
    /**
     * Récupère toutes les demandes
     */
    public List<Demande> getAllDemandes() {
        return demandeRepository.findAll();
    }
    
    /**
     * Récupère une demande par son ID
     */
    public Demande getDemandeById(int id) {
        return demandeRepository.findById(id).orElse(null);
    }
    
    /**
     * Crée une nouvelle demande avec son statut initial dans une transaction atomique
     * Si une erreur se produit, tout est annulé (rollback)
     */
    @Transactional(rollbackFor = Exception.class)
    public Demande createDemande(Demande demande) {
        try {
            // 1. Créer et sauvegarder la demande
            Demande savedDemande = demandeRepository.save(demande);
            
            // 2. Récupérer l'ID généré automatiquement
            int demandeId = savedDemande.getId();  // ID généré par AUTO_INCREMENT
            
            // 3. Récupérer le statut initial "En attente" (ID = 1)
            Statut statut = statutRepository.findById(1)
                    .orElseThrow(() -> new Exception("Statut 'En attente' non trouvé"));
            
            // 4. Créer l'entrée de statut pour la demande
            DemandeStatut demandeStatut = new DemandeStatut();
            demandeStatut.setDemande(savedDemande);  // Contient déjà l'ID
            demandeStatut.setStatut(statut);
            demandeStatut.setDate(java.time.LocalDateTime.now());
            
            // 5. Insérer dans demande_statut
            demandeStatutRepository.save(demandeStatut);
            
            return savedDemande;
            
        } catch (Exception e) {
            // En cas d'erreur, la transaction sera automatiquement rollbackée
            throw new RuntimeException("Erreur lors de la création de la demande avec statut: " + e.getMessage(), e);
        }
    }
    
    /**
     * Met à jour une demande
     */
    public Demande updateDemande(Demande demande) {
        return demandeRepository.save(demande);
    }
    
    /**
     * Supprime une demande
     */
    public void deleteDemande(int id) {
        demandeRepository.deleteById(id);
    }
}
