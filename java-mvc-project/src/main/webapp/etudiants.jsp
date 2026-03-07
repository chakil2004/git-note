<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Etudiant" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Gestion des étudiants</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>
<div class="container">
<h1>Gestion des étudiants</h1>

<%--
  Attendu :
  - attribut request "etudiants" contient List<Etudiant>
  - attribut request "message" contient message de statut
--%>

<c:if test="${not empty message}">
    <div style="padding:10px; border:1px solid #0a0; background:#dfd;">${message}</div>
</c:if>

<table border="1" cellpadding="6" cellspacing="0">
    <thead>
    <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="etudiant" items="${etudiants}">
        <tr>
            <td>${etudiant.id}</td>
            <td>${etudiant.nom}</td>
            <td>
                <a href="etudiants/edit?id=${etudiant.id}">Modifier</a>
                <a href="etudiants/delete?id=${etudiant.id}" onclick="return confirm('Supprimer ?');">Supprimer</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<h2>Ajouter un étudiant</h2>
<p><a href="etudiants/create">Ajouter un étudiant</a></p>

<p><a href="index.jsp">Retour</a></p>
</div>
</body>
</html>
