<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.model.Demande" %>
<%
    Demande demande = (Demande) request.getAttribute("demande");
    boolean isEdit = demande != null;
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PROJET2 - <%= isEdit ? "Modifier" : "Ajouter" %> une Demande</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <header class="header">
            <h1><%= isEdit ? "Modifier" : "Ajouter" %> une Demande</h1>
            <p><%= isEdit ? "Modifiez les informations de la demande" : "Entrez les informations de la nouvelle demande" %></p>
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
            <div class="form-container">
                <form action="${pageContext.request.contextPath}/demande" method="post" class="form">
                    <input type="hidden" name="action" value="<%= isEdit ? "update" : "add" %>">
                    <% if (isEdit) { %>
                        <input type="hidden" name="id" value="<%= demande.getId() %>">
                    <% } %>
                    
                    <div class="form-group">
                        <label for="clientId">Client:</label>
                        <select id="clientId" name="clientId" class="form-control" required>
                            <option value="">Sélectionner un client...</option>
                            <option value="1" <%= isEdit && demande.getClientId() == 1 ? "selected" : "" %>>Client 1</option>
                            <option value="2" <%= isEdit && demande.getClientId() == 2 ? "selected" : "" %>>Client 2</option>
                            <option value="3" <%= isEdit && demande.getClientId() == 3 ? "selected" : "" %>>Client 3</option>
                        </select>
                    </div>
                    
                    <% if (isEdit) { %>
                        <div class="form-group">
                            <label for="dateDemande">Date de la demande:</label>
                            <input type="datetime-local" id="dateDemande" name="dateDemande" class="form-control" 
                                   value="<%= demande.getDateDemande().toString().replace("T", " ") %>" required>
                        </div>
                    <% } %>
                    
                    <div class="form-group">
                        <label for="description">Description:</label>
                        <textarea id="description" name="description" class="form-control" rows="4" required><%= isEdit ? demande.getDescription() : "" %></textarea>
                    </div>
                    
                    <div class="form-group">
                        <label for="lieu">Lieu:</label>
                        <input type="text" id="lieu" name="lieu" class="form-control" 
                               value="<%= isEdit ? demande.getLieu() : "" %>" required>
                    </div>
                    
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            <%= isEdit ? "Mettre à jour" : "Ajouter" %>
                        </button>
                        <a href="${pageContext.request.contextPath}/demande" class="btn btn-secondary">Annuler</a>
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
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
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
            font-weight: 600;
            color: #333;
        }
        
        .form-control {
            padding: 0.75rem;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 1rem;
        }
        
        .form-control:focus {
            outline: none;
            border-color: #007bff;
            box-shadow: 0 0 0 2px rgba(0,123,255,0.25);
        }
        
        .form-actions {
            display: flex;
            gap: 1rem;
            justify-content: flex-end;
            margin-top: 1rem;
        }
        
        .btn {
            padding: 0.75rem 1.5rem;
            border: none;
            border-radius: 4px;
            text-decoration: none;
            cursor: pointer;
            font-size: 1rem;
        }
        
        .btn-primary {
            background: #007bff;
            color: white;
        }
        
        .btn-primary:hover {
            background: #0056b3;
        }
        
        .btn-secondary {
            background: #6c757d;
            color: white;
        }
        
        .btn-secondary:hover {
            background: #545b62;
        }
    </style>
</body>
</html>
