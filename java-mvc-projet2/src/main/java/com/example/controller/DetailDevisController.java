package com.example.controller;

import com.example.model.DetailDevis;
import com.example.model.Devis;
import com.example.service.DetailDevisService;
import com.example.service.DevisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller pour gérer les détails de devis
 */
@Controller
public class DetailDevisController {
    
    @Autowired
    private DetailDevisService detailDevisService;
    
    @Autowired
    private DevisService devisService;
    
    @GetMapping("/detailDevis")
    public String listDetails(@RequestParam(value = "action", required = false) String action,
                              @RequestParam(value = "id", required = false) Integer id,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        
        if ("edit".equals(action) && id != null) {
            DetailDevis detail = detailDevisService.getDetailById(id);
            if (detail != null) {
                model.addAttribute("detail", detail);
                model.addAttribute("devis", devisService.getAllDevis());
                return "detailDevis-form";
            }
        } else if ("delete".equals(action) && id != null) {
            detailDevisService.deleteDetail(id);
            redirectAttributes.addFlashAttribute("message", "Détail supprimé avec succès");
            return "redirect:/detailDevis";
        } else {
            List<DetailDevis> details = detailDevisService.getAllDetails();
            model.addAttribute("details", details);
            model.addAttribute("devis", devisService.getAllDevis());
            model.addAttribute("pageTitle", "Détails Devis");
            return "detailDevis";
        }
        
        return "redirect:/detailDevis";
    }
    
    @PostMapping("/detailDevis")
    public String handleDetail(@RequestParam(value = "action") String action,
                              @RequestParam(value = "id", required = false) Integer id,
                              @RequestParam("devisId") int devisId,
                              @RequestParam("libelle") String libelle,
                              @RequestParam("prixUnitaire") java.math.BigDecimal prixUnitaire,
                              @RequestParam("quantite") int quantite,
                              RedirectAttributes redirectAttributes) {
        
        if ("add".equals(action)) {
            DetailDevis detail = new DetailDevis();
            
            // Créer l'objet de relation Devis
            com.example.model.Devis devis = new com.example.model.Devis();
            devis.setId(devisId);
            detail.setDevis(devis);
            
            detail.setLibelle(libelle);
            detail.setPrixUnitaire(prixUnitaire);
            detail.setQuantite(quantite);
            
            detailDevisService.createDetail(detail);
            redirectAttributes.addFlashAttribute("message", "Détail créé avec succès");
            
        } else if ("update".equals(action) && id != null) {
            DetailDevis detail = new DetailDevis();
            detail.setId(id);
            
            // Créer l'objet de relation Devis
            com.example.model.Devis devis = new com.example.model.Devis();
            devis.setId(devisId);
            detail.setDevis(devis);
            
            detail.setLibelle(libelle);
            detail.setPrixUnitaire(prixUnitaire);
            detail.setQuantite(quantite);
            
            detailDevisService.updateDetail(detail);
            redirectAttributes.addFlashAttribute("message", "Détail mis à jour avec succès");
        }
        
        return "redirect:/detailDevis";
    }
}
