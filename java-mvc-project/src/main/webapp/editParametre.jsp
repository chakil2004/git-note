<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.example.model.Parametre" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Modifier paramètre</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>
<div class="container">
<c:set var="isNew" value="${empty parametre}" />
<h1>${isNew ? 'Ajouter un paramètre' : 'Modifier paramètre'}</h1>

<c:if test="${not empty message}">
    <div style="padding:10px; border:1px solid #0a0; background:#dfd;">${message}</div>
</c:if>

<form action="${isNew ? '/parametres/create' : '/parametres/edit'}" method="post">
    <c:if test="${not isNew}">
        <input type="hidden" name="id" value="${parametre.id}" />
    </c:if>
    <label>Matière ID: <input type="number" name="matiereId" value="${parametre.matiereId}" required/></label><br/>
    <label>Méthode ID: <input type="number" name="methodeId" value="${parametre.methodeId}" required/></label><br/>
    <label>Solution ID: <input type="number" name="solutionId" value="${parametre.solutionId}" required/></label><br/>
    <label>Seuil: <input type="number" step="0.01" name="seuil" value="${parametre.seuil}" required/></label><br/>
    <button type="submit">${isNew ? 'Ajouter' : 'Enregistrer'}</button>
</form>

<p><a href="/parametres">Retour à la liste</a></p>
</div>
</body>
</html>
