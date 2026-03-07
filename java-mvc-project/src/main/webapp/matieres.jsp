<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Matiere" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Gestion des matières</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>
<div class="container">
<h1>Gestion des matières</h1>

<p><a href="/matieres/create">Ajouter une matière</a></p>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="matiere" items="${matieres}">
        <tr>
            <td>${matiere.id}</td>
            <td>${matiere.nom}</td>
            <td>
                <a href="/matieres/edit?id=${matiere.id}">Modifier</a>
                <a href="/matieres/delete?id=${matiere.id}" onclick="return confirm('Supprimer ?');">Supprimer</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<p><a href="index.jsp">Retour</a></p>
</div>
</body>
</html>
