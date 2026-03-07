<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Note" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Gestion des notes</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>
<div class="container">
<h1>Gestion des notes</h1>

<c:if test="${not empty message}">
    <div style="padding:10px; border:1px solid #0a0; background:#dfd;">${message}</div>
</c:if>

<table border="1" cellpadding="6" cellspacing="0">
    <thead>
    <tr>
        <th>Etudiant ID</th>
        <th>Prof ID</th>
        <th>Matière ID</th>
        <th>Valeur</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="note" items="${notes}">
        <tr>
            <td>${note.etudiantId}</td>
            <td>${note.profId}</td>
            <td>${note.matiereId}</td>
            <td>${note.valeur}</td>
            <td>
                <a href="${pageContext.request.contextPath}/notes/edit?etudiantId=${note.etudiantId}&profId=${note.profId}&matiereId=${note.matiereId}">Modifier</a>
                <a href="${pageContext.request.contextPath}/notes/delete?etudiantId=${note.etudiantId}&profId=${note.profId}&matiereId=${note.matiereId}" onclick="return confirm('Supprimer ?');">Supprimer</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<h2>Ajouter une note</h2>
<p><a href="${pageContext.request.contextPath}/notes/create">Ajouter une note</a></p>

<p><a href="index.jsp">Retour</a></p>
</div>
</body>
</html>
