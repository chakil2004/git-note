package com.example.service;

import com.example.model.DetailDevis;
import com.example.repository.DetailDevisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour la gestion des détails de devis
 */
@Service
public class DetailDevisService {
    
    @Autowired
    private DetailDevisRepository detailDevisRepository;
    
    /**
     * Récupère tous les détails de devis
     */
    public List<DetailDevis> getAllDetails() {
        return detailDevisRepository.findAll();
    }
    
    /**
     * Récupère un détail par son ID
     */
    public DetailDevis getDetailById(int id) {
        return detailDevisRepository.findById(id).orElse(null);
    }
    
    /**
     * Récupère les détails par devis
     */
    public List<DetailDevis> getDetailsByDevis(int devisId) {
        return detailDevisRepository.findAll().stream()
                .filter(detail -> detail.getDevisId() == devisId)
                .toList();
    }
    
    /**
     * Crée un nouveau détail
     */
    public DetailDevis createDetail(DetailDevis detail) {
        return detailDevisRepository.save(detail);
    }
    
    /**
     * Met à jour un détail
     */
    public DetailDevis updateDetail(DetailDevis detail) {
        return detailDevisRepository.save(detail);
    }
    
    /**
     * Supprime un détail
     */
    public void deleteDetail(int id) {
        detailDevisRepository.deleteById(id);
    }
}
