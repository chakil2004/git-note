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

import java.time.LocalDateTime;

/**
 * Service pour la gestion des statuts de demande avec transactions
 */
@Service
public class DemandeStatutService {
    
    @Autowired
    private DemandeRepository demandeRepository;
    
    @Autowired
    private DemandeStatutRepository demandeStatutRepository;
    
    @Autowired
    private StatutRepository statutRepository;
    
    /**
     * Ajoute un nouveau statut à l'historique d'une demande
     * Chaque changement de statut est conservé pour la traçabilité
     */
    @Transactional(rollbackFor = Exception.class)
    public DemandeStatut ajouterStatutHistorique(int demandeId, int nouveauStatutId) throws Exception {
        try {
            // 1. Récupérer la demande
            Demande demande = demandeRepository.findById(demandeId)
                    .orElseThrow(() -> new Exception("Demande non trouvée avec l'ID: " + demandeId));
            
            // 2. Récupérer le nouveau statut
            Statut nouveauStatut = statutRepository.findById(nouveauStatutId)
                    .orElseThrow(() -> new Exception("Statut non trouvé avec l'ID: " + nouveauStatutId));
            
            // 3. Créer la nouvelle entrée de statut
            DemandeStatut demandeStatut = new DemandeStatut();
            demandeStatut.setDemande(demande);
            demandeStatut.setStatut(nouveauStatut);
            demandeStatut.setDate(LocalDateTime.now());
            
            // 4. Sauvegarder
            return demandeStatutRepository.save(demandeStatut);
            
        } catch (Exception e) {
            throw new Exception("Erreur lors de la mise à jour du statut: " + e.getMessage(), e);
        }
    }
    
    /**
     * Récupère tous les statuts d'une demande
     */
    public java.util.List<DemandeStatut> getStatutsByDemande(int demandeId) {
        return demandeStatutRepository.findByDemande_Id(demandeId);
    }
    
    /**
     * Récupère le dernier statut d'une demande
     */
    public DemandeStatut getDernierStatut(int demandeId) {
        java.util.List<DemandeStatut> statuts = demandeStatutRepository.findByDemande_Id(demandeId);
        return statuts.isEmpty() ? null : statuts.get(statuts.size() - 1);
    }
}
