<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.example.model.Etudiant" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Modifier étudiant</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>
<div class="container">
<c:set var="isNew" value="${empty etudiant}" />
<h1>${isNew ? 'Ajouter un étudiant' : 'Modifier étudiant'}</h1>

<%--
  Attendu :
  - attribut request "etudiant" contient l'objet Etudiant à modifier
--%>

<c:if test="${not empty message}">
    <div style="padding:10px; border:1px solid #0a0; background:#dfd;">${message}</div>
</c:if>

<c:set var="isNew" value="${empty etudiant}" />

<form action="${isNew ? 'etudiants/create' : 'etudiants/edit'}" method="post">
    <c:if test="${not isNew}">
        <input type="hidden" name="id" value="${etudiant.id}" />
    </c:if>
    <label>Nom: <input type="text" name="nom" value="${etudiant.nom}" required/></label>
    <button type="submit">${isNew ? 'Ajouter' : 'Enregistrer'}</button>
</form>

<p><a href="etudiants">Retour à la liste</a></p>
</div>
</body>
</html>
