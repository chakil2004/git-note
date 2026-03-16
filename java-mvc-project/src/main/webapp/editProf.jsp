<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.example.model.Prof" %>
<%
    // Définir le titre de la page
    request.setAttribute("pageTitle", "Professeurs");
%>
<jsp:include page="includes/header.jsp"/>

<c:set var="isNew" value="${empty prof}" />
<h1>${isNew ? 'Ajouter un professeur' : 'Modifier professeur'}</h1>

<c:if test="${not empty message}">
    <div class="alert alert-success">${message}</div>
</c:if>

<form action="${pageContext.request.contextPath}/${isNew ? 'profs/create' : 'profs/edit'}" method="post">
    <c:if test="${not isNew}">
        <input type="hidden" name="id" value="${prof.id}" />
    </c:if>
    
    <div class="form-group">
        <label for="nom">Nom du professeur</label>
        <input type="text" id="nom" name="nom" value="${prof.nom}" required 
               placeholder="Entrez le nom du professeur"/>
    </div>
    
    <div class="form-actions">
        <a href="${pageContext.request.contextPath}/profs" class="btn btn-secondary">
            ❌ Annuler
        </a>
        <button type="submit" class="btn btn-primary">
            ${isNew ? '+ Ajouter' : '💾 Enregistrer'}
        </button>
    </div>
</form>

<jsp:include page="includes/footer.jsp"/>
