<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.example.model.Matiere" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Modifier matière</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>
<div class="container">
<c:set var="isNew" value="${empty matiere}" />
<h1>${isNew ? 'Ajouter une matière' : 'Modifier matière'}</h1>

<form action="${isNew ? '/matieres/create' : '/matieres/edit'}" method="post">
    <c:if test="${not isNew}">
        <input type="hidden" name="id" value="${matiere.id}" />
    </c:if>
    <label>Nom: <input type="text" name="nom" value="${matiere.nom}" required/></label>
    <button type="submit">${isNew ? 'Ajouter' : 'Enregistrer'}</button>
</form>

<p><a href="/matieres">Retour à la liste</a></p>
</div>
</body>
</html>
