package com.example.controller;

import com.example.config.DatabaseConfig;
import com.example.model.TypeDevis;
import com.example.service.TypeDevisService;

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
 * Controller pour gérer les types de devis
 */
@WebServlet("/typeDevis")
public class TypeDevisController extends HttpServlet {
    
    private TypeDevisService typeDevisService;
    
    @Override
    public void init() {
        typeDevisService = new TypeDevisService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String action = request.getParameter("action");
            
            if ("add".equals(action) || "edit".equals(action)) {
                // Afficher le formulaire
                if ("edit".equals(action)) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    try (Connection conn = DatabaseConfig.getConnection()) {
                        TypeDevis type = typeDevisService.getTypeById(conn, id);
                        request.setAttribute("type", type);
                    }
                }
                request.getRequestDispatcher("typeDevis-form.jsp").forward(request, response);
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                try (Connection conn = DatabaseConfig.getConnection()) {
                    typeDevisService.deleteType(conn, id);
                }
                response.sendRedirect("typeDevis");
            } else {
                // Afficher la liste des types de devis
                try (Connection conn = DatabaseConfig.getConnection()) {
                    List<TypeDevis> types = typeDevisService.getAllTypes(conn);
                    request.setAttribute("types", types);
                }
                request.getRequestDispatcher("typeDevis.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        try (Connection conn = DatabaseConfig.getConnection()) {
            if ("add".equals(action)) {
                TypeDevis type = new TypeDevis();
                type.setLibelle(request.getParameter("libelle"));
                
                typeDevisService.createType(conn, type);
                
            } else if ("update".equals(action)) {
                TypeDevis type = new TypeDevis();
                type.setId(Integer.parseInt(request.getParameter("id")));
                type.setLibelle(request.getParameter("libelle"));
                
                typeDevisService.updateType(conn, type);
            }
            
            response.sendRedirect(request.getContextPath() + "/typeDevis");
            
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'opération", e);
        }
    }
}
