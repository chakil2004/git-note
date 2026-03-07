<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.example.model.Prof" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Modifier prof</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>
<div class="container">
<c:set var="isNew" value="${empty prof}" />
<h1>${isNew ? 'Ajouter un prof' : 'Modifier prof'}</h1>

<form action="${isNew ? '/profs/create' : '/profs/edit'}" method="post">
    <c:if test="${not isNew}">
        <input type="hidden" name="id" value="${prof.id}" />
    </c:if>
    <label>Nom: <input type="text" name="nom" value="${prof.nom}" required/></label>
    <button type="submit">${isNew ? 'Ajouter' : 'Enregistrer'}</button>
</form>

<p><a href="/profs">Retour à la liste</a></p>
</div>
</body>
</html>
