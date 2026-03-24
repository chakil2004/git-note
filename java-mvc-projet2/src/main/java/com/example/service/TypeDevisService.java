package com.example.service;

import com.example.model.TypeDevis;
import com.example.repository.TypeDevisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour la gestion des types de devis
 */
@Service
public class TypeDevisService {
    
    @Autowired
    private TypeDevisRepository typeDevisRepository;
    
    /**
     * Récupère tous les types de devis
     */
    public List<TypeDevis> getAllTypes() {
        return typeDevisRepository.findAll();
    }
    
    /**
     * Récupère un type par son ID
     */
    public TypeDevis getTypeById(int id) {
        return typeDevisRepository.findById(id).orElse(null);
    }
    
    /**
     * Crée un nouveau type
     */
    public TypeDevis createType(TypeDevis type) {
        return typeDevisRepository.save(type);
    }
    
    /**
     * Met à jour un type
     */
    public TypeDevis updateType(TypeDevis type) {
        return typeDevisRepository.save(type);
    }
    
    /**
     * Supprime un type
     */
    public void deleteType(int id) {
        typeDevisRepository.deleteById(id);
    }
}
