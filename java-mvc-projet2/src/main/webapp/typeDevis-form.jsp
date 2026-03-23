<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.model.TypeDevis" %>
<%@ page import="com.example.service.TypeDevisService" %>
<%@ page import="java.util.List" %>
<%
    TypeDevis type = (TypeDevis) request.getAttribute("type");
    boolean isEdit = type != null;
    
    // Charger tous les types de devis pour le menu déroulant
    TypeDevisService typeDevisService = new TypeDevisService();
    List<TypeDevis> types = null;
    try (java.sql.Connection conn = com.example.config.DatabaseConfig.getConnection()) {
        types = typeDevisService.getAllTypes(conn);
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PROJET2 - <%= isEdit ? "Modifier" : "Ajouter" %> un Type de Devis</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <header class="header">
            <h1><%= isEdit ? "Modifier" : "Ajouter" %> un Type de Devis</h1>
            <p><%= isEdit ? "Modifiez les informations du type de devis" : "Entrez les informations du nouveau type de devis" %></p>
        </header>
        
        <nav class="nav">
            <a href="${pageContext.request.contextPath}/">Accueil</a>
            <a href="${pageContext.request.contextPath}/client">Clients</a>
            <a href="${pageContext.request.contextPath}/demande">Demandes</a>
            <a href="${pageContext.request.contextPath}/devis">Devis</a>
            <a href="${pageContext.request.contextPath}/travaux">Travaux</a>
            <a href="${pageContext.request.contextPath}/typeDevis" class="active">Types Devis</a>
            <a href="${pageContext.request.contextPath}/statut">Statuts</a>
            <a href="${pageContext.request.contextPath}/statutTravaux">Statuts Travaux</a>
        </nav>
        
        <main class="main">
            <div class="form-container">
                <form action="${pageContext.request.contextPath}/typeDevis" method="post" class="form">
                    <input type="hidden" name="action" value="<%= isEdit ? "update" : "add" %>">
                    <% if (isEdit) { %>
                        <input type="hidden" name="id" value="<%= type.getId() %>">
                    <% } %>
                    
                    <div class="form-group">
                        <label for="libelle">Libellé:</label>
                        <input type="text" id="libelle" name="libelle" class="form-control" 
                               value="<%= isEdit ? type.getLibelle() : "" %>" required>
                    </div>
                    
                    <div class="form-actions">
                        <button type="submit" class="btn">
                            <%= isEdit ? "Mettre à jour" : "Ajouter" %>
                        </button>
                        <a href="${pageContext.request.contextPath}/typeDevis" class="btn">Annuler</a>
                    </div>
                </form>
            </div>
            
            <!-- Liste des types de devis existants -->
            <div class="list-container">
                <h3>Types de devis existants</h3>
                <% if (types != null && !types.isEmpty()) { %>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Libellé</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (TypeDevis t : types) { %>
                                <tr>
                                    <td><%= t.getId() %></td>
                                    <td><%= t.getLibelle() %></td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/typeDevis?action=edit&id=<%= t.getId() %>" class="btn">Modifier</a>
                                        <a href="${pageContext.request.contextPath}/typeDevis?action=delete&id=<%= t.getId() %>" class="btn" onclick="return confirm('Êtes-vous sûr ?')">Supprimer</a>
                                    </td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                <% } else { %>
                    <p>Aucun type de devis trouvé.</p>
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
