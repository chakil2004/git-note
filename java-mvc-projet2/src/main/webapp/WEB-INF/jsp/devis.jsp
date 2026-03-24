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
    <title>PROJET2 - Devis</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <header class="header">
            <h1>Gestion des Devis</h1>
            <p>Liste de tous les devis enregistrés</p>
        </header>
        
        <nav class="nav">
            <a href="${pageContext.request.contextPath}/">Accueil</a>
            <a href="${pageContext.request.contextPath}/client">Clients</a>
            <a href="${pageContext.request.contextPath}/demande">Demandes</a>
            <a href="${pageContext.request.contextPath}/devis" class="active">Devis</a>
            <a href="${pageContext.request.contextPath}/travaux">Travaux</a>
            <a href="${pageContext.request.contextPath}/typeDevis">Types Devis</a>
            <a href="${pageContext.request.contextPath}/statut">Statuts</a>
            <a href="${pageContext.request.contextPath}/statutTravaux">Statuts Travaux</a>
        </nav>
        
        <main class="main">
            <div class="actions">
                <h2>Ajouter un nouveau devis</h2>
                <form action="${pageContext.request.contextPath}/devis" method="post" class="form-inline">
                    <input type="hidden" name="action" value="add">
                    <div class="form-group">
                        <label for="demandeId">Demande:</label>
                        <select id="demandeId" name="demandeId" class="form-control" required>
                            <option value="">Sélectionner une demande...</option>
                            <% if (demandesList != null) { 
                                for (Demande demande : demandesList) { %>
                                    <option value="<%= demande.getId() %>"><%= demande.getDescription() %></option>
                                <% }
                            } %>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="typeDevisId">Type Devis:</label>
                        <select id="typeDevisId" name="typeDevisId" class="form-control" required>
                            <option value="">Sélectionner un type...</option>
                            <% if (typesList != null) { 
                                for (TypeDevis type : typesList) { %>
                                    <option value="<%= type.getId() %>"><%= type.getLibelle() %></option>
                                <% }
                            } %>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="statutId">Statut:</label>
                        <select id="statutId" name="statutId" class="form-control" required>
                            <option value="">Sélectionner un statut...</option>
                            <% if (statutsList != null) { 
                                for (Statut statut : statutsList) { %>
                                    <option value="<%= statut.getId() %>"><%= statut.getLibelle() %></option>
                                <% }
                            } %>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary">Ajouter le devis</button>
                </form>
            </div>
            
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
                                        if (type.getId() == devis.getTypeDevisId()) {
                                            typeLibelle = type.getLibelle();
                                            break;
                                        }
                                    }
                                }
                                
                                if (statutsList != null) {
                                    for (Statut statut : statutsList) {
                                        if (statut.getId() == devis.getStatutId()) {
                                            statutLibelle = statut.getLibelle();
                                            break;
                                        }
                                    }
                                }
                                
                                if (demandesList != null) {
                                    for (Demande demande : demandesList) {
                                        if (demande.getId() == devis.getDemandeId()) {
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
                                        <a href="${pageContext.request.contextPath}/devis?action=edit&id=<%= devis.getId() %>" class="btn btn-sm btn-primary">Modifier</a>
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
        .form-inline {
            display: flex;
            gap: 1rem;
            align-items: end;
            margin-bottom: 2rem;
            padding: 1rem;
            background: #f8f9fa;
            border-radius: 8px;
            flex-wrap: wrap;
        }
        
        .form-inline .form-group {
            margin-bottom: 0;
        }
        
        .form-inline .form-control {
            width: 120px;
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
        
        .table-container {
            margin-top: 2rem;
        }
        
        .actions {
            margin-bottom: 2rem;
        }
        
        .actions h2 {
            margin-bottom: 1rem;
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
