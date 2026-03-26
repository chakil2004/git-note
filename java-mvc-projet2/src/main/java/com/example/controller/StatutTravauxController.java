package com.example.controller;

import com.example.model.StatutTravaux;
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
 * Controller pour gérer les statuts de travaux
 */
@Controller
public class StatutTravauxController {
    
    @Autowired
    private StatutTravauxService statutTravauxService;
    
    @GetMapping("/statutTravaux")
    public String listStatutsTravaux(@RequestParam(value = "action", required = false) String action,
                                     @RequestParam(value = "id", required = false) Integer id,
                                     Model model,
                                     RedirectAttributes redirectAttributes) {
        
        if ("edit".equals(action) && id != null) {
            StatutTravaux statut = statutTravauxService.getStatutById(id);
            if (statut != null) {
                model.addAttribute("statut", statut);
                return "statutTravaux-form";
            }
        } else if ("delete".equals(action) && id != null) {
            statutTravauxService.deleteStatut(id);
            redirectAttributes.addFlashAttribute("message", "Statut supprimé avec succès");
            return "redirect:/statutTravaux";
        } else {
            List<StatutTravaux> statuts = statutTravauxService.getAllStatuts();
            model.addAttribute("statuts", statuts);
            model.addAttribute("pageTitle", "Statuts Travaux");
            return "statutTravaux";
        }
        
        return "redirect:/statutTravaux";
    }
    
    @PostMapping("/statutTravaux")
    public String handleStatutTravaux(@RequestParam(value = "action") String action,
                                     @RequestParam(value = "id", required = false) Integer id,
                                     @RequestParam("libelle") String libelle,
                                     RedirectAttributes redirectAttributes) {
        
        if ("add".equals(action)) {
            StatutTravaux statut = new StatutTravaux();
            statut.setLibelle(libelle);
            
            statutTravauxService.createStatut(statut);
            redirectAttributes.addFlashAttribute("message", "Statut créé avec succès");
            
        } else if ("update".equals(action) && id != null) {
            StatutTravaux statut = new StatutTravaux();
            statut.setId(id);
            statut.setLibelle(libelle);
            
            statutTravauxService.updateStatut(statut);
            redirectAttributes.addFlashAttribute("message", "Statut mis à jour avec succès");
        }
        
        return "redirect:/statutTravaux";
    }
}
