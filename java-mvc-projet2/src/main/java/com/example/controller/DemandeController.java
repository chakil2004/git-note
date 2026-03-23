package com.example.controller;

import com.example.config.DatabaseConfig;
import com.example.model.Demande;
import com.example.model.Client;
import com.example.service.DemandeService;
import com.example.service.ClientService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller pour gérer les demandes de forage
 */
@WebServlet("/demande")
public class DemandeController extends HttpServlet {
    
    private DemandeService demandeService;
    private ClientService clientService;
    
    @Override
    public void init() {
        demandeService = new DemandeService();
        clientService = new ClientService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        try (Connection conn = DatabaseConfig.getConnection()) {
            if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Demande demande = demandeService.getDemandeById(conn, id);
                request.setAttribute("demande", demande);
                request.getRequestDispatcher("/demande-form.jsp").forward(request, response);
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                demandeService.deleteDemande(conn, id);
                response.sendRedirect(request.getContextPath() + "/demande");
            } else {
                // Charger la liste des demandes et des clients
                List<Demande> demandes = demandeService.getAllDemandes(conn);
                List<Client> clients = clientService.getAllClients(conn);
                
                request.setAttribute("demandes", demandes);
                request.setAttribute("clients", clients);
                request.setAttribute("pageTitle", "Demandes de Forage");
                request.getRequestDispatcher("/demandes.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Erreur de base de données", e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        try (Connection conn = DatabaseConfig.getConnection()) {
            if ("add".equals(action)) {
                Demande demande = new Demande();
                demande.setClientId(Integer.parseInt(request.getParameter("clientId")));
                demande.setDateDemande(java.time.LocalDateTime.now());
                demande.setDescription(request.getParameter("description"));
                demande.setLieu(request.getParameter("lieu"));
                
                demandeService.createDemande(conn, demande);
                
            } else if ("update".equals(action)) {
                Demande demande = new Demande();
                demande.setId(Integer.parseInt(request.getParameter("id")));
                demande.setClientId(Integer.parseInt(request.getParameter("clientId")));
                demande.setDateDemande(java.time.LocalDateTime.parse(request.getParameter("dateDemande")));
                demande.setDescription(request.getParameter("description"));
                demande.setLieu(request.getParameter("lieu"));
                
                demandeService.updateDemande(conn, demande);
            }
            
            response.sendRedirect(request.getContextPath() + "/demande");
            
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'opération", e);
        }
    }
}
