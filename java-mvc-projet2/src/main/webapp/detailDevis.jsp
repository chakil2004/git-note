<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.DetailDevis" %>
<%
    List<DetailDevis> details = (List<DetailDevis>) request.getAttribute("details");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PROJET2 - Détails de Devis</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <header class="header">
            <h1>Gestion des Détails de Devis</h1>
            <p>Liste de tous les détails de devis enregistrés</p>
        </header>
        
        <nav class="nav">
            <a href="${pageContext.request.contextPath}/">Accueil</a>
            <a href="${pageContext.request.contextPath}/client">Clients</a>
            <a href="${pageContext.request.contextPath}/demande">Demandes</a>
            <a href="${pageContext.request.contextPath}/devis">Devis</a>
            <a href="${pageContext.request.contextPath}/travaux">Travaux</a>
            <a href="${pageContext.request.contextPath}/typeDevis">Types Devis</a>
            <a href="${pageContext.request.contextPath}/statut">Statuts</a>
            <a href="${pageContext.request.contextPath}/statutTravaux">Statuts Travaux</a>
        </nav>
        
        <main class="main">
            <div class="actions">
                <h2>Ajouter un nouveau détail de devis</h2>
                <form action="${pageContext.request.contextPath}/detailDevis" method="post" class="form-inline">
                    <input type="hidden" name="action" value="add">
                    <div class="form-group">
                        <label for="devisId">ID Devis:</label>
                        <input type="number" id="devisId" name="devisId" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="libelle">Libellé:</label>
                        <input type="text" id="libelle" name="libelle" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="montant">Montant:</label>
                        <input type="number" id="montant" name="montant" step="0.01" class="form-control" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Ajouter le détail</button>
                </form>
            </div>
            
            <div class="table-container">
                <h2>Liste des détails de devis</h2>
                <% if (details != null && !details.isEmpty()) { %>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>ID Devis</th>
                                <th>Libellé</th>
                                <th>Montant</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (DetailDevis detail : details) { %>
                                <tr>
                                    <td><%= detail.getId() %></td>
                                    <td><%= detail.getDevisId() %></td>
                                    <td><%= detail.getLibelle() %></td>
                                    <td><%= detail.getMontant() %> €</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/detailDevis?action=edit&id=<%= detail.getId() %>" class="btn btn-sm btn-primary">Modifier</a>
                                        <a href="${pageContext.request.contextPath}/detailDevis?action=delete&id=<%= detail.getId() %>" class="btn btn-sm btn-danger" onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce détail de devis ?')">Supprimer</a>
                                    </td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                <% } else { %>
                    <div class="alert alert-info">
                        Aucun détail de devis trouvé.
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
            width: 150px;
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
