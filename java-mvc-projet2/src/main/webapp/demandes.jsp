<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Demande" %>
<%@ page import="com.example.model.Client" %>
<%
    List<Demande> demandes = (List<Demande>) request.getAttribute("demandes");
    List<Client> clients = (List<Client>) request.getAttribute("clients");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PROJET2 - Demandes de Forage</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <header class="header">
            <h1>Gestion des Demandes de Forage</h1>
            <p>Liste de toutes les demandes de forage enregistrées</p>
        </header>
        
        <nav class="nav">
            <a href="${pageContext.request.contextPath}/">Accueil</a>
            <a href="${pageContext.request.contextPath}/client">Clients</a>
            <a href="${pageContext.request.contextPath}/demande" class="active">Demandes</a>
            <a href="${pageContext.request.contextPath}/devis">Devis</a>
            <a href="${pageContext.request.contextPath}/travaux">Travaux</a>
            <a href="${pageContext.request.contextPath}/typeDevis">Types Devis</a>
            <a href="${pageContext.request.contextPath}/statut">Statuts</a>
            <a href="${pageContext.request.contextPath}/statutTravaux">Statuts Travaux</a>
        </nav>
        
        <main class="main">
            <div class="actions">
                <h2>Ajouter une nouvelle demande</h2>
                <form action="${pageContext.request.contextPath}/demande" method="post" class="form-inline">
                    <input type="hidden" name="action" value="add">
                    <div class="form-group">
                        <label for="clientId">Client:</label>
                        <select id="clientId" name="clientId" class="form-control" required>
                            <option value="">Sélectionner un client...</option>
                            <% if (clients != null) { 
                                for (Client client : clients) { %>
                                    <option value="<%= client.getId() %>"><%= client.getNom() %></option>
                                <% }
                            } %>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="description">Description:</label>
                        <textarea id="description" name="description" class="form-control" rows="3" required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="lieu">Lieu:</label>
                        <input type="text" id="lieu" name="lieu" class="form-control" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Ajouter la demande</button>
                </form>
            </div>
            
            <div class="table-container">
                <h2>Liste des demandes</h2>
                <% if (demandes != null && !demandes.isEmpty()) { %>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Client</th>
                                <th>Date Demande</th>
                                <th>Lieu</th>
                                <th>Description</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Demande demande : demandes) { %>
                                <tr>
                                    <td><%= demande.getId() %></td>
                                    <td><%= demande.getClientId() %></td>
                                    <td><%= demande.getDateDemande() %></td>
                                    <td><%= demande.getLieu() %></td>
                                    <td><%= demande.getDescription().length() > 50 ? demande.getDescription().substring(0, 50) + "..." : demande.getDescription() %></td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/demande?action=edit&id=<%= demande.getId() %>" class="btn btn-sm btn-primary">Modifier</a>
                                        <a href="${pageContext.request.contextPath}/demande?action=delete&id=<%= demande.getId() %>" class="btn btn-sm btn-danger" onclick="return confirm('Êtes-vous sûr de vouloir supprimer cette demande ?')">Supprimer</a>
                                    </td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                <% } else { %>
                    <div class="alert alert-info">
                        Aucune demande de forage trouvée.
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
            width: 200px;
        }
        
        .form-inline textarea {
            width: 300px;
            min-height: 80px;
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
