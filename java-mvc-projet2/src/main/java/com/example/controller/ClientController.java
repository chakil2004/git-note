package com.example.controller;

import com.example.model.Client;
import com.example.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller pour gérer les clients
 */
@Controller
public class ClientController {
    
    @Autowired
    private ClientService clientService;
    
    @GetMapping("/client")
    public String listClients(@RequestParam(value = "action", required = false) String action,
                              @RequestParam(value = "id", required = false) Integer id,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        
        if ("edit".equals(action) && id != null) {
            Client client = clientService.getClientById(id);
            if (client != null) {
                model.addAttribute("client", client);
                return "client-form";
            }
        } else if ("delete".equals(action) && id != null) {
            clientService.deleteClient(id);
            redirectAttributes.addFlashAttribute("message", "Client supprimé avec succès");
            return "redirect:/client";
        } else {
            List<Client> clients = clientService.getAllClients();
            model.addAttribute("clients", clients);
            model.addAttribute("pageTitle", "Clients");
            return "clients";
        }
        
        return "redirect:/client";
    }
    
    @PostMapping("/client")
    public String handleClient(@RequestParam(value = "action") String action,
                               @RequestParam(value = "id", required = false) Integer id,
                               @RequestParam("nom") String nom,
                               @RequestParam("contact") String contact,
                               RedirectAttributes redirectAttributes) {
        
        if ("add".equals(action)) {
            Client client = new Client();
            client.setNom(nom);
            client.setContact(contact);
            clientService.createClient(client);
            redirectAttributes.addFlashAttribute("message", "Client créé avec succès");
            
        } else if ("update".equals(action) && id != null) {
            Client client = new Client();
            client.setId(id);
            client.setNom(nom);
            client.setContact(contact);
            clientService.updateClient(client);
            redirectAttributes.addFlashAttribute("message", "Client mis à jour avec succès");
        }
        
        return "redirect:/client";
    }
    
    @GetMapping("/client/new")
    public String newClientForm(Model model) {
        model.addAttribute("client", new Client());
        return "client-form";
    }
}
