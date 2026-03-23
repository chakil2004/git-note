package com.example.controller;

import com.example.config.DatabaseConfig;
import com.example.model.Client;
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
 * Controller pour gérer les clients
 */
@WebServlet("/client")
public class ClientController extends HttpServlet {
    
    private ClientService clientService;
    
    @Override
    public void init() {
        clientService = new ClientService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        try (Connection conn = DatabaseConfig.getConnection()) {
            if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Client client = clientService.getClientById(conn, id);
                request.setAttribute("client", client);
                request.getRequestDispatcher("/client-form.jsp").forward(request, response);
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                clientService.deleteClient(conn, id);
                response.sendRedirect(request.getContextPath() + "/client");
            } else {
                List<Client> clients = clientService.getAllClients(conn);
                request.setAttribute("clients", clients);
                request.setAttribute("pageTitle", "Clients");
                request.getRequestDispatcher("/clients.jsp").forward(request, response);
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
                Client client = new Client();
                client.setNom(request.getParameter("nom"));
                client.setContact(request.getParameter("contact"));
                
                clientService.createClient(conn, client);
                
            } else if ("update".equals(action)) {
                Client client = new Client();
                client.setId(Integer.parseInt(request.getParameter("id")));
                client.setNom(request.getParameter("nom"));
                client.setContact(request.getParameter("contact"));
                
                clientService.updateClient(conn, client);
            }
            
            response.sendRedirect(request.getContextPath() + "/client");
            
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'opération", e);
        }
    }
}
