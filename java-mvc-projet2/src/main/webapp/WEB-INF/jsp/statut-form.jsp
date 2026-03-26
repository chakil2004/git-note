<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.model.Statut" %>
<%@ page import="com.example.service.StatutService" %>
<%@ page import="java.util.List" %>
<%
    Statut statut = (Statut) request.getAttribute("statut");
    boolean isEdit = statut != null;
    
    // Charger tous les statuts pour le menu déroulant
    StatutService statutService = new StatutService();
    List<Statut> statuts = null;
    try (java.sql.Connection conn = com.example.config.DatabaseConfig.getConnection()) {
        statuts = statutService.getAllStatuts(conn);
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PROJET2 - <%= isEdit ? "Modifier" : "Ajouter" %> un Statut</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <header class="header">
            <h1><%= isEdit ? "Modifier" : "Ajouter" %> un Statut</h1>
            <p><%= isEdit ? "Modifiez les informations du statut" : "Entrez les informations du nouveau statut" %></p>
        </header>
        
        <nav class="nav">
            <a href="${pageContext.request.contextPath}/">Accueil</a>
            <a href="${pageContext.request.contextPath}/client">Clients</a>
            <a href="${pageContext.request.contextPath}/demande">Demandes</a>
            <a href="${pageContext.request.contextPath}/devis">Devis</a>
            <a href="${pageContext.request.contextPath}/travaux">Travaux</a>
            <a href="${pageContext.request.contextPath}/typeDevis">Types Devis</a>
            <a href="${pageContext.request.contextPath}/statut" class="active">Statuts</a>
            <a href="${pageContext.request.contextPath}/statutTravaux">Statuts Travaux</a>
        </nav>
        
        <main class="main">
            <div class="form-container">
                <form action="${pageContext.request.contextPath}/statut" method="post" class="form">
                    <input type="hidden" name="action" value="<%= isEdit ? "update" : "add" %>">
                    <% if (isEdit) { %>
                        <input type="hidden" name="id" value="<%= statut.getId() %>">
                    <% } %>
                    
                    <div class="form-group">
                        <label for="libelle">Libellé:</label>
                        <input type="text" id="libelle" name="libelle" class="form-control" 
                               value="<%= isEdit ? statut.getLibelle() : "" %>" required>
                    </div>
                    
                    <div class="form-actions">
                        <button type="submit" class="btn">
                            <%= isEdit ? "Mettre à jour" : "Ajouter" %>
                        </button>
                        <a href="${pageContext.request.contextPath}/statut" class="btn">Annuler</a>
                    </div>
                </form>
            </div>
            
            <!-- Liste des statuts existants -->
            <div class="list-container">
                <h3>Statuts existants</h3>
                <% if (statuts != null && !statuts.isEmpty()) { %>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Libellé</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Statut s : statuts) { %>
                                <tr>
                                    <td><%= s.getId() %></td>
                                    <td><%= s.getLibelle() %></td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/statut?action=edit&id=<%= s.getId() %>" class="btn">Modifier</a>
                                        <a href="${pageContext.request.contextPath}/statut?action=delete&id=<%= s.getId() %>" class="btn" onclick="return confirm('Êtes-vous sûr ?')">Supprimer</a>
                                    </td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                <% } else { %>
                    <p>Aucun statut trouvé.</p>
                <% } %>
            </div>
        </main>
        
        <footer class="footer">
            <p>&copy; ETU003653</p>
        </footer>
    </div>
    
    <style>
        .form-container {
            max-width: 600px;
            margin: 2rem auto;
            padding: 2rem;
            border: 1px solid black;
        }
        
        .form {
            display: flex;
            flex-direction: column;
            gap: 1.5rem;
        }
        
        .form-group {
            display: flex;
            flex-direction: column;
        }
        
        .form-group label {
            margin-bottom: 0.5rem;
            font-weight: bold;
        }
        
        .form-control {
            padding: 0.75rem;
            border: 1px solid black;
            width: 100%;
        }
        
        .form-actions {
            display: flex;
            gap: 1rem;
            justify-content: flex-end;
            margin-top: 1rem;
        }
        
        .list-container {
            max-width: 800px;
            margin: 2rem auto;
            padding: 2rem;
            border: 1px solid black;
        }
        
        .list-container h3 {
            margin-bottom: 1rem;
        }
    </style>
</body>
</html>
