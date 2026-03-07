<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Prof" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Gestion des profs</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>
<div class="container">
<h1>Gestion des profs</h1>

<p><a href="/profs/create">Ajouter un prof</a></p>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="prof" items="${profs}">
        <tr>
            <td>${prof.id}</td>
            <td>${prof.nom}</td>
            <td>
                <a href="/profs/edit?id=${prof.id}">Modifier</a>
                <a href="/profs/delete?id=${prof.id}" onclick="return confirm('Supprimer ?');">Supprimer</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<p><a href="index.jsp">Retour</a></p>
</div>
</body>
</html>
