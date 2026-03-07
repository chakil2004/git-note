<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.example.model.Note" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Modifier note</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>
<div class="container">
<c:set var="isNew" value="${empty note}" />
<h1>${isNew ? 'Ajouter une note' : 'Modifier note'}</h1>

<c:if test="${not empty message}">
    <div style="padding:10px; border:1px solid #0a0; background:#dfd;">${message}</div>
</c:if>

<form action="${pageContext.request.contextPath}/${isNew ? 'notes/create' : 'notes/edit'}" method="post">
    <c:if test="${not isNew}">
        <input type="hidden" name="etudiantId" value="${note.etudiantId}" />
        <input type="hidden" name="profId" value="${note.profId}" />
        <input type="hidden" name="matiereId" value="${note.matiereId}" />
    </c:if>
    <label>Etudiant ID: <input type="number" name="etudiantId" value="${note.etudiantId}" required/></label><br/>
    <label>Prof ID: <input type="number" name="profId" value="${note.profId}" required/></label><br/>
    <label>Matière ID: <input type="number" name="matiereId" value="${note.matiereId}" required/></label><br/>
    <label>Valeur: <input type="number" step="0.01" name="valeur" value="${note.valeur}" required/></label><br/>
    <button type="submit">${isNew ? 'Ajouter' : 'Enregistrer'}</button>
</form>

<p><a href="${pageContext.request.contextPath}/notes">Retour à la liste</a></p>
</div>
</body>
</html>
