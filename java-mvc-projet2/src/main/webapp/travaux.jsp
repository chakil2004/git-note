<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Travaux" %>
<%@ page import="com.example.model.StatutTravaux" %>
<%@ page import="com.example.model.Demande" %>
<%
    List<Travaux> travauxList = (List<Travaux>) request.getAttribute("travaux");
    List<StatutTravaux> statutsList = (List<StatutTravaux>) request.getAttribute("statuts");
    List<Demande> demandesList = (List<Demande>) request.getAttribute("demandes");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PROJET2 - Travaux</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <header class="header">
            <h1>Gestion des Travaux</h1>
            <p>Liste de tous les travaux enregistrés</p>
        </header>
        
        <nav class="nav">
            <a href="${pageContext.request.contextPath}/">Accueil</a>
            <a href="${pageContext.request.contextPath}/client">Clients</a>
            <a href="${pageContext.request.contextPath}/demande">Demandes</a>
            <a href="${pageContext.request.contextPath}/devis">Devis</a>
            <a href="${pageContext.request.contextPath}/travaux" class="active">Travaux</a>
            <a href="${pageContext.request.contextPath}/typeDevis">Types Devis</a>
            <a href="${pageContext.request.contextPath}/statut">Statuts</a>
            <a href="${pageContext.request.contextPath}/statutTravaux">Statuts Travaux</a>
        </nav>
        
        <main class="main">
            <div class="actions">
                <h2>Ajouter un nouveau travaux</h2>
                <form action="${pageContext.request.contextPath}/travaux" method="post" class="form-inline">
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
                        <label for="statutTravauxId">Statut Travaux:</label>
                        <select id="statutTravauxId" name="statutTravauxId" class="form-control" required>
                            <option value="">Sélectionner un statut...</option>
                            <% if (statutsList != null) { 
                                for (StatutTravaux statut : statutsList) { %>
                                    <option value="<%= statut.getId() %>"><%= statut.getLibelle() %></option>
                                <% }
                            } %>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary">Ajouter le travaux</button>
                </form>
            </div>
            
            <div class="table-container">
                <h2>Liste des travaux</h2>
                <% if (travauxList != null && !travauxList.isEmpty()) { %>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Demande</th>
                                <th>Statut Travaux</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Travaux travaux : travauxList) { 
                                // Chercher le libellé du statut et la description de la demande
                                String statutLibelle = "Inconnu";
                                String demandeDescription = "Inconnue";
                                
                                if (statutsList != null) {
                                    for (StatutTravaux statut : statutsList) {
                                        if (statut.getId() == travaux.getStatutTravauxId()) {
                                            statutLibelle = statut.getLibelle();
                                            break;
                                        }
                                    }
                                }
                                
                                if (demandesList != null) {
                                    for (Demande demande : demandesList) {
                                        if (demande.getId() == travaux.getDemandeId()) {
                                            demandeDescription = demande.getDescription();
                                            break;
                                        }
                                    }
                                }
                            %>
                                <tr>
                                    <td><%= travaux.getId() %></td>
                                    <td><%= demandeDescription %></td>
                                    <td><%= statutLibelle %></td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/travaux?action=edit&id=<%= travaux.getId() %>" class="btn btn-sm btn-primary">Modifier</a>
                                        <a href="${pageContext.request.contextPath}/travaux?action=delete&id=<%= travaux.getId() %>" class="btn btn-sm btn-danger" onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce travaux ?')">Supprimer</a>
                                    </td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                <% } else { %>
                    <div class="alert alert-info">
                        Aucun travaux trouvé.
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
