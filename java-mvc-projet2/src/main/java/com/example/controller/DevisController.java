package com.example.controller;

import com.example.config.DatabaseConfig;
import com.example.model.Devis;
import com.example.model.TypeDevis;
import com.example.model.Statut;
import com.example.model.Demande;
import com.example.service.DevisService;
import com.example.service.TypeDevisService;
import com.example.service.StatutService;
import com.example.service.DemandeService;

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
 * Controller pour gérer les devis
 */
@WebServlet("/devis")
public class DevisController extends HttpServlet {
    
    private DevisService devisService;
    private TypeDevisService typeDevisService;
    private StatutService statutService;
    private DemandeService demandeService;
    
    @Override
    public void init() {
        devisService = new DevisService();
        typeDevisService = new TypeDevisService();
        statutService = new StatutService();
        demandeService = new DemandeService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        try (Connection conn = DatabaseConfig.getConnection()) {
            if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Devis devis = devisService.getDevisById(conn, id);
                request.setAttribute("devis", devis);
                request.getRequestDispatcher("/devis-form.jsp").forward(request, response);
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                devisService.deleteDevis(conn, id);
                response.sendRedirect(request.getContextPath() + "/devis");
            } else {
                // Charger la liste des devis, types de devis, statuts et demandes
                List<Devis> devisList = devisService.getAllDevis(conn);
                List<TypeDevis> typesList = typeDevisService.getAllTypes(conn);
                List<Statut> statutsList = statutService.getAllStatuts(conn);
                List<Demande> demandesList = demandeService.getAllDemandes(conn);
                
                request.setAttribute("devis", devisList);
                request.setAttribute("types", typesList);
                request.setAttribute("statuts", statutsList);
                request.setAttribute("demandes", demandesList);
                request.setAttribute("pageTitle", "Devis");
                request.getRequestDispatcher("/devis.jsp").forward(request, response);
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
                Devis devis = new Devis();
                devis.setDemandeId(Integer.parseInt(request.getParameter("demandeId")));
                devis.setTypeDevisId(Integer.parseInt(request.getParameter("typeDevisId")));
                devis.setDateDevis(java.time.LocalDateTime.now());
                devis.setStatutId(Integer.parseInt(request.getParameter("statutId")));
                
                devisService.createDevis(conn, devis);
                
            } else if ("update".equals(action)) {
                Devis devis = new Devis();
                devis.setId(Integer.parseInt(request.getParameter("id")));
                devis.setDemandeId(Integer.parseInt(request.getParameter("demandeId")));
                devis.setTypeDevisId(Integer.parseInt(request.getParameter("typeDevisId")));
                devis.setDateDevis(java.time.LocalDateTime.parse(request.getParameter("dateDevis")));
                devis.setStatutId(Integer.parseInt(request.getParameter("statutId")));
                
                devisService.updateDevis(conn, devis);
            }
            
            response.sendRedirect(request.getContextPath() + "/devis");
            
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'opération", e);
        }
    }
}
