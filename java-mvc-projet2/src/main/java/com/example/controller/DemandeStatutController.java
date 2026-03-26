package com.example.controller;

import com.example.model.DemandeStatut;
import com.example.service.DemandeStatutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller pour gérer les statuts de demande
 */
@Controller
public class DemandeStatutController {
    
    @Autowired
    private DemandeStatutService demandeStatutService;
    
    @GetMapping("/demandeStatut")
    public String handleStatut(@RequestParam(value = "demandeId", required = false) Integer demandeId,
                               @RequestParam(value = "nouveauStatutId", required = false) Integer nouveauStatutId,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        
        // Si c'est un changement de statut
        if (demandeId != null && nouveauStatutId != null) {
            try {
                demandeStatutService.ajouterStatutHistorique(demandeId, nouveauStatutId);
                redirectAttributes.addFlashAttribute("message", "Statut mis à jour avec succès");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour du statut: " + e.getMessage());
            }
            return "redirect:/demande";
        }
        
        // Si c'est consultation de l'historique
        if (demandeId != null) {
            List<DemandeStatut> statuts = demandeStatutService.getStatutsByDemande(demandeId);
            model.addAttribute("statuts", statuts);
            model.addAttribute("demandeId", demandeId);
            model.addAttribute("pageTitle", "Statuts de la Demande #" + demandeId);
            return "demandeStatut";
        } else {
            model.addAttribute("error", "ID de demande non spécifié");
            return "redirect:/demande";
        }
    }
}
