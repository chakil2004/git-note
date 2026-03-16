<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.example.model.Etudiant" %>
<%
    // Définir le titre de la page
    request.setAttribute("pageTitle", "Étudiants");
%>
<jsp:include page="includes/header.jsp"/>

<c:set var="isNew" value="${empty etudiant}" />
<h1>${isNew ? 'Ajouter un étudiant' : 'Modifier étudiant'}</h1>

<c:if test="${not empty message}">
    <div class="alert alert-success">${message}</div>
</c:if>

<form action="${pageContext.request.contextPath}/${isNew ? 'etudiants/create' : 'etudiants/edit'}" method="post">
    <c:if test="${not isNew}">
        <input type="hidden" name="id" value="${etudiant.id}" />
    </c:if>
    
    <div class="form-group">
        <label for="nom">Nom de l'étudiant</label>
        <input type="text" id="nom" name="nom" value="${etudiant.nom}" required 
               placeholder="Entrez le nom de l'étudiant"/>
    </div>
    
    <div class="form-actions">
        <a href="${pageContext.request.contextPath}/etudiants" class="btn btn-secondary">
            ❌ Annuler
        </a>
        <button type="submit" class="btn btn-primary">
            ${isNew ? '+ Ajouter' : '💾 Enregistrer'}
        </button>
    </div>
</form>

<jsp:include page="includes/footer.jsp"/>
