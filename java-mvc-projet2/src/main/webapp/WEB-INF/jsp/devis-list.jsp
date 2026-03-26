<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Devis" %>
<%@ page import="com.example.model.TypeDevis" %>
<%@ page import="com.example.model.Statut" %>
<%@ page import="com.example.model.Demande" %>
<%
    List<Devis> devisList = (List<Devis>) request.getAttribute("devis");
    List<TypeDevis> typesList = (List<TypeDevis>) request.getAttribute("types");
    List<Statut> statutsList = (List<Statut>) request.getAttribute("statuts");
    List<Demande> demandesList = (List<Demande>) request.getAttribute("demandes");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PROJET2 - Liste des Devis</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <header class="header">
            <div class="header-content">
                <div>
                    <h1>Liste des Devis</h1>
                    <p>Tous les devis enregistrés dans le système</p>
                </div>
                <div class="header-actions">
                    <a href="${pageContext.request.contextPath}/devis/create" class="btn btn-success">Créer un nouveau devis</a>
                </div>
            </div>
        </header>
        
        <nav class="nav">
            <a href="${pageContext.request.contextPath}/">Accueil</a>
            <a href="${pageContext.request.contextPath}/client">Clients</a>
            <a href="${pageContext.request.contextPath}/demande">Demandes</a>
            <a href="${pageContext.request.contextPath}/devis/list" class="active">Devis</a>
            <a href="${pageContext.request.contextPath}/travaux">Travaux</a>
            <a href="${pageContext.request.contextPath}/typeDevis">Types Devis</a>
            <a href="${pageContext.request.contextPath}/statut">Statuts</a>
            <a href="${pageContext.request.contextPath}/statutTravaux">Statuts Travaux</a>
        </nav>
        
        <main class="main">
            <div class="table-container">
                <h2>Liste des devis</h2>
                <% if (devisList != null && !devisList.isEmpty()) { %>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Demande</th>
                                <th>Type Devis</th>
                                <th>Date Devis</th>
                                <th>Statut</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Devis devis : devisList) { 
                                // Chercher les libellés
                                String typeLibelle = "Inconnu";
                                String statutLibelle = "Inconnu";
                                String demandeDescription = "Inconnue";
                                
                                if (typesList != null) {
                                    for (TypeDevis type : typesList) {
                                        if (type.getId() == devis.getTypeDevis().getId()) {
                                            typeLibelle = type.getLibelle();
                                            break;
                                        }
                                    }
                                }
                                
                                if (statutsList != null) {
                                    for (Statut statut : statutsList) {
                                        if (statut.getId() == devis.getStatut().getId()) {
                                            statutLibelle = statut.getLibelle();
                                            break;
                                        }
                                    }
                                }
                                
                                if (demandesList != null) {
                                    for (Demande demande : demandesList) {
                                        if (demande.getId() == devis.getDemande().getId()) {
                                            demandeDescription = demande.getDescription();
                                            break;
                                        }
                                    }
                                }
                            %>
                                <tr>
                                    <td><%= devis.getId() %></td>
                                    <td><%= demandeDescription %></td>
                                    <td><%= typeLibelle %></td>
                                    <td><%= devis.getDateDevis() %></td>
                                    <td><%= statutLibelle %></td>
                                    <td>
                                        
                                        <a href="${pageContext.request.contextPath}/devis?action=delete&id=<%= devis.getId() %>" class="btn btn-sm btn-danger" onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce devis ?')">Supprimer</a>
                                    </td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                <% } else { %>
                    <div class="alert alert-info">
                        Aucun devis trouvé.
                    </div>
                <% } %>
            </div>
        </main>
        
        <footer class="footer">
            <p>&copy; ETU003653</p>
        </footer>
    </div>
    
    <style>
        .header-content {
            display: flex;
            justify-content: space-between;
            align-items: center;
            width: 100%;
        }
        
        .header-actions {
            display: flex;
            gap: 1rem;
        }
        
        .table-container {
            margin-top: 2rem;
        }
        
        .table-container h2 {
            margin-bottom: 1.5rem;
            color: #333;
        }
        
        .table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        .table th,
        .table td {
            padding: 1rem;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        
        .table th {
            background: #f8f9fa;
            font-weight: 600;
            color: #333;
        }
        
        .table tbody tr:hover {
            background: #f8f9fa;
        }
        
        .btn {
            padding: 0.75rem 1.5rem;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            font-size: 1rem;
            margin-right: 0.5rem;
        }
        
        .btn-success {
            background: #28a745;
            color: white;
        }
        
        .btn-success:hover {
            background: #1e7e34;
        }
        
        .btn-sm {
            padding: 0.25rem 0.5rem;
            font-size: 0.875rem;
            text-decoration: none;
            border-radius: 3px;
            margin-right: 0.25rem;
        }
        
        .btn-sm.btn-primary {
            background: #007bff;
            color: white;
        }
        
        .btn-sm.btn-primary:hover {
            background: #0056b3;
        }
        
        .btn-sm.btn-danger {
            background: #dc3545;
            color: white;
        }
        
        .btn-sm.btn-danger:hover {
            background: #c82333;
        }
        
        .alert {
            padding: 1rem;
            border-radius: 4px;
            margin: 1rem 0;
        }
        
        .alert-info {
            background: #d1ecf1;
            color: #0c5460;
            border: 1px solid #bee5eb;
        }
    </style>
</body>
</html>
