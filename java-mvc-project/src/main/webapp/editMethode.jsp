<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.example.model.Methode" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Modifier méthode</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>
<div class="container">
<c:set var="isNew" value="${empty methode}" />
<h1>${isNew ? 'Ajouter une méthode' : 'Modifier méthode'}</h1>

<c:if test="${not empty message}">
    <div style="padding:10px; border:1px solid #0a0; background:#dfd;">${message}</div>
</c:if>

<form action="${isNew ? 'methodes/create' : 'methodes/edit'}" method="post">
    <c:if test="${not isNew}">
        <input type="hidden" name="id" value="${methode.id}" />
    </c:if>
    <label>Valeur: <input type="text" name="stringValeur" value="${methode.stringValeur}" required/></label><br/>
    <label>Référence: <input type="text" name="ref" value="${methode.ref}" required/></label><br/>
    <button type="submit">${isNew ? 'Ajouter' : 'Enregistrer'}</button>
</form>

<p><a href="methodes">Retour à la liste</a></p>
</div>
</body>
</html>
