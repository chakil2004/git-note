package com.example.controller;

import com.example.model.Travaux;
import com.example.model.Demande;
import com.example.model.StatutTravaux;
import com.example.service.TravauxService;
import com.example.service.DemandeService;
import com.example.service.StatutTravauxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller pour gérer les travaux
 */
@Controller
public class TravauxController {
    
    @Autowired
    private TravauxService travauxService;
    
    @Autowired
    private DemandeService demandeService;
    
    @Autowired
    private StatutTravauxService statutTravauxService;
    
    @GetMapping("/travaux")
    public String listTravaux(@RequestParam(value = "action", required = false) String action,
                              @RequestParam(value = "id", required = false) Integer id,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        
        if ("edit".equals(action) && id != null) {
            Travaux travaux = travauxService.getTravauxById(id);
            if (travaux != null) {
                model.addAttribute("travaux", travaux);
                model.addAttribute("demandes", demandeService.getAllDemandes());
                model.addAttribute("statuts", statutTravauxService.getAllStatuts());
                return "travaux-form";
            }
        } else if ("delete".equals(action) && id != null) {
            travauxService.deleteTravaux(id);
            redirectAttributes.addFlashAttribute("message", "Travaux supprimés avec succès");
            return "redirect:/travaux";
        } else {
            List<Travaux> travauxList = travauxService.getAllTravaux();
            List<Demande> demandesList = demandeService.getAllDemandes();
            List<StatutTravaux> statutsList = statutTravauxService.getAllStatuts();
            
            model.addAttribute("travaux", travauxList);
            model.addAttribute("demandes", demandesList);
            model.addAttribute("statuts", statutsList);
            model.addAttribute("pageTitle", "Travaux");
            return "travaux";
        }
        
        return "redirect:/travaux";
    }
    
    @PostMapping("/travaux")
    public String handleTravaux(@RequestParam(value = "action") String action,
                               @RequestParam(value = "id", required = false) Integer id,
                               @RequestParam("demandeId") int demandeId,
                               @RequestParam("statutTravauxId") int statutTravauxId,
                               RedirectAttributes redirectAttributes) {
        
        if ("add".equals(action)) {
            Travaux travaux = new Travaux();
            
            // Créer les objets de relation
            com.example.model.Demande demande = new com.example.model.Demande();
            demande.setId(demandeId);
            travaux.setDemande(demande);
            
            com.example.model.StatutTravaux statutTravaux = new com.example.model.StatutTravaux();
            statutTravaux.setId(statutTravauxId);
            travaux.setStatutTravaux(statutTravaux);
            
            travauxService.createTravaux(travaux);
            redirectAttributes.addFlashAttribute("message", "Travaux créés avec succès");
            
        } else if ("update".equals(action) && id != null) {
            Travaux travaux = new Travaux();
            travaux.setId(id);
            
            // Créer les objets de relation
            com.example.model.Demande demande = new com.example.model.Demande();
            demande.setId(demandeId);
            travaux.setDemande(demande);
            
            com.example.model.StatutTravaux statutTravaux = new com.example.model.StatutTravaux();
            statutTravaux.setId(statutTravauxId);
            travaux.setStatutTravaux(statutTravaux);
            
            travauxService.updateTravaux(travaux);
            redirectAttributes.addFlashAttribute("message", "Travaux mis à jour avec succès");
        }
        
        return "redirect:/travaux";
    }
}
