package com.example.config;

import com.example.model.Client;
import com.example.model.TypeDevis;
import com.example.model.Statut;
import com.example.model.StatutTravaux;
import com.example.repository.ClientRepository;
import com.example.repository.TypeDevisRepository;
import com.example.repository.StatutRepository;
import com.example.repository.StatutTravauxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private TypeDevisRepository typeDevisRepository;
    
    @Autowired
    private StatutRepository statutRepository;
    
    @Autowired
    private StatutTravauxRepository statutTravauxRepository;

    @Override
    public void run(String... args) throws Exception {
        // Vérifier si les données existent déjà
        if (typeDevisRepository.count() == 0) {
            initializeTypeDevis();
        }
        
        if (statutRepository.count() == 0) {
            initializeStatuts();
        }
        
        if (statutTravauxRepository.count() == 0) {
            initializeStatutsTravaux();
        }
        
        if (clientRepository.count() == 0) {
            initializeClients();
        }
    }
    
    private void initializeTypeDevis() {
        TypeDevis etude = new TypeDevis();
        etude.setLibelle("Etude");
        typeDevisRepository.save(etude);
        
        TypeDevis forage = new TypeDevis();
        forage.setLibelle("Forage");
        typeDevisRepository.save(forage);
        
        System.out.println("✅ Types de devis initialisés");
    }
    
    private void initializeStatuts() {
        Statut enAttente = new Statut();
        enAttente.setLibelle("En attente");
        statutRepository.save(enAttente);
        
        Statut accepte = new Statut();
        accepte.setLibelle("Accepté");
        statutRepository.save(accepte);
        
        Statut refuse = new Statut();
        refuse.setLibelle("Refusé");
        statutRepository.save(refuse);
        
        System.out.println("✅ Statuts initialisés");
    }
    
    private void initializeStatutsTravaux() {
        StatutTravaux debut = new StatutTravaux();
        debut.setLibelle("Début");
        statutTravauxRepository.save(debut);
        
        StatutTravaux recuperation = new StatutTravaux();
        recuperation.setLibelle("Récupération eau");
        statutTravauxRepository.save(recuperation);
        
        StatutTravaux test = new StatutTravaux();
        test.setLibelle("Test sanitaire");
        statutTravauxRepository.save(test);
        
        System.out.println("✅ Statuts travaux initialisés");
    }
    
    private void initializeClients() {
        Client client1 = new Client();
        client1.setNom("Entreprise Alpha");
        client1.setContact("contact@alpha.com");
        clientRepository.save(client1);
        
        Client client2 = new Client();
        client2.setNom("Société Beta");
        client2.setContact("contact@beta.fr");
        clientRepository.save(client2);
        
        Client client3 = new Client();
        client3.setNom("Gamma SARL");
        client3.setContact("gamma@sarl.com");
        clientRepository.save(client3);
        
        System.out.println("✅ Clients initialisés");
    }
}
