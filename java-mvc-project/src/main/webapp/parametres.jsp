<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Parametre" %>
<%@ page import="com.example.model.Matiere" %>
<%@ page import="com.example.model.Methode" %>
<%@ page import="com.example.model.Solution" %>
<%@ page import="java.util.Collections" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Gestion des paramètres</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>
<div class="container">
<h1>Gestion des paramètres</h1>

<%!
    // Fonction pour trouver le nom d'une matière par son ID
    private String findMatiereName(int matiereId, List<Matiere> matieres) {
        if (matieres == null) return String.valueOf(matiereId);
        for (Matiere m : matieres) {
            if (m.getId() == matiereId) {
                return m.getNom();
            }
        }
        return String.valueOf(matiereId);
    }
    
    // Fonction pour trouver le nom d'une méthode par son ID
    private String findMethodeName(int methodeId, List<Methode> methodes) {
        if (methodes == null) return String.valueOf(methodeId);
        for (Methode m : methodes) {
            if (m.getId() == methodeId) {
                return m.getStringValeur();
            }
        }
        return String.valueOf(methodeId);
    }
    
    // Fonction pour trouver le nom d'une solution par son ID
    private String findSolutionName(int solutionId, List<Solution> solutions) {
        if (solutions == null) return String.valueOf(solutionId);
        for (Solution s : solutions) {
            if (s.getId() == solutionId) {
                return s.getStringValeur();
            }
        }
        return String.valueOf(solutionId);
    }
%>

<%
    // Récupérer les listes depuis la requête
    List<Matiere> matieres = (List<Matiere>) request.getAttribute("matieres");
    List<Methode> methodes = (List<Methode>) request.getAttribute("methodes");
    List<Solution> solutions = (List<Solution>) request.getAttribute("solutions");
%>

<c:if test="${not empty message}">
    <div style="padding:10px; border:1px solid #0a0; background:#dfd;">${message}</div>
</c:if>

<table border="1" cellpadding="6" cellspacing="0">
    <thead>
    <tr>
        <th>ID</th>
        <th>Matière</th>
        <th>Méthode</th>
        <th>Solution</th>
        <th>Seuil</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
    List<Parametre> parametres = (List<Parametre>) request.getAttribute("parametres");
    if (parametres != null) {
        for (Parametre param : parametres) {
%>
        <tr>
            <td><%= param.getId() %></td>
            <td><%= findMatiereName(param.getMatiereId(), matieres) %></td>
            <td><%= findMethodeName(param.getMethodeId(), methodes) %></td>
            <td><%= findSolutionName(param.getSolutionId(), solutions) %></td>
            <td><%= param.getSeuil() %></td>
            <td>
                <a href="/parametres/edit?id=<%= param.getId() %>">Modifier</a>
                <a href="/parametres/delete?id=<%= param.getId() %>" onclick="return confirm('Supprimer ?');">Supprimer</a>
            </td>
        </tr>
<%
        }
    }
%>
    </tbody>
</table>

<h2>Ajouter un paramètre</h2>
<p><a href="/parametres/create">Ajouter un paramètre</a></p>

<p><a href="index.jsp">Retour</a></p>
</div>
</body>
</html>
