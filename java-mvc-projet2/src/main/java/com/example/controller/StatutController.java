package com.example.controller;

import com.example.model.Statut;
import com.example.service.StatutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller pour gérer les statuts
 */
@Controller
public class StatutController {
    
    @Autowired
    private StatutService statutService;
    
    @GetMapping("/statut")
    public String listStatuts(@RequestParam(value = "action", required = false) String action,
                              @RequestParam(value = "id", required = false) Integer id,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        
        if ("edit".equals(action) && id != null) {
            Statut statut = statutService.getStatutById(id);
            if (statut != null) {
                model.addAttribute("statut", statut);
                return "statut-form";
            }
        } else if ("delete".equals(action) && id != null) {
            statutService.deleteStatut(id);
            redirectAttributes.addFlashAttribute("message", "Statut supprimé avec succès");
            return "redirect:/statut";
        } else {
            List<Statut> statuts = statutService.getAllStatuts();
            model.addAttribute("statuts", statuts);
            model.addAttribute("pageTitle", "Statuts");
            return "statuts";
        }
        
        return "redirect:/statut";
    }
    
    @PostMapping("/statut")
    public String handleStatut(@RequestParam(value = "action") String action,
                              @RequestParam(value = "id", required = false) Integer id,
                              @RequestParam("libelle") String libelle,
                              RedirectAttributes redirectAttributes) {
        
        if ("add".equals(action)) {
            Statut statut = new Statut();
            statut.setLibelle(libelle);
            
            statutService.createStatut(statut);
            redirectAttributes.addFlashAttribute("message", "Statut créé avec succès");
            
        } else if ("update".equals(action) && id != null) {
            Statut statut = new Statut();
            statut.setId(id);
            statut.setLibelle(libelle);
            
            statutService.updateStatut(statut);
            redirectAttributes.addFlashAttribute("message", "Statut mis à jour avec succès");
        }
        
        return "redirect:/statut";
    }
}
