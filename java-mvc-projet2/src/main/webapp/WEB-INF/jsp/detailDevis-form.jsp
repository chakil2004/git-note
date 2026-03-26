<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.model.DetailDevis" %>
<%
    DetailDevis detail = (DetailDevis) request.getAttribute("detail");
    boolean isEdit = detail != null;
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PROJET2 - <%= isEdit ? "Modifier" : "Ajouter" %> un Détail de Devis</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <header class="header">
            <h1><%= isEdit ? "Modifier" : "Ajouter" %> un Détail de Devis</h1>
            <p><%= isEdit ? "Modifiez les informations du détail" : "Entrez les informations du nouveau détail" %></p>
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
            <div class="form-container">
                <form action="${pageContext.request.contextPath}/detailDevis" method="post" class="form">
                    <input type="hidden" name="action" value="<%= isEdit ? "update" : "add" %>">
                    <% if (isEdit) { %>
                        <input type="hidden" name="id" value="<%= detail.getId() %>">
                    <% } %>
                    
                    <div class="form-group">
                        <label for="devisId">Devis:</label>
                        <select id="devisId" name="devisId" class="form-control" required>
                            <option value="">Sélectionner un devis...</option>
                            <option value="1" <%= isEdit && detail.getDevisId() == 1 ? "selected" : "" %>>Devis 1</option>
                            <option value="2" <%= isEdit && detail.getDevisId() == 2 ? "selected" : "" %>>Devis 2</option>
                            <option value="3" <%= isEdit && detail.getDevisId() == 3 ? "selected" : "" %>>Devis 3</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="libelle">Libellé:</label>
                        <input type="text" id="libelle" name="libelle" class="form-control" 
                               value="<%= isEdit ? detail.getLibelle() : "" %>" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="prixUnitaire">Prix Unitaire (€):</label>
                        <input type="number" id="prixUnitaire" name="prixUnitaire" step="0.01" class="form-control" 
                               value="<%= isEdit ? detail.getPrixUnitaire() : "" %>" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="quantite">Quantité:</label>
                        <input type="number" id="quantite" name="quantite" class="form-control" 
                               value="<%= isEdit ? detail.getQuantite() : "" %>" required>
                    </div>
                    
                    <div class="form-actions">
                        <button type="submit" class="btn">
                            <%= isEdit ? "Mettre à jour" : "Ajouter" %>
                        </button>
                        <a href="${pageContext.request.contextPath}/detailDevis" class="btn">Annuler</a>
                    </div>
                </form>
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
    </style>
</body>
</html>
