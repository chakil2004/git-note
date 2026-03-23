<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.model.Travaux" %>
<%
    Travaux travaux = (Travaux) request.getAttribute("travaux");
    boolean isEdit = travaux != null;
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PROJET2 - <%= isEdit ? "Modifier" : "Ajouter" %> des Travaux</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <header class="header">
            <h1><%= isEdit ? "Modifier" : "Ajouter" %> des Travaux</h1>
            <p><%= isEdit ? "Modifiez les informations des travaux" : "Entrez les informations des nouveaux travaux" %></p>
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
            <div class="form-container">
                <form action="${pageContext.request.contextPath}/travaux" method="post" class="form">
                    <input type="hidden" name="action" value="<%= isEdit ? "update" : "add" %>">
                    <% if (isEdit) { %>
                        <input type="hidden" name="id" value="<%= travaux.getId() %>">
                    <% } %>
                    
                    <div class="form-group">
                        <label for="demandeId">Demande:</label>
                        <select id="demandeId" name="demandeId" class="form-control" required>
                            <option value="">Sélectionner une demande...</option>
                            <option value="1" <%= isEdit && travaux.getDemandeId() == 1 ? "selected" : "" %>>Demande 1</option>
                            <option value="2" <%= isEdit && travaux.getDemandeId() == 2 ? "selected" : "" %>>Demande 2</option>
                            <option value="3" <%= isEdit && travaux.getDemandeId() == 3 ? "selected" : "" %>>Demande 3</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="statutTravauxId">Statut des travaux:</label>
                        <select id="statutTravauxId" name="statutTravauxId" class="form-control" required>
                            <option value="">Sélectionner un statut...</option>
                            <option value="1" <%= isEdit && travaux.getStatutTravauxId() == 1 ? "selected" : "" %>>Non commencé</option>
                            <option value="2" <%= isEdit && travaux.getStatutTravauxId() == 2 ? "selected" : "" %>>En cours</option>
                            <option value="3" <%= isEdit && travaux.getStatutTravauxId() == 3 ? "selected" : "" %>>Terminé</option>
                        </select>
                    </div>
                    
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            <%= isEdit ? "Mettre à jour" : "Ajouter" %>
                        </button>
                        <a href="${pageContext.request.contextPath}/travaux" class="btn btn-secondary">Annuler</a>
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
            max-width: 500px;
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
