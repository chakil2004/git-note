package com.example.controller;

import com.example.config.DatabaseConfig;
import com.example.model.StatutTravaux;
import com.example.service.StatutTravauxService;

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
 * Controller pour gérer les statuts de travaux
 */
@WebServlet("/statutTravaux")
public class StatutTravauxController extends HttpServlet {
    
    private StatutTravauxService statutTravauxService;
    
    @Override
    public void init() {
        statutTravauxService = new StatutTravauxService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        try (Connection conn = DatabaseConfig.getConnection()) {
            if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                StatutTravaux statut = statutTravauxService.getStatutById(conn, id);
                request.setAttribute("statut", statut);
                request.getRequestDispatcher("/statutTravaux-form.jsp").forward(request, response);
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                statutTravauxService.deleteStatut(conn, id);
                response.sendRedirect(request.getContextPath() + "/statutTravaux");
            } else {
                List<StatutTravaux> statuts = statutTravauxService.getAllStatuts(conn);
                request.setAttribute("statuts", statuts);
                request.setAttribute("pageTitle", "Statuts de Travaux");
                request.getRequestDispatcher("/statutTravaux.jsp").forward(request, response);
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
                StatutTravaux statut = new StatutTravaux();
                statut.setLibelle(request.getParameter("libelle"));
                
                statutTravauxService.createStatut(conn, statut);
                
            } else if ("update".equals(action)) {
                StatutTravaux statut = new StatutTravaux();
                statut.setId(Integer.parseInt(request.getParameter("id")));
                statut.setLibelle(request.getParameter("libelle"));
                
                statutTravauxService.updateStatut(conn, statut);
            }
            
            response.sendRedirect(request.getContextPath() + "/statutTravaux");
            
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'opération", e);
        }
    }
}
