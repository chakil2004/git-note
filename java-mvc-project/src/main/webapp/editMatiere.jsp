<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.example.model.Matiere" %>
<%
    // Définir le titre de la page
    request.setAttribute("pageTitle", "Matières");
%>
<jsp:include page="includes/header.jsp"/>

<c:set var="isNew" value="${empty matiere}" />
<h1>${isNew ? 'Ajouter une matière' : 'Modifier matière'}</h1>

<c:if test="${not empty message}">
    <div class="alert alert-success">${message}</div>
</c:if>

<form action="${pageContext.request.contextPath}/${isNew ? 'matieres/create' : 'matieres/edit'}" method="post">
    <c:if test="${not isNew}">
        <input type="hidden" name="id" value="${matiere.id}" />
    </c:if>
    
    <div class="form-group">
        <label for="nom">Nom de la matière</label>
        <input type="text" id="nom" name="nom" value="${matiere.nom}" required 
               placeholder="Entrez le nom de la matière"/>
    </div>
    
    <div class="form-actions">
        <a href="${pageContext.request.contextPath}/matieres" class="btn btn-secondary">
            ❌ Annuler
        </a>
        <button type="submit" class="btn btn-primary">
            ${isNew ? '+ Ajouter' : '💾 Enregistrer'}
        </button>
    </div>
</form>

<jsp:include page="includes/footer.jsp"/>
