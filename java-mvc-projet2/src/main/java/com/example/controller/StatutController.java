package com.example.controller;

import com.example.config.DatabaseConfig;
import com.example.model.Statut;
import com.example.service.StatutService;

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
 * Controller pour gérer les statuts
 */
@WebServlet("/statut")
public class StatutController extends HttpServlet {
    
    private StatutService statutService;
    
    @Override
    public void init() {
        statutService = new StatutService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        try (Connection conn = DatabaseConfig.getConnection()) {
            if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Statut statut = statutService.getStatutById(conn, id);
                request.setAttribute("statut", statut);
                request.getRequestDispatcher("/statut-form.jsp").forward(request, response);
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                statutService.deleteStatut(conn, id);
                response.sendRedirect(request.getContextPath() + "/statut");
            } else {
                List<Statut> statuts = statutService.getAllStatuts(conn);
                request.setAttribute("statuts", statuts);
                request.setAttribute("pageTitle", "Statuts");
                request.getRequestDispatcher("/statuts.jsp").forward(request, response);
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
                Statut statut = new Statut();
                statut.setLibelle(request.getParameter("libelle"));
                
                statutService.createStatut(conn, statut);
                
            } else if ("update".equals(action)) {
                Statut statut = new Statut();
                statut.setId(Integer.parseInt(request.getParameter("id")));
                statut.setLibelle(request.getParameter("libelle"));
                
                statutService.updateStatut(conn, statut);
            }
            
            response.sendRedirect(request.getContextPath() + "/statut");
            
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'opération", e);
        }
    }
}
