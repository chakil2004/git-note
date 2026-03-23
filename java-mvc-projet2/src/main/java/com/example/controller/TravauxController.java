package com.example.controller;

import com.example.config.DatabaseConfig;
import com.example.model.Travaux;
import com.example.model.StatutTravaux;
import com.example.model.Demande;
import com.example.service.TravauxService;
import com.example.service.StatutTravauxService;
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
 * Controller pour gérer les travaux
 */
@WebServlet("/travaux")
public class TravauxController extends HttpServlet {
    
    private TravauxService travauxService;
    private StatutTravauxService statutTravauxService;
    private DemandeService demandeService;
    
    @Override
    public void init() {
        travauxService = new TravauxService();
        statutTravauxService = new StatutTravauxService();
        demandeService = new DemandeService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        try (Connection conn = DatabaseConfig.getConnection()) {
            if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Travaux travaux = travauxService.getTravauxById(conn, id);
                request.setAttribute("travaux", travaux);
                request.getRequestDispatcher("/travaux-form.jsp").forward(request, response);
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                travauxService.deleteTravaux(conn, id);
                response.sendRedirect(request.getContextPath() + "/travaux");
            } else {
                // Charger la liste des travaux, statuts et demandes
                List<Travaux> travauxList = travauxService.getAllTravaux(conn);
                List<StatutTravaux> statutsList = statutTravauxService.getAllStatuts(conn);
                List<Demande> demandesList = demandeService.getAllDemandes(conn);
                
                request.setAttribute("travaux", travauxList);
                request.setAttribute("statuts", statutsList);
                request.setAttribute("demandes", demandesList);
                request.setAttribute("pageTitle", "Travaux");
                request.getRequestDispatcher("/travaux.jsp").forward(request, response);
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
                Travaux travaux = new Travaux();
                travaux.setDemandeId(Integer.parseInt(request.getParameter("demandeId")));
                travaux.setStatutTravauxId(Integer.parseInt(request.getParameter("statutTravauxId")));
                
                travauxService.createTravaux(conn, travaux);
                
            } else if ("update".equals(action)) {
                Travaux travaux = new Travaux();
                travaux.setId(Integer.parseInt(request.getParameter("id")));
                travaux.setDemandeId(Integer.parseInt(request.getParameter("demandeId")));
                travaux.setStatutTravauxId(Integer.parseInt(request.getParameter("statutTravauxId")));
                
                travauxService.updateTravaux(conn, travaux);
            }
            
            response.sendRedirect(request.getContextPath() + "/travaux");
            
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'opération", e);
        }
    }
}
