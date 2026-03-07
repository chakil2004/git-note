<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Methode" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Gestion des méthodes</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>
<div class="container">
<h1>Gestion des méthodes</h1>

<c:if test="${not empty message}">
    <div style="padding:10px; border:1px solid #0a0; background:#dfd;">${message}</div>
</c:if>

<table border="1" cellpadding="6" cellspacing="0">
    <thead>
    <tr>
        <th>ID</th>
        <th>Valeur</th>
        <th>Référence</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="methode" items="${methodes}">
        <tr>
            <td>${methode.id}</td>
            <td>${methode.stringValeur}</td>
            <td>${methode.ref}</td>
            
        </tr>
    </c:forEach>
    </tbody>
</table>

<p><a href="index.jsp">Retour</a></p>
</div>
</body>
</html>
