<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.DemandeStatut" %>
<%
    List<DemandeStatut> statuts = (List<DemandeStatut>) request.getAttribute("statuts");
    Integer demandeId = (Integer) request.getAttribute("demandeId");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PROJET2 - Statuts de Demande</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <header class="header">
            <h1>Statuts de la Demande #<%= demandeId != null ? demandeId : "" %></h1>
            <p>Historique des changements de statut</p>
        </header>
        
        <nav class="nav">
            <a href="${pageContext.request.contextPath}/">Accueil</a>
            <a href="${pageContext.request.contextPath}/demande">Demandes</a>
            <a href="${pageContext.request.contextPath}/statut">Statuts</a>
        </nav>
        
        <main class="main">         
            <div class="table-container">
                <h2>Historique des statuts</h2>
                <% if (statuts != null && !statuts.isEmpty()) { %>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>ID Demande</th>
                                <th>Statut</th>
                                <th>Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (DemandeStatut statut : statuts) { %>
                                <tr>
                                    <td><%= statut.getId() %></td>
                                    <td><%= statut.getDemandeId() %></td>
                                    <td><%= statut.getStatut().getLibelle() %></td>
                                    <td><%= statut.getDate() %></td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                <% } else { %>
                    <div class="alert alert-info">
                        Aucun statut trouvé pour cette demande.
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
        }
        
        .form-inline .form-group {
            margin-bottom: 0;
        }
        
        .form-inline .form-control {
            width: 200px;
        }
        
        .btn {
            padding: 0.75rem 1.5rem;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
        }
        
        .btn-primary {
            background: #007bff;
            color: white;
        }
        
        .btn-primary:hover {
            background: #0056b3;
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
