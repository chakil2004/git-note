package com.example.controller;

import com.example.model.TypeDevis;
import com.example.service.TypeDevisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller pour gérer les types de devis
 */
@Controller
public class TypeDevisController {
    
    @Autowired
    private TypeDevisService typeDevisService;
    
    @GetMapping("/typeDevis")
    public String listTypes(@RequestParam(value = "action", required = false) String action,
                            @RequestParam(value = "id", required = false) Integer id,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        
        if ("edit".equals(action) && id != null) {
            TypeDevis type = typeDevisService.getTypeById(id);
            if (type != null) {
                model.addAttribute("type", type);
                return "typeDevis-form";
            }
        } else if ("delete".equals(action) && id != null) {
            typeDevisService.deleteType(id);
            redirectAttributes.addFlashAttribute("message", "Type supprimé avec succès");
            return "redirect:/typeDevis";
        } else {
            List<TypeDevis> types = typeDevisService.getAllTypes();
            model.addAttribute("types", types);
            model.addAttribute("pageTitle", "Types Devis");
            return "typeDevis";
        }
        
        return "redirect:/typeDevis";
    }
    
    @PostMapping("/typeDevis")
    public String handleType(@RequestParam(value = "action") String action,
                            @RequestParam(value = "id", required = false) Integer id,
                            @RequestParam("libelle") String libelle,
                            RedirectAttributes redirectAttributes) {
        
        if ("add".equals(action)) {
            TypeDevis type = new TypeDevis();
            type.setLibelle(libelle);
            
            typeDevisService.createType(type);
            redirectAttributes.addFlashAttribute("message", "Type créé avec succès");
            
        } else if ("update".equals(action) && id != null) {
            TypeDevis type = new TypeDevis();
            type.setId(id);
            type.setLibelle(libelle);
            
            typeDevisService.updateType(type);
            redirectAttributes.addFlashAttribute("message", "Type mis à jour avec succès");
        }
        
        return "redirect:/typeDevis";
    }
}
