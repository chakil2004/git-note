package com.example.controller;

import com.example.model.Demande;
import com.example.model.Client;
import com.example.service.DemandeService;
import com.example.service.ClientService;
import com.example.service.DemandeStatutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * Controller pour gérer les demandes de forage
 */
@Controller
public class DemandeController {
    
    @Autowired
    private DemandeService demandeService;
    
    @Autowired
    private ClientService clientService;
    
    @Autowired
    private DemandeStatutService demandeStatutService;
    
    @GetMapping("/demande")
    public String listDemandes(@RequestParam(value = "action", required = false) String action,
                               @RequestParam(value = "id", required = false) Integer id,
                               Model model,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttributes) {
        
        if ("edit".equals(action) && id != null) {
            Demande demande = demandeService.getDemandeById(id);
            if (demande != null) {
                model.addAttribute("demande", demande);
                model.addAttribute("clients", clientService.getAllClients());
                return "demande-form";
            }
        } else if ("delete".equals(action) && id != null) {
            demandeService.deleteDemande(id);
            redirectAttributes.addFlashAttribute("message", "Demande supprimée avec succès");
            return "redirect:/demande";
        } else {
            // Charger la liste des demandes et des clients
            List<Demande> demandes = demandeService.getAllDemandes();
            List<Client> clients = clientService.getAllClients();
            
            // Ajouter les statuts actuels pour chaque demande
            for (Demande demande : demandes) {
                com.example.model.DemandeStatut dernierStatut = demandeStatutService.getDernierStatut(demande.getId());
                // On peut ajouter le statut comme attribut de la demande ou le passer séparément
                request.setAttribute("statut_" + demande.getId(), dernierStatut);
            }
            
            model.addAttribute("demandes", demandes);
            model.addAttribute("clients", clients);
            model.addAttribute("pageTitle", "Demandes de Forage");
            return "demandes";
        }
        
        return "redirect:/demande";
    }
    
    @PostMapping("/demande")
    public String handleDemande(@RequestParam(value = "action") String action,
                                @RequestParam(value = "id", required = false) Integer id,
                                @RequestParam("clientId") int clientId,
                                @RequestParam("description") String description,
                                @RequestParam("lieu") String lieu,
                                RedirectAttributes redirectAttributes) {
        
        if ("add".equals(action)) {
            Demande demande = new Demande();
            
            // Créer l'objet de relation Client
            com.example.model.Client client = new com.example.model.Client();
            client.setId(clientId);
            demande.setClient(client);
            
            demande.setDateDemande(java.time.LocalDateTime.now());
            demande.setDescription(description);
            demande.setLieu(lieu);
            
            try {
                // Utiliser la transaction intégrée dans createDemande()
                demandeService.createDemande(demande);
                redirectAttributes.addFlashAttribute("message", "Demande créée avec succès et statut initial assigné");
            } catch (RuntimeException e) {
                redirectAttributes.addFlashAttribute("error", "Erreur lors de la création: " + e.getMessage());
            }
            
        } else if ("update".equals(action) && id != null) {
            Demande demande = new Demande();
            demande.setId(id);
            
            // Créer l'objet de relation Client
            com.example.model.Client client = new com.example.model.Client();
            client.setId(clientId);
            demande.setClient(client);
            
            demande.setDateDemande(java.time.LocalDateTime.now());
            demande.setDescription(description);
            demande.setLieu(lieu);
            
            demandeService.updateDemande(demande);
            redirectAttributes.addFlashAttribute("message", "Demande mise à jour avec succès");
        }
        
        return "redirect:/demande";
    }
    
    @GetMapping("/demande/new")
    public String newDemandeForm(Model model) {
        model.addAttribute("demande", new Demande());
        model.addAttribute("clients", clientService.getAllClients());
        return "demande-form";
    }
}
