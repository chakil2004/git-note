package com.example.service;

import com.example.model.Client;
import com.example.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour la gestion des clients
 */
@Service
public class ClientService {
    
    @Autowired
    private ClientRepository clientRepository;
    
    /**
     * Récupère tous les clients
     */
    public List<Client> getAllClients() {
        return clientRepository.findAllByOrderByNomAsc();
    }
    
    /**
     * Récupère un client par son ID
     */
    public Client getClientById(int id) {
        return clientRepository.findById(id).orElse(null);
    }
    
    /**
     * Crée un nouveau client
     */
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }
    
    /**
     * Met à jour un client
     */
    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }
    
    /**
     * Supprime un client
     */
    public void deleteClient(int id) {
        clientRepository.deleteById(id);
    }
}
