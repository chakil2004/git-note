package com.example.controller;

import com.example.model.Devis;
import com.example.model.TypeDevis;
import com.example.model.Statut;
import com.example.model.Demande;
import com.example.service.DevisService;
import com.example.service.TypeDevisService;
import com.example.service.StatutService;
import com.example.service.DemandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller pour gérer les devis
 */
@Controller
public class DevisController {
    
    @Autowired
    private DevisService devisService;
    
    @Autowired
    private TypeDevisService typeDevisService;
    
    @Autowired
    private StatutService statutService;
    
    @Autowired
    private DemandeService demandeService;
    
    @GetMapping("/devis")
    public String listDevis(@RequestParam(value = "action", required = false) String action,
                           @RequestParam(value = "id", required = false) Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        
        if ("edit".equals(action) && id != null) {
            Devis devis = devisService.getDevisById(id);
            if (devis != null) {
                model.addAttribute("devis", devis);
                model.addAttribute("types", typeDevisService.getAllTypes());
                model.addAttribute("statuts", statutService.getAllStatuts());
                model.addAttribute("demandes", demandeService.getAllDemandes());
                return "devis-form";
            }
        } else if ("delete".equals(action) && id != null) {
            devisService.deleteDevis(id);
            redirectAttributes.addFlashAttribute("message", "Devis supprimé avec succès");
            return "redirect:/devis";
        } else {
            // Charger la liste des devis, types de devis, statuts et demandes
            List<Devis> devisList = devisService.getAllDevis();
            List<TypeDevis> typesList = typeDevisService.getAllTypes();
            List<Statut> statutsList = statutService.getAllStatuts();
            List<Demande> demandesList = demandeService.getAllDemandes();
            
            model.addAttribute("devis", devisList);
            model.addAttribute("types", typesList);
            model.addAttribute("statuts", statutsList);
            model.addAttribute("demandes", demandesList);
            model.addAttribute("pageTitle", "Devis");
            return "devis";
        }
        
        return "redirect:/devis";
    }
    
    @PostMapping("/devis")
    public String handleDevis(@RequestParam(value = "action") String action,
                             @RequestParam(value = "id", required = false) Integer id,
                             @RequestParam("demandeId") int demandeId,
                             @RequestParam("typeDevisId") int typeDevisId,
                             @RequestParam("statutId") int statutId,
                             RedirectAttributes redirectAttributes) {
        
        if ("add".equals(action)) {
            Devis devis = new Devis();
            
            // Créer les objets de relation
            com.example.model.Demande demande = new com.example.model.Demande();
            demande.setId(demandeId);
            devis.setDemande(demande);
            
            com.example.model.TypeDevis typeDevis = new com.example.model.TypeDevis();
            typeDevis.setId(typeDevisId);
            devis.setTypeDevis(typeDevis);
            
            com.example.model.Statut statut = new com.example.model.Statut();
            statut.setId(statutId);
            devis.setStatut(statut);
            
            devis.setDateDevis(java.time.LocalDateTime.now());
            
            devisService.createDevis(devis);
            redirectAttributes.addFlashAttribute("message", "Devis créé avec succès");
            
        } else if ("update".equals(action) && id != null) {
            Devis devis = new Devis();
            devis.setId(id);
            
            // Créer les objets de relation
            com.example.model.Demande demande = new com.example.model.Demande();
            demande.setId(demandeId);
            devis.setDemande(demande);
            
            com.example.model.TypeDevis typeDevis = new com.example.model.TypeDevis();
            typeDevis.setId(typeDevisId);
            devis.setTypeDevis(typeDevis);
            
            com.example.model.Statut statut = new com.example.model.Statut();
            statut.setId(statutId);
            devis.setStatut(statut);
            
            devis.setDateDevis(java.time.LocalDateTime.now());
            
            devisService.updateDevis(devis);
            redirectAttributes.addFlashAttribute("message", "Devis mis à jour avec succès");
        }
        
        return "redirect:/devis";
    }
    
    @GetMapping("/devis/new")
    public String newDevisForm(Model model) {
        model.addAttribute("devis", new Devis());
        model.addAttribute("types", typeDevisService.getAllTypes());
        model.addAttribute("statuts", statutService.getAllStatuts());
        model.addAttribute("demandes", demandeService.getAllDemandes());
        return "devis-form";
    }
}
