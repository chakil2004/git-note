package com.example.controller;

import com.example.config.DatabaseConfig;
import com.example.model.DetailDevis;
import com.example.service.DetailDevisService;

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
 * Controller pour gérer les détails de devis
 */
@WebServlet("/detailDevis")
public class DetailDevisController extends HttpServlet {
    
    private DetailDevisService detailDevisService;
    
    @Override
    public void init() {
        detailDevisService = new DetailDevisService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        try (Connection conn = DatabaseConfig.getConnection()) {
            if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                DetailDevis detail = detailDevisService.getDetailById(conn, id);
                request.setAttribute("detail", detail);
                request.getRequestDispatcher("/detailDevis-form.jsp").forward(request, response);
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                detailDevisService.deleteDetail(conn, id);
                response.sendRedirect(request.getContextPath() + "/detailDevis");
            } else {
                List<DetailDevis> details = detailDevisService.getAllDetails(conn);
                request.setAttribute("details", details);
                request.setAttribute("pageTitle", "Détails de Devis");
                request.getRequestDispatcher("/detailDevis.jsp").forward(request, response);
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
                DetailDevis detail = new DetailDevis();
                detail.setDevisId(Integer.parseInt(request.getParameter("devisId")));
                detail.setLibelle(request.getParameter("libelle"));
                detail.setMontant(new java.math.BigDecimal(request.getParameter("montant")));
                
                detailDevisService.createDetail(conn, detail);
                
            } else if ("update".equals(action)) {
                DetailDevis detail = new DetailDevis();
                detail.setId(Integer.parseInt(request.getParameter("id")));
                detail.setDevisId(Integer.parseInt(request.getParameter("devisId")));
                detail.setLibelle(request.getParameter("libelle"));
                detail.setMontant(new java.math.BigDecimal(request.getParameter("montant")));
                
                detailDevisService.updateDetail(conn, detail);
            }
            
            response.sendRedirect(request.getContextPath() + "/detailDevis");
            
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'opération", e);
        }
    }
}
