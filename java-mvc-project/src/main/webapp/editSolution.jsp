<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.example.model.Solution" %>
<%
    // Définir le titre de la page
    request.setAttribute("pageTitle", "Solutions");
%>
<jsp:include page="includes/header.jsp"/>

<c:set var="isNew" value="${empty solution}" />
<h1>${isNew ? 'Ajouter une solution' : 'Modifier solution'}</h1>

<c:if test="${not empty message}">
    <div class="alert alert-success">${message}</div>
</c:if>

<form action="${pageContext.request.contextPath}/solutions/${isNew ? 'create' : 'edit'}" method="post">
    <c:if test="${not isNew}">
        <input type="hidden" name="id" value="${solution.id}" />
    </c:if>
    
    <div class="form-group">
        <label for="stringValeur">Valeur de la solution</label>
        <input type="text" id="stringValeur" name="stringValeur" value="${solution.stringValeur}" required 
               placeholder="Entrez la valeur de la solution"/>
    </div>
    
    <div class="form-group">
        <label for="ref">Référence</label>
        <input type="text" id="ref" name="ref" value="${solution.ref}" required 
               placeholder="Entrez la référence"/>
    </div>
    
    <div class="form-actions">
        <a href="${pageContext.request.contextPath}/solutions" class="btn btn-secondary">
            ❌ Annuler
        </a>
        <button type="submit" class="btn btn-primary">
            ${isNew ? '+ Ajouter' : '💾 Enregistrer'}
        </button>
    </div>
</form>

<jsp:include page="includes/footer.jsp"/>
