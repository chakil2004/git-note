<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.example.model.Solution" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Modifier solution</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>
<div class="container">
<c:set var="isNew" value="${empty solution}" />
<h1>${isNew ? 'Ajouter une solution' : 'Modifier solution'}</h1>

<c:if test="${not empty message}">
    <div style="padding:10px; border:1px solid #0a0; background:#dfd;">${message}</div>
</c:if>

<form action="${isNew ? 'solutions/create' : 'solutions/edit'}" method="post">
    <c:if test="${not isNew}">
        <input type="hidden" name="id" value="${solution.id}" />
    </c:if>
    <label>Valeur: <input type="text" name="stringValeur" value="${solution.stringValeur}" required/></label><br/>
    <label>Référence: <input type="text" name="ref" value="${solution.ref}" required/></label><br/>
    <button type="submit">${isNew ? 'Ajouter' : 'Enregistrer'}</button>
</form>

<p><a href="solutions">Retour à la liste</a></p>
</div>
</body>
</html>
